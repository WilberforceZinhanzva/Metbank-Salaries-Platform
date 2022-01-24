package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableBankUserProfile;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="bank_profiles")
public class BankUserProfile extends UserProfile {
    @Column(name="full_name", nullable = false)
    private String fullName;
    @Column(name="email")
    private String email;
    @ManyToOne
    @JoinColumn(name="bank_company")
    private BankCompany bankCompany;

    public TransferableBankUserProfile serializeForTransfer() {
        return new TransferableBankUserProfile(this);
    }
}
