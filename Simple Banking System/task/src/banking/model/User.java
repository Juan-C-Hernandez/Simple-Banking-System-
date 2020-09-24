package banking.model;

public class User {
    private int id;
    private Card card;
    private int balance;

    public User() {
        this(0);
    }

    public User(int balance) {
        this.balance = balance;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "balance: " + balance + "\n" +
                "card: " + card;
    }
}
