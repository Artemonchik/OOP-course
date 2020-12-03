import java.util.HashMap;

public class RecordBook {
    private final String name;
    private final int group;
    private Semester currentSemester;
    private Mark qualificationWorkMark = Mark.NONE;
    private final Page[] pages = new Page[12];

    /**
     * Creates a new RecordBook
     *
     * @param name     name of the student
     * @param group    group of the student
     * @param semester semestr that student graduated from
     */
    public RecordBook(String name, int group, Semester semester) {
        this.currentSemester = semester;
        this.name = name;
        this.group = group;
        for (int i = 1; i < 12; i++) {
            pages[i] = new Page(Semester.byInt(i));
        }
    }

    /**
     * Add new subject to a Record book
     *
     * @param name     name of the subject
     * @param semester semester when the student passed this subject
     * @param mark     mark the student got
     */
    public void setSubject(String name, Semester semester, Mark mark) {
        pages[semester.getValue()].subjects.put(name, mark);
    }

    /**
     * Set mark of qualification work to the Record Book
     *
     * @param mark mark of qualification work
     */
    public void setQualificationWorkMark(Mark mark) {
        if (!mark.isDiff()) {
            throw new IllegalArgumentException("Mark should be 5 or 4 or 3");
        }
        this.qualificationWorkMark = mark;
    }

    /**
     * @return average score for the whole semesters
     */
    public double getAverageScore() {
        double markSum = 0;
        int subjectCount = 0;
        for (int i = 1; i <= 11; i++) {
            Page page = pages[i];
            for (Mark mark : page.subjects.values()) {
                if (!mark.isDiff()) {
                    continue;
                }
                markSum += mark.getValue();
                subjectCount++;
            }
        }
        return markSum / subjectCount;
    }

    /**
     * @return true if student can get red diploma, false otherwise
     */
    public boolean isRedDiploma() {
        HashMap<String, Mark> attachedMarks = new HashMap<>();

        for (int i = 1; i <= 11; i++) {
            Page page = pages[i];
            for (String subjectName : page.subjects.keySet()) {
                attachedMarks.put(subjectName, page.subjects.get(subjectName));
            }
        }

        int countAMarks = 0;
        int countDiffMarks = 0;
        for (String subjectName : attachedMarks.keySet()) {
            Mark mark = attachedMarks.get(subjectName);
            if (mark.isDiff()) {
                countDiffMarks++;
            }
            if (mark == Mark.A) {
                countAMarks++;
            }
            if (mark == Mark.C) {
                return false;
            }
        }
        if (qualificationWorkMark != Mark.A && qualificationWorkMark != Mark.NONE) {
            return false;
        }
        return countAMarks * 100 / countDiffMarks > 75;

    }

    /**
     * @return true if student can get increased Scholarship for the semester he passed, false otherwise
     */
    public boolean isIncreasedScholarship() {
        Page page = pages[currentSemester.getValue()];
        for (Mark mark : page.subjects.values()) {
            if (!mark.isHighest()) {
                return false;
            }
        }
        return true;
    }

    public int getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public Semester getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(Semester currentSemester) {
        this.currentSemester = currentSemester;
    }

    private static class Page { // Can be added addition information later, that's why it is private class with unused fields
        private final Semester semester;
        private final HashMap<String, Mark> subjects = new HashMap<>();

        Page(Semester semester) {
            this.semester = semester;
        }
    }
}
