package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.FileBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;

public interface FileBasedSalaryDisbursementRequestRepository extends JpaRepository<FileBasedSalaryDisbursementRequest,String> {

    Page<FileBasedSalaryDisbursementRequest> findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(String doneBy, DisbursementRequestProcessing stage, Pageable pageable);
    Page<FileBasedSalaryDisbursementRequest> findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(String companyId,String doneBy, DisbursementRequestProcessing stage, Pageable pageable);
    Page<FileBasedSalaryDisbursementRequest> findByCurrentStage(DisbursementRequestProcessing currentStage, Pageable pageable);
    Page<FileBasedSalaryDisbursementRequest> findByCompany_IdAndCurrentStage(String companyId,DisbursementRequestProcessing currentStage, Pageable pageable);
    Page<FileBasedSalaryDisbursementRequest> findByCompany_Id(String id, Pageable pageable);
    Page<FileBasedSalaryDisbursementRequest> findByCompany_NameContainingIgnoreCase(String companyName, Pageable pageable);

    Long countByCompany_Name(String companyName);

}
