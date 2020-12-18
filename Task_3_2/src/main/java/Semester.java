public enum Semester {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8),
    NINTH(9),
    TENTH(10),
    ELEVENTH(11);
    private final int value;

    Semester(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Semester byInt(int i) {
        for (Semester semester : Semester.values()) {
            if (semester.getValue() == i) {
                return semester;
            }
        }
        return null;
    }
}
