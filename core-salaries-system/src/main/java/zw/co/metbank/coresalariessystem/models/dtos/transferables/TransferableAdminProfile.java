package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.AdminProfile;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferableAdminProfile implements Transferable {
    private String id;
    private String fullName;
    private String department;

    public TransferableAdminProfile(AdminProfile profile){
        this.id = profile.getId();
        this.fullName = profile.getFullName();
        this.department = profile.getDepartment();

    }
}
