import java.math.BigDecimal;

// Parent class
abstract class Payment {

    private BigDecimal amount;

    public Payment(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // Polymorphic method
    public abstract void pay();
}


// Child class
class UPI extends Payment {

    private String upiId;

    public UPI(BigDecimal amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }

    @Override
    public void pay() {
        System.out.println(
                "Paid " + getAmount() +
                        " using UPI: " + upiId
        );
    }
}


// Child class
class CreditCard extends Payment {

    private String cardNumber;

    public CreditCard(BigDecimal amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay() {
        System.out.println(
                "Paid " + getAmount() +
                        " using Credit Card ending with " +
                        cardNumber.substring(cardNumber.length() - 4)
        );
    }
}


// Child class
class Wallet extends Payment {

    private String walletName;

    public Wallet(BigDecimal amount, String walletName) {
        super(amount);
        this.walletName = walletName;
    }

    @Override
    public void pay() {
        System.out.println(
                "Paid " + getAmount() +
                        " using Wallet: " + walletName
        );
    }
}


// Main class
public class PaymentDemo {

    public static void main(String[] args) {

        Payment payment1 = new UPI(
                new BigDecimal("1000"),
                "rahul@upi"
        );

        Payment payment2 = new CreditCard(
                new BigDecimal("2500"),
                "1234567812345678"
        );

        Payment payment3 = new Wallet(
                new BigDecimal("500"),
                "PayWallet"
        );

        payment1.pay();
        payment2.pay();
        payment3.pay();
    }
}