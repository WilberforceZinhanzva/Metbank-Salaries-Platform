package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClientCompany;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="client_company")
@Data
@NoArgsConstructor
public class ClientCompany implements Serializable {
    @Id
    private String id;
    private String name;
    @Column(name="registered_on")
    private LocalDate registeredOn;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "clientCompany")
    private List<ClientProfile> usersForCompany = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "clientCompany")
    private List<ClientCompanyActionLogger> actionLogs = new ArrayList<>();

    public ClientCompany(String name){
        this.id = GlobalMethods.generateId("CC");
        this.name = name;
        this.registeredOn = LocalDate.now();
    }

    @Override
    public TransferableClientCompany serializeForTransfer() {
        return new TransferableClientCompany(this);
    }
}
