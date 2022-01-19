package zw.co.metbank.coresalariessystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferablePermission;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableRole;
import zw.co.metbank.coresalariessystem.services.RolesAndPermissionsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles-and-permissions")
public class RolesAndPermissionsController {

    @Autowired
    private RolesAndPermissionsService rolesAndPermissionsService;


    @GetMapping("/roles")
    public ResponseEntity<List<TransferableRole>> roles(){
        return new ResponseEntity<>(rolesAndPermissionsService.roles(), HttpStatus.OK);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<TransferablePermission>> permissions(){
        return new ResponseEntity<>(rolesAndPermissionsService.permissions(),HttpStatus.OK);
    }
}
