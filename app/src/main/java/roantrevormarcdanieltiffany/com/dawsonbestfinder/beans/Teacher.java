package roantrevormarcdanieltiffany.com.dawsonbestfinder.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Teacher bean based on json file
 * given for this assignment
 *
 * @since 2017-11-19.
 */
public class Teacher {
    private String first_name;
    private String last_name;
    private String full_name;
    private String local;
    private String office;
    private String email;
    private List<String> departments;
    private List<String> positions;
    private List<String> sectors;

    /**
     * Full constructor, parameters based on json file
     * given for this assignment
     *
     * @param firstName
     *      Teacher's first name
     * @param lastName
     *      Teacher's last name
     * @param fullName
     *      Teacher's full name
     * @param local
     *      Teacher's local
     * @param office
     *      Teacher's office (where students can visit for questions)
     * @param email
     *      Teacher's contact email
     * @param departments
     *      Teacher's departments (ex: Fine Arts)
     * @param positions
     *      Teacher's positions (ex: Lab Technician)
     * @param sectors
     *      Teacher's sectors (ex: Sector: Creative & Applied Arts)
     */
    public Teacher(String firstName, String lastName, String fullName, String local, String office, String email, List<String> departments, List<String> positions, List<String> sectors) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.full_name = fullName;
        this.local = local;
        this.office = office;
        this.email = email;
        this.departments = departments;
        this.positions = positions;
        this.sectors = sectors;
    }

    /**
     * Default constructor
     */
    public Teacher() {
        this("","","","","","",new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public List<String> getSectors() {
        return sectors;
    }

    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;

        Teacher teacher = (Teacher) o;

        if (getFirst_name() != null ? !getFirst_name().equals(teacher.getFirst_name()) : teacher.getFirst_name() != null)
            return false;
        if (getLast_name() != null ? !getLast_name().equals(teacher.getLast_name()) : teacher.getLast_name() != null)
            return false;
        if (getFull_name() != null ? !getFull_name().equals(teacher.getFull_name()) : teacher.getFull_name() != null)
            return false;
        if (getLocal() != null ? !getLocal().equals(teacher.getLocal()) : teacher.getLocal() != null)
            return false;
        if (getOffice() != null ? !getOffice().equals(teacher.getOffice()) : teacher.getOffice() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(teacher.getEmail()) : teacher.getEmail() != null)
            return false;
        if (getDepartments() != null ? !getDepartments().equals(teacher.getDepartments()) : teacher.getDepartments() != null)
            return false;
        if (getPositions() != null ? !getPositions().equals(teacher.getPositions()) : teacher.getPositions() != null)
            return false;
        return getSectors() != null ? getSectors().equals(teacher.getSectors()) : teacher.getSectors() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirst_name() != null ? getFirst_name().hashCode() : 0;
        result = 31 * result + (getLast_name() != null ? getLast_name().hashCode() : 0);
        result = 31 * result + (getFull_name() != null ? getFull_name().hashCode() : 0);
        result = 31 * result + (getLocal() != null ? getLocal().hashCode() : 0);
        result = 31 * result + (getOffice() != null ? getOffice().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getDepartments() != null ? getDepartments().hashCode() : 0);
        result = 31 * result + (getPositions() != null ? getPositions().hashCode() : 0);
        result = 31 * result + (getSectors() != null ? getSectors().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", local='" + local + '\'' +
                ", office='" + office + '\'' +
                ", email='" + email + '\'' +
                ", departments=" + departments +
                ", positions=" + positions +
                ", sectors=" + sectors +
                '}';
    }
}
