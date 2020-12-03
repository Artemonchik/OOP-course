import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecordBookTest {
    @Test
    public void myRBtest() {
        RecordBook recordBook = new RecordBook("Artem Tarasov", 19214, Semester.SECOND);
        recordBook.setSubject("введение в алгебру и анализ", Semester.FIRST, Mark.A);
        recordBook.setSubject("введение в дискретную математику и математическую логику", Semester.FIRST, Mark.A);
        recordBook.setSubject("декларативное программирование", Semester.FIRST, Mark.A);
        recordBook.setSubject("императивное программирование", Semester.FIRST, Mark.A);
        recordBook.setSubject("иностранный язык", Semester.FIRST, Mark.ACCEPTED);
        recordBook.setSubject("основы культуры речи", Semester.FIRST, Mark.A);
        recordBook.setSubject("история", Semester.FIRST, Mark.A);
        recordBook.setSubject("физическая культура и спорт (элективная дисциплина)", Semester.FIRST, Mark.ACCEPTED);
        recordBook.setSubject("физическая культура и спорт", Semester.FIRST, Mark.ACCEPTED);
        recordBook.setSubject("цифровые платформы", Semester.FIRST, Mark.ACCEPTED);

        recordBook.setSubject("введение в алгебру и анализ", Semester.SECOND, Mark.A);
        recordBook.setSubject("введение в дискретную математику и математическую логику", Semester.SECOND, Mark.A);
        recordBook.setSubject("декларативное программирование", Semester.SECOND, Mark.A);
        recordBook.setSubject("императивное программирование", Semester.SECOND, Mark.A);
        recordBook.setSubject("иностранный язык", Semester.SECOND, Mark.A);
        recordBook.setSubject("физическая культура и спорт (элективная дисциплина)", Semester.SECOND, Mark.ACCEPTED);
        recordBook.setSubject("физическая культура и спорт", Semester.SECOND, Mark.ACCEPTED);
        recordBook.setSubject("цифровые платформы", Semester.SECOND, Mark.A);

        assertTrue(recordBook.isRedDiploma());
        assertTrue(recordBook.isIncreasedScholarship());
        assertEquals(5, recordBook.getAverageScore());
        recordBook.setQualificationWorkMark(Mark.B);
        assertFalse(recordBook.isRedDiploma());
    }

    @Test
    public void RBtest1() {
        RecordBook recordBook = new RecordBook("Artem Tarasov", 19214, Semester.SECOND);
        recordBook.setSubject("введение в алгебру и анализ", Semester.FIRST, Mark.B);
        recordBook.setSubject("введение в дискретную математику и математическую логику", Semester.FIRST, Mark.A);
        recordBook.setSubject("декларативное программирование", Semester.FIRST, Mark.A);
        recordBook.setSubject("императивное программирование", Semester.FIRST, Mark.B);
        recordBook.setSubject("иностранный язык", Semester.FIRST, Mark.ACCEPTED);
        recordBook.setSubject("основы культуры речи", Semester.FIRST, Mark.B);
        recordBook.setSubject("история", Semester.FIRST, Mark.A);
        recordBook.setSubject("физическая культура и спорт (элективная дисциплина)", Semester.FIRST, Mark.ACCEPTED);
        recordBook.setSubject("физическая культура и спорт", Semester.FIRST, Mark.ACCEPTED);
        recordBook.setSubject("цифровые платформы", Semester.FIRST, Mark.ACCEPTED);

        recordBook.setSubject("введение в алгебру и анализ", Semester.SECOND, Mark.B);
        recordBook.setSubject("введение в дискретную математику и математическую логику", Semester.SECOND, Mark.A);
        recordBook.setSubject("декларативное программирование", Semester.SECOND, Mark.B);
        recordBook.setSubject("императивное программирование", Semester.SECOND, Mark.B);
        recordBook.setSubject("иностранный язык", Semester.SECOND, Mark.B);
        recordBook.setSubject("физическая культура и спорт (элективная дисциплина)", Semester.SECOND, Mark.ACCEPTED);
        recordBook.setSubject("физическая культура и спорт", Semester.SECOND, Mark.ACCEPTED);
        recordBook.setSubject("цифровые платформы", Semester.SECOND, Mark.B);

        assertFalse(recordBook.isRedDiploma());
        assertFalse(recordBook.isIncreasedScholarship());
        assertTrue(4.3 < recordBook.getAverageScore() && recordBook.getAverageScore() < 4.4);
    }

    @Test
    public void test3(){
        RecordBook recordBook = new RecordBook("Temchik", 12234, Semester.ELEVENTH);
        recordBook.setSubject("Subject1", Semester.FIFTH, Mark.B);
        recordBook.setSubject("Subject2", Semester.SECOND, Mark.B);
        recordBook.setSubject("Subject3", Semester.THIRD, Mark.B);
        recordBook.setSubject("Subject4", Semester.FOURTH, Mark.B);
        recordBook.setSubject("Subject5", Semester.FIFTH, Mark.A);
        recordBook.setSubject("Subject6", Semester.SIXTH, Mark.B);
        recordBook.setSubject("Subject7", Semester.SEVENTH, Mark.A);
        recordBook.setSubject("Subject8", Semester.EIGHTH, Mark.A);
        recordBook.setSubject("Subject4", Semester.NINTH, Mark.A);
        recordBook.setSubject("Subject3", Semester.TENTH, Mark.A);
        recordBook.setSubject("Subject2", Semester.ELEVENTH, Mark.A);
        recordBook.setQualificationWorkMark(Mark.A);

        assertTrue(recordBook.isIncreasedScholarship());
        assertFalse(recordBook.isRedDiploma());
        assertTrue(4.5 < recordBook.getAverageScore() && recordBook.getAverageScore() < 4.6);
    }
}

