package com.health_donate.health.controller;

import com.health_donate.health.service.ReceiptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadReceipt(
            @RequestParam String receiptId,
            @RequestParam String userName,
            @RequestParam String pharmacy,
            @RequestParam double amount
    ) {
        byte[] pdfData = receiptService.generateReceiptPdf(receiptId, userName, pharmacy, amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "receipt_" + receiptId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfData);
    }
}

