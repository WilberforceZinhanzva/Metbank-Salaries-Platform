package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zw.co.metbank.coresalariessystem.exceptions.FileCreationException;
import zw.co.metbank.coresalariessystem.exceptions.InvalidConsumableException;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.exceptions.SalaryProcessingException;
import zw.co.metbank.coresalariessystem.files.FileInfo;
import zw.co.metbank.coresalariessystem.files.LocalStorageFileManager;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableDisbursementInput;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableFileBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableInputBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.entities.*;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;
import zw.co.metbank.coresalariessystem.models.enums.SalaryDisbursementRequestSearchKey;
import zw.co.metbank.coresalariessystem.models.extras.SalaryRequestCsvEntry;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.repositories.ClientCompanyRepository;
import zw.co.metbank.coresalariessystem.repositories.FileBasedSalaryDisbursementRequestRepository;
import zw.co.metbank.coresalariessystem.repositories.InputBasedSalaryDisbursementRequestRepository;
import zw.co.metbank.coresalariessystem.repositories.SalaryDisbursementRequestRepository;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisbursementService {

    @Autowired
    private SalaryDisbursementRequestRepository salaryDisbursementRequestRepository;
    @Autowired
    private InputBasedSalaryDisbursementRequestRepository inputBasedSalaryDisbursementRequestRepository;
    @Autowired
    private FileBasedSalaryDisbursementRequestRepository fileBasedSalaryDisbursementRequestRepository;
    @Autowired
    private LocalStorageFileManager localStorageFileManager;
    @Autowired
    private SalaryCsvFilesHandlerService salaryCsvFilesHandlerService;
    @Autowired
    private ClientCompanyRepository clientCompanyRepository;



    public Page<TransferableFileBasedSalaryDisbursementRequest> fileBasedRequests(int page , int pageSize, SalaryDisbursementRequestSearchKey searchKey, String searchParam){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<FileBasedSalaryDisbursementRequest> resultPage = new PageImpl<>(new ArrayList<>(),pageable,0);
        switch(searchKey){
            case TIME_FRAME:
                break;
            case STAGE:
                resultPage = fileBasedSalaryDisbursementRequestRepository.findByCurrentStage(DisbursementRequestProcessing.valueOf(searchParam),pageable);
                break;
            case INITIATOR:
               resultPage= fileBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.INITIATED,pageable);
                break;
            case AUTHORIZER:
                resultPage= fileBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.AUTHORIZED,pageable);
                break;
            case REVIEWER:
                resultPage= fileBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.REVIEWED,pageable);
                break;
            case APPROVER:
                resultPage= fileBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.APPROVED,pageable);
                break;
            case COMPANY:
                resultPage = fileBasedSalaryDisbursementRequestRepository.findByCompany_NameContainingIgnoreCase(searchParam,pageable);
                break;
            default:
                resultPage = fileBasedSalaryDisbursementRequestRepository.findAll(pageable);
                break;
        }

        List<TransferableFileBasedSalaryDisbursementRequest> serializedList = resultPage.getContent().stream().map(FileBasedSalaryDisbursementRequest::serializeForTransfer).collect(Collectors.toList());
        Page<TransferableFileBasedSalaryDisbursementRequest> serializedPage = new PageImpl<>(serializedList,pageable,resultPage.getTotalElements());
        return serializedPage;
    }

    public Page<TransferableInputBasedSalaryDisbursementRequest> inputBasedRequests(int page , int pageSize, SalaryDisbursementRequestSearchKey searchKey, String searchParam){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<InputBasedSalaryDisbursementRequest> resultPage = new PageImpl<>(new ArrayList<>(),pageable,0);
        switch(searchKey){
            case TIME_FRAME:
                break;
            case STAGE:
                resultPage = inputBasedSalaryDisbursementRequestRepository.findByCurrentStage(DisbursementRequestProcessing.valueOf(searchParam),pageable);
                break;
            case INITIATOR:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.INITIATED,pageable);
                break;
            case AUTHORIZER:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.AUTHORIZED,pageable);
                break;
            case REVIEWER:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.REVIEWED,pageable);
                break;
            case APPROVER:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(searchParam,DisbursementRequestProcessing.APPROVED,pageable);
                break;
            case COMPANY:
                resultPage = inputBasedSalaryDisbursementRequestRepository.findByCompany_NameContainingIgnoreCase(searchParam,pageable);
                break;
            default:
                resultPage = inputBasedSalaryDisbursementRequestRepository.findAll(pageable);
                break;
        }

        List<TransferableInputBasedSalaryDisbursementRequest> serializedList = resultPage.getContent().stream().map(InputBasedSalaryDisbursementRequest::serializeForTransfer).collect(Collectors.toList());
        Page<TransferableInputBasedSalaryDisbursementRequest> serializedPage = new PageImpl<>(serializedList,pageable,resultPage.getTotalElements());
        return serializedPage;
    }

    public TransferableFileBasedSalaryDisbursementRequest disbursementRequest(MultipartFile multipartFile, StreamlinedAuthenticatedUser authenticatedUser){

        String initiatorId = authenticatedUser.getUserId();
        String initiatorName = authenticatedUser.getFullname();

        //[FETCH COMPANY]

        Optional<ClientCompany>company =clientCompanyRepository.findById(authenticatedUser.findInfoByKey("companyId"));
        if(company.isEmpty())
            throw new ResourceNotFoundException("Company not found!");

        FileBasedSalaryDisbursementRequest request = new FileBasedSalaryDisbursementRequest();
        request.setId(GlobalMethods.generateId("SALREQF"));
        request.setPlacedOn(LocalDateTime.now());
        request.setCurrentStage(DisbursementRequestProcessing.INITIATED);
        request.setCompany(company.get());

        FileInfo fileInfo = new FileInfo();
        try {
            fileInfo = localStorageFileManager.saveFile(GlobalMethods.generateFilename(request.getId()),multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DisbursementFile disbursementFile = new DisbursementFile();
        disbursementFile.setId(GlobalMethods.generateId("DISBFILE"));
        disbursementFile.setFileSize(fileInfo.getFileSize());
        disbursementFile.setFilePath(fileInfo.getFilePath());
        disbursementFile.setOriginalFileName(fileInfo.getOriginalFileName());
        disbursementFile.setDisbursementRequest(request);

        request.setDisbursementFile(disbursementFile);


        DisbursementProcessLogger logger = new DisbursementProcessLogger();
        logger.setStage(DisbursementRequestProcessing.INITIATED);
        logger.setDoneBy(initiatorName);
        logger.setActorId(initiatorId);
        logger.setDoneAt(LocalDateTime.now());
        logger.setDisbursementRequest(request);

        request.getActionLogging().add(logger);

        return fileBasedSalaryDisbursementRequestRepository.save(request).serializeForTransfer();

    }

    public TransferableInputBasedSalaryDisbursementRequest disbursementRequest(List<ConsumableDisbursementInput> consumable, StreamlinedAuthenticatedUser authenticatedUser){

        String initiatorId = authenticatedUser.getUserId();
        String initiatorName = authenticatedUser.getFullname();

        //[FETCH COMPANY]
        Optional<ClientCompany> company =clientCompanyRepository.findById(authenticatedUser.findInfoByKey("companyId"));
        if(company.isEmpty())
            throw new ResourceNotFoundException("Company not found");

        InputBasedSalaryDisbursementRequest request = new InputBasedSalaryDisbursementRequest();
        request.setId(GlobalMethods.generateId("SALREQI"));
        request.setPlacedOn(LocalDateTime.now());
        request.setCurrentStage(DisbursementRequestProcessing.INITIATED);
        request.setCompany(company.get());

        for(ConsumableDisbursementInput disbursementInput: consumable){
            ValidityChecker vc = disbursementInput.checkValidity();
            if(!vc.isValid())
                throw new InvalidConsumableException(vc.getMessage());

            DisbursementInput i = new DisbursementInput();
            i.setDate(LocalDate.now().format(GlobalMethods.dayMonthYearFormatter()));
            i.setRemitterAccountNumber(disbursementInput.getRemitterAccountNumber());
            i.setBeneficiaryBankName(disbursementInput.getBeneficiaryBankName());
            i.setBeneficiaryBankCode(disbursementInput.getBeneficiaryBankCode());
            i.setBeneficiaryAccountNumber(disbursementInput.getBeneficiaryAccountNumber());
            i.setBeneficiaryName(disbursementInput.getBeneficiaryName());
            i.setAmount(disbursementInput.getAmount());
            i.setCurrency(disbursementInput.getCurrency());
            i.setReference(disbursementInput.getReference());
            i.setRemitterName(disbursementInput.getRemitterName());
            i.setSalaryDisbursementRequest(request);

            request.getDisbursementInputs().add(i);
        }

        List<SalaryRequestCsvEntry> csvEntryList = new ArrayList<>();
        csvEntryList = request.getDisbursementInputs().stream().map(input ->{
            SalaryRequestCsvEntry entry = new SalaryRequestCsvEntry();
            entry.setDate(input.getDate());
            entry.setAmount(input.getAmount());
            entry.setBeneficiaryAccountNumber(input.getBeneficiaryAccountNumber());
            entry.setBeneficiaryBankCode(input.getBeneficiaryBankCode());
            entry.setBeneficiaryBankName(input.getBeneficiaryBankName());
            entry.setBeneficiaryName(input.getBeneficiaryName());
            entry.setCurrency(input.getCurrency());
            entry.setReference(input.getReference());
            entry.setRemitterAccountNumber(input.getRemitterAccountNumber());
            entry.setRemitterName(input.getRemitterName());
            return entry;
        }).collect(Collectors.toList());
        FileInfo fileInfo = salaryCsvFilesHandlerService.createSalariesFile(request.getId(),csvEntryList);

        if(Optional.ofNullable(fileInfo).isEmpty())
            throw new FileCreationException("Error creating a csv salaries file");


        GeneratedSalariesFile generatedSalariesFile = new GeneratedSalariesFile();
        generatedSalariesFile.setId(GlobalMethods.generateId("GSALFILE"));
        generatedSalariesFile.setFilePath(fileInfo.getFilePath());
        generatedSalariesFile.setFileSize(fileInfo.getFileSize());
        generatedSalariesFile.setRequest(request);

        request.setGeneratedSalariesFile(generatedSalariesFile);


        DisbursementProcessLogger logger = new DisbursementProcessLogger();
        logger.setStage(DisbursementRequestProcessing.INITIATED);
        logger.setDoneBy(initiatorName);
        logger.setActorId(initiatorId);
        logger.setDoneAt(LocalDateTime.now());
        logger.setDisbursementRequest(request);

        request.getActionLogging().add(logger);

        return inputBasedSalaryDisbursementRequestRepository.save(request).serializeForTransfer();

    }

    public Boolean deleteDisbursementRequest(String requestId){
        Optional<SalaryDisbursementRequest> request = salaryDisbursementRequestRepository.findById(requestId);
        if(request.isEmpty())
            throw new ResourceNotFoundException("Disbursement request not found!");
        salaryDisbursementRequestRepository.delete(request.get());

        if(request.get() instanceof InputBasedSalaryDisbursementRequest){
            InputBasedSalaryDisbursementRequest inputReq = ((InputBasedSalaryDisbursementRequest) request.get());
            localStorageFileManager.deleteFile(inputReq.getGeneratedSalariesFile().getFilePath());

        }
        if(request.get() instanceof FileBasedSalaryDisbursementRequest){
            FileBasedSalaryDisbursementRequest fileReq = (FileBasedSalaryDisbursementRequest) request.get();
            localStorageFileManager.deleteFile(fileReq.getDisbursementFile().getFilePath());
        }
        return true;
    }

    //[CLIENT COMPANY SPECIFIC REQUESTS]

    public Page<TransferableFileBasedSalaryDisbursementRequest> fileBasedRequests(int page , int pageSize, SalaryDisbursementRequestSearchKey searchKey, String searchParam,StreamlinedAuthenticatedUser authenticatedUser){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<FileBasedSalaryDisbursementRequest> resultPage = new PageImpl<>(new ArrayList<>(),pageable,0);
        switch(searchKey){
            case TIME_FRAME:
                break;
            case STAGE:
                resultPage = fileBasedSalaryDisbursementRequestRepository.findByCompany_IdAndCurrentStage(authenticatedUser.findInfoByKey("companyId"),DisbursementRequestProcessing.valueOf(searchParam),pageable);
                break;
            case INITIATOR:
                resultPage= fileBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.INITIATED,pageable);
                break;
            case AUTHORIZER:
                resultPage= fileBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.AUTHORIZED,pageable);
                break;
            case REVIEWER:
                resultPage= fileBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.REVIEWED,pageable);
                break;
            case APPROVER:
                resultPage= fileBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.APPROVED,pageable);
                break;
            default:
                resultPage = fileBasedSalaryDisbursementRequestRepository.findByCompany_Id(authenticatedUser.findInfoByKey("companyId"),pageable);
                break;
        }

        List<TransferableFileBasedSalaryDisbursementRequest> serializedList = resultPage.getContent().stream().map(FileBasedSalaryDisbursementRequest::serializeForTransfer).collect(Collectors.toList());
        Page<TransferableFileBasedSalaryDisbursementRequest> serializedPage = new PageImpl<>(serializedList,pageable,resultPage.getTotalElements());
        return serializedPage;
    }
    public Page<TransferableInputBasedSalaryDisbursementRequest> inputBasedRequests(int page , int pageSize, SalaryDisbursementRequestSearchKey searchKey, String searchParam,StreamlinedAuthenticatedUser authenticatedUser){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<InputBasedSalaryDisbursementRequest> resultPage = new PageImpl<>(new ArrayList<>(),pageable,0);
        switch(searchKey){
            case TIME_FRAME:
                break;
            case STAGE:
                resultPage = inputBasedSalaryDisbursementRequestRepository.findByCompany_IdAndCurrentStage(authenticatedUser.findInfoByKey("companyId"),DisbursementRequestProcessing.valueOf(searchParam),pageable);
                break;
            case INITIATOR:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.INITIATED,pageable);
                break;
            case AUTHORIZER:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.AUTHORIZED,pageable);
                break;
            case REVIEWER:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.REVIEWED,pageable);
                break;
            case APPROVER:
                resultPage= inputBasedSalaryDisbursementRequestRepository.findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(authenticatedUser.findInfoByKey("companyId"),searchParam,DisbursementRequestProcessing.APPROVED,pageable);
                break;
            default:
                resultPage = inputBasedSalaryDisbursementRequestRepository.findByCompany_Id(authenticatedUser.findInfoByKey("companyId"),pageable);
                break;
        }

        List<TransferableInputBasedSalaryDisbursementRequest> serializedList = resultPage.getContent().stream().map(InputBasedSalaryDisbursementRequest::serializeForTransfer).collect(Collectors.toList());
        Page<TransferableInputBasedSalaryDisbursementRequest> serializedPage = new PageImpl<>(serializedList,pageable,resultPage.getTotalElements());
        return serializedPage;
    }







    //[REQUEST PROCESSING]
    private Transferable changeDisbursementRequestStatus(SalaryDisbursementRequest request,DisbursementRequestProcessing newStatus, StreamlinedAuthenticatedUser authenticatedUser){


        String actor = authenticatedUser.getFullname();
        String actorId = authenticatedUser.getUserId();


        request.setCurrentStage(newStatus);

        DisbursementProcessLogger logger = new DisbursementProcessLogger();
        logger.setId(GlobalMethods.generateId("LOG"));
        logger.setDisbursementRequest(request);
        logger.setDoneAt(LocalDateTime.now());
        logger.setActorId(actorId);
        logger.setDoneBy(actor);
        logger.setStage(newStatus);

        request.getActionLogging().add(logger);

        SalaryDisbursementRequest savedRequest = salaryDisbursementRequestRepository.save(request);

        if(savedRequest instanceof FileBasedSalaryDisbursementRequest)
            return ((FileBasedSalaryDisbursementRequest) savedRequest) .serializeForTransfer();
        else
            return ((InputBasedSalaryDisbursementRequest) savedRequest) .serializeForTransfer();

    }

    public Transferable authorizeRequest(String requestId,DisbursementRequestProcessing newStatus, StreamlinedAuthenticatedUser authenticatedUser){
        Optional<SalaryDisbursementRequest> request = salaryDisbursementRequestRepository.findById(requestId);

        if(request.isEmpty())
           throw new ResourceNotFoundException("Salary disbursement request not found!");

        if(request.get().getCurrentStage() != DisbursementRequestProcessing.INITIATED)
            throw new SalaryProcessingException("Salary request cannot be authorized at this stage");

        return changeDisbursementRequestStatus(request.get(),DisbursementRequestProcessing.AUTHORIZED,authenticatedUser);
    }
    public Transferable reviewRequest(String requestId,DisbursementRequestProcessing newStatus, StreamlinedAuthenticatedUser authenticatedUser){
        Optional<SalaryDisbursementRequest> request = salaryDisbursementRequestRepository.findById(requestId);

        if(request.isEmpty())
            throw new ResourceNotFoundException("Salary disbursement request not found!");

        if(request.get().getCurrentStage() != DisbursementRequestProcessing.AUTHORIZED)
            throw new SalaryProcessingException("Salary request cannot be reviewed at this stage");

        return changeDisbursementRequestStatus(request.get(),DisbursementRequestProcessing.REVIEWED,authenticatedUser);
    }
    public Transferable approveRequest(String requestId,DisbursementRequestProcessing newStatus, StreamlinedAuthenticatedUser authenticatedUser){
        Optional<SalaryDisbursementRequest> request = salaryDisbursementRequestRepository.findById(requestId);

        if(request.isEmpty())
            throw new ResourceNotFoundException("Salary disbursement request not found!");

        if(request.get().getCurrentStage() != DisbursementRequestProcessing.REVIEWED)
            throw new SalaryProcessingException("Salary request cannot be approved at this stage");

        return changeDisbursementRequestStatus(request.get(),DisbursementRequestProcessing.APPROVED,authenticatedUser);
    }
    public Transferable declineRequest(String requestId, DisbursementRequestProcessing newStatus, StreamlinedAuthenticatedUser authenticatedUser){
        Optional<SalaryDisbursementRequest> request = salaryDisbursementRequestRepository.findById(requestId);

        if(request.isEmpty())
            throw new ResourceNotFoundException("Salary disbursement request not found!");

        return changeDisbursementRequestStatus(request.get(),DisbursementRequestProcessing.DECLINED,authenticatedUser);
    }


}
