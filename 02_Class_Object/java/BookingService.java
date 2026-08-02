import java.util.ArrayList;
import java.util.List;

public class BookingService {

    private List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void displayMovies() {

        System.out.println("\nMovies");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public void displayAvailableSeats(Screen screen) {

        System.out.println("\nAvailable Seats");
        for (Seat seat : screen.getSeats()) {
            if (!seat.isBooked()) {
                System.out.print(seat.getSeatId() + " ");
            }
        }
        System.out.println();
    }

    public void bookSeat(User user,
                         Movie movie,
                         Screen screen,
                         int seatNumber) {

        for (Seat seat : screen.getSeats()) {

            if (seat.getSeatId() == seatNumber) {
                if (seat.isBooked()) {
                    System.out.println("Seat already booked.");
                    return;
                }

                seat.setBooked();

                System.out.println("\nBooking Successful");
                System.out.println("User : " + user.getName());
                System.out.println("Movie : " + movie.getMovieName());
                System.out.println("Screen : " + screen.getScreenId());
                System.out.println("Seat : " + seatNumber);

                return;
            }
        }

        System.out.println("Invalid Seat Number");
    }
}