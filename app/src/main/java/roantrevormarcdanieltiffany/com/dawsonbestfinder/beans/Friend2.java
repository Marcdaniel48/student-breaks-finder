package roantrevormarcdanieltiffany.com.dawsonbestfinder.beans;

/**
 * Teacher bean based on json friend api call
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class Friend2 {

    private String email;
    private String firstName;
    private String lastName;

    /**
     * Three param constructor
     *
     * @param email
     *      Friend2's email
     * @param firstName
     *      Friend2's first name
     * @param lastName
     *      Friend2's last name
     */
    public Friend2(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Default no-param constructor
     */
    public Friend2() {
        this("","","");
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Friend2))
            return false;

        Friend2 fren = (Friend2) obj;

        if (getEmail() != null ? !getEmail().equals(fren.getEmail()) : fren.getEmail() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(fren.getFirstName()) : fren.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(fren.getLastName()) : fren.getLastName() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getEmail() != null ? getEmail().hashCode() : 0;
        result = 42 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 42 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - Email: " + email;
    }
}
