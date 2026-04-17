import enums.ActionLetter;
import model.CardReader;
import model.CoinAcceptor;
import model.CocaCola;
import model.Mars;
import model.Pistachios;
import model.Product;
import model.Snickers;
import model.Soda;
import model.Water;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private boolean isExit;
    private CoinAcceptor coinAcceptor;
    private CardReader cardReader;
    private Scanner scanner;

    public AppRunner() {
        this.isExit = false;
        this.coinAcceptor = new CoinAcceptor(0);
        this.cardReader = new CardReader();
        this.scanner = new Scanner(System.in);
    }

    public static void run() {
        new AppRunner().start();
    }

    private void start() {
        UniversalArray<Product> products = new UniversalArrayImpl<>();

        products.add(new Water(ActionLetter.B, 20));
        products.add(new CocaCola(ActionLetter.C, 50));
        products.add(new Soda(ActionLetter.D, 30));
        products.add(new Snickers(ActionLetter.E, 80));
        products.add(new Mars(ActionLetter.F, 80));
        products.add(new Pistachios(ActionLetter.G, 130));

        while (!isExit) {
            showProducts(products);
            chooseAction(products);
        }
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс монетами");
        print(" p - Оплата картой");
        showActions(products);
        print(" h - Выйти");

        String input = fromConsole().trim();

        if (input.isEmpty()) {
            print("Вы ничего не ввели");
            return;
        }

        String action = input.substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            coinAcceptor.addMoney();
            return;
        }

        if ("p".equalsIgnoreCase(action)) {
            int sum = cardReader.addMoney();
            coinAcceptor.addAmount(sum);
            return;
        }

        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }

        try {
            ActionLetter selectedLetter = ActionLetter.valueOf(action.toUpperCase());

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(selectedLetter)) {
                    if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                        coinAcceptor.reduceAmount(products.get(i).getPrice());
                        print("Вы купили " + products.get(i).getName());
                    } else {
                        print("Недостаточно денег");
                    }
                    return;
                }
            }

            print("Недопустимая буква. Попробуйте еще раз.");

        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                print(String.format(" %s - %s",
                        products.get(i).getActionLetter().getValue(),
                        products.get(i).getName()));
            }
        }
    }

    private String fromConsole() {
        return scanner.nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }

        print("");
        print("Монет на сумму: " + coinAcceptor.getAmount());
    }

    private void print(String text) {
        System.out.println(text);
    }
}