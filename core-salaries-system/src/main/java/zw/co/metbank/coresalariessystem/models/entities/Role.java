package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableRole;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="roles")
public class Role implements Serializable {
    @Id
    private String id;
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public  Role(String name){
        this.name = name;
        this.id = GlobalMethods.generateId("RL");
    }

    @Override
    public TransferableRole serializeForTransfer() {
        return new TransferableRole(this);
    }
}
