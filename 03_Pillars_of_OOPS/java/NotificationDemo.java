abstract class Notification {

    private String recipient;

    public Notification(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    // Abstract method
    public abstract void send(String message);
}


// Email implementation
class EmailNotification extends Notification {

    public EmailNotification(String recipient) {
        super(recipient);
    }

    @Override
    public void send(String message) {

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

        Notification email =
                new EmailNotification("abc@gmail.com");

        Notification sms =
                new SMSNotification("9876543210");

        Notification push =
                new PushNotification("user123");

        email.send("Your order has been shipped.");

        sms.send("Your OTP is 1234.");

        push.send("You have a new notification.");
    }
}