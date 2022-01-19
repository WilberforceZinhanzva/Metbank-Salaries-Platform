package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableAdmin;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableBankUser;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClient;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    private String id;
    private String username;
    private String password;
    private Boolean accountLocked;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_profile")
    private UserProfile profile;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="id"),
            inverseJoinColumns = @JoinColumn(name="id")
    )
    private List<Role> roles = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name="user_permissions",
            joinColumns = @JoinColumn(name="id"),
            inverseJoinColumns = @JoinColumn(name="id")
    )
    private List<Permission> permissions = new ArrayList<>();

    @Override
    public Transferable serializeForTransfer() {
        if(profile instanceof AdminProfile)
            return new TransferableAdmin(this);
        if(profile instanceof ClientProfile)
            return new TransferableClient(this);
        if(profile instanceof BankUserProfile)
            return new TransferableBankUser(this);

        return null;
    }
}
