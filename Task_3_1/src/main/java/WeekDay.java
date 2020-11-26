import java.util.NoSuchElementException;

public enum WeekDay {
    SUNDAY(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    private final int weekNumber;

    WeekDay(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public static WeekDay getWeekDayByCode(int code) {
        for (WeekDay weekDay : WeekDay.values()) {
            if (weekDay.getWeekNumber() == code) {
                return weekDay;
            }
        }
        throw new NoSuchElementException("There is no such weekday by this code");
    }
}
