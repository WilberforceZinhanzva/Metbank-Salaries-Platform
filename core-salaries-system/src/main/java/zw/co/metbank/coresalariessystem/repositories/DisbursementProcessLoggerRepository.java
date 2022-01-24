package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementProcessLogger;


public interface DisbursementProcessLoggerRepository extends JpaRepository<DisbursementProcessLogger,String> {
}
