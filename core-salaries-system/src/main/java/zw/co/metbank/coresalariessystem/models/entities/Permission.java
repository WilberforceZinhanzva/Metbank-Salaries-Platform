package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferablePermission;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="permissions")
public class Permission implements Serializable {
    @Id
    private String id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "permissions")
    private List<User> users = new ArrayList<>();

    public Permission(String name, String description){
        this.name = name;
        this.description = description;
        this.id = GlobalMethods.generateId("PMSSN");
    }

    @Override
    public TransferablePermission serializeForTransfer() {
        return new TransferablePermission(this);
    }
}
