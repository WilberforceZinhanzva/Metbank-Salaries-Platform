package zw.co.metbank.coresalariessystem.controllers.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zw.co.metbank.coresalariessystem.jwt.UsernameAndPassword;

@RestController
public class LoginController {

    @ApiOperation("Login")
    @PostMapping(value = "/login")
    public void fakeLogin(@RequestBody UsernameAndPassword usernameAndPassword){
        throw new IllegalStateException("Fake Login");
    }
}
