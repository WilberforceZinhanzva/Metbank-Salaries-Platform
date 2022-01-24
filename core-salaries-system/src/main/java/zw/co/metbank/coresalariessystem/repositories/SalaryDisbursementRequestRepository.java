package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.SalaryDisbursementRequest;

public interface SalaryDisbursementRequestRepository extends JpaRepository<SalaryDisbursementRequest,String> {
}
