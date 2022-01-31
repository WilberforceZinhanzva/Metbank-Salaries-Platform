package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.InvalidConsumableException;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableAdmin;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableAdmin;
import zw.co.metbank.coresalariessystem.models.entities.*;
import zw.co.metbank.coresalariessystem.models.enums.AdminsSearchKey;
import zw.co.metbank.coresalariessystem.models.enums.Permissions;
import zw.co.metbank.coresalariessystem.models.enums.Roles;
import zw.co.metbank.coresalariessystem.projections.IdsOnly;
import zw.co.metbank.coresalariessystem.repositories.AdminProfileRepository;
import zw.co.metbank.coresalariessystem.repositories.PermissionRepository;
import zw.co.metbank.coresalariessystem.repositories.RoleRepository;
import zw.co.metbank.coresalariessystem.repositories.UserRepository;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private AdminProfileRepository adminProfileRepository;



    public Page<TransferableAdmin> admins(AdminsSearchKey searchKey, String searchParam, int page, int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<User> usersPage = new PageImpl<>(new ArrayList<>(), pageable, 0);

        Optional<Role> adminRole = roleRepository.findByName("LITE_ADMIN");
        Optional<Role> adminRole2 = roleRepository.findByName("SUPER_ADMIN");
        List<Role> rolesList = new ArrayList<>();
        if(adminRole.isPresent()) rolesList.add(adminRole.get());
        if(adminRole2.isPresent()) rolesList.add(adminRole2.get());

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
                Page<IdsOnly> profileIds = adminProfileRepository.findByFullNameContainingIgnoreCase(searchParam,pageable);
                List<String> serializedIds = profileIds.getContent().stream().map(IdsOnly::getId).collect(Collectors.toList());
                usersPage = userRepository.findByProfile_IdIn(serializedIds,pageable);
                break;
            case DEPARTMENT:
                Page<IdsOnly> profileIds2 = adminProfileRepository.findByDepartmentContainingIgnoreCase(searchParam,pageable);
                List<String> serializedIds2 = profileIds2.getContent().stream().map(IdsOnly::getId).collect(Collectors.toList());
                usersPage = userRepository.findByProfile_IdIn(serializedIds2,pageable);
                break;
            case PERMISSION:
                Optional<Permission> permission = permissionRepository.findByName(searchParam);
                if(permission.isPresent()){
                    usersPage = userRepository.findByRolesInAndPermissionsIn(rolesList,Arrays.asList(permission.get()),pageable);
                }
                break;
            default:
                usersPage = userRepository.findByRolesIn(rolesList,pageable);
                break;
        }

        List<TransferableAdmin> serializedList = usersPage.getContent().stream().map(user ->{
            return (TransferableAdmin)user.serializeForTransfer();
        }).collect(Collectors.toList());

        Page<TransferableAdmin> serializedPage = new PageImpl<>(serializedList,pageable,usersPage.getTotalElements());
        return serializedPage;
    }

    public TransferableAdmin newAdmin(ConsumableAdmin consumable, StreamlinedAuthenticatedUser authenticatedUser){

        String actor = authenticatedUser.getFullname();
        String actorId = authenticatedUser.getUserId();

        ValidityChecker vc = consumable.checkValidity();
        if(!vc.isValid())
            throw new InvalidConsumableException(vc.getMessage());

        User admin = new User();
        admin.setId(GlobalMethods.generateId("ADMIN"));
        admin.setUsername(consumable.getUsername());
        admin.setPassword(consumable.getUsername());
        admin.setAccountLocked(false);


        Optional<Role> role = roleRepository.findByName(consumable.getRole());
        if(role.isEmpty())
            throw new ResourceNotFoundException("Role "+ consumable.getRole()+ " not found");
        admin.getRoles().add(role.get());

        List<Permission> permissions = permissionRepository.findAll();
        if(role.get().getName().contentEquals(Roles.SUPER_ADMIN.name())){
            admin.getPermissions().addAll(permissions);
        }
        else if(role.get().getName().contentEquals(Roles.LITE_ADMIN.name())){
            List<String> excludedPermissions = Arrays.asList(
                    Permissions.DeleteAdmins.name(),
                    Permissions.DeleteBanks.name(),
                    Permissions.DeleteClients.name(),
                    Permissions.InitiateSalaryRequest.name(),
                    Permissions.AuthorizeSalaryRequest.name()
            );
            List<Permission> litePermissions = permissions.stream().dropWhile(p->{
                return excludedPermissions.contains(p.getName());
            }).collect(Collectors.toList());
            admin.getPermissions().addAll(litePermissions);
        }

        //[PROFILE]
        AdminProfile adminProfile = new AdminProfile();
        adminProfile.setId(GlobalMethods.generateId("PRFL"));
        adminProfile.setFullName(consumable.getFullName());
        adminProfile.setDepartment(consumable.getDepartment());
        adminProfile.setUser(admin);

        admin.setProfile(adminProfile);

        //[LOGGING]
        UserActionLogger actionLogger = new UserActionLogger();
        actionLogger.setId(GlobalMethods.generateId("LOG"));
        actionLogger.setAction("Registration");
        actionLogger.setActionDoneBy(actor);
        actionLogger.setActorId(actorId);
        actionLogger.setActionDoneAt(LocalDateTime.now());
        actionLogger.setUser(admin);

        admin.getActionsLogs().add(actionLogger);

        //[SAVING TO DB]
        return (TransferableAdmin) userRepository.save(admin).serializeForTransfer();

    }
}
