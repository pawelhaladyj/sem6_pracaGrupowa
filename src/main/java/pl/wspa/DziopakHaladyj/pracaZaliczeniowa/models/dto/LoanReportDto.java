package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import java.util.Objects;

public class LoanReportDto {
    private String reportTitle;

    @Override
    public String toString() {
        return "LoanReportDto{" +
                "reportTitle='" + reportTitle + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanReportDto that = (LoanReportDto) o;
        return Objects.equals(reportTitle, that.reportTitle) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportTitle, content);
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LoanReportDto() {
    }

    public LoanReportDto(String reportTitle, String content) {
        this.reportTitle = reportTitle;
        this.content = content;
    }

    private String content;
}
