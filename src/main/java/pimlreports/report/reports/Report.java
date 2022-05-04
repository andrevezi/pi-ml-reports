package pimlreports.report.reports;

public interface Report {
    public void generateHeader();
    public void generateBody();
    public void generateFooter();
    public void print();
}

