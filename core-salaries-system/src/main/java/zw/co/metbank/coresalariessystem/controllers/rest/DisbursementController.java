package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableDisbursementInput;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableFileBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableInputBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.enums.SalaryDisbursementRequestSearchKey;
import zw.co.metbank.coresalariessystem.services.DisbursementService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/disbursements/requests")
public class DisbursementController {

    @Autowired
    private DisbursementService disbursementService;


    @GetMapping("/file-based")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_LITE_ADMIN')")
    public ResponseEntity<Page<TransferableFileBasedSalaryDisbursementRequest>> fileBasedRequests(@RequestParam int page ,@RequestParam int pageSize,@RequestParam SalaryDisbursementRequestSearchKey searchKey,@RequestParam String searchParam){
        Page<TransferableFileBasedSalaryDisbursementRequest> resultPage = disbursementService.fileBasedRequests(page, pageSize, searchKey, searchParam);
        return new ResponseEntity<>(resultPage,HttpStatus.OK);
    }

    @GetMapping("/input-based")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_LITE_ADMIN')")
    public ResponseEntity<Page<TransferableInputBasedSalaryDisbursementRequest>> inputBasedRequests(int page , int pageSize, SalaryDisbursementRequestSearchKey searchKey, String searchParam){
        Page<TransferableInputBasedSalaryDisbursementRequest> resultPage = disbursementService.inputBasedRequests(page, pageSize, searchKey, searchParam);
        return new ResponseEntity<>(resultPage,HttpStatus.OK);
    }

    @PostMapping(value = "/file-based", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('InitiateSalaryRequest')")
    public ResponseEntity<TransferableFileBasedSalaryDisbursementRequest> disbursementRequest(@RequestPart MultipartFile multipartFile, Principal principal){
        TransferableFileBasedSalaryDisbursementRequest request = disbursementService.disbursementRequest(multipartFile, principal);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping("/input-based")
    public ResponseEntity<TransferableInputBasedSalaryDisbursementRequest> disbursementRequest(@RequestBody List<ConsumableDisbursementInput> consumable, Principal principal){
        TransferableInputBasedSalaryDisbursementRequest request = disbursementService.disbursementRequest(consumable, principal);
        return new ResponseEntity<>(request,HttpStatus.OK);
    }
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Boolean> deleteDisbursementRequest(@PathVariable("requestId") String requestId){
        Boolean isDeleted = disbursementService.deleteDisbursementRequest(requestId);
        return new ResponseEntity<>(isDeleted,HttpStatus.OK);
    }
}
