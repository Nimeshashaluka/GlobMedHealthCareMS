package model;

public class ReportType {

    private int reportTypeId;
    private String name;

    // Constructors
    public ReportType() {
    }

    public ReportType(int reportTypeId, String name) {
        this.reportTypeId = reportTypeId;
        this.name = name;
    }

    // Getters and Setters
    public int getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
