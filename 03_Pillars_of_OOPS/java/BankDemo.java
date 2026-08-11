import java.math.BigDecimal;

// Abstraction: base account type that hides internal state and exposes operations.
abstract class BankAccount {

    /* Encapsulation: the account's internal state is private so it cannot be
       modified directly from outside. Access is provided through controlled
       methods (getters and business operations). This prevents callers from
       putting the object into an invalid state. */
    private int accountNumber;
    private String accountHolder;
    private BigDecimal balance;

    public BankAccount(
            int accountNumber,
            String accountHolder,
            BigDecimal balance) {

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    // Read-only accessors: expose state safely without providing setters.
    // There are no public setters for `balance`/`accountNumber`/`accountHolder`,
    // so external code cannot arbitrarily change them.
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    // Public operation: deposit. This method enforces validation rules before
    // updating the private `balance`. External callers cannot change `balance`
    // directly — they must use methods like this which implement business rules.
    public void deposit(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid deposit amount");
            return;
        }

        balance = balance.add(amount);

        System.out.println(
                "Deposited: " + amount
        );
    }

    // Withdraw is abstract because different account types may have different
    // withdrawal rules. The abstract method is part of the public API that
    // subclasses must implement while still respecting encapsulation.
    public abstract void withdraw(BigDecimal amount);

    // Protected helper: allows subclasses to modify `balance` while keeping the
    // field itself private. This preserves encapsulation (external classes
    // cannot access `balance` directly) but permits controlled internal use.
    protected void deductBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}


// Child class
class SavingsAccount extends BankAccount {

    private static final BigDecimal MIN_BALANCE =
            new BigDecimal("1000");

    // SavingsAccount enforces a minimum balance. All logic that changes state
    // goes through the BankAccount public/protected methods so the internal
    // representation remains encapsulated.

    public SavingsAccount(
            int accountNumber,
            String accountHolder,
            BigDecimal balance) {

        super(accountNumber, accountHolder, balance);
    }

    @Override
    public void withdraw(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid withdrawal amount");
            return;
        }

        BigDecimal remainingBalance =
                getBalance().subtract(amount);

        if (remainingBalance.compareTo(MIN_BALANCE) < 0) {
            System.out.println(
                    "Withdrawal failed. " +
                            "Minimum balance of " +
                            MIN_BALANCE +
                            " must be maintained."
            );
            return;
        }

        deductBalance(amount);

        System.out.println(
                "Withdrawn: " + amount
        );
    }
}


// Main class
public class BankDemo {

    public static void main(String[] args) {

        BankAccount account =
                new SavingsAccount(
                        101,
                        "Priya",
                        new BigDecimal("10000")
                );

        System.out.println(
                "Account Holder: " +
                        account.getAccountHolder()
        );

        System.out.println(
                "Initial Balance: " +
                        account.getBalance()
        );

        account.deposit(
                new BigDecimal("5000")
        );

        account.withdraw(
                new BigDecimal("3000")
        );

        account.withdraw(
                new BigDecimal("12000")
        );

        System.out.println(
                "Final Balance: " +
                        account.getBalance()
        );
    }
}