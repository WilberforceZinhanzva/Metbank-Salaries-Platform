package zw.co.metbank.coresalariessystem.models.dtos.consumables;

import com.google.common.base.Strings;
import zw.co.metbank.coresalariessystem.models.interfaces.Consumable;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

public class ConsumableAdmin implements Consumable {
    private String username;
    private String password;
    private String fullName;
    private String department;

    @Override
    public ValidityChecker checkValidity() {
        ValidityChecker vc = new ValidityChecker();
        if(Strings.isNullOrEmpty(username)){
            vc.setValid(false);
            vc.setMessage("Username required");
            return vc;
        }
        if(Strings.isNullOrEmpty(password)){
            vc.setValid(false);
            vc.setMessage("Password required");
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
        return vc;
    }
}
