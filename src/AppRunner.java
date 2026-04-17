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

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final CoinAcceptor coinAcceptor;
    private final CardReader cardReader;

    private static boolean isExit = false;

    private final Scanner scanner = new Scanner(System.in);

    private AppRunner(CoinAcceptor coinAcceptor, CardReader cardReader) {
        this.coinAcceptor = coinAcceptor;
        this.cardReader = cardReader;

        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
    }

    public static void run() {
        AppRunner app = new AppRunner(new CoinAcceptor(0), new CardReader());
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        int currentAmount = getCurrentAmount();
        print("Монет на сумму: " + currentAmount);

        UniversalArray<Product> allowedProducts = getAllowedProducts(currentAmount);

        if (allowedProducts.size() == 0) {
            showPaymentActions();
            print(" h - Выйти");
            chooseActionWhenNothingAvailable();
            return;
        }

        showPaymentActions();
        showActions(allowedProducts);
        print(" h - Выйти");
        chooseAction(allowedProducts);
    }

    private int getCurrentAmount() {
        if (coinAcceptor != null) {
            return coinAcceptor.getAmount();
        }

        if (cardReader != null) {
            return cardReader.getAmount();
        }

        return 0;
    }

    private UniversalArray<Product> getAllowedProducts(int currentAmount) {
        UniversalArray<Product> allowedProducts = new UniversalArrayImpl<>();

        for (int i = 0; i < products.size(); i++) {
            if (currentAmount >= products.get(i).getPrice()) {
                allowedProducts.add(products.get(i));
            }
        }

        return allowedProducts;
    }

    private void chooseActionWhenNothingAvailable() {
        String input = fromConsole().trim();

        if (input.isEmpty()) {
            print("Вы ничего не ввели");
            return;
        }

        String action = input.substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            if (coinAcceptor != null) {
                coinAcceptor.addMoney();
            } else {
                print("Оплата монетами недоступна");
            }
            return;
        }

        if ("p".equalsIgnoreCase(action)) {
            if (cardReader != null) {
                cardReader.addMoney();
            } else {
                print("Оплата картой недоступна");
            }
            return;
        }

        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }

        print("Недопустимая команда. Попробуйте еще раз.");
    }

    private void chooseAction(UniversalArray<Product> products) {
        String input = fromConsole().trim();

        if (input.isEmpty()) {
            print("Вы ничего не ввели");
            return;
        }

        String action = input.substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            if (coinAcceptor != null) {
                coinAcceptor.addMoney();
            } else {
                print("Оплата монетами недоступна");
            }
            return;
        }

        if ("p".equalsIgnoreCase(action)) {
            if (cardReader != null) {
                cardReader.addMoney();
            } else {
                print("Оплата картой недоступна");
            }
            return;
        }

        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }

        try {
            ActionLetter letter = ActionLetter.valueOf(action.toUpperCase());

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(letter)) {
                    if (coinAcceptor != null) {
                        coinAcceptor.reduceAmount(products.get(i).getPrice());
                    } else if (cardReader != null) {
                        cardReader.reduceAmount(products.get(i).getPrice());
                    }

                    print("Вы купили " + products.get(i).getName());
                    return;
                }
            }

            print("Недопустимая буква. Попробуйте еще раз.");

        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.");
        }
    }

    private void showPaymentActions() {
        if (coinAcceptor != null) {
            print(" a - Пополнить баланс монетами");
        }

        if (cardReader != null) {
            print(" p - Оплата картой");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s",
                    products.get(i).getActionLetter().getValue(),
                    products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return scanner.nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}