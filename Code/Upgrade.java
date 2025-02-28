package Code;

public class Upgrade {
    private final String name;
    private int price;
    private int purchaseCount = 0;

    Upgrade(String name, int price) {
        this.name = name;
        this.price = price;

    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String toString() {
        return "Name: " + name +
                "Price: " + price +
                "Amount Purchased: " + purchaseCount;
    }
}
