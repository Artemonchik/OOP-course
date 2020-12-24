import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

import java.time.DateTimeException;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

/**
 * This class describes dates: YYYY-MM-DD from 0000-01-01
 * Never use it, use standard libraries
 */
public class Calendar {
    private int year;
    private int month;
    private int day;

    private final int[] dayNumsInMonth = {-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

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


    /**
     * Set date to year-month-day
     *
     * @param year  - integer value in [0, MAX_INT/2]
     * @param month - month in [1, 12]
     * @param day   - day in [0, MAX_DAY], where MAX_DAY depends on month
     * @apiNote you can call by for example this way: Calendar(2020, 5, 32), and date will be set in 2020-06-01.
     * this constructor implements autocorrection
     */

    public Calendar(int year, int month, int day) {
        if (year < 0 || year > Integer.MAX_VALUE / 2) {
            throw new IllegalArgumentException("Year must be between [0, MAX_INT/2]");
        }
        this.year = year;
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between [1, 12]");
        }
        this.month = month;
        setLeapState();
        if (day < 1 || day > dayNumsInMonth[month]) {
            throw new IllegalArgumentException(
                    String.format("Day must be between [1, %d] for this specified month", dayNumsInMonth[month]));
        }
        this.day = day;
    }

    /**
     * @param days number of date since the birth of christ: Calendar(1) == 0001-01-01
     */

    public Calendar(int days) {
        if (days < 0) {
            throw new IllegalArgumentException("Days must be non negative value");
        }
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
     * @param year  year you want to add
     * @param month month you want to add
     * @param day   day you want to add
     */
    public void addParams(int year, int month, int day) {
        this.year += year;
        this.year += (this.month + month - 1) / 12;
        this.month = (this.month + month - 1) % 12 + 1;
        this.day += day;
        setLeapState();
        while (this.day > dayNumsInMonth[this.month]){
            this.month++;
            this.day -= dayNumsInMonth[month];
            this.year += (this.month + month - 1) / 12;
            this.month = (this.month + month - 1) % 12 + 1;
            setLeapState();
        }
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
