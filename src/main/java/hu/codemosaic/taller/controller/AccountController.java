package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.dto.AccountDto;
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

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountDto));

    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccount() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
