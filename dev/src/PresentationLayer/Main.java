package PresentationLayer;

import ServiceLayer.Service;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        Scanner scan = new Scanner(System.in);
        String input = "";
        PresentationModel pm = new PresentationModel(service);
        do {
            input = scan.nextLine();
            pm.execute(input);
        }
        while (!input.equals("exit"));
        System.out.println("thank you");
    }
}
