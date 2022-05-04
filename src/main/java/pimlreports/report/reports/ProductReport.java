package pimlreports.report.reports;

import com.lowagie.text.*;
import com.lowagie.text.Font;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import pimlreports.report.entity.Product;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductReport implements Report {

    private final List<Product> productList;
    private final Document filePDF;

    public ProductReport(List<Product> productList) {
        this.productList = productList;
        this.filePDF = new Document();
        try {
            String reportPath = "ProductReport.pdf";
            PdfWriter.getInstance(this.filePDF, new FileOutputStream(reportPath));
            this.filePDF.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateHeader() {
        Paragraph paragraphTitle = new Paragraph();
        paragraphTitle.setAlignment(Element.ALIGN_CENTER);
        paragraphTitle.add(new Chunk("PRODUCT REPORT", new Font(Font.HELVETICA, 24)));
        this.filePDF.add(paragraphTitle);
        this.filePDF.add(new Paragraph(" "));


        Paragraph paragraphDate = new Paragraph();
        paragraphDate.setAlignment(Element.ALIGN_RIGHT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime todayDate = LocalDateTime.now();
        String formattedDate = todayDate.format(formatter);
        paragraphDate.add(new Chunk("Report generated : " + formattedDate));
        this.filePDF.add(paragraphDate);
        this.filePDF.add(new Paragraph());
        this.filePDF.add(new Paragraph());


        Paragraph paragraphBreak = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------");
        this.filePDF.add(paragraphBreak);
        this.filePDF.add(new Paragraph());

    }

    @Override
    public void generateBody() {
        PdfPTable tableProducts = this.createTableWithHeaders();
        this.addProductsToTable(tableProducts);
        this.filePDF.add(tableProducts);
    }

    @Override
    public void generateFooter() {

    }

    @Override
    public void print() {
        if (this.filePDF != null && this.filePDF.isOpen()) {
            this.filePDF.close();
        }
    }

    private PdfPTable createTableWithHeaders() {
        PdfPTable tableProducts = new PdfPTable(3);
        tableProducts.setWidthPercentage(98);
        tableProducts.setWidths(new float[]{2f, 1f, 1f});

        PdfPCell cellTitle = new PdfPCell(new Phrase("PRODUCT"));
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        tableProducts.addCell(cellTitle);

        cellTitle = new PdfPCell(new Phrase("PRODUCT SIZE"));
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableProducts.addCell(cellTitle);

        cellTitle = new PdfPCell(new Phrase("UNIT PRICE"));
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setBackgroundColor(Color.LIGHT_GRAY);
        tableProducts.addCell(cellTitle);

        return tableProducts;
    }

    private void addProductsToTable(PdfPTable tableProducts) {
        int count = 1;
        for (Product product : productList) {
            //Paragraph paragraphProduct = new Paragraph();
            //paragraphProduct.setAlignment(Element.ALIGN_LEFT);
            //paragraphProduct.add(new Chunk(product.getName()));
            PdfPCell cellName = new PdfPCell(new Phrase(product.getName()));
            PdfPCell cellProductSize = new PdfPCell(new Phrase(String.valueOf(product.getSize())));
            cellProductSize.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cellPrice = new PdfPCell(new Phrase(String.valueOf(product.getPrice())));
            cellProductSize.setHorizontalAlignment(Element.ALIGN_CENTER);
            //this.filePDF.add(new Paragraph());
            //this.filePDF.add(paragraphProduct);
            if (count % 2 == 0) {
                cellName.setBackgroundColor(Color.lightGray);
                cellProductSize.setBackgroundColor(Color.lightGray);
                cellPrice.setBackgroundColor(Color.lightGray);
            }
            tableProducts.addCell(cellName);
            tableProducts.addCell(cellProductSize);
            tableProducts.addCell(cellPrice);
            count++;
        }
    }
}
