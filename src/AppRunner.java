import enums.ActionLetter;
import model.CoinAcceptor;
import model.CocaCola;
import model.Mars;
import model.MoneyAcceptor;
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
    private final MoneyAcceptor moneyAcceptor;
    private final Scanner scanner;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });

        moneyAcceptor = new CoinAcceptor(0);
        scanner = new Scanner(System.in);
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + moneyAcceptor.getAmount());

        UniversalArray<Product> allowedProducts = new UniversalArrayImpl<>();
        allowedProducts.addAll(getAllowedProducts().toArray());

        chooseAction(allowedProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowedProducts = new UniversalArrayImpl<>();

        for (int i = 0; i < products.size(); i++) {
            if (moneyAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowedProducts.add(products.get(i));
            }
        }

        return allowedProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");

        String input = fromConsole().trim();

        if (input.isEmpty()) {
            print("Вы ничего не ввели");
            return;
        }

        String action = input.substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            moneyAcceptor.addMoney();
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
                    moneyAcceptor.reduceAmount(products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
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