package zw.co.metbank.coresalariessystem.models.dtos.consumables;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.interfaces.Consumable;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

@Data
@NoArgsConstructor
public class ConsumableDisbursementInput implements Consumable {

    private String remitterAccountNumber;
    private String beneficiaryBankName;
    private String beneficiaryBankCode;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private String amount;
    private String currency;
    private String reference;
    private String remitterName;

    @Override
    public ValidityChecker checkValidity() {
        ValidityChecker vc = new ValidityChecker();
        if(Strings.isNullOrEmpty(remitterAccountNumber)){
            vc.setValid(false); vc.setMessage("Remitter account number must not be empty");
            return vc;
        }
        if(Strings.isNullOrEmpty(beneficiaryBankName)){
            vc.setValid(false); vc.setMessage("Beneficiary bank name must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(beneficiaryBankCode)){
            vc.setValid(false); vc.setMessage("Beneficiary bank code must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(beneficiaryAccountNumber)){
            vc.setValid(false); vc.setMessage("Beneficiary account number must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(beneficiaryName)){
            vc.setValid(false); vc.setMessage("Beneficiary name must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(amount)){
            vc.setValid(false); vc.setMessage("Amount must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(currency)){
            vc.setValid(false); vc.setMessage("Currency must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(reference)){
            vc.setValid(false); vc.setMessage("Reference must not be empty!");
            return vc;
        }
        if(Strings.isNullOrEmpty(remitterName)){
            vc.setValid(false); vc.setMessage("Remitter name must not be empty!");
            return vc;
        }
        return vc;
    }
}
