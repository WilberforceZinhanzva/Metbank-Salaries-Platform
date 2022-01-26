package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.InputBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;

public interface InputBasedSalaryDisbursementRequestRepository extends JpaRepository<InputBasedSalaryDisbursementRequest,String> {
    Page<InputBasedSalaryDisbursementRequest> findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(String doneBy, DisbursementRequestProcessing stage, Pageable pageable);
    Page<InputBasedSalaryDisbursementRequest> findByCompany_IdAndActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(String companyId,String doneBy, DisbursementRequestProcessing stage, Pageable pageable);
    Page<InputBasedSalaryDisbursementRequest> findByCurrentStage(DisbursementRequestProcessing currentStage, Pageable pageable);
    Page<InputBasedSalaryDisbursementRequest> findByCompany_IdAndCurrentStage(String companyId,DisbursementRequestProcessing currentStage, Pageable pageable);

    Page<InputBasedSalaryDisbursementRequest> findByCompany_Id(String companyId, Pageable pageable);
    Page<InputBasedSalaryDisbursementRequest> findByCompany_NameContainingIgnoreCase(String companyName, Pageable pageable);
}
