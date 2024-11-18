package ltweb.controller;

import ltweb.model.LoginResponse;
import ltweb.model.LoginUserModel;
import ltweb.model.RegisterUserModel;
import ltweb.service.JwtService;
import ltweb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserModel registerUserModel) {
        userService.signup(registerUserModel);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserModel loginUserModel) {
        var user = userService.authenticate(loginUserModel);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
