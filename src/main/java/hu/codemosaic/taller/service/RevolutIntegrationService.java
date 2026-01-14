package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.AccountTransactionDb;
import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.db.CategoryDb;
import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.enums.Currency;
import hu.codemosaic.taller.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static hu.codemosaic.taller.util.MapperUtil.mapCategoryEntityToCategoryDto;
import static hu.codemosaic.taller.util.MapperUtil.mapUserAccountEntityToAccountDto;

@Service
@RequiredArgsConstructor
@Log
public class RevolutIntegrationService {

    private final AccountTransactionDb accountTransactionDb;
    private final CategoryDb categoryDb;
    private final AppUserDb appUserDb;


    public List<AccountTransactionDto> createTransactionsBulkPreview(MultipartFile file, UUID currentUserId) {
        List<AccountTransactionDto> previewList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {

                // skip header
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");

                try {
                    String type = data[0].trim();
                    String product = data[1].trim(); // not used currently
                    Instant startedDate = Instant.now();
                    if (data.length > 6 && !data[2].isEmpty()) {
                        try { startedDate = Instant.parse(data[6].trim()); } catch (DateTimeParseException e) {
                            //todo: log warning
                        }
                    }
                    String completedDate = data[3].trim(); // not used currently
                    String description = data[4].trim();
                    BigDecimal amount = new BigDecimal(data[5].trim()).abs();
                    String fee = data[6].trim(); // not used currently
                    Currency currency = Currency.valueOf(data[7].trim().toUpperCase());
                    String state = data[8].trim(); // not used currently
                    BigDecimal balance = new BigDecimal(data[9].trim());

                    TransactionType transactionType = calcTransactionType(type, description, amount);

                    AccountTransactionDto dto = AccountTransactionDto.builder()
                            .description(description)
                            .amount(amount)
                            .currency(currency)
                            .transactionType(transactionType)
                            .transactionTime(startedDate)
                            .build();

                    previewList.add(dto);

                } catch (Exception e) {
                    log.warning("Line process error: " + line + " -> " + e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during CSV file reading: " + e.getMessage());
        }

        return previewList;
    }

    private TransactionType calcTransactionType(String type, String description, BigDecimal amount) {
        switch (type) {
            case "Card Payment":
                return TransactionType.EXPENSE;
            case "Transfer":
                if(description.contains("Transfer to")) {
                    return TransactionType.EXPENSE;
                }
                if(description.contains("Transfer from")) {
                    return TransactionType.REIMBURSEMENT;
                }
            default:
                return null; // or throw an exception if type is unrecognized
        }
    }
}
