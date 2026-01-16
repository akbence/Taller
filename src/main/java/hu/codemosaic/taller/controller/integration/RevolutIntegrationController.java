package hu.codemosaic.taller.controller.integration;

import hu.codemosaic.taller.controller.BaseApiController;
import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.service.RevolutIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(BaseApiController.BASE_PATH + "/integration/revolut")
@RequiredArgsConstructor
public class RevolutIntegrationController extends BaseApiController{

    private final RevolutIntegrationService revolutIntegrationService;

    @PostMapping(value = BaseApiController.BASE_PATH  + "/transaction/bulk-preview", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<AccountTransactionDto>> createTransactionsBulkPreview(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(revolutIntegrationService.createTransactionsBulkPreview(file, getCurrentUserId()));
    }

}
