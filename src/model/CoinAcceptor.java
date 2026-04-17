package model;

import java.util.Scanner;

public class CoinAcceptor {
    private int amount;
    private Scanner scanner;

    public CoinAcceptor(int amount) {
        this.amount = amount;
        this.scanner = new Scanner(System.in);
    }

    public int getAmount() {
        return amount;
    }

    public void addMoney() {
        System.out.println("Введите сумму монет:");

        String input = scanner.nextLine();
        input = input.trim();

        if (input.isEmpty()) {
            System.out.println("Вы ничего не ввели");
            return;
        }

        try {
            int sum = Integer.parseInt(input);

            if (sum <= 0) {
                System.out.println("Сумма должна быть больше нуля");
                return;
            }

            amount = amount + sum;
            System.out.println("Баланс пополнен на " + sum);

        } catch (Exception e) {
            System.out.println("Нужно ввести целое число");
        }
    }

    public void addAmount(int sum) {
        if (sum > 0) {
            amount = amount + sum;
        }
    }

    public void reduceAmount(int price) {
        amount = amount - price;
    }
}