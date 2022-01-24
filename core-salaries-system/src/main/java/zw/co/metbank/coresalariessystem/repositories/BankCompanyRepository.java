package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.BankCompany;

public interface BankCompanyRepository extends JpaRepository<BankCompany,String> {
}
