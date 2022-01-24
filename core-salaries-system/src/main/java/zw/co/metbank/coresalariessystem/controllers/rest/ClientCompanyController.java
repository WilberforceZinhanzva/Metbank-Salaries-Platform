package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableClientCompany;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClientCompany;
import zw.co.metbank.coresalariessystem.services.ClientCompanyService;

import java.security.Principal;

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
    @PreAuthorize("hasAuthority('RegisterClients')")
    public ResponseEntity<TransferableClientCompany> newClientCompany(@RequestBody ConsumableClientCompany consumable, Principal principal){
        TransferableClientCompany clientCompany = clientCompanyService.newClientCompany(consumable,principal);
        return new ResponseEntity<>(clientCompany,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DeleteClients')")
    public ResponseEntity<TransferableClientCompany> deleteClientCompany(@PathVariable("id") String id){
        TransferableClientCompany clientCompany = clientCompanyService.deleteClientCompany(id);
        return new ResponseEntity<>(clientCompany,HttpStatus.OK);
    }

}
