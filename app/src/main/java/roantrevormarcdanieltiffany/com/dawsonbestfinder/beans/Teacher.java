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
    private String firstName;
    private String lastName;
    private String fullName;
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
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

        if (getFirstName() != null ? !getFirstName().equals(teacher.getFirstName()) : teacher.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(teacher.getLastName()) : teacher.getLastName() != null)
            return false;
        if (getFullName() != null ? !getFullName().equals(teacher.getFullName()) : teacher.getFullName() != null)
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
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
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
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", local='" + local + '\'' +
                ", office='" + office + '\'' +
                ", email='" + email + '\'' +
                ", departments=" + departments +
                ", positions=" + positions +
                ", sectors=" + sectors +
                '}';
    }
}
