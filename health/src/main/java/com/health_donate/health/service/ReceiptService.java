package com.health_donate.health.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class ReceiptService {

    public byte[] generateReceiptPdf(String receiptId, String userFullName, String pharmacyName, double amountPaid) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Titre
        Paragraph title = new Paragraph("Reçu de Paiement - HealthDonate", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("\n"));

        // Corps
        document.add(new Paragraph("📄 Numéro du reçu : " + receiptId, bodyFont));
        document.add(new Paragraph("👤 Utilisateur : " + userFullName, bodyFont));
        document.add(new Paragraph("🏥 Pharmacie : " + pharmacyName, bodyFont));
        document.add(new Paragraph("💵 Montant payé : " + amountPaid + " FCFA", bodyFont));
        document.add(new Paragraph("📅 Date : " + LocalDate.now(), bodyFont));

        document.add(new Paragraph("\n\nMerci pour votre confiance.", bodyFont));

        document.close();
        return out.toByteArray();
    }
}

