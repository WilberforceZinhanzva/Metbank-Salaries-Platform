package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.metbank.coresalariessystem.models.extras.LoggedUserDetails;
import zw.co.metbank.coresalariessystem.security.AuthenticatedUser;



@RestController
@RequestMapping("/api/v1/login_handler")
public class LoginSuccessHandlerController {



    @GetMapping
    public ResponseEntity<LoggedUserDetails> successLogin(){
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoggedUserDetails loggedUserDetails = new LoggedUserDetails(authenticatedUser);
        return new ResponseEntity<>(loggedUserDetails, HttpStatus.OK);
    }
}
