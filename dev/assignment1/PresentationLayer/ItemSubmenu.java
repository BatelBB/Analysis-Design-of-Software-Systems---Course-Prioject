package assignment1.PresentationLayer;

public class ItemSubmenu extends Menu{
    public ItemSubmenu(String name) {
        super(name);
    }

    @Override
    public int run() {
        return input.nextInt(
                "0. Create new item\n" +
                "1. Edit quantity of an existing item\n" +
                "2. Edit price of an existing item");
    }
}
