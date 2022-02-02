package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.ClientProfile;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferableClientProfile implements Transferable {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String company;

    public TransferableClientProfile(ClientProfile profile){
        this.id = profile.getId();
        this.fullName = profile.getFullName();
        this.email = profile.getEmail();
        this.phoneNumber = profile.getPhoneNumber();
        this.company = profile.getClientCompany().getName();
    }
}
