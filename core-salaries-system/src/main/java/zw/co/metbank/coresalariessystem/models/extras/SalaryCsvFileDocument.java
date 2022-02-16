package zw.co.metbank.coresalariessystem.models.extras;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class SalaryCsvFileDocument {

    private List<SalaryRequestCsvEntry> recordEntries = new ArrayList<>();

    private List<AggregateAmount> amounts = new ArrayList<>();
    private int totalBeneficiaries;

    public void resolveDocument(){
        this.totalBeneficiaries = recordEntries.size();

        Map<String,String> totalAmountsMap = new HashMap<String,String>();
        recordEntries.forEach(entry ->{
            if(totalAmountsMap.isEmpty()){
                totalAmountsMap.putIfAbsent(entry.getCurrency(),entry.getAmount());
            }else{
                if(totalAmountsMap.containsKey(entry.getCurrency())){

                    BigDecimal currentAmount = new BigDecimal(totalAmountsMap.get(entry.getCurrency()));
                    String newAmount = currentAmount.add(new BigDecimal(entry.getAmount())).setScale(2, RoundingMode.HALF_EVEN).toString();
                    totalAmountsMap.replace(entry.getCurrency(),currentAmount.toString(),newAmount);

                }else{
                    totalAmountsMap.putIfAbsent(entry.getCurrency(),entry.getAmount());
                }
            }
        });

        totalAmountsMap.forEach((k,v) ->{
            AggregateAmount aggregateAmount = new AggregateAmount();
            aggregateAmount.setCurrency(k);
            aggregateAmount.setAmount(v);
            amounts.add(aggregateAmount);
        });

    }
}
