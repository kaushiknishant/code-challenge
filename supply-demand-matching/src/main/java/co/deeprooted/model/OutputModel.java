package co.deeprooted.model;

public class OutputModel {

    private String demandOrderId;
    private String supplyOrderId;
    private int price;
    private int quantity;

    public String getDemandOrderId() {
        return demandOrderId;
    }

    public void setDemandOrderId(String demandOrderId) {
        this.demandOrderId = demandOrderId;
    }

    public String getSupplyOrderId() {
        return supplyOrderId;
    }

    public void setSupplyOrderId(String supplyOrderId) {
        this.supplyOrderId = supplyOrderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
