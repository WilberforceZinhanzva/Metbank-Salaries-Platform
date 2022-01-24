package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.InputBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;

public interface InputBasedSalaryDisbursementRequestRepository extends JpaRepository<InputBasedSalaryDisbursementRequest,String> {
    Page<InputBasedSalaryDisbursementRequest> findByActionLoggingDoneByContainingIgnoreCaseAndActionLoggingStage(String doneBy, DisbursementRequestProcessing stage, Pageable pageable);
    Page<InputBasedSalaryDisbursementRequest> findByCurrentStage(DisbursementRequestProcessing currentStage, Pageable pageable);
}
