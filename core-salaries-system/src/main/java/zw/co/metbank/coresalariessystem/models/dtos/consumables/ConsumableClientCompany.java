package zw.co.metbank.coresalariessystem.models.dtos.consumables;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.interfaces.Consumable;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

@Data
@NoArgsConstructor
public class ConsumableClientCompany implements Consumable {
    private String name;
    private String accountNumber;
    private String personInCharge;
    private String address;
    private String email;
    private String phone;
    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonTitle;

    @Override
    public ValidityChecker checkValidity() {
        ValidityChecker vc = new ValidityChecker();

        if(Strings.isNullOrEmpty(name)){
            vc.setValid(false);
            vc.setMessage("Name must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(accountNumber)){
            vc.setValid(false);
            vc.setMessage("Account number must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(personInCharge)){
            vc.setValid(false);
            vc.setMessage("Person in charge must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(address)){
            vc.setValid(false);
            vc.setMessage("Address must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(email)){
            vc.setValid(false);
            vc.setMessage("Email must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(phone)){
            vc.setValid(false);
            vc.setMessage("Phone must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(contactPersonName)){
            vc.setValid(false);
            vc.setMessage("Contact person name must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(contactPersonPhone)){
            vc.setValid(false);
            vc.setMessage("Contact person phone must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(contactPersonTitle)){
            vc.setValid(false);
            vc.setMessage("Contact person title must not be empty");
            return vc;
        }

        return vc;
    }
}
