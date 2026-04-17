package model;

public class MoneyAcceptor {
    protected int amount;

    public MoneyAcceptor() {
        this.amount = 0;
    }

    public MoneyAcceptor(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int sum) {
        if (sum > 0) {
            amount = amount + sum;
        }
    }

    public void reduceAmount(int price) {
        amount = amount - price;
    }

    public int addMoney() {
        return 0;
    }
}