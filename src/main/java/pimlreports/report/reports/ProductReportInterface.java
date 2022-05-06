package pimlreports.report.reports;

public interface ProductReportInterface {
    public void generateHeader(String name);
    public void generateBody();
    public void generateFooter();
    public void print();
}

