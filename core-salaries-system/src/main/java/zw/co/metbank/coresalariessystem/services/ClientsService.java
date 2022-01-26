package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.InvalidConsumableException;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableClient;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClient;
import zw.co.metbank.coresalariessystem.models.entities.*;
import zw.co.metbank.coresalariessystem.models.enums.ClientsSearchKey;
import zw.co.metbank.coresalariessystem.models.enums.Permissions;
import zw.co.metbank.coresalariessystem.models.enums.Roles;
import zw.co.metbank.coresalariessystem.projections.IdsOnly;
import zw.co.metbank.coresalariessystem.repositories.*;
import zw.co.metbank.coresalariessystem.security.AuthenticatedUser;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Autowired
    private ClientProfileRepository clientProfileRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public Page<TransferableClient> clients(ClientsSearchKey searchKey, String searchParam, int page , int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<User> usersPage = new PageImpl<>(new ArrayList<>(),pageable,0);

        Optional<Role> superClientRole = roleRepository.findByName("SUPER_CLIENT");
        Optional<Role> liteClientRole = roleRepository.findByName("LITE_CLIENT");
        List<Role> rolesList = new ArrayList<>();
        if(superClientRole.isPresent()) rolesList.add(superClientRole.get());
        if(liteClientRole.isPresent()) rolesList.add(liteClientRole.get());

        switch(searchKey){

            case USERNAME:
                usersPage = userRepository.findByRolesInAndUsernameContainingIgnoreCase(rolesList,searchParam,pageable);
                break;
            case LOCKED_ACCOUNT:
                usersPage = userRepository.findByRolesInAndAccountLocked(rolesList,true,pageable);
                break;
            case UNLOCKED_ACCOUNT:
                usersPage = userRepository.findByRolesInAndAccountLocked(rolesList,false,pageable);
                break;
            case FULLNAME:
                Page<IdsOnly> profileIds =clientProfileRepository.findByFullNameContainingIgnoreCase(searchParam,pageable);
                List<String> serializedIds = profileIds.getContent().stream().map(IdsOnly::getId).collect(Collectors.toList());
                usersPage = userRepository.findByProfile_IdIn(serializedIds,pageable);
                break;
            case EMAIL:
                Page<IdsOnly> profileIds2 = clientProfileRepository.findByEmailContainingIgnoreCase(searchParam,pageable);
                List<String> serializedIds2 = profileIds2.getContent().stream().map(IdsOnly::getId).collect(Collectors.toList());
                usersPage = userRepository.findByProfile_IdIn(serializedIds2,pageable);
                break;
            case PHONE:
                Page<IdsOnly> profileIds3 = clientProfileRepository.findByPhoneNumberContainingIgnoreCase(searchParam,pageable);
                List<String> serializedIds3 = profileIds3.getContent().stream().map(IdsOnly::getId).collect(Collectors.toList());
                usersPage = userRepository.findByProfile_IdIn(serializedIds3,pageable);
                break;
            case COMPANY:
                Page<IdsOnly> profileIds4 = clientProfileRepository.findByClientCompany_NameContainingIgnoreCase(searchParam,pageable);
                List<String> serializedIds4 = profileIds4.getContent().stream().map(IdsOnly::getId).collect(Collectors.toList());
                usersPage = userRepository.findByProfile_IdIn(serializedIds4,pageable);
                break;
            case PERMISSION:
                Optional<Permission> permission = permissionRepository.findByName(searchParam);
                if(permission.isPresent()){
                    usersPage = userRepository.findByRolesInAndPermissionsIn(rolesList, Arrays.asList(permission.get()),pageable);
                }
                break;
            default:
                usersPage = userRepository.findByRolesIn(rolesList,pageable);
                break;
        }

        List<TransferableClient> serializedList =  usersPage.getContent().stream().map(user->{
            return (TransferableClient)user.serializeForTransfer();
        }).collect(Collectors.toList());

        Page<TransferableClient> serializedPage = new PageImpl<>(serializedList,pageable,usersPage.getTotalElements());
        return serializedPage;
    }

    public TransferableClient newClient(ConsumableClient consumable, AuthenticatedUser authenticatedUser){


        String actor = authenticatedUser.getFullname();
        String actorId = authenticatedUser.getUserId();


        ValidityChecker vc = consumable.checkValidity();
        if(!vc.isValid())
            throw new InvalidConsumableException(vc.getMessage());

        User client = new User();
        client.setId(GlobalMethods.generateId("CLIENT"));
        client.setUsername(consumable.getUsername());
        client.setPassword(passwordEncoder.encode(consumable.getUsername()));
        client.setAccountLocked(false);

        Optional<Role> role = roleRepository.findByName(consumable.getRole());
        if(role.isEmpty())
            throw new ResourceNotFoundException("Role "+ consumable.getRole()+ " could not be found!");
        client.getRoles().add(role.get());

        if(role.get().getName().contentEquals(Roles.SUPER_CLIENT.name())){

            Optional<Permission> p1 = permissionRepository.findByName(Permissions.RegisterClients.name());
            if(p1.isPresent())
                client.getPermissions().add(p1.get());

            Optional<Permission> p2 = permissionRepository.findByName(Permissions.AuthorizeSalaryRequest.name);
            if(p2.isPresent())
                client.getPermissions().add(p2.get());



        }
        else if(role.get().getName().contentEquals(Roles.LITE_CLIENT.name())){

            Optional<Permission> p3 = permissionRepository.findByName(Permissions.InitiateSalaryRequest.name);
            if(p3.isPresent())
                client.getPermissions().add(p3.get());
        }

        //[PROFILE]
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setId(GlobalMethods.generateId("PRFL"));

        Optional<ClientCompany> clientCompany = clientCompanyRepository.findById(consumable.getCompanyId());
        if(clientCompany.isEmpty())
            throw new ResourceNotFoundException("Client company not found");

        clientProfile.setClientCompany(clientCompany.get());
        clientProfile.setEmail(consumable.getEmail());
        clientProfile.setFullName(consumable.getFullName());
        clientProfile.setPhoneNumber(consumable.getPhoneNumber());
        clientProfile.setUser(client);

        client.setProfile(clientProfile);

        //[LOGGING]
        UserActionLogger actionLogger = new UserActionLogger();
        actionLogger.setId(GlobalMethods.generateId("LOG"));
        actionLogger.setAction("Registration");
        actionLogger.setActionDoneAt(LocalDateTime.now());
        actionLogger.setActionDoneBy(actor);
        actionLogger.setActorId(actorId);
        actionLogger.setUser(client);

        client.getActionsLogs().add(actionLogger);

        //[SAVING TO DB]
        return (TransferableClient) userRepository.save(client).serializeForTransfer();
    }

}
