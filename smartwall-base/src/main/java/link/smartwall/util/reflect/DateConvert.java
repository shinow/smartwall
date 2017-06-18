package link.smartwall.util.reflect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert implements IConvert<Date> {

    @Override
    public Date convert(Object value) {
    	Date date = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			date= df.parse(String.valueOf(value));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return date;
    }
}
