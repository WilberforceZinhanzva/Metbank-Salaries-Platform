package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.ClientCompany;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransferableClientCompany implements Transferable {
    private String id;
    private String name;
    private LocalDate registeredOn;

    public TransferableClientCompany(ClientCompany company){
        this.id = company.getId();
        this.name = company.getName();
        this.registeredOn = company.getRegisteredOn();
    }
}
