package model;

public class ReportItem {

    private int reportItemId;
    private Report report;
    private String description;
    private String value;

    // Constructors
    public ReportItem() {
    }

    public ReportItem(int reportItemId, Report report, String description, String value) {
        this.reportItemId = reportItemId;
        this.report = report;
        this.description = description;
        this.value = value;
    }

    // Getters and Setters
    public int getReportItemId() {
        return reportItemId;
    }

    public void setReportItemId(int reportItemId) {
        this.reportItemId = reportItemId;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
