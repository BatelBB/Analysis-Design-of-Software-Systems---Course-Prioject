package PresentationLayer;

import ServiceLayer.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        Scanner scan = new Scanner(System.in);
        String input = "";
        do {
            input = scan.nextLine();
            System.out.println(LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        while (!input.equals("Exit"));
        System.out.println("thank you");
    }
}
