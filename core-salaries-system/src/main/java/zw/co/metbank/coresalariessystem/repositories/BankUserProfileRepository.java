package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.BankUserProfile;

public interface BankUserProfileRepository extends JpaRepository<BankUserProfile,String> {
}
