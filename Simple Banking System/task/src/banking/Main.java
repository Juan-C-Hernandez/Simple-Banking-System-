package banking;

import banking.view.Menu;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            if (args[0].equals("-fileName")) {
                new Menu(args[1]).start();
            }
        }

        throw new IllegalArgumentException("You should pass database name using -fileName");
    }
}
