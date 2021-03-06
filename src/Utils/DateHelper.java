package Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    public static boolean valida (Date start) {
        Calendar aux = GregorianCalendar.getInstance();
        aux.setTime( start);
        aux.add( GregorianCalendar.SECOND, 30 );
        Date aux2 = aux.getTime();

        return new Date().before(aux2);
    }
}
