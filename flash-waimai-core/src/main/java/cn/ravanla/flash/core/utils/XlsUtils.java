package cn.ravanla.flash.core.utils;

import java.util.Date;

public class XlsUtils {
    public String dateFmt(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        return DateUtil.formatDate(date,fmt);
    }
}
