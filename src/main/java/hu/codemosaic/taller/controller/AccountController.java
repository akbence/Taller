package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.dto.AccountContainerDto;
import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.security.Auth;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hu.codemosaic.taller.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(BaseApiController.BASE_PATH + "/account-container")
@RequiredArgsConstructor
public class AccountController extends BaseApiController{

    private final AccountService accountService;

    @Auth
    @PostMapping
    public ResponseEntity<AccountContainerDto> createAccountContainer(@RequestBody AccountContainerDto accountContainerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccountContainer(accountContainerDto, getCurrentUsername()));

    }

    @Auth
    @GetMapping
    public ResponseEntity<List<AccountContainerDto>> getAllAccountContainer() {
        return ResponseEntity.ok(accountService.getAllAccountsByOwnerId(getCurrentUserId()));
    }

    @Auth
    @GetMapping("/{containerId}/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsByContainerId(@PathParam(value = "containerId") UUID containerId) {
        return ResponseEntity.ok(accountService.getAccountsByContainerId(containerId, getCurrentUserId()));
    }
}
