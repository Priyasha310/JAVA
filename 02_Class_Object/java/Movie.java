public class Movie {
    private int movieId;
    private String movieName;
    private int duration;

    public Movie(int movieId, String movieName, int duration){
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
