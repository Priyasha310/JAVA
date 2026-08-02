public class Seat{
    private int seatId;
    private boolean booked;

    public Seat(int seatId){
        this.seatId = seatId;
        this.booked = false;
    }

    public int getSeatId() {
        return seatId;
    }

    public boolean isBooked(){
        return booked;
    }

    public void setBooked(){
        booked = true;
    }

    @Override
    public String toString() {
        return "Seat " + seatId +
                (booked ? " (Booked)" : " (Available)");
    }
}
