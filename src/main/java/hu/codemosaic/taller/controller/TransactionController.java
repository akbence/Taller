package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController extends BaseApiController{

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<AccountTransactionDto> createTransaction(@RequestBody AccountTransactionDto accountTransactionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(accountTransactionDto));

    }

    @GetMapping
    public ResponseEntity<List<AccountTransactionDto>> getTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}