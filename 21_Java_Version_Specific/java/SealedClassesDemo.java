public class SealedClassesDemo {

    public static void main(String[] args) {

        // Vehicle is sealed, so only Car and Bike
        // can directly extend it.
        Vehicle car = new Car();
        Vehicle bike = new Bike();

        System.out.println(
                "Vehicle type: " +
                car.getClass().getSimpleName()
        );

        System.out.println(
                "Vehicle type: " +
                bike.getClass().getSimpleName()
        );

        // SportsBike is allowed because Bike is non-sealed.
        Bike sportsBike = new SportsBike();

        System.out.println(
                "Bike type: " +
                sportsBike.getClass().getSimpleName()
        );
    }
}

/*
 * Sealed class:
 * Only Car and Bike can directly extend Vehicle.
 */
sealed class Vehicle
        permits Car, Bike {
}

/*
 * final:
 * This branch of the hierarchy is closed.
 * No class can extend Car.
 */
final class Car extends Vehicle {
}

/*
 * non-sealed:
 * This branch is opened again.
 * Any class can extend Bike.
 */
non-sealed class Bike extends Vehicle {
}

/*
 * Allowed because Bike is non-sealed.
 */
class SportsBike extends Bike {
}

/*
 * This would NOT compile:
 *
 * class Truck extends Vehicle {
 * }
 *
 * Truck is not listed in Vehicle's permits clause.
 *
 *
 * This would also NOT compile:
 *
 * class ElectricCar extends Car {
 * }
 *
 * Car is final.
 */
