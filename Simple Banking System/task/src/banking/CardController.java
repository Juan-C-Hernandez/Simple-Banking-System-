package banking;

import banking.model.Card;

public class CardController {
    public static String cardTable = "CREATE TABLE IF NOT EXISTS card (\n" +
            "    id INTEGER PRIMARY KEY,\n" +
            "    number TEXT NOT NULL UNIQUE,\n" +
            "    pin TEXT NOT NULL,\n" +
            "    balance INTEGER NOT NULL DEFAULT 0,\n" +
            "    CHECK (\n" +
            "        length(number) = 16 AND\n" +
            "        length(pin) = 4\n" +
            "    )\n" +
            ");";

    public static Card createCard(int bin, long accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(bin).append(accountId);
        sb.append(LuhnAlgorithm.luhnCheckSumDigit(sb.toString()));
        int pin = (int) (1000 + Math.random() * 9000);
        return new Card(sb.toString(), Integer.toString(pin));
    }
}
