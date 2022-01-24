package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.ClientCompany;

import java.util.Optional;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany,String> {
    Optional<ClientCompany> findByName(String name);
}
