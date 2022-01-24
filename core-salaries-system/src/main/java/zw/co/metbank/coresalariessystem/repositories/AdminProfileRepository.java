package zw.co.metbank.coresalariessystem.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.metbank.coresalariessystem.models.entities.AdminProfile;
import zw.co.metbank.coresalariessystem.projections.IdsOnly;

public interface AdminProfileRepository extends JpaRepository<AdminProfile,String> {
    Page<IdsOnly> findByFullNameContainingIgnoreCase(String fullname, Pageable pageable);
    Page<IdsOnly> findByDepartmentContainingIgnoreCase(String department, Pageable pageable);

}
