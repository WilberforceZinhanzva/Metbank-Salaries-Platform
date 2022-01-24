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
    @Override
    public ValidityChecker checkValidity() {
        ValidityChecker vc = new ValidityChecker();

        if(Strings.isNullOrEmpty(name)){
            vc.setValid(false);
            vc.setMessage("Name must not be empty");
            return vc;
        }
        return vc;
    }
}
