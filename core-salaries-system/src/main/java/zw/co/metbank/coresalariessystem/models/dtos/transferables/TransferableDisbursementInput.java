package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementInput;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferableDisbursementInput implements Transferable {
    private String id;
    private String date;
    private String remitterAccountNumber;
    private String beneficiaryBankName;
    private String beneficiaryBankCode;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private String amount;
    private String currency;
    private String reference;
    private String remitterName;

    public TransferableDisbursementInput(DisbursementInput input){
        this.id = input.getId();
        this.date = input.getDate();
        this.remitterAccountNumber = input.getRemitterAccountNumber();
        this.beneficiaryAccountNumber = input.getBeneficiaryAccountNumber();
        this.beneficiaryBankName = input.getBeneficiaryBankName();
        this.beneficiaryBankCode = input.getBeneficiaryBankCode();
        this.beneficiaryName = input.getBeneficiaryName();
        this.amount = input.getAmount();
        this.currency = input.getCurrency();
        this.reference = input.getReference();
        this.remitterName = input.getRemitterName();
    }
}
