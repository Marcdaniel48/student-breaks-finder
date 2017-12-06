package roantrevormarcdanieltiffany.com.dawsonbestfinder.beans;

/**
 * Bean class representing the current location of a user --> current course & section.
 * Used alongside the FriendFinder API.
 */
public class FriendLocation
{
    private static final String TAG = FriendLocation.class.getSimpleName();

    private String course;
    private String section;

    public FriendLocation()
    {
        this("", "");
    }

    public FriendLocation(String course, String section)
    {
        this.course = course;
        this.section = section;
    }


    public void setCourse(String course)
    {
        this.course = course;
    }

    public String getCourse()
    {
        return course;
    }

    public void setSection(String section)
    {
        this.section = section;
    }

    public String getSection()
    {
        return section;
    }

    @Override
    public String toString()
    {
        return course + ", " + section;
    }
}
