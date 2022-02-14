package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {
    Boolean existsByNameIgnoreCase(String name);
    Optional<Role> findByName(String name);
    List<Role> findByNameIn(List<String> roleNames);

}
