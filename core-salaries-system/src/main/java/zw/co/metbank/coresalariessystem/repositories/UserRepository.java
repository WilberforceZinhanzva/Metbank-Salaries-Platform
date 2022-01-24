package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.Permission;
import zw.co.metbank.coresalariessystem.models.entities.Role;
import zw.co.metbank.coresalariessystem.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findByUsername(String username);

    Page<User> findByProfile_IdIn(List<String> ids, Pageable pageable);
    Page<User> findByRolesInAndPermissionsIn(List<Role> roles, List<Permission> permissions,Pageable pageable);

    Page<User> findByRolesInAndUsernameContainingIgnoreCase(List<Role> roles, String username, Pageable pageable);
    Page<User> findByRolesInAndAccountLocked(List<Role> roles, Boolean lockStatus, Pageable pageable);
    Page<User> findByRolesIn(List<Role> roles, Pageable pageable);


}
