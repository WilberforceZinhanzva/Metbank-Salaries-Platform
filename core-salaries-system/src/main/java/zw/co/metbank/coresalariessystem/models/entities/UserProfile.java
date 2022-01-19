package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="user_profiles")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserProfile implements Serializable {
    @Id
    private String id;
    @OneToOne(mappedBy = "profile")
    private User user;


}
