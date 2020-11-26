import java.time.DateTimeException;
import java.util.Objects;

/**
 * This class describes dates: YYYY-MM-DD from 0000-01-01
 * Never use it, use standard libraries
 */
public class Calendar {
    private int year;
    private int month;
    private int day;

    private final int[] dayNumsInMonth = {-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * Set date to year-month-day
     *
     * @param year  - integer value in [0, MAX_INT/2]
     * @param month - month in [1, MAX_INT]
     * @param day   - day in [0, MAX_INT]
     * @apiNote you can call by for example this way: Calendar(2020, 5, 32), and date will be set in 2020-06-01.
     * this constructor implements autocorrection
     */

    public Calendar(int year, int month, int day) {
//        year += (month - 1) / 12 + 1;
//        month = (month - 1) % 12 + 1;
        this.year = year;
        this.month = month;
        this.day = day;

    }

    /**
     * @param days number of date since the birth of christ: Calendar(1) == 0001-01-01
     */

    public Calendar(int days) {
        year = 1;
        year += (days - 1) / (400 * 365 + 97) * 400;
        days = (days - 1) % (400 * 365 + 97) + 1;
        year += (days - 1) / (365 * 4 + 1) * 4;
        days = (days - 1) % (365 * 4 + 1) + 1;
        year += (days - 1) / 365;
        days = (days - 1) % 365 + 1;

        setLeapState();
        month = 1;
        while (days > dayNumsInMonth[month]) {
            days -= dayNumsInMonth[month];
            month++;
        }
        day = days;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    private int leapCount() {
        return (year - 1) / 4 - (year - 1) / 100 + (year - 1) / 400;
    }

    private boolean isLeap() {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);

    }

    private void setLeapState() {
        if (isLeap()) {
            dayNumsInMonth[2] = 29;
        } else {
            dayNumsInMonth[2] = 28;
        }
    }

    public int toDays() {
        int days = 0;
        days += (year - 1) * 365 + leapCount();
        setLeapState();
        for (int i = 1; i < month; i++) {
            days += dayNumsInMonth[i];
        }
        days += day;
        return days;
    }


    public WeekDay getWeekDay() {
        return WeekDay.getWeekDayByCode(this.toDays() % 7);
    }

    /**
     * @param date1 the date you want to subtract from
     * @param date2 the date you want to subtract
     * @return difference between current date and date in params (current date - date)
     */
    public static int subDate(Calendar date1, Calendar date2) { // Should i implement this methods as static or not???
        return date1.toDays() - date2.toDays();
    }

    /**
     * @param calendar the Calendar instance you want to add params
     * @param year     year you want to add
     * @param month    month you want to add
     * @param day      day you want to add
     * @return new Calendar instance with increased params from calendar
     */
    public static Calendar addParams(Calendar calendar, int year, int month, int day) {
        Calendar tempDate = new Calendar(year, month, day);
        int days = calendar.toDays() + tempDate.toDays();
        return new Calendar(days);
    }

    /**
     * @param calendar the Calendar instance you want to subtract params
     * @param year     year you want to subtract
     * @param month    month you want to subtract
     * @param day      day you want to subtract
     * @return new Calendar instance with reduced params from calendar
     */
    public static Calendar subParams(Calendar calendar, int year, int month, int day) {
        Calendar tempDate = new Calendar(year, month, day);
        int days = calendar.toDays() - tempDate.toDays();
        if (days < 0) {
            throw new DateTimeException("calendar date should be greater then date from other params");
        }
        return new Calendar(days);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calendar calendar = (Calendar) o;
        return year == calendar.year &&
                month == calendar.month &&
                day == calendar.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

}
