package org.fishbone.dailycosts.utils;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.fishbone.dailycosts.models.Purchase;
import org.springframework.stereotype.Component;

@Component
public class FilesExporter {

    public void setResponseHeader(HttpServletResponse response, String contentType, String extension, String prefix) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = dateFormat.format(new Date());
        String fileName = prefix + timeStamp + extension;

        response.setContentType(contentType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=" + fileName;

        response.setHeader(headerKey, headerValue);
    }

    public void exportToPDF(List<Purchase> purchaseList, Double totalSum, HttpServletResponse response) throws IOException {
        setResponseHeader(response, "application/pdf", ".pdf", "Purchases_");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph para = new Paragraph("Total expenses", font);
        para.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(para);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        writeTotalHeader(table);
        writeTotalData(table, purchaseList);
        document.add(table);

        font.setSize(16);
        font.setColor(Color.RED);
        para = new Paragraph("Total sum: " + totalSum , font);
        para.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(para);

        font.setSize(18);
        font.setColor(Color.BLACK);

        para = new Paragraph("List of purchases", font);
        para.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(para);

        table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);

        writePurchaseHeader(table);
        writePurchaseData(table, purchaseList);
        document.add(table);

        document.close();
    }

    private void writePurchaseData(PdfPTable table, List<Purchase> purchaseList) {
        for (Purchase purchase : purchaseList) {
            table.addCell(purchase.getProductName());
            table.addCell(purchase.getProductCategory());
            table.addCell(String.valueOf(purchase.getPrice()));
            table.addCell(purchase.getFormattedDate());
        }
    }

    private void writePurchaseHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.ORANGE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Product", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Category", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);
    }

    private void writeTotalHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.ORANGE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Category", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total Sum", font));
        table.addCell(cell);
    }

    private void writeTotalData(PdfPTable table, List<Purchase> purchaseList) {
        Map<String, Double> totalCosts = purchaseList
            .stream()
            .collect(Collectors.groupingBy(Purchase::getProductCategory, Collectors.summingDouble(Purchase::getPrice)));

        totalCosts.forEach((category,price)  -> {
            table.addCell(category);
            table.addCell(String.valueOf(price));
        });
    }
}
