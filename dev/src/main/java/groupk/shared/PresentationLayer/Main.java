package groupk.shared.PresentationLayer;

import groupk.shared.business.Facade;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome!");
        new Facade();
        new App("database.db").main();

    }
}
