package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableActionLogger;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_actions_logger")
@Data
@NoArgsConstructor
public class UserActionLogger extends ActionLogger{
    @ManyToOne
    @JoinColumn(name="user")
    private User user;
    @Override
    public TransferableActionLogger serializeForTransfer() {
        return new TransferableActionLogger(this);
    }
}
