package roantrevormarcdanieltiffany.com.dawsonbestfinder.beans;

/**
 * Created by sirMerr on 2017-12-06.
 */

public class QueryParam {
    private String key;
    private String value;

    public QueryParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public QueryParam() {
        this("", "");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryParam)) return false;

        QueryParam that = (QueryParam) o;

        if (getKey() != null ? !getKey().equals(that.getKey()) : that.getKey() != null)
            return false;
        return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueryParam{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
