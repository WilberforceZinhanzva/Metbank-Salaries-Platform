package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableInputBasedSalaryDisbursementRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class InputBasedSalaryDisbursementRequest extends SalaryDisbursementRequest{
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salaryDisbursementRequest")
    List<DisbursementInput> disbursementInputs = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="generated_salaries_file")
    private GeneratedSalariesFile generatedSalariesFile;

    @Override
    public TransferableInputBasedSalaryDisbursementRequest serializeForTransfer() {
        return new TransferableInputBasedSalaryDisbursementRequest(this);
    }
}
