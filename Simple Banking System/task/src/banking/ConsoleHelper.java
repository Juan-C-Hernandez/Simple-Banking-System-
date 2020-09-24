package banking;

import java.util.Scanner;

public class ConsoleHelper {
    private static final ConsoleHelper instance = new ConsoleHelper();
    private final Scanner scanner = new Scanner(System.in);

    private ConsoleHelper() {}

    public static ConsoleHelper getInstance() {
        return instance;
    }

    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public int readInt(String message) {
        System.out.println(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public void close() {
        scanner.close();
    }
}
