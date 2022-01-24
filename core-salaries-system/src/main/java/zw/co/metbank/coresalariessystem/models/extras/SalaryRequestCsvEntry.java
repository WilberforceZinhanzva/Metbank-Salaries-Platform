package zw.co.metbank.coresalariessystem.models.extras;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaryRequestCsvEntry {
    @CsvBindByName(column = "Date")
    private String date;
    @CsvBindByName(column = "RemitterAccountNumber")
    private String remitterAccountNumber;
    @CsvBindByName(column = "BeneficiaryBankName")
    private String beneficiaryBankName;
    @CsvBindByName(column = "BeneficiaryBankCode")
    private String beneficiaryBankCode;
    @CsvBindByName(column = "BeneficiaryAccountNumber")
    private String beneficiaryAccountNumber;
    @CsvBindByName(column = "BeneficiaryName")
    private String beneficiaryName;
    @CsvBindByName(column = "Amount")
    private String amount;
    @CsvBindByName(column = "Currency")
    private String currency;
    @CsvBindByName(column = "Reference")
    private String reference;
    @CsvBindByName(column = "RemitterName")
    private String remitterName;
}
