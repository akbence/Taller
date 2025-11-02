package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.dto.AccountContainerDto;
import hu.codemosaic.taller.security.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.codemosaic.taller.service.AccountService;

import java.util.List;

@RestController
@RequestMapping(BaseApiController.BASE_PATH + "/account")
@RequiredArgsConstructor
public class AccountController extends BaseApiController{

    private final AccountService accountService;

    @Auth
    @PostMapping
    public ResponseEntity<AccountContainerDto> createAccountContainer(@RequestBody AccountContainerDto accountContainerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountContainerDto, getCurrentUsername()));

    }

    @GetMapping
    public ResponseEntity<List<AccountContainerDto>> getAccount() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
