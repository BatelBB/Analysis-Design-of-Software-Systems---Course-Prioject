package assignment1.PresentationLayer;

public class SupplierSubmenu extends Menu{
    public SupplierSubmenu(String name) {
        super(name);
    }

    @Override
    public int run() {
        return input.nextInt(
                "0. Create new supplier card\n" +
                "1. Edit existing supplier card\n" +
                "2. Delete existing supplier");
    }
}
