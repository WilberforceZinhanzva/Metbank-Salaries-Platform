package zw.co.metbank.coresalariessystem.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.models.entities.Permission;
import zw.co.metbank.coresalariessystem.models.entities.Role;
import zw.co.metbank.coresalariessystem.models.enums.Permissions;
import zw.co.metbank.coresalariessystem.models.enums.Roles;
import zw.co.metbank.coresalariessystem.repositories.PermissionRepository;
import zw.co.metbank.coresalariessystem.repositories.RoleRepository;

@Slf4j
@Service
public class StartupService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public void init(){
        log.info("Initializing the system");
        createRoles();
        createPermissions();
        createSuperAdmin();
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
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.RegisterClients.name))
            permissionRepository.save(new Permission(Permissions.RegisterClients.name, Permissions.RegisterClients.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.RegisterAdmins.name))
            permissionRepository.save(new Permission(Permissions.RegisterAdmins.name,Permissions.RegisterAdmins.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.RegisterBanks.name))
            permissionRepository.save(new Permission(Permissions.RegisterBanks.name,Permissions.RegisterAdmins.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.DeleteClients.name))
            permissionRepository.save(new Permission(Permissions.DeleteClients.name,Permissions.DeleteClients.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.DeleteAdmins.name))
            permissionRepository.save(new Permission(Permissions.DeleteAdmins.name,Permissions.DeleteAdmins.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.DeleteBanks.name))
            permissionRepository.save(new Permission(Permissions.DeleteBanks.name,Permissions.DeleteBanks.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.ChangeUserPermissions.name))
            permissionRepository.save(new Permission(Permissions.ChangeUserPermissions.name,Permissions.ChangeUserPermissions.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.InitiateSalaryRequest.name))
            permissionRepository.save(new Permission(Permissions.InitiateSalaryRequest.name,Permissions.ChangeUserPermissions.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.AuthorizeSalaryRequest.name))
            permissionRepository.save(new Permission(Permissions.AuthorizeSalaryRequest.name,Permissions.AuthorizeSalaryRequest.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.ReviewSalaryRequest.name))
            permissionRepository.save(new Permission(Permissions.ReviewSalaryRequest.name,Permissions.ReviewSalaryRequest.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.ApproveSalaryRequest.name))
            permissionRepository.save(new Permission(Permissions.ApproveSalaryRequest.name,Permissions.ApproveSalaryRequest.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.DeclineSalaryRequest.name))
            permissionRepository.save(new Permission(Permissions.DeclineSalaryRequest.name,Permissions.DeclineSalaryRequest.description));
        if(!permissionRepository.existsByNameIgnoreCase(Permissions.DownloadFiles.name))
            permissionRepository.save(new Permission(Permissions.DownloadFiles.name,Permissions.DownloadFiles.description));

        log.info("Permissions up to date!");
    }

    public void createSuperAdmin(){

    }
}
