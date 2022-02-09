package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.BankCompany;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

@Data
@NoArgsConstructor
public class TransferableBankCompany implements Transferable {
    private String id;
    private String name;
    private String registeredOn;

    public TransferableBankCompany(BankCompany company){
        this.id = company.getId();
        this.name = company.getName();
        this.registeredOn = GlobalMethods.formatDateOnly(company.getRegisteredOn());

    }
}
