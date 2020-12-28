import org.junit.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;


public class TaskQueryTests {
    private void calendAssert(Calendar calendar, int year, int month, int day) {
        assertEquals(year, calendar.getYear());
        assertEquals(month, calendar.getMonth());
        assertEquals(day, calendar.getDay());
    }

    /**
     * We suppose that current date is 2020-11-26
     */

    @Test
    public void dateIn1024() {
        Calendar calendar = new Calendar(2020, 11, 26);
        Calendar searchDate = new Calendar(calendar.toDays() + 1024);
        calendAssert(searchDate, 2023, 9, 16);
    }

    @Test
    public void howLongAgoWasVictoryDay(){
        Calendar calendar = new Calendar(2020, 11, 26);
        calendar.addParams(-1945, -5, -9);
        calendAssert(calendar, 75, 6, 17);
    }

    @Test
    public void dayOfWeekIWasBorn(){
        Calendar calendar = new Calendar(2001, 8, 18);
        assertEquals(WeekDay.SATURDAY, calendar.getWeekDay());
    }

    @Test
    public void dayOfWeekIn17Weeks(){
        Calendar calendar = new Calendar(2020, 11, 26);
        Calendar dayOfWeekIn17Weeks = new Calendar(calendar.toDays() + 17 * 7);
        assertEquals(WeekDay.THURSDAY, dayOfWeekIn17Weeks.getWeekDay());
    }

    @Test
    public void daysBeforeNewYear(){
        Calendar calendar = new Calendar(2020, 11, 26);
        Calendar newYearDate = new Calendar(2021, 1, 1);
        assertEquals(36, Calendar.subDate(newYearDate, calendar));
    }

    @Test
    public void nextFriday13(){
        Calendar calendar = new Calendar(2020, 11, 26);
        int i = 0;
        Calendar date;
        while (true){
            date = new Calendar(calendar.toDays() + i);
            if(date.getWeekDay() == WeekDay.FRIDAY && date.getDay() == 13){
                break;
            }
            i++;
        }

        Calendar nextFriday13 = new Calendar(2021,8, 13);
        assertEquals(nextFriday13, date);
    }
}
