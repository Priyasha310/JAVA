import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

enum TransactionType {
    DEPOSIT,
    WITHDRAW
}

class Transaction {

    private final LocalDateTime date;
    private final TransactionType type;
    private final BigDecimal amount;
    private final BigDecimal balanceAfterTransaction;

    public Transaction(
            LocalDateTime date,
            TransactionType type,
            BigDecimal amount,
            BigDecimal balanceAfterTransaction) {

        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    @Override
    public String toString() {
        return date + " " +
               type + " " +
               amount + " " +
               balanceAfterTransaction;
    }
}

class Account {

    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Transaction> transactions = new ArrayList<>();

    public BigDecimal getBalance() {
        return balance;
    }

    public void depositAmount(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be greater than zero");
        }

        balance = balance.add(amount);

        transactions.add(
                new Transaction(
                        LocalDateTime.now(),
                        TransactionType.DEPOSIT,
                        amount,
                        balance
                )
        );
    }

    public void withdrawAmount(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be greater than zero");
        }

        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException(
                    "Insufficient balance");
        }

        balance = balance.subtract(amount);

        transactions.add(
                new Transaction(
                        LocalDateTime.now(),
                        TransactionType.WITHDRAW,
                        amount,
                        balance
                )
        );
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}

public class ATM {

    public static void main(String[] args) {

        Account account = new Account();

        account.depositAmount(new BigDecimal("10000"));
        account.depositAmount(new BigDecimal("5000"));
        account.withdrawAmount(new BigDecimal("3000"));

        System.out.println("Current Balance: " + account.getBalance());

        System.out.println("\nMini Statement:");
        for (Transaction transaction : account.getTransactions()) {
            System.out.println(transaction);
        }
    }
}