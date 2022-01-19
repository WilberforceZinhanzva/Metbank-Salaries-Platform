package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableActionLogger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name="user_resource_action_logger")
public class UserResourceActionLogger extends ActionLogger{
    @Override
    public TransferableActionLogger serializeForTransfer() {
        return new TransferableActionLogger(this);
    }
}
