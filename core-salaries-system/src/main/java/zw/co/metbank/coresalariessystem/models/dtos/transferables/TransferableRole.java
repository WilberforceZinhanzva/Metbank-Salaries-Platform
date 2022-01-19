package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.Role;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferableRole implements Transferable {
    private String id;
    private String name;

    public TransferableRole(Role role){
        this.id=role.getId();
        this.name = role.getName();
    }
}
