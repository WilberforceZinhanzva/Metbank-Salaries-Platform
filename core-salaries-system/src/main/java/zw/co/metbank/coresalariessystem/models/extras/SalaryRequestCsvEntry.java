package zw.co.metbank.coresalariessystem.models.extras;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaryRequestCsvEntry {
    @CsvBindByName(column = "Date",required = true)
    private String date;
    @CsvBindByName(column = "RemitterAccountNumber",required = true)
    private String remitterAccountNumber;
    @CsvBindByName(column = "BeneficiaryBankName",required = true)
    private String beneficiaryBankName;
    @CsvBindByName(column = "BeneficiaryBankCode",required = true)
    private String beneficiaryBankCode;
    @CsvBindByName(column = "BeneficiaryAccountNumber",required = true)
    private String beneficiaryAccountNumber;
    @CsvBindByName(column = "BeneficiaryName",required = true)
    private String beneficiaryName;
    @CsvBindByName(column = "Amount",required = true)
    private String amount;
    @CsvBindByName(column = "Currency",required = true)
    private String currency;
    @CsvBindByName(column = "Reference",required = true)
    private String reference;
    @CsvBindByName(column = "RemitterName",required = true)
    private String remitterName;
}
