package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.SalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;

public interface SalaryDisbursementRequestRepository extends JpaRepository<SalaryDisbursementRequest,String> {
    Long countByCurrentStageAndCompany_Name(DisbursementRequestProcessing currentStage,String companyName);
    Long countByCurrentStage(DisbursementRequestProcessing currentStage);
}
