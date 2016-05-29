package com.iread.utils;

import java.util.List;

public interface WebResultSet<T> {
    Long getTotalRecords();
    Long getTotalDisplayRecords();
    List<T> getAaData();
    String getsEcho();
}
