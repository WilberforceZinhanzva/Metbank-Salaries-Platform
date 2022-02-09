package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.ClientCompany;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

@Data
@NoArgsConstructor
public class TransferableClientCompany implements Transferable {
    private String id;
    private String name;
    private String registeredOn;
    private String accountNumber;
    private String personInCharge;
    private String address;
    private String email;
    private String phone;
    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonTitle;

    public TransferableClientCompany(ClientCompany company){
        this.id = company.getId();
        this.name = company.getName();
        this.registeredOn = GlobalMethods.formatDateOnly(company.getRegisteredOn());
        this.accountNumber = company.getAccountNumber();
        this.personInCharge = company.getPersonInCharge();
        this.address = company.getAddress();
        this.email = company.getEmail();
        this.phone = company.getPhone();
        this.contactPersonName = company.getContactPersonName();
        this.contactPersonPhone = company.getContactPersonPhone();
        this.contactPersonTitle = company.getContactPersonTitle();
    }
}
