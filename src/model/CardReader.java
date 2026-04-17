package model;

import java.util.Scanner;

public class CardReader {
    private Scanner scanner;

    public CardReader() {
        this.scanner = new Scanner(System.in);
    }

    public int addMoney() {
        System.out.println("Введите номер карты:");
        String cardNumber = scanner.nextLine().trim();

        if (cardNumber.isEmpty()) {
            System.out.println("Номер карты не введен");
            return 0;
        }

        System.out.println("Введите одноразовый пароль:");
        String password = scanner.nextLine().trim();

        if (password.isEmpty()) {
            System.out.println("Пароль не введен");
            return 0;
        }

        System.out.println("Введите сумму пополнения:");
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

            System.out.println("Оплата картой прошла успешно");
            return sum;

        } catch (NumberFormatException e) {
            System.out.println("Нужно ввести целое число");
            return 0;
        }
    }
}