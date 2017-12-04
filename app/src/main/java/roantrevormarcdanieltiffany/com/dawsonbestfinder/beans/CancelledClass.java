package roantrevormarcdanieltiffany.com.dawsonbestfinder.beans;

/**
 * CancelledClass
 * bean to hold information about classes that have been cancelled
 * including title of the course, course number, course teacher and the day that it is cancelled on
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 *
 */
public class CancelledClass {
    private final String title;
    private final String code;
    private final String teacher;
    private final String date;


    /**
     * Constructor for the cancelled class bean
     * @param title title of the course
     * @param code code of the course
     * @param teacher
     * @param date
     */
    public CancelledClass(String title, String code, String teacher, String date){
        this.title = title;
        this.code = code;
        this.teacher = teacher;
        this.date = date;
    }

    /**
     * getter for the title of the course
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for the code of the course
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * getter for the teacher of the course
     * @return
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * getter for the date of the courses cancellation
     * @return
     */
    public String getDate() {
        return date;
    }




}
