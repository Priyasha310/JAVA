package org.example;

import org.example.notification.EmailService;
import org.example.notification.NotificationService;

public class Main {

    public static void main(String[] args) {

        // Dependency is created outside OrderService.
        NotificationService notification = new EmailService();

        // Dependency is injected through the constructor.
        OrderService orderService = new OrderService(notification);

        orderService.placeOrder();
    }
}
