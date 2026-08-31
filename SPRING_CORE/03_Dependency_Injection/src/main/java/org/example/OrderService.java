package org.example;

import org.example.notification.NotificationService;

public class OrderService {

    private final NotificationService notification;

    // Constructor Injection
    public OrderService(NotificationService notification) {
        this.notification = notification;
    }

    public void placeOrder() {
        System.out.println("Order placed");
        notification.sendNotification();
    }
}
