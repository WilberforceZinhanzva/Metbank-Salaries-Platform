package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,String> {
    Boolean existsByNameIgnoreCase(String name);
    Optional<Permission> findByName(String name);
    List<Permission> findByNameIn(List<String> names);
}
