package groupk.shared.BusinessLayer.Inventory.Service.Objects;

import groupk.shared.BusinessLayer.Inventory.Product;

import java.util.List;

public class ProductItemReport extends Report{

    private List<ProductItem> productItemList;

    public ProductItemReport(groupk.shared.BusinessLayer.Inventory.Report report) {
        super(report);
        List<groupk.shared.BusinessLayer.Inventory.ProductItem> BusinessProductItemList=report.getProductItemList();
        for (groupk.shared.BusinessLayer.Inventory.ProductItem p:BusinessProductItemList) {
            productItemList.add(new adss_group_k.BusinessLayer.Inventory.Service.Objects.ProductItem(p));
        }
    }

    public String toString(){
        String s= "Id: "+id+"\n"+ "name: "+name+"\n"+ "date: "+date+"\n"+ "report_producer: "+report_producer+"\n";
        switch (reportType){
            case byProduct:
                return s + "The Items ids are" + productItemList.get(0).getProductName() + "product:" + "\n" + productItemList();
            case bySupplier:
                return s+ "The Items that "+ productItemList.get(0).getSupplier()+" is provides are:"+"\n" + productItemList();
            case Defective:
                return s+"The defective products are:"+"\n" + productItemList();
            case Expired:
                return s+ "The expired products items are:\n"+ productItemList();
        }
        return null;
    }
    private String productItemList(){
        String s="";
        for (ProductItem p:productItemList) {
            s=s+p.getId()+"\n";
        }
        return s;
    }
}
