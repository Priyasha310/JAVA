import java.util.ArrayList;
import java.util.List;

public class Screen {
    private int screenId;

    private List<Seat> seats = new ArrayList<>();

    public Screen(int screenId, int totalSeats) {

        this.screenId = screenId;

        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new Seat(i));
        }
    }

    public int getScreenId() {
        return screenId;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
