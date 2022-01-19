package zw.co.metbank.coresalariessystem.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferablePermission;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="permissions")
public class Permission implements Serializable {
    @Id
    private String id;
    private String name;
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @Override
    public TransferablePermission serializeForTransfer() {
        return new TransferablePermission(this);
    }
}
