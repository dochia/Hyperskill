package machine;

import java.util.Scanner;

enum CoffeeType {
    espresso(250, 0, 16, 4),
    latte(350, 75, 20, 7),
    cappuccino(200, 100, 12, 6);


    final int water;
    final int milk;
    final int coffee;
    final int cost;

    private static CoffeeType[] list = CoffeeType.values();
    public static CoffeeType getCoffeeByOrdinal(int i) {
        return list[i];
    }

    CoffeeType(int waterNeeded, int milkNeeded, int coffeeNeeded, int money) {
        water = waterNeeded;
        milk = milkNeeded;
        coffee = coffeeNeeded;
        cost = money;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffee() {
        return coffee;
    }

    public int getCost() {
        return cost;
    }
}

class CoffeeMachineResources {
    private int water;
    private int milk;
    private int cups;
    private int beans;
    private int money;
    CoffeeType[] types;

    CoffeeMachineResources() {
        water = 440;
        milk = 540;
        beans = 120;
        money = 550;
        cups = 9;
        types = new CoffeeType[]{CoffeeType.espresso, CoffeeType.latte, CoffeeType.cappuccino};
    }

    void refillWater(int value) {
        water += value;
    }

    void refillMilk(int value) {
        milk += value;
    }

    void refillCups(int value) {
        cups += value;
    }

    void refillBeans(int value) {
        beans += value;
    }

    int giveMoney() {
        int helper = money;
        money = 0;
        return helper;
    }

    void printResources() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", water);
        System.out.printf("%d of milk\n", milk);
        System.out.printf("%d of coffee beans\n", beans);
        System.out.printf("%d of disposable cups\n", cups);
        System.out.printf("%d of money\n", money);
    }

    boolean verifyResources(int type) {
        CoffeeType actual = CoffeeType.getCoffeeByOrdinal(type);
        boolean flag = true;
        if (water < actual.getWater()) {
            System.out.println("Sorry, not enough water!");
            flag = false;
        }
        if (milk < actual.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            flag = false;
        }
        if (beans < actual.getCoffee()) {
            System.out.println("Sorry, not enough beans!");
            flag = false;
        }
        if (cups < 1) {
            System.out.println("Sorry, not enough cups!");
            flag = false;
        }
        return flag;
    }

    void makeCoffee(int type) {
        CoffeeType actual = CoffeeType.getCoffeeByOrdinal(type);
        water -= actual.getWater();
        milk -= actual.getMilk();
        beans -= actual.getCoffee();
        money += actual.getCost();
        cups--;
    }
}

public class CoffeeMachine {
    final static Scanner sc = new Scanner(System.in);
    final static CoffeeMachineResources machine = new CoffeeMachineResources();
    static boolean closed = false;

    private static void refill() {
        System.out.println("Write how many ml of water do you want to add: ");
        machine.refillWater(sc.nextInt());
        System.out.println("Write how many ml of milk do you want to add: ");
        machine.refillMilk(sc.nextInt());
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        machine.refillBeans(sc.nextInt());
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        machine.refillCups(sc.nextInt());
    }

    private static void buy() {
        System.out.println("What do you want to buy?");
        int i = 1;
        for (CoffeeType coffee : CoffeeType.values()) {
            System.out.printf("%d - %s\n", i++, coffee.name());
        }
        System.out.println("back - to main menu:");

        String value = sc.next();
        if (value.equals("back")) {
            return;
        }

        int option = Integer.parseInt(value) - 1;
        if (machine.verifyResources(option)) {
            System.out.println("I have enough resources, making you a coffee!");
            machine.makeCoffee(option);
        } else {
            System.out.printf("Not enough resources to make %s\n", CoffeeType.getCoffeeByOrdinal(option).toString());
        }
    }

    private static void take() {
        System.out.printf("I gave you %d\n", machine.giveMoney());
    }

    private static void makeChoice() {
        //System.out.println("Write action (buy, fill, take, remaining, exit):");
        String option = sc.next();
        switch (option) {
            case "buy":
                buy();
                break;
            case "fill":
                refill();
                break;
            case "take":
                take();
                break;
            case "remaining":
                machine.printResources();
                break;
            case "exit":
                closed = true;
                break;
            default:
                System.out.println("Please choose one of the provided options");
                makeChoice();
        }
    }

    private static void stage5() {
        while (!closed) {
            makeChoice();
        }
    }

    public static void main(String[] args) {
        //stage4();
        stage5();
    }
}
