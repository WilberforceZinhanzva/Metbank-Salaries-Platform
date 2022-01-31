package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableDisbursementInput;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableFileBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableInputBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;
import zw.co.metbank.coresalariessystem.models.enums.SalaryDisbursementRequestSearchKey;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;
import zw.co.metbank.coresalariessystem.services.DisbursementService;

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

    @GetMapping("/file-based/company-specific")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_CLIENT','ROLE_LITE_CLIENT')")
    public ResponseEntity<Page<TransferableFileBasedSalaryDisbursementRequest>> fileBasedCompanyRequests(@RequestParam int page ,@RequestParam int pageSize,@RequestParam SalaryDisbursementRequestSearchKey searchKey,@RequestParam String searchParam){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<TransferableFileBasedSalaryDisbursementRequest> resultPage = disbursementService.fileBasedRequests(page, pageSize, searchKey, searchParam,authenticatedUser);
        return new ResponseEntity<>(resultPage,HttpStatus.OK);
    }

    @GetMapping("/input-based/company-specific")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_CLIENT','ROLE_LITE_CLIENT')")
    public ResponseEntity<Page<TransferableInputBasedSalaryDisbursementRequest>> inputBasedCompanyRequests(int page , int pageSize, SalaryDisbursementRequestSearchKey searchKey, String searchParam){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Page<TransferableInputBasedSalaryDisbursementRequest> resultPage = disbursementService.inputBasedRequests(page, pageSize, searchKey, searchParam,authenticatedUser);
        return new ResponseEntity<>(resultPage,HttpStatus.OK);
    }



    @PostMapping(value = "/file-based", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('Initiate Salary Request')")
    public ResponseEntity<TransferableFileBasedSalaryDisbursementRequest> disbursementRequest(@RequestPart MultipartFile multipartFile){
       StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransferableFileBasedSalaryDisbursementRequest request = disbursementService.disbursementRequest(multipartFile, authenticatedUser);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping("/input-based")
    @PreAuthorize("hasAuthority('Initiate Salary Request')")
    public ResponseEntity<TransferableInputBasedSalaryDisbursementRequest> disbursementRequest(@RequestBody List<ConsumableDisbursementInput> consumable){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransferableInputBasedSalaryDisbursementRequest request = disbursementService.disbursementRequest(consumable, authenticatedUser);
        return new ResponseEntity<>(request,HttpStatus.OK);
    }
    @DeleteMapping("/{requestId}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') AND hasAuthority('Delete Salary Request')")
    public ResponseEntity<Boolean> deleteDisbursementRequest(@PathVariable("requestId") String requestId){
        Boolean isDeleted = disbursementService.deleteDisbursementRequest(requestId);
        return new ResponseEntity<>(isDeleted,HttpStatus.OK);
    }

    //[REQUEST PROCESSING]

    @PutMapping("/{requestId}/authorize")
    @PreAuthorize("hasAuthority('Authorize Salary Request')")
    public ResponseEntity<Transferable> authorizeRequest(@PathVariable("requestId") String requestId){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transferable result = disbursementService.authorizeRequest(requestId, DisbursementRequestProcessing.AUTHORIZED, authenticatedUser);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/{requestId}/review")
    @PreAuthorize("hasAuthority('Review Salary Request')")
    public ResponseEntity<Transferable> reviewRequest(@PathVariable("requestId") String requestId){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transferable result = disbursementService.reviewRequest(requestId, DisbursementRequestProcessing.REVIEWED, authenticatedUser);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/{requestId}/approve")
    @PreAuthorize("hasAuthority('Approve Salary Request')")
    public ResponseEntity<Transferable> approveRequest(@PathVariable("requestId") String requestId){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transferable result = disbursementService.approveRequest(requestId, DisbursementRequestProcessing.APPROVED, authenticatedUser);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/{requestId}/decline")
    @PreAuthorize("hasAuthority('Decline Salary Request')")
    public ResponseEntity<Transferable> declineRequest(@PathVariable("requestId") String requestId){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transferable result = disbursementService.declineRequest(requestId,DisbursementRequestProcessing.DECLINED,authenticatedUser);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
