package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableBankUserProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name="bank_profiles")
public class BankUserProfile extends UserProfile {
    @Column(name="full_name", nullable = false)
    private String fullName;
    @Column(name="email")
    private String email;

    public TransferableBankUserProfile serializeForTransfer() {
        return new TransferableBankUserProfile(this);
    }
}
