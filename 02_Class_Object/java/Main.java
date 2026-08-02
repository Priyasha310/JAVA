// MovieTicketBooking
// │
// ├── Movie.java
// ├── Screen.java
// ├── Seat.java
// ├── User.java
// ├── BookingService.java
// └── Main.java

public class Main {

    public static void main(String[] args) {

        BookingService service = new BookingService();
        Movie movie = new Movie(
                1,
                "Interstellar",
                169
        );
        service.addMovie(movie);
        
        Screen screen = new Screen(
                1,
                10
        );

        User user1 = new User(
                101,
                "Rahul"
        );
        User user2 = new User(
                102,
                "Priya"
        );

        service.displayMovies();
        service.displayAvailableSeats(screen);
        service.bookSeat(user1,
                movie,
                screen,
                4);
        service.bookSeat(user2,
                movie,
                screen,
                4);
        service.displayAvailableSeats(screen);
    }
}
