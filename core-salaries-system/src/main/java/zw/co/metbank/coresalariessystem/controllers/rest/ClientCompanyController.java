package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableClientCompany;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClientCompany;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;
import zw.co.metbank.coresalariessystem.services.ClientCompanyService;

@RestController
@RequestMapping("/api/v1/client-companies")
public class ClientCompanyController {

    @Autowired
    private ClientCompanyService clientCompanyService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_LITE_ADMIN')")
    public ResponseEntity<Page<TransferableClientCompany>> clientCompanies(@RequestParam int page, @RequestParam int pageSize){
        Page<TransferableClientCompany> resultPage = clientCompanyService.clientCompanies(page, pageSize);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Register Clients')")
    public ResponseEntity<TransferableClientCompany> newClientCompany(@RequestBody ConsumableClientCompany consumable){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransferableClientCompany clientCompany = clientCompanyService.newClientCompany(consumable,authenticatedUser);
        return new ResponseEntity<>(clientCompany,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Delete Clients')")
    public ResponseEntity<TransferableClientCompany> deleteClientCompany(@PathVariable("id") String id){
        TransferableClientCompany clientCompany = clientCompanyService.deleteClientCompany(id);
        return new ResponseEntity<>(clientCompany,HttpStatus.OK);
    }

}
