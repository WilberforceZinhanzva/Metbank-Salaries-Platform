package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.Permission;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferablePermission implements Transferable {
    private String id;
    private String name;
    private String description;

    public TransferablePermission(Permission permission){
        this.id = permission.getId();
        this.name = permission.getName();
        this.description = permission.getDescription();
    }
}
