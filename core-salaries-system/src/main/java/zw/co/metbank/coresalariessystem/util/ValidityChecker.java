package zw.co.metbank.coresalariessystem.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidityChecker {
    private boolean valid = true;
    private String message = "Valid Payload";

    public boolean isValid(){
        return valid;
    }
}
