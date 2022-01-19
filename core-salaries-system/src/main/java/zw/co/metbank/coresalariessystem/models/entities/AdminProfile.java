package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableAdminProfile;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name="admin_profiles")
public class AdminProfile extends UserProfile{
    private String fullName;
    private String department;

    @Override
    public TransferableAdminProfile serializeForTransfer() {
        return new TransferableAdminProfile(this);
    }
}
