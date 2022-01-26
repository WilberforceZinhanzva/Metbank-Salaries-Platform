package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.entities.User;
import zw.co.metbank.coresalariessystem.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        userRepository.delete(user.get());
        return true;
    }

    public Boolean lockAccount(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        user.get().setAccountLocked(true);
        userRepository.save(user.get());
        return true;
    }

    public Boolean unlockAccount(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        user.get().setAccountLocked(false);
        userRepository.save(user.get());
        return true;
    }


}
