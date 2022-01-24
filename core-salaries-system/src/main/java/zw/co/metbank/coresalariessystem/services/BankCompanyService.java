package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.InvalidConsumableException;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableBankCompany;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableBankCompany;
import zw.co.metbank.coresalariessystem.models.entities.BankCompany;
import zw.co.metbank.coresalariessystem.models.entities.BankCompanyActionLogger;
import zw.co.metbank.coresalariessystem.models.extras.LoggedUserDetails;
import zw.co.metbank.coresalariessystem.repositories.BankCompanyRepository;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankCompanyService {

    @Autowired
    private BankCompanyRepository bankCompanyRepository;

    @Autowired
    private LoggedUserDetailsService loggedUserDetailsService;

    public Page<TransferableBankCompany> bankCompanies(int page, int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);

        Page<BankCompany> companyPage = bankCompanyRepository.findAll(pageable);
        List<TransferableBankCompany> serializedList = companyPage.getContent().stream().map(BankCompany::serializeForTransfer).collect(Collectors.toList());
        Page<TransferableBankCompany> serializedPage = new PageImpl<>(serializedList,pageable,companyPage.getTotalElements());
        return serializedPage;
    }

    public TransferableBankCompany newBankCompany(ConsumableBankCompany consumable, Principal principal){

        LoggedUserDetails loggedUserDetails = loggedUserDetailsService.loggedUserDetails(principal.getName());
        String actor = loggedUserDetails.getFullname();
        String actorId = loggedUserDetails.getId();


        ValidityChecker vc = consumable.checkValidity();
        if(!vc.isValid())
            throw new InvalidConsumableException(vc.getMessage());
        BankCompany bankCompany = new BankCompany(consumable.getName());

        BankCompanyActionLogger actionLogger = new BankCompanyActionLogger();
        actionLogger.setId(GlobalMethods.generateId("BCAL"));
        actionLogger.setAction("Added a new bank company");
        actionLogger.setActionDoneAt(LocalDateTime.now());
        actionLogger.setActionDoneBy(actor);
        actionLogger.setActorId(actorId);
        actionLogger.setBankCompany(bankCompany);

        bankCompany.getActionLogs().add(actionLogger);

        return bankCompanyRepository.save(bankCompany).serializeForTransfer();

    }

    public TransferableBankCompany deleteBankCompany(String id){
        Optional<BankCompany> bankCompany = bankCompanyRepository.findById(id);
        if(bankCompany.isEmpty())
            throw new ResourceNotFoundException("Bank company not found!");
        bankCompanyRepository.delete(bankCompany.get());
        return bankCompany.get().serializeForTransfer();
    }
}
