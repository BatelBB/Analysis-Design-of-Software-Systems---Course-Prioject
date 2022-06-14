package groupk.shared.PresentationLayer;

import groupk.shared.PresentationLayer.EmployeesLogistics.Application;
import groupk.shared.business.Facade;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome!");
        App app = new App("database.db");
        app.main();

    }
}
