import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotSystem {

    // =========================
    // ENUMS
    // =========================

    enum VehicleType {
        BIKE,
        CAR,
        TRUCK
    }

    enum SpotType {
        BIKE,
        CAR,
        TRUCK
    }

    enum PaymentMethod {
        CASH,
        CARD,
        UPI
    }

    // =========================
    // VEHICLE
    // =========================

    static class Vehicle {
        private final int vehicleId;
        private final String vehicleNumber;
        private final VehicleType vehicleType;

        public Vehicle(int vehicleId, String vehicleNumber, VehicleType vehicleType) {
            this.vehicleId = vehicleId;
            this.vehicleNumber = vehicleNumber;
            this.vehicleType = vehicleType;
        }

        public int getVehicleId() {
            return vehicleId;
        }

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public VehicleType getVehicleType() {
            return vehicleType;
        }

        @Override
        public String toString() {
            return vehicleNumber + " (" + vehicleType + ")";
        }
    }

    // =========================
    // PARKING SPOT
    // =========================

    static class ParkingSpot {
        private final int spotId;
        private final SpotType spotType;
        private final double charge;
        private Vehicle vehicle;

        public ParkingSpot(int spotId, SpotType spotType, double charge) {
            this.spotId = spotId;
            this.spotType = spotType;
            this.charge = charge;
        }

        public int getSpotId() {
            return spotId;
        }

        public SpotType getSpotType() {
            return spotType;
        }

        public double getCharge() {
            return charge;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public boolean isAvailable() {
            return vehicle == null;
        }

        public boolean canPark(Vehicle vehicle) {
            return isAvailable()
                    && spotType.name().equals(vehicle.getVehicleType().name());
        }

        public void parkVehicle(Vehicle vehicle) {
            if (!isAvailable()) {
                throw new IllegalStateException("Parking spot is already occupied.");
            }

            if (!canPark(vehicle)) {
                throw new IllegalArgumentException(
                        "Vehicle type does not match spot type."
                );
            }

            this.vehicle = vehicle;
        }

        public void removeVehicle() {
            this.vehicle = null;
        }

        @Override
        public String toString() {
            return "Spot " + spotId +
                    " [" + spotType + "] - " +
                    (isAvailable() ? "AVAILABLE" : "OCCUPIED");
        }
    }

    // =========================
    // PARKING FLOOR
    // =========================

    static class ParkingFloor {
        private final int floorId;
        private final List<ParkingSpot> parkingSpots;

        public ParkingFloor(int floorId) {
            this.floorId = floorId;
            this.parkingSpots = new ArrayList<>();
        }

        public int getFloorId() {
            return floorId;
        }

        public List<ParkingSpot> getParkingSpots() {
            return parkingSpots;
        }

        public void addParkingSpot(ParkingSpot parkingSpot) {
            parkingSpots.add(parkingSpot);
        }

        public ParkingSpot findAvailableSpot(Vehicle vehicle) {
            for (ParkingSpot spot : parkingSpots) {
                if (spot.canPark(vehicle)) {
                    return spot;
                }
            }

            return null;
        }

        public void displayAvailableSpots() {
            System.out.println("Floor " + floorId + ":");

            for (ParkingSpot spot : parkingSpots) {
                if (spot.isAvailable()) {
                    System.out.println("  " + spot);
                }
            }
        }
    }

    // =========================
    // TICKET
    // =========================

    static class Ticket {
        private final int ticketId;
        private final Vehicle vehicle;
        private final ParkingSpot parkingSpot;
        private final LocalDateTime entryTime;

        private LocalDateTime exitTime;
        private double fee;

        public Ticket(
                int ticketId,
                Vehicle vehicle,
                ParkingSpot parkingSpot
        ) {
            this.ticketId = ticketId;
            this.vehicle = vehicle;
            this.parkingSpot = parkingSpot;
            this.entryTime = LocalDateTime.now();
        }

        public int getTicketId() {
            return ticketId;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public ParkingSpot getParkingSpot() {
            return parkingSpot;
        }

        public LocalDateTime getEntryTime() {
            return entryTime;
        }

        public LocalDateTime getExitTime() {
            return exitTime;
        }

        public double getFee() {
            return fee;
        }

        public void closeTicket() {
            this.exitTime = LocalDateTime.now();
            this.fee = calculateFee();
        }

        public long getDurationInHours() {
            LocalDateTime endTime =
                    exitTime != null ? exitTime : LocalDateTime.now();

            long minutes =
                    Duration.between(entryTime, endTime).toMinutes();

            // Minimum charge = 1 hour
            return Math.max(1, (minutes + 59) / 60);
        }

        private double calculateFee() {
            return getDurationInHours() * parkingSpot.getCharge();
        }

        @Override
        public String toString() {
            return "Ticket{" +
                    "ticketId=" + ticketId +
                    ", vehicle=" + vehicle +
                    ", spot=" + parkingSpot.getSpotId() +
                    ", fee=" + fee +
                    '}';
        }
    }

    // =========================
    // PAYMENT
    // =========================

    static class Payment {
        private final int paymentId;
        private final PaymentMethod paymentMethod;
        private final double amount;
        private boolean success;

        public Payment(
                int paymentId,
                PaymentMethod paymentMethod,
                double amount
        ) {
            this.paymentId = paymentId;
            this.paymentMethod = paymentMethod;
            this.amount = amount;
        }

        public int getPaymentId() {
            return paymentId;
        }

        public PaymentMethod getPaymentMethod() {
            return paymentMethod;
        }

        public double getAmount() {
            return amount;
        }

        public boolean isSuccess() {
            return success;
        }

        public void processPayment() {
            // Simplified payment processing
            this.success = amount >= 0;
        }
    }

    // =========================
    // PARKING LOT
    // =========================

    static class ParkingLot {
        private final int lotId;
        private final String lotName;

        private final List<ParkingFloor> floors;
        private final Map<Integer, Ticket> activeTickets;

        public ParkingLot(int lotId, String lotName) {
            this.lotId = lotId;
            this.lotName = lotName;
            this.floors = new ArrayList<>();
            this.activeTickets = new HashMap<>();
        }

        public void addFloor(ParkingFloor floor) {
            floors.add(floor);
        }

        public Ticket parkVehicle(
                Vehicle vehicle,
                int ticketId
        ) {
            // Check whether vehicle is already parked
            boolean alreadyParked = activeTickets.values()
                    .stream()
                    .anyMatch(ticket ->
                            ticket.getVehicle()
                                    .getVehicleNumber()
                                    .equals(vehicle.getVehicleNumber()));

            if (alreadyParked) {
                throw new IllegalStateException(
                        "Vehicle is already parked."
                );
            }

            ParkingSpot availableSpot =
                    findAvailableSpot(vehicle);

            if (availableSpot == null) {
                throw new IllegalStateException(
                        "No suitable parking spot available."
                );
            }

            // Park vehicle
            availableSpot.parkVehicle(vehicle);

            // Generate ticket
            Ticket ticket =
                    new Ticket(ticketId, vehicle, availableSpot);

            activeTickets.put(ticketId, ticket);

            System.out.println(
                    "Vehicle parked successfully."
            );

            System.out.println(
                    "Ticket ID: " + ticketId
            );

            System.out.println(
                    "Spot ID: " + availableSpot.getSpotId()
            );

            return ticket;
        }

        private ParkingSpot findAvailableSpot(
                Vehicle vehicle
        ) {
            for (ParkingFloor floor : floors) {

                ParkingSpot spot =
                        floor.findAvailableSpot(vehicle);

                if (spot != null) {
                    return spot;
                }
            }

            return null;
        }

        public Payment removeVehicle(
                int ticketId,
                int paymentId,
                PaymentMethod paymentMethod
        ) {

            Ticket ticket =
                    activeTickets.get(ticketId);

            if (ticket == null) {
                throw new IllegalArgumentException(
                        "Invalid ticket ID."
                );
            }

            // Close ticket and calculate fee
            ticket.closeTicket();

            // Make payment
            Payment payment =
                    new Payment(
                            paymentId,
                            paymentMethod,
                            ticket.getFee()
                    );

            payment.processPayment();

            if (!payment.isSuccess()) {
                throw new IllegalStateException(
                        "Payment failed."
                );
            }

            // Free parking spot
            ticket.getParkingSpot().removeVehicle();

            // Remove active ticket
            activeTickets.remove(ticketId);

            System.out.println(
                    "Vehicle exited successfully."
            );

            System.out.println(
                    "Duration: " +
                    ticket.getDurationInHours() +
                    " hour(s)"
            );

            System.out.println(
                    "Fee: " +
                    ticket.getFee()
            );

            return payment;
        }

        public void displayAvailableSpots() {
            System.out.println(
                    "\nAvailable spots in " + lotName + ":"
            );

            for (ParkingFloor floor : floors) {
                floor.displayAvailableSpots();
            }
        }

        public int getLotId() {
            return lotId;
        }

        public String getLotName() {
            return lotName;
        }
    }

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        // Create Parking Lot
        ParkingLot parkingLot =
                new ParkingLot(
                        1,
                        "City Center Parking"
                );

        // Create Floors
        ParkingFloor floor1 =
                new ParkingFloor(1);

        ParkingFloor floor2 =
                new ParkingFloor(2);

        // Add Parking Spots
        floor1.addParkingSpot(
                new ParkingSpot(
                        101,
                        SpotType.CAR,
                        50
                )
        );

        floor1.addParkingSpot(
                new ParkingSpot(
                        102,
                        SpotType.CAR,
                        50
                )
        );

        floor1.addParkingSpot(
                new ParkingSpot(
                        103,
                        SpotType.BIKE,
                        20
                )
        );

        floor2.addParkingSpot(
                new ParkingSpot(
                        201,
                        SpotType.TRUCK,
                        100
                )
        );

        floor2.addParkingSpot(
                new ParkingSpot(
                        202,
                        SpotType.CAR,
                        60
                )
        );

        // Add floors to parking lot
        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);

        // Display available spots
        parkingLot.displayAvailableSpots();

        // Create vehicle
        Vehicle car =
                new Vehicle(
                        1,
                        "KA01AB1234",
                        VehicleType.CAR
                );

        // Park vehicle
        Ticket ticket =
                parkingLot.parkVehicle(
                        car,
                        1001
                );

        // Display available spots
        parkingLot.displayAvailableSpots();

        // Vehicle exits
        parkingLot.removeVehicle(
                ticket.getTicketId(),
                5001,
                PaymentMethod.UPI
        );

        // Display available spots again
        parkingLot.displayAvailableSpots();
    }
}