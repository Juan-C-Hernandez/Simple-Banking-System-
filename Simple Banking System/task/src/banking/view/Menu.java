package banking.view;

import banking.CardController;
import banking.ConsoleHelper;
import banking.LuhnAlgorithm;
import banking.controller.SQLiteDB;
import banking.model.Card;
import banking.model.User;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private ConsoleHelper consoleHelper = ConsoleHelper.getInstance();
    private final String ln = System.lineSeparator();
    private final String userMenuString = "1. Balance" + ln
            + "2. Add income" + ln
            + "3. Do transfer" + ln
            + "4. Close account" + ln
            + "5. Log out" + ln
            + "0. Exit" + ln;

    private final String mainMenuString = "1. Create an account" + ln
            + "2. Log into account" + ln
            + "0. Exit" + ln;

    private SQLiteDB sqLiteDB;
    private String fileName;
    private User currentUser;

    public Menu(String fileName) {
        this.fileName = fileName;
    }

    public void start() {
        sqLiteDB = new SQLiteDB();
        sqLiteDB.createDataBase(fileName);
        sqLiteDB.connect();
        mainMenu();
    }

    public void mainMenu() {
        int choice = consoleHelper.readInt(mainMenuString);
        switch (choice) {
            case 1:
                //Create User
                createUser();
                break;
            case 2:
                //Log into account
                login();
                break;
            case 0:
                exit();
            default:
                System.out.println("Wrong input");
        }
    }

    public void userMenu() {
        int choice = consoleHelper.readInt(userMenuString);
        switch (choice) {
            case 1:
                //Get balance
                printBalance();
                break;
            case 2:
                //Add income
                addIncome();
                break;
            case 3:
                //Do transfer
                doTransfer();
                break;
            case 4:
                //Close account
                closeAccount();
                break;
            case 5:
                //Log out
                logout();
                break;
            case 0:
                exit();
            default:
                System.out.println("Wrong input");
                userMenu();
        }
    }

    public void createUser() {
        System.out.println("");
        long n = (long) (100000000 + Math.random() * 900000000);
        Card newCard = CardController.createCard(400000, n);
        User newUser = new User();
        newUser.setCard(newCard);
        sqLiteDB.addUser(newUser);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(newCard.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(newCard.getPin() + ln);
        mainMenu();
    }

    public void login() {
        String cardNumber = consoleHelper.readString("Enter your card number: " + ln);
        String pin = consoleHelper.readString("Enter your PIN:" + ln);
        currentUser = sqLiteDB.getUserByCardNumberAndPin(cardNumber, pin);
        System.out.println("sin npe");
        if (currentUser == null) {
            System.out.println("Wrong card number or PIN!" + ln);
            mainMenu();
        } else {
            System.out.println("You have successfully logged in!" + ln);
            userMenu();
        }
    }

    public void exit() {
        sqLiteDB.closeConnection();
        System.out.println("bye!");
        System.exit(0);
    }

    public void printBalance() {
        System.out.println("Balance: " + currentUser.getBalance() + ln);
        userMenu();
    }

    public void addIncome() {
        int income = consoleHelper.readInt("Enter income:" + ln);
        if (income > 0) {
            sqLiteDB.addIncome(currentUser, income);
            System.out.println("Income was added!");
        }
        userMenu();
    }

    public void doTransfer() {
        String cardNumber = consoleHelper.readString("Transfer" + ln +
                "Enter card number:" + ln);
        if (!cardNumber.equals(currentUser.getCard().getCardNumber())) {
            if (LuhnAlgorithm.checkLuhnNumber(cardNumber)) {
                if (sqLiteDB.containsCardNumber(cardNumber)) {
                    int transfer = consoleHelper.readInt("Enter how much money you want to transfer:" + ln);
                    sqLiteDB.doTransfer(currentUser, cardNumber, transfer);
                    System.out.println("Success!");
                } else {
                    System.out.println("Such a card does not exist.");
                }
            } else{
                System.out.println("Probably you made mistake in the card number. Please try again!");
            }
        } else {
            System.out.println("You can't transfer money to the same account!");
        }
        userMenu();
    }

    public void closeAccount() {
        sqLiteDB.deleteUser(currentUser);
        System.out.println("The account has been closed!");
        currentUser = null;
        mainMenu();
    }

    public void logout() {
        currentUser = null;
        System.out.println("You have successfully logged out!" + ln);
        mainMenu();
    }
}
