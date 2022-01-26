package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableClient;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClient;
import zw.co.metbank.coresalariessystem.models.enums.ClientsSearchKey;
import zw.co.metbank.coresalariessystem.security.AuthenticatedUser;
import zw.co.metbank.coresalariessystem.services.ClientsService;

@RestController
@RequestMapping("/api/v1/users/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_LITE_ADMIN')")
    public ResponseEntity<Page<TransferableClient>> clients(@RequestParam ClientsSearchKey searchKey,@RequestParam String searchParam,@RequestParam int page ,@RequestParam int pageSize){
        Page<TransferableClient> resultPage = clientsService.clients(searchKey, searchParam, page, pageSize);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Register Clients')")
    public ResponseEntity<TransferableClient> newClient(@RequestBody ConsumableClient consumable){
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransferableClient result = clientsService.newClient(consumable,authenticatedUser);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
