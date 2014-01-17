package com.appagility.j2ee.websocket.dispatcher.messages.incoming;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.appagility.j2ee.websocket.dispatcher.ResourceConverter;
import org.junit.Test;

/**
 * @author rbarefield
 */
public class DateFormatTest {


    @Test
    public void testDateFormat()
    {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, 2013);
        instance.set(Calendar.MONTH, 3);
        instance.set(Calendar.DAY_OF_MONTH, 4);
        instance.set(Calendar.HOUR, 23);

        Date time = instance.getTime();

        DateFormat df = new SimpleDateFormat(ResourceConverter.ECMA_COMPATIBLE_DATETIME_FORMAT);

        System.out.println(df.format(time));
    }

    @Test
    public void testParse() throws ParseException {

        String toParse = "2013-04-03T23:00:00.000Z";

        DateFormat df = new SimpleDateFormat(ResourceConverter.ECMA_COMPATIBLE_DATETIME_FORMAT);

        df.parse(toParse);
    }
}
