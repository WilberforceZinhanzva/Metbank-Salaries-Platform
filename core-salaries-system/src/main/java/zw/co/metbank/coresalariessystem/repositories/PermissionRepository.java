package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission,String> {
    Boolean existsByNameIgnoreCase(String name);
}
