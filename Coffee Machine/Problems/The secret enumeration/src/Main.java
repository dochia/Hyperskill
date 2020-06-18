import java.time.DayOfWeek;

public class Main {

    public static void main(String[] args) {
        int count = 0;
        String searchStr = "STAR";
        for (Secret s: Secret.values()) {
            count += s.name().startsWith(searchStr) ? 1 : 0;
        }
        System.out.println(count);

    }
}
