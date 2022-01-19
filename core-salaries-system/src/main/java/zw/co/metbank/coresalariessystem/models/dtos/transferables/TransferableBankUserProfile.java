package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.BankUserProfile;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferableBankUserProfile implements Transferable {
    private String id;
    private String fullName;
    private String email;

    public TransferableBankUserProfile(BankUserProfile profile){
        this.id = profile.getId();
        this.fullName = profile.getFullName();
        this.email = profile.getEmail();
    }

}
