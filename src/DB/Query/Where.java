package DB.Query;

public class Where {
    public String key;

    public String value;

    public Where(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Where{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
