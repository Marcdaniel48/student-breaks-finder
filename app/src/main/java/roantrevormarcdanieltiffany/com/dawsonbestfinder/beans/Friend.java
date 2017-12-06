package roantrevormarcdanieltiffany.com.dawsonbestfinder.beans;

/**
 * Bean class representing a Friend.
 * Used alongside the FriendFinder API.
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
    public String toString()
    {
        return firstname + " " + lastname + ", " + email;
    }
}
