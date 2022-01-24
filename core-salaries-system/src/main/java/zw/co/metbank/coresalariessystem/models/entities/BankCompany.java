package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableBankCompany;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="bank_company")
@Data
@NoArgsConstructor
public class BankCompany implements Serializable {
    @Id
    private String id;
    private String name;
    @Column(name="registered_on")
    private LocalDate registeredOn;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "bankCompany")
    private List<BankUserProfile> usersForBank = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "bankCompany")
    private List<BankCompanyActionLogger> actionLogs = new ArrayList<>();

    public BankCompany(String name){
        this.id = GlobalMethods.generateId("BC");
        this.name = name;
        this.registeredOn = LocalDate.now();
    }

    @Override
    public TransferableBankCompany serializeForTransfer() {
        return new TransferableBankCompany(this);
    }
}
