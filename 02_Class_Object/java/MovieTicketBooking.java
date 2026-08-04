// MovieTicketBooking - Single File Version
import java.util.ArrayList;
import java.util.List;

public class MovieTicketBooking {

    public static void main(String[] args) {

        BookingService service = new BookingService();
        Movie movie = new Movie(1, "Interstellar", 169);
        service.addMovie(movie);

        Screen screen = new Screen(1, 10);

        User user1 = new User(101, "Rahul");
        User user2 = new User(102, "Priya");

        service.displayMovies();
        service.displayAvailableSeats(screen);
        service.bookSeat(user1, movie, screen, 4);
        service.bookSeat(user2, movie, screen, 4);
        service.displayAvailableSeats(screen);
    }

    static class BookingService {
        private final List<Movie> movies = new ArrayList<>();

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

        public void bookSeat(User user, Movie movie, Screen screen, int seatNumber) {
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

    static class Movie {
        private int movieId;
        private String movieName;
        private int duration;

        public Movie(int movieId, String movieName, int duration) {
            this.movieId = movieId;
            this.movieName = movieName;
            this.duration = duration;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        @Override
        public String toString() {
            return "Movie{" +
                    "id=" + movieId +
                    ", name='" + movieName + '\'' +
                    ", duration=" + duration + " mins}";
        }
    }

    static class Screen {
        private int screenId;
        private final List<Seat> seats = new ArrayList<>();

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

    static class Seat {
        private int seatId;
        private boolean booked;

        public Seat(int seatId) {
            this.seatId = seatId;
            this.booked = false;
        }

        public int getSeatId() {
            return seatId;
        }

        public boolean isBooked() {
            return booked;
        }

        public void setBooked() {
            booked = true;
        }

        @Override
        public String toString() {
            return "Seat " + seatId + (booked ? " (Booked)" : " (Available)");
        }
    }

    static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
