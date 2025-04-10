import java.util.*;
interface ATMInterface {
    void checkBalance(Account acc);
    void deposit(Account acc);
    void withdraw(Account acc);
}
class Account {
    private int balance;
    public Account(){
        balance=0;
    }
    public int getBalance(){
        return balance;
    }

    public void deposit(int amount){
        balance+=amount;
    }
    public boolean withdraw(int amount){
        if(amount>balance) return false;
        balance-=amount;
        return true;
    }
}
class PremiumAccount extends Account {
    public void addBonus(){git 
        deposit(100);
    }

    
    public int getBalance(){
        return super.getBalance()+100;
    }
}

class ATMOperations implements ATMInterface{
    Scanner sc=new Scanner(System.in);
    HashMap<Integer, Account> userAccounts=new HashMap<>();

    public void start(){
        System.out.println("Welcome to OOP ATM Machine");
        try{
            operate();
        }catch(InputMismatchException e) {
            System.out.println("Invalid input! PIN must be numbers only.");
        }
    }

    public void operate() {
        try{
            System.out.print("Enter your PIN: ");
            int pin=sc.nextInt();
            Account acc;
            if (userAccounts.containsKey(pin)) {
                acc=userAccounts.get(pin);
            }else{
                System.out.println("Account not found. Creating new...");
                acc=createAccount();
                userAccounts.put(pin, acc);
            }
            menu(acc);
        }catch(InputMismatchException e){
            System.out.println("Invalid input! Please enter digits only.");
            sc.nextLine();
        }
    }

    private Account createAccount() {
        System.out.println("Select account type:\n1. Normal Account\n2. Premium Account");
        try {
            int type=sc.nextInt();
            if (type==2) {
                PremiumAccount pAcc=new PremiumAccount();
                pAcc.addBonus();
                return pAcc;
            }
        } catch(InputMismatchException e){
            System.out.println("Invalid input. Creating normal account by default.");
            sc.nextLine();
        }
        return new Account();
    }
    public void menu(Account acc) {
        while (true) {
            try{
                System.out.println("\n1. Check Balance\n2. Deposit\n3. Withdraw\n4. Exit\n5. Switch Account");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1->checkBalance(acc);
                    case 2->deposit(acc);
                    case 3->withdraw(acc);
                    case 4->{
                        System.out.println("Thanks for using our ATM.");
                        return;
                    }
                    case 5->operate();
                    default->System.out.println("Invalid option");
                }
            } catch(InputMismatchException e){
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
    }
    public void deposit(Account acc){
        try{
            System.out.print("Enter deposit amount: ");
            int amount=sc.nextInt();
            if (amount<=0)throw new IllegalArgumentException("Amount must be positive.");
            acc.deposit(amount);
            System.out.println("Deposit successful.");
        } catch(InputMismatchException e) {
            System.out.println("Invalid input! Enter numeric values only.");
            sc.nextLine();
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deposit(Account acc, int amount){
        acc.deposit(amount);
        System.out.println("Auto-deposit of ₹"+amount+" completed.");
    }
    @Override
    public void checkBalance(Account acc) {
        System.out.println("Current Balance: "+acc.getBalance());
    }
    @Override
    public void withdraw(Account acc) {
        try {
            System.out.print("Enter withdrawal amount: ");
            int amount=sc.nextInt();
            if (amount<=0)throw new IllegalArgumentException("Amount must be positive.");
            if (acc.withdraw(amount)) {
                System.out.println("Withdrawal successful.");
            }else{
                System.out.println("Insufficient balance.");
            }
        } catch(InputMismatchException e) {
            System.out.println("Invalid input! Enter numeric values only.");
            sc.nextLine();
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        ATMOperations atm = new ATMOperations();
        atm.start();
    }
}
