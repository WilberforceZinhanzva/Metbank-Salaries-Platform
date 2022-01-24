package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableAdmin;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableAdmin;
import zw.co.metbank.coresalariessystem.models.enums.AdminsSearchKey;
import zw.co.metbank.coresalariessystem.services.AdminsService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminsController {

    @Autowired
    private AdminsService adminsService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_LITE_ADMIN')")
    public ResponseEntity<Page<TransferableAdmin>> admins(@RequestParam AdminsSearchKey searchKey,@RequestParam String searchParam,@RequestParam int page,@RequestParam int pageSize){
        Page<TransferableAdmin> resultPage = adminsService.admins(searchKey, searchParam, page, pageSize);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasPermission('RegisterAdmins')")
    public ResponseEntity<TransferableAdmin> newAdmin(@RequestBody ConsumableAdmin consumable, Principal principal){
        TransferableAdmin result = adminsService.newAdmin(consumable,principal);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
