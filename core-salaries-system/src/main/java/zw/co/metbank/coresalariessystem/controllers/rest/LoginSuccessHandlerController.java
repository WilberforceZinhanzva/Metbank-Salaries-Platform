package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.metbank.coresalariessystem.models.extras.LoggedUserDetails;
import zw.co.metbank.coresalariessystem.services.LoggedUserDetailsService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/login_handler")
public class LoginSuccessHandlerController {

    @Autowired
    private LoggedUserDetailsService loggedUserDetailsService;

    @GetMapping
    public ResponseEntity<LoggedUserDetails> successLogin(Principal principal){
        LoggedUserDetails loggedUserDetails = loggedUserDetailsService.loggedUserDetails(principal.getName());
        return new ResponseEntity<>(loggedUserDetails, HttpStatus.OK);
    }
}
