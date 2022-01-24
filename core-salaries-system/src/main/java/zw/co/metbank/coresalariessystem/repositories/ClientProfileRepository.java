package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.ClientProfile;
import zw.co.metbank.coresalariessystem.projections.IdsOnly;

public interface ClientProfileRepository extends JpaRepository<ClientProfile,String> {
    Page<IdsOnly> findByFullNameContainingIgnoreCase(String fullname, Pageable pageable);
    Page<IdsOnly> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<IdsOnly> findByPhoneNumberContainingIgnoreCase(String phone, Pageable pageable);
    Page<IdsOnly> findByClientCompany_NameContainingIgnoreCase(String companyName, Pageable pageable);
}
