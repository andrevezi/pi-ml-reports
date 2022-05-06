package pimlreports.report.reports;

public interface SalesReportInterface {
    public void generateHeader();
    public void generateBody();
    public void generateFooter();
    public void print();
}
