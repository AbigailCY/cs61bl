/**
 * This class represents a bank account whose current balance is a nonnegative
 * amount in US dollars.
 */
public class Account {

    public int balance;
    public Account parentAccount;

    /** Initialize an account with the given balance. */
    public Account(int balance,  Account parentA) {
        this.balance = balance;
        this.parentAccount = parentA;
    }
    public Account(int balance) {
        this.balance = balance;
        this.parentAccount = null;
    }

    /** Deposits amount into the current account. */
    public void deposit(int amount) {
        if (amount < 0) {
            System.out.println("Cannot deposit negative amount.");
        } else {
            balance += amount;
        }
    }

    /**
     * Subtract amount from the account if possible. If subtracting amount
     * would leave a negative balance, print an error message and leave the
     * balance unchanged.
     */
    public boolean withdraw(int amount) {
        // TODO
        if (amount < 0) {
            System.out.println("Cannot withdraw negative amount.");
            return false;
        } else if (balance < amount) {

            if (this.parentAccount == null){
                System.out.println("Cannot withdraw amount, and no parent account.");
                return false;
            } 

            int accumulate = balance;
            Account a = this.parentAccount;
            while (a != null){
                accumulate += a.balance;
                a = a.parentAccount;
            }

            if (accumulate < amount){
                System.out.println("Insufficient balance in parent amounts.");
                return false;
            } else {

                amount = amount - balance;
                this.balance = 0;
                
                while (this.parentAccount.balance >= 0){

                    if (amount > this.parentAccount.balance){
                        amount -= this.parentAccount.balance;
                        this.parentAccount.balance = 0;
                        this.parentAccount = this.parentAccount.parentAccount;
                    } else {
                        this.parentAccount.balance -= amount;
                        // System.out.println(this.parentAccount.balance);
                        break;
                    }               
                }
                return true;
            }

        } else {
            balance -= amount;
            return true;
        }
    }

    /**
     * Merge account other into this account by removing all money from other
     * and depositing it into this account.
     */
    public void merge(Account other) {
        // TODO
        balance = this.balance + other.balance;
        other.balance = 0;
    }

    public static void main(String[] args){
        Account christine = new Account(500);
        Account matt = new Account(100, christine);
        matt.withdraw(150);
    }




}
