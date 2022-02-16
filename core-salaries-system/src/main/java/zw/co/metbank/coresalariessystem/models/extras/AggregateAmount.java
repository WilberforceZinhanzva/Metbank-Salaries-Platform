package zw.co.metbank.coresalariessystem.models.extras;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AggregateAmount {
    private String currency;
    private String amount;
}
