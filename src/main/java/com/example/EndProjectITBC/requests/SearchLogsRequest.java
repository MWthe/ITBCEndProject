package com.example.EndProjectITBC.requests;

import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

public class SearchLogsRequest {


    private Integer logType;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public SearchLogsRequest() {
    }

    public SearchLogsRequest(Integer logType, LocalDate dateFrom, LocalDate dateTo) {
        this.logType = logType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "SearchLogsRequest{" +
                "logType=" + logType +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
