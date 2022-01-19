package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="action_logger")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ActionLogger implements Serializable {
    @Id
    private String id;
    private String action;
    @Column(name="action_done_by")
    private String actionDoneBy;
    @Column(name="actor_id")
    private String actorId;
    @Column(name="action_done_at")
    private LocalDateTime actionDoneAt;
}
