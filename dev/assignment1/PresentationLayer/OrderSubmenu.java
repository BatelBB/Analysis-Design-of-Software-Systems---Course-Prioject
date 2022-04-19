package assignment1.PresentationLayer;

public class OrderSubmenu extends Menu{
    public OrderSubmenu(String name) {
        super(name);
    }

    @Override
    public int run() {
        return input.nextInt(
                "0. Create a new order\n" +
                "1. Edit an existing order\n" +
                "2. Delete an existing order\n" +
                "3. Display order from supplier");
    }
}
