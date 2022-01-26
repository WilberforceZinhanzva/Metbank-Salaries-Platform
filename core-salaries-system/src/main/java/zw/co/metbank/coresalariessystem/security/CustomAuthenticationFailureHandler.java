package zw.co.metbank.coresalariessystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import zw.co.metbank.coresalariessystem.exceptions.CustomException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        Map<String, Object> data = new HashMap<>();
//        data.put("timestamp", LocalDateTime.now().toString());
//        data.put("message",exception.getMessage());
//        response.getOutputStream().println(objectMapper.writeValueAsString(data));

        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        CustomException customException = new CustomException(exception.getMessage(), HttpStatus.FORBIDDEN);
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, customException);
        out.flush();
    }
}
