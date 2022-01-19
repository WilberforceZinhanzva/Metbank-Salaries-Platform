package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.Permission;
import zw.co.metbank.coresalariessystem.models.entities.Role;
import zw.co.metbank.coresalariessystem.models.entities.User;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TransferableBankUser implements Transferable {
    private String id;
    private String username;
    private Boolean accountLocked;
    private TransferableBankUserProfile profile;
    private List<TransferableRole> roles = new ArrayList<>();
    private List<TransferablePermission> permissions = new ArrayList<>();


    public TransferableBankUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.accountLocked = user.getAccountLocked();
        this.profile = (TransferableBankUserProfile) user.getProfile().serializeForTransfer();
        this.roles = user.getRoles().stream().map(Role::serializeForTransfer).collect(Collectors.toList());
        this.permissions = user.getPermissions().stream().map(Permission::serializeForTransfer).collect(Collectors.toList());

    }
}
