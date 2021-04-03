package com.github.wolfgang.operation.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wolfgang
 * @date 2020-02-17 15:24:10
 * @version $ Id: DateUtils.java, v 0.1  wolfgang Exp $
 */
public class DateUtils {
    public static Date add(long duration, TimeUnit timeUnit) {
        return add(new Date(), duration, timeUnit);
    }

    public static Date add(Date base, long duration, TimeUnit timeUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(Calendar.SECOND, (int) timeUnit.toSeconds(duration));
        return calendar.getTime();
    }
}
