package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableDisbursementInput;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import javax.persistence.*;

@Entity
@Data
@Table(name="disbursement_inputs")
public class DisbursementInput implements Serializable {
    @Id
    private String id;
    @Column(name="m_date")
    private String date;
    @Column(name="remitter_account_number")
    private String remitterAccountNumber;
    @Column(name="beneficiary_bank_name")
    private String beneficiaryBankName;
    @Column(name="beneficiary_bank_code")
    private String beneficiaryBankCode;
    @Column(name="beneficiary_account_number")
    private String beneficiaryAccountNumber;
    @Column(name="beneficiary_name")
    private String beneficiaryName;
    private String amount;
    private String currency;
    private String reference;
    @Column(name="remitter_name")
    private String remitterName;
    @ManyToOne
    @JoinColumn(name="salary_disbursement_request")
    private InputBasedSalaryDisbursementRequest salaryDisbursementRequest;

    public DisbursementInput(){
        this.id = GlobalMethods.generateId("DISI");
    }

    @Override
    public TransferableDisbursementInput serializeForTransfer() {
        return new TransferableDisbursementInput(this);
    }
}
