package zw.co.metbank.coresalariessystem.models.dtos.consumables;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.interfaces.Consumable;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

@Data
@NoArgsConstructor
public class ConsumableAdmin implements Consumable {
    private String username;
    private String fullName;
    private String department;
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
        if(Strings.isNullOrEmpty(department)){
            vc.setValid(false);
            vc.setMessage("Department required");
            return vc;
        }
        if(Strings.isNullOrEmpty(role)){
            vc.setValid(false);
            vc.setMessage("Role required");
            return vc;
        }
        return vc;
    }
}
