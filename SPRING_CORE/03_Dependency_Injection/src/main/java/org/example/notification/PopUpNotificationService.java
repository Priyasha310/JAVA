package org.example.notification;

public class PopUpNotificationService implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("Sending Pop-up Notification");
    }
}
