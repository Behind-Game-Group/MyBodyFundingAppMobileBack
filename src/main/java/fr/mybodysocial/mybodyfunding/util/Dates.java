package fr.mybodysocial.mybodyfunding.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Dates {
    private static final String formatDate = "yyyy-MM-dd";

    public static final Date stringToDate(String dateStr) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
        return formatter.parse(dateStr);
    }

    public static final String dateToString(Date date) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
        return formatter.format(date);
    }

    public static final Date dateSqlToUtil(java.sql.Date dateSql) {
        return new Date(dateSql.getTime());
    }

    public static final java.sql.Date dateUtilToSql(Date dateUtil) {
        return new java.sql.Date(dateUtil.getTime());
    }

    public static final long dayLeft(Date firstDate, Date secondDate) {
        long diff = secondDate.getTime() - firstDate.getTime();
        TimeUnit time = TimeUnit.DAYS;
        return time.convert(diff, TimeUnit.MILLISECONDS);
    }
}
