package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableActionLogger;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="client_company_actions_log")
@Data
@NoArgsConstructor
public class ClientCompanyActionLogger extends ActionLogger{
    @ManyToOne
    @JoinColumn(name="client_company")
    private ClientCompany clientCompany;
    @Override
    public TransferableActionLogger serializeForTransfer() {
        return new TransferableActionLogger(this);
    }
}
