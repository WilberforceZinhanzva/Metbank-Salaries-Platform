package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.metbank.coresalariessystem.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") String id){
        Boolean result = userService.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<Boolean> lockAccount(@PathVariable("id") String id){
        Boolean result = userService.lockAccount(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/{id}/unlock")
    public ResponseEntity<Boolean> unlockAccount(@PathVariable("id") String id){
        Boolean result = userService.unlockAccount(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
