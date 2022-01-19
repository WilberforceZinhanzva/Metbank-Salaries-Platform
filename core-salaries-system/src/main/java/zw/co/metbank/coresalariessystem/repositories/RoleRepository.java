package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role,String> {
    Boolean existsByNameIgnoreCase(String name);
}
