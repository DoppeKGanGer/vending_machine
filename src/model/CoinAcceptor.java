package model;

import java.util.Scanner;

public class CoinAcceptor extends MoneyAcceptor {
    private Scanner scanner;

    public CoinAcceptor(int amount) {
        super(amount);
        this.scanner = new Scanner(System.in);
    }

    public int addMoney() {
        System.out.println("Введите сумму монет:");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Вы ничего не ввели");
            return 0;
        }

        try {
            int sum = Integer.parseInt(input);

            if (sum <= 0) {
                System.out.println("Сумма должна быть больше нуля");
                return 0;
            }

            addAmount(sum);
            System.out.println("Баланс пополнен на " + sum);
            return sum;

        } catch (NumberFormatException e) {
            System.out.println("Нужно ввести целое число");
            return 0;
        }
    }
}