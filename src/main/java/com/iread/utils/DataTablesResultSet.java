package com.iread.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class DataTablesResultSet<T> implements WebResultSet<T> {
    private final String sEcho;
    private final Long iTotalRecords;
    private final Long iTotalDisplayRecords;
    private final List<T> aaData;

    public DataTablesResultSet(ResultSet<T> rs) {
        this.sEcho = "";
        this.aaData = rs.getRows();
        this.iTotalRecords = rs.getTotalRecords();
        this.iTotalDisplayRecords = rs.getTotalRecords();
    }

    @Override
    public String getsEcho() {
        return sEcho;
    }

    @Override
    @JsonProperty("iTotalRecords")
    public Long getTotalRecords() {
        return iTotalRecords;
    }

    @Override
    @JsonProperty("iTotalDisplayRecords")
    public Long getTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    @Override
    public List<T> getAaData() {
        return Collections.unmodifiableList(aaData);
    }
}