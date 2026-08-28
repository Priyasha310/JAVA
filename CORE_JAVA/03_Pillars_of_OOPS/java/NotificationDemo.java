// Abstraction: `Notification` defines the high-level concept of a
// notification without exposing implementation details. Concrete
// subclasses provide specific sending behavior (email, SMS, push).
abstract class Notification {

    // Encapsulated state shared by all notifications
    private String recipient;

    public Notification(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    // Abstract method: defines the contract for sending a message.
    // Subclasses must implement this method. This is the essence of
    // abstraction — the caller knows *what* can be done (send), but not
    // *how* it is done by each concrete type.
    public abstract void send(String message);
}


// Email implementation
class EmailNotification extends Notification {

    public EmailNotification(String recipient) {
        super(recipient);
    }

    @Override
    public void send(String message) {
        // Concrete implementation for email delivery
        System.out.println(
                "Sending Email to " +
                        getRecipient() +
                        ": " +
                        message
        );
    }
}


// SMS implementation
class SMSNotification extends Notification {

    public SMSNotification(String recipient) {
        super(recipient);
    }

    @Override
    public void send(String message) {
        // Concrete implementation for SMS delivery
        System.out.println(
            "Sending SMS to " +
                getRecipient() +
                ": " +
                message
        );
    }
}


// Push implementation
class PushNotification extends Notification {

    public PushNotification(String recipient) {
        super(recipient);
    }

    @Override
    public void send(String message) {
        // Concrete implementation for push notification delivery
        System.out.println(
            "Sending Push Notification to " +
                getRecipient() +
                ": " +
                message
        );
    }
}


// Main class
public class NotificationDemo {

    public static void main(String[] args) {

        // Use the abstract type `Notification` to hold concrete
        // implementations. The code that uses these objects does not
        // need to know the concrete class — it relies on the abstract
        // contract (`send`) only. This is runtime polymorphism enabled
        // by abstraction.
        Notification email = new EmailNotification("abc@gmail.com");

        Notification sms = new SMSNotification("9876543210");

        Notification push = new PushNotification("user123");

        email.send("Your order has been shipped.");

        sms.send("Your OTP is 1234.");

        push.send("You have a new notification.");
    }
}