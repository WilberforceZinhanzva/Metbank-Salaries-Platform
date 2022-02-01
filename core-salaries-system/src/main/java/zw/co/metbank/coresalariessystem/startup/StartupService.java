package zw.co.metbank.coresalariessystem.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.entities.*;
import zw.co.metbank.coresalariessystem.models.enums.Permissions;
import zw.co.metbank.coresalariessystem.models.enums.Roles;
import zw.co.metbank.coresalariessystem.repositories.PermissionRepository;
import zw.co.metbank.coresalariessystem.repositories.RoleRepository;
import zw.co.metbank.coresalariessystem.repositories.UserRepository;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StartupService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${storage.uploads.files}")
    private String uploadsDirectory;



    public void init(){
        log.info("Initializing the system");
        createRoles();
        createPermissions();
        createSuperAdmin();
        setUpUploadsDirectory();
        log.info("Initialization finished");
    }

    public void createRoles(){
        if(!roleRepository.existsByNameIgnoreCase(Roles.SUPER_ADMIN.toString()))
            roleRepository.save(new Role(Roles.SUPER_ADMIN.toString()));
        if(!roleRepository.existsByNameIgnoreCase(Roles.LITE_ADMIN.toString()))
            roleRepository.save(new Role(Roles.LITE_ADMIN.toString()));
        if(!roleRepository.existsByNameIgnoreCase(Roles.SUPER_CLIENT.toString()))
            roleRepository.save(new Role(Roles.SUPER_CLIENT.toString()));
        if(!roleRepository.existsByNameIgnoreCase(Roles.LITE_CLIENT.toString()))
            roleRepository.save(new Role(Roles.LITE_CLIENT.toString()));
        if(!roleRepository.existsByNameIgnoreCase(Roles.SUPER_BANK_USER.toString()))
            roleRepository.save(new Role(Roles.SUPER_BANK_USER.toString()));
        if(!roleRepository.existsByNameIgnoreCase(Roles.LITE_BANK_USER.toString()))
            roleRepository.save(new Role(Roles.LITE_BANK_USER.toString()));
        log.info("Roles up to date!");
    }
    public void createPermissions(){


        for(Permissions permissions : new ArrayList<Permissions>(EnumSet.allOf(Permissions.class))){
            if(!permissionRepository.existsByNameIgnoreCase(permissions.name)){
                permissionRepository.save(new Permission(permissions.name,permissions.description));
            }
        }

        log.info("Permissions up to date!");
    }

    public void createSuperAdmin(){
        log.info("Creating super admin");
        if(!userRepository.existsByUsernameIgnoreCase("admin@metbank.co.zw")){

            User admin = new User();
            admin.setId(GlobalMethods.generateId("ADMIN"));
            admin.setUsername("admin@metbank.co.zw");
            admin.setPassword(passwordEncoder.encode("m3tb@nk888"));
            admin.setAccountLocked(false);
            admin.setPasswordRequired(false);

            Optional<Role> role = roleRepository.findByName(Roles.SUPER_ADMIN.name());
            if(role.isEmpty())
                throw new ResourceNotFoundException("Role "+ Roles.SUPER_ADMIN.name()+ " not found");
            admin.getRoles().add(role.get());

            List<Permission> permissions = permissionRepository.findAll();
            admin.getPermissions().addAll(permissions);

            //[PROFILE]
            AdminProfile adminProfile = new AdminProfile();
            adminProfile.setId(GlobalMethods.generateId("PRFL"));
            adminProfile.setFullName("Super Admin");
            adminProfile.setDepartment("Default");
            adminProfile.setUser(admin);

            admin.setProfile(adminProfile);

            //[LOGGING]
            UserActionLogger actionLogger = new UserActionLogger();
            actionLogger.setId(GlobalMethods.generateId("LOG"));
            actionLogger.setAction("Auto Registration");
            actionLogger.setActionDoneBy("System");
            actionLogger.setActorId("System");
            actionLogger.setActionDoneAt(LocalDateTime.now());
            actionLogger.setUser(admin);

            admin.getActionsLogs().add(actionLogger);

            //[SAVING TO DB]
            userRepository.save(admin);



        }
        log.info("Finished creating super admin");
    }

    public void setUpUploadsDirectory(){
        try {

            if(!new File(uploadsDirectory).exists()){
                Files.createDirectories(Path.of(uploadsDirectory));
                log.info("Created uploads directory");
            }


        } catch (IOException e) {
            log.warn(e.getMessage());
            e.printStackTrace();

        }
    }
}
