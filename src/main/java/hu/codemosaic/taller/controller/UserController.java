package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.dto.LoginRequest;
import hu.codemosaic.taller.dto.TokenResponse;
import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.security.NoAuth;
import hu.codemosaic.taller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseApiController{

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto created = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @NoAuth
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        var token = userService.login(request.getUsername(), request.getPassword());
        return token != null ? ResponseEntity.ok(new TokenResponse(token)) :  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
