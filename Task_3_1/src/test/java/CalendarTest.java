import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarTest {
    private void calendAssert(Calendar calendar, int year, int month, int day) {
        assertEquals(year, calendar.getYear());
        assertEquals(month, calendar.getMonth());
        assertEquals(day, calendar.getDay());
    }
    private void calendAssert(Calendar cal1, Calendar cal2){
        calendAssert(cal2, cal1.getYear(), cal1.getMonth(), cal1.getDay());
    }

    @Test
    public void toDaysTest() {
        Calendar cal1 = new Calendar(2020, 11, 26);
        Calendar cal2 = new Calendar(2095, 1, 1);
        assertEquals(27064, cal2.toDays() - cal1.toDays());

        Calendar cal3 = new Calendar(1975, 2, 26);
        Calendar cal4 = new Calendar(2286, 3, 13);
        assertEquals(113606, cal4.toDays() - cal3.toDays());

        Calendar cal5 = new Calendar(1975, 2, 26);
        Calendar cal6 = new Calendar(2399, 3, 13);
        assertEquals(154878, cal6.toDays() - cal5.toDays());


        Calendar cal7 = new Calendar(1975, 2, 26);
        Calendar cal8 = new Calendar(2401, 3, 13);
        assertEquals(155609, cal8.toDays() - cal7.toDays());

        Calendar cal9 = new Calendar(1975, 2, 26);
        Calendar cal10 = new Calendar(2531, 3, 13);
        assertEquals(203090, cal10.toDays() - cal9.toDays());

        Calendar cal11 = new Calendar(1975, 12, 6);
        Calendar cal12 = new Calendar(3121, 11, 30);
        assertEquals(418562,Calendar.subDate(cal12, cal11));

        Calendar cal13 = new Calendar(1, 1, 1);
        Calendar cal14 = new Calendar(3, 1, 1);
        assertEquals(365*2, Calendar.subDate(cal14, cal13));
    }

    @Test
    public void weekDayTest() {
        Calendar cal1 = new Calendar(2020, 11, 26);
        assertEquals(WeekDay.THURSDAY, cal1.getWeekDay());

        Calendar cal2 = new Calendar(2023, 6, 25);
        assertEquals(WeekDay.SUNDAY, cal2.getWeekDay());
    }

    @Test
    public void fromDaysTest() {
        Calendar cal1 = new Calendar(2020, 11, 26);
        Calendar cal2 = new Calendar(cal1.toDays());
        calendAssert(cal1, cal2);

        Calendar cal3 = new Calendar(2342, 1, 28);
        Calendar cal4 = new Calendar(cal1.toDays());
        calendAssert(cal1, cal2);
    }
}
