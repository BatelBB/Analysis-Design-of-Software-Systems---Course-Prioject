package groupk.logistics.business;

public enum Products {
    Water_7290019056966,
    Milk_7290111607400,
    Eggs_4902505139314;

    public static boolean contains(String product) {
        if (product != null & (product == "Water_7290019056966" | product == "Milk_7290111607400" | product == "Eggs_4902505139314"))
            return true;
        else
            return false;
    }

    public static String[] getProductsSKUList() {
        return new String[]{"Water_7290019056966", "Milk_7290111607400", "Eggs_4902505139314"};
    }
}
