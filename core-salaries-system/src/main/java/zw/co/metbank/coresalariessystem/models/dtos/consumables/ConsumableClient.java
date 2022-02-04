package zw.co.metbank.coresalariessystem.models.dtos.consumables;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.interfaces.Consumable;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

@Data
@NoArgsConstructor
public class ConsumableClient implements Consumable {
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String companyId;
    private String role;

    @Override
    public ValidityChecker checkValidity() {
        ValidityChecker vc = new ValidityChecker();
        if(Strings.isNullOrEmpty(username)){
            vc.setValid(false);
            vc.setMessage("Username required");
            return vc;
        }

        if(Strings.isNullOrEmpty(fullName)){
            vc.setValid(false);
            vc.setMessage("Fullname required");
            return vc;
        }
        if(Strings.isNullOrEmpty(email)){
            vc.setValid(false);
            vc.setMessage("Email required");
            return vc;
        }
        if(Strings.isNullOrEmpty(phoneNumber)){
            vc.setValid(false);
            vc.setMessage("Phonenumber required");
            return vc;
        }
        if(Strings.isNullOrEmpty(companyId)){
            vc.setValid(false);
            vc.setMessage("Company Id required");
            return vc;
        }
        return vc;

    }
}
