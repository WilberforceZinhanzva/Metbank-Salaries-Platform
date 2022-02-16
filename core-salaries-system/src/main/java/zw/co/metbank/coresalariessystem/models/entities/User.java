package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
    @Column(unique=true)
    private String username;
    private String password;
    private Boolean accountLocked;
    private Boolean passwordRequired = true;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_profile")
    private UserProfile profile;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH},fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName="id")
    )
    private List<Role> roles = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name="user_permissions",
            joinColumns = @JoinColumn(referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName="id")
    )
    private List<Permission> permissions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserActionLogger> actionsLogs = new ArrayList<>();

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
