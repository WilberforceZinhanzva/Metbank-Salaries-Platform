package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableActionLogger;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bank_company_actions_log")
@Data
@NoArgsConstructor
public class BankCompanyActionLogger extends ActionLogger{
    @ManyToOne
    private BankCompany bankCompany;
    @Override
    public TransferableActionLogger serializeForTransfer() {
        return new TransferableActionLogger(this);
    }
}
