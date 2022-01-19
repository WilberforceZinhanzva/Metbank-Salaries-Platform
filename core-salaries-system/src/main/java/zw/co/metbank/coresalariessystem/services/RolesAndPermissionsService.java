package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferablePermission;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableRole;
import zw.co.metbank.coresalariessystem.models.entities.Permission;
import zw.co.metbank.coresalariessystem.models.entities.Role;
import zw.co.metbank.coresalariessystem.repositories.PermissionRepository;
import zw.co.metbank.coresalariessystem.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolesAndPermissionsService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;


    public List<TransferableRole> roles(){
        return roleRepository.findAll().stream().map(Role::serializeForTransfer).collect(Collectors.toList());
    }

    public List<TransferablePermission> permissions(){
        return permissionRepository.findAll().stream().map(Permission::serializeForTransfer).collect(Collectors.toList());
    }

}
