package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableBankCompany;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableBankCompany;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;
import zw.co.metbank.coresalariessystem.services.BankCompanyService;

@RestController
@RequestMapping("/api/v1/bank-companies")
public class BankCompanyController {

    @Autowired
    private BankCompanyService bankCompanyService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_LITE_ADMIN')")
    public ResponseEntity<Page<TransferableBankCompany>> bankCompanies(@RequestParam int page,@RequestParam int pageSize){
        Page<TransferableBankCompany> resultPage = bankCompanyService.bankCompanies(page, pageSize);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('RegisterBanks')")
    public ResponseEntity<TransferableBankCompany> newBankCompany(@RequestBody ConsumableBankCompany consumable){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransferableBankCompany result = bankCompanyService.newBankCompany(consumable,authenticatedUser);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DeleteBanks')")
    public ResponseEntity<TransferableBankCompany> deleteBankCompany(@PathVariable("id") String id){
        TransferableBankCompany result = bankCompanyService.deleteBankCompany(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
