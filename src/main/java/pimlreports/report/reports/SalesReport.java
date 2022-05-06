package pimlreports.report.reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pimlreports.report.dto.SoldProductsDTO;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SalesReport implements SalesReportInterface {

    private final List<SoldProductsDTO> saleList;
    private final Document filePDF;

    public SalesReport(List<SoldProductsDTO> saleList) {
        this.saleList = saleList;
        this.filePDF = new Document();
        try {
            String reportPath = "SalesReport.pdf";
            PdfWriter.getInstance(this.filePDF, new FileOutputStream(reportPath));
            this.filePDF.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateHeader() {
        this.addImage("ML.png");
        this.jumpLine();
        this.jumpLine();
        Paragraph paragraphTitle = new Paragraph();
        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
        paragraphTitle.add(new Chunk("SALES REPORT", new Font(Font.HELVETICA, 24)));
        this.filePDF.add(paragraphTitle);
        this.jumpLine();

        Paragraph paragraphDate = new Paragraph();
        paragraphDate.setAlignment(Element.ALIGN_RIGHT);
        paragraphDate.add(new Chunk("Report generated : " + this.addFormattedDate()));
        this.filePDF.add(paragraphDate);
        this.addBreak();
    }

    @Override
    public void generateBody() {
        PdfPTable tableProducts = this.createTableWithHeaders();
        this.addProductsToTable(tableProducts);
        this.filePDF.add(tableProducts);
    }

    @Override
    public void generateFooter() {
        this.addBreak();
        this.jumpLine();
        this.addFooter();

    }

    @Override
    public void print() {
        if (this.filePDF != null && this.filePDF.isOpen()) {
            this.filePDF.close();
        }
    }

    private PdfPTable createTableWithHeaders() {
        PdfPTable tableProducts = new PdfPTable(5);
        tableProducts.setWidthPercentage(98);
        tableProducts.setWidths(new float[]{1f, 2f, 1f, 1f, 1f});


        PdfPCell cellTitle = new PdfPCell(new Phrase("PRODUCT ID"));
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        tableProducts.addCell(cellTitle);

        cellTitle = new PdfPCell(new Phrase("PRODUCT"));
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableProducts.addCell(cellTitle);

        cellTitle = new PdfPCell(new Phrase("PRODUCT QTY"));
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableProducts.addCell(cellTitle);

        cellTitle = new PdfPCell(new Phrase("UNIT PRICE"));
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        tableProducts.addCell(cellTitle);

        cellTitle = new PdfPCell(new Phrase("TOTAL"));
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableProducts.addCell(cellTitle);

        return tableProducts;
    }

    private void jumpLine() {
        this.filePDF.add(new Paragraph(" "));
    }

    private void addProductsToTable(PdfPTable tableProducts) {
        int count = 1;
        for (SoldProductsDTO sale : saleList) {

            PdfPCell cellID = new PdfPCell(new Phrase(sale.getProduct_id().toString()));
            cellID.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cellName = new PdfPCell(new Phrase(sale.getName()));
            PdfPCell cellQty = new PdfPCell(new Phrase(String.valueOf(sale.getQuantity())));
            cellQty.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cellPrice = new PdfPCell(new Phrase("R$"+String.valueOf(sale.getPrice())));
            cellPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cellTotal = new PdfPCell(new Phrase("R$"+String.valueOf(sale.getTotal())));
            cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);

            if (count % 2 == 0) {
                cellID.setBackgroundColor(Color.lightGray);
                cellName.setBackgroundColor(Color.lightGray);
                cellQty.setBackgroundColor(Color.lightGray);
                cellPrice.setBackgroundColor(Color.lightGray);
                cellTotal.setBackgroundColor(Color.lightGray);
            }
            tableProducts.addCell(cellID);
            tableProducts.addCell(cellName);
            tableProducts.addCell(cellQty);
            tableProducts.addCell(cellPrice);
            tableProducts.addCell(cellTotal);
            count++;
        }
    }

    private void addImage(String imgPath) {
        Image imgTitle = null;
        try {
            imgTitle = Image.getInstance(imgPath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "IMG Not Found", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        if (imgTitle != null) {
            imgTitle.setAlignment(Element.ALIGN_RIGHT);
            this.filePDF.add(imgTitle);
        }
    }

    private void addFooter() {
        Paragraph footer = new Paragraph();
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.add(new Chunk("www.mercadolivre.com.br", new Font(Font.TIMES_ROMAN, 14)));
        this.filePDF.add(footer);
    }

    private void addBreak() {
        Paragraph paragraphBreak = new Paragraph("__________________________________________________________");
        paragraphBreak.setAlignment(Element.ALIGN_CENTER);
        this.jumpLine();
        this.filePDF.add(paragraphBreak);
        this.jumpLine();
    }
    private String addFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime todayDate = LocalDateTime.now();
        return todayDate.format(formatter);
    }
}
