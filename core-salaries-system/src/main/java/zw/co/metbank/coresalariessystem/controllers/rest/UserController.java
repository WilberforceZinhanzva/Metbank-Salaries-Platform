package zw.co.metbank.coresalariessystem.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import zw.co.metbank.coresalariessystem.models.extras.Password;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.services.UserService;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") String id){
        Boolean result = userService.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}/lock")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Boolean> lockAccount(@PathVariable("id") String id){
        Boolean result = userService.lockAccount(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/{id}/unlock")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Boolean> unlockAccount(@PathVariable("id") String id){
        Boolean result = userService.unlockAccount(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value="/password-reset",consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<Transferable> passwordReset(@RequestBody Password password){

        Transferable result = userService.changePassword(password.getPassword());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
