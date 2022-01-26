package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.InvalidConsumableException;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.dtos.consumables.ConsumableClientCompany;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClientCompany;
import zw.co.metbank.coresalariessystem.models.entities.ClientCompany;
import zw.co.metbank.coresalariessystem.models.entities.ClientCompanyActionLogger;
import zw.co.metbank.coresalariessystem.repositories.ClientCompanyRepository;
import zw.co.metbank.coresalariessystem.security.AuthenticatedUser;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;
import zw.co.metbank.coresalariessystem.util.ValidityChecker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientCompanyService {

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;



    public Page<TransferableClientCompany> clientCompanies(int page, int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<ClientCompany> companiesPage = clientCompanyRepository.findAll(pageable);
        List<TransferableClientCompany> serializedList = companiesPage.getContent().stream().map(ClientCompany::serializeForTransfer).collect(Collectors.toList());
        Page<TransferableClientCompany> serializedPage = new PageImpl<>(serializedList,pageable,companiesPage.getTotalElements());
        return serializedPage;
    }

    public TransferableClientCompany newClientCompany(ConsumableClientCompany consumable, AuthenticatedUser authenticatedUser){

        String actor = authenticatedUser.getFullname();
        String actorId = authenticatedUser.getUserId();

        ValidityChecker vc = consumable.checkValidity();
        if(!vc.isValid())
            throw new InvalidConsumableException(vc.getMessage());
        ClientCompany clientCompany = new ClientCompany(consumable.getName());

        ClientCompanyActionLogger logger = new ClientCompanyActionLogger();
        logger.setId(GlobalMethods.generateId("CCAL"));
        logger.setAction("Added a new client company");
        logger.setActionDoneAt(LocalDateTime.now());
        logger.setActionDoneBy(actor);
        logger.setActorId(actorId);
        logger.setClientCompany(clientCompany);

        clientCompany.getActionLogs().add(logger);

        return clientCompanyRepository.save(clientCompany).serializeForTransfer();
    }

    public TransferableClientCompany deleteClientCompany(String id){
        Optional<ClientCompany> clientCompany = clientCompanyRepository.findById(id);
        if(clientCompany.isEmpty())
            throw  new ResourceNotFoundException("Client company not found!");
        clientCompanyRepository.deleteById(id);
        return clientCompany.get().serializeForTransfer();
    }
}
