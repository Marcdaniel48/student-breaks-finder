package roantrevormarcdanieltiffany.com.dawsonbestfinder.entities;

import android.util.Log;

/**
 * Bean class representing a Friend.
 * Used alongside the FriendFinder API.
 *
 * @author Tiffany Le-Nguyen
 * @author Roan Chamberlain
 * @author Marc-Daniel Dialogo
 * @author Trevor Eames
 */
public class Friend
{
    private static final String TAG = Friend.class.getSimpleName();

    private String email;
    private String firstname;
    private String lastname;

    public Friend()
    {
        this("","","");
    }

    public Friend(String email, String firstname, String lastname)
    {
        Log.d(TAG, "Friend bean instantiated.");
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getLastname()
    {
        return lastname;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Friend))
            return false;

        Friend fren = (Friend) obj;

        if (getEmail() != null ? !getEmail().equals(fren.getEmail()) : fren.getEmail() != null)
            return false;
        if (getFirstname() != null ? !getFirstname().equals(fren.getFirstname()) : fren.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(fren.getLastname()) : fren.getLastname() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getEmail() != null ? getEmail().hashCode() : 0;
        result = 42 * result + (getFirstname() != null ? getFirstname().hashCode() : 0);
        result = 42 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return firstname + " " + lastname + ", " + email;
    }


}
