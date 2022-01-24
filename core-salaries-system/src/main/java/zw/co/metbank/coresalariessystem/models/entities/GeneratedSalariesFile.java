package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableGeneratedSalariesFile;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;

import javax.persistence.*;

@Entity
@Table(name="generated_salaries_files")
@Data
@NoArgsConstructor
public class GeneratedSalariesFile implements Serializable {
    @Id
    private String id;
    @Column(name="file_size")
    private String fileSize;
    @Column(name="file_path")
    private String filePath;
    @OneToOne(mappedBy = "generatedSalariesFile")
    private InputBasedSalaryDisbursementRequest request;

    @Override
    public TransferableGeneratedSalariesFile serializeForTransfer() {
        return new TransferableGeneratedSalariesFile(this);
    }
}
