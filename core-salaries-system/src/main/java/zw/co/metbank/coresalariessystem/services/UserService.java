package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.InvalidPasswordException;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferablePermission;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableRole;
import zw.co.metbank.coresalariessystem.models.entities.Permission;
import zw.co.metbank.coresalariessystem.models.entities.Role;
import zw.co.metbank.coresalariessystem.models.entities.User;
import zw.co.metbank.coresalariessystem.models.extras.PasswordConfirmation;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.repositories.PermissionRepository;
import zw.co.metbank.coresalariessystem.repositories.RoleRepository;
import zw.co.metbank.coresalariessystem.repositories.UserRepository;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public Boolean deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        userRepository.delete(user.get());
        return true;
    }

    public Boolean lockAccount(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        user.get().setAccountLocked(true);
        userRepository.save(user.get());
        return true;
    }

    public Boolean unlockAccount(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        user.get().setAccountLocked(false);
        userRepository.save(user.get());
        return true;
    }


    public Transferable changePassword(String newPassword){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(authenticatedUser.getUserId());
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");

        String encodedPassword = passwordEncoder.encode(newPassword);

        if(passwordEncoder.matches(user.get().getUsername(),encodedPassword))
            throw new InvalidPasswordException("Password cannot be similar to your username!");
        if(newPassword.length() < 8)
            throw new InvalidPasswordException("Password must be at least 8 characters long!");

        user.get().setPassword(encodedPassword);
        user.get().setPasswordRequired(false);

        return userRepository.save(user.get()).serializeForTransfer();

    }

    public PasswordConfirmation confirmPassword(String password){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(authenticatedUser.getUserId());
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");


        if(passwordEncoder.matches(password,user.get().getPassword()))
            return new PasswordConfirmation(true);
        else
            return new PasswordConfirmation(false);
    }


    public List<TransferableRole> userRoles(String userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        List<Role> roles = user.get().getRoles();
        return roles.stream().map(Role::serializeForTransfer).collect(Collectors.toList());
    }

    public List<TransferablePermission> userPermissions(String userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        List<Permission> permissions = user.get().getPermissions();
        return permissions.stream().map(Permission::serializeForTransfer).collect(Collectors.toList());
    }

    public Transferable revokePermissions(String userId,List<String> permissions){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");

        List<Permission> requiredPermissions = permissionRevoker.apply(permissions,user.get().getPermissions());

        user.get().setPermissions(requiredPermissions);

        //user.get().getPermissions().stream().forEach(p-> System.out.println(p.getName()));


        return userRepository.save(user.get()).serializeForTransfer();
    }

    public Transferable revokeRoles(String userId, List<String> roleNames){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        List<Role> requiredRoles = rolesRevoker.apply(roleNames,user.get().getRoles());
        user.get().setRoles(requiredRoles);
        return userRepository.save(user.get()).serializeForTransfer();
    }

    public Transferable addRoles(String userId, List<String> roleNames){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");

        List<Role> roles = roleRepository.findByNameIn(roleNames);

        List<Role> explicitNewRoles = rolesRestructure.apply(user.get().getRoles(),roles);
        user.get().getRoles().addAll(explicitNewRoles);

        return userRepository.save(user.get()).serializeForTransfer();


    }

    public Transferable addPermissions(String userId, List<String> permissionNames){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");

        List<Permission> permissions = permissionRepository.findByNameIn(permissionNames);
        List<Permission> explicitNewPermissions = permissionsRestructure.apply(user.get().getPermissions(),permissions);
        user.get().getPermissions().addAll(explicitNewPermissions);
        return userRepository.save(user.get()).serializeForTransfer();

    }


    //[Functional Api]

    private BiFunction<List<String>,List<Permission>,List<Permission>> permissionRevoker = (namesList, assignedPermissions)->{
//        return assignedPermissions.stream().dropWhile(p ->{
//            return namesList.stream().anyMatch(s->s.contentEquals(p.getName()));
//        }).collect(Collectors.toList());

        return assignedPermissions.stream().filter(p ->{
            return !namesList.stream().anyMatch(s->s.contentEquals(p.getName()));
        }).collect(Collectors.toList());
    };

    private BiFunction<List<String>, List<Role>,List<Role>> rolesRevoker = (namesList, assignedRoles)->{
//        return assignedRoles.stream().dropWhile(r->{
//            return namesList.stream().anyMatch(s->s.contentEquals(r.getName()));
//        }).collect(Collectors.toList());

        return assignedRoles.stream().filter(r ->{
            return !namesList.stream().anyMatch(s->s.contentEquals(r.getName()));
        }).collect(Collectors.toList());
    };

    private BiFunction<List<Role>, List<Role>, List<Role>> rolesRestructure = (currentRoles, newRoles)->{
        return newRoles.stream().dropWhile(nr -> {
            return currentRoles.stream().anyMatch(cr-> cr.getName().contentEquals(nr.getName()));
        }).collect(Collectors.toList());
    };

    private BiFunction<List<Permission>, List<Permission>, List<Permission>> permissionsRestructure = (currentPermissions, newPermissions)->{
        return newPermissions.stream().dropWhile(np ->{
           return currentPermissions.stream().anyMatch(cp->cp.getName().contentEquals(np.getName()));
        }).collect(Collectors.toList());
    };
}
