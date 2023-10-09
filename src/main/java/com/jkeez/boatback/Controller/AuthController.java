package com.jkeez.boatback.Controller;

import com.jkeez.boatback.Dto.UserAccountDTO;
import com.jkeez.boatback.Dto.UserLoginDTO;
import com.jkeez.boatback.Dto.UserRegistrationDTO;
import com.jkeez.boatback.Entity.UserAccount;
import com.jkeez.boatback.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Authentication controller that handles login and register requests with parameter validations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles register requests with given user information
     * @param registrationDTO user information
     * @return entity response with created user
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        UserAccount newUser = authService.registerNewUser(registrationDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Handles login requests with given credentials.
     * @param userLoginDTO user's credentials
     * @return logged in user
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        UserAccount newUser = authService.loginUser(userLoginDTO);
        return ResponseEntity.ok(new UserAccountDTO(newUser));
    }

    /**
     * Handles MethodArgumentNotValidException
     * @param ex exception
     * @return errors
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

