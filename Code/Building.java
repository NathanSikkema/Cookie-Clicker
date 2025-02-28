package Code;

import javafx.scene.image.Image;


public class Building {

    private String name;
    private Image icon;
    private Image mainImage;
    private int mainImageXPos = 360;
    private int mainImageYPos;
    private int baseCPs;
    private int price;
    private int originalPrice;
    private int count = 0;
    private int cookiesPerSecond;
    private boolean isSeen = false;
    private boolean upgradeStatus;

    Building(String name, int price, int cPs, int yPos, String iconPath, String mainImagePath) {
        initializeBuilding(name, price, cPs, yPos, iconPath, mainImagePath, false);
    }

    Building(String name, int price, int cPs, int yPos, String iconPath, String mainImagePath, boolean isSeen) {
        initializeBuilding(name, price, cPs, yPos, iconPath, mainImagePath, isSeen);
    }


    private void initializeBuilding(String name, int price, int cPs, int yPos, String iconPath, String mainImagePath, boolean isSeen) {
        this.name = name;
        this.price = price;
        this.originalPrice = price;
        this.cookiesPerSecond = cPs;
        this.baseCPs = cPs;
        this.mainImageYPos = yPos;
        this.isSeen = isSeen;
        this.upgradeStatus = false;

        this.icon = loadImage(iconPath);
        this.mainImage = loadImage(mainImagePath);
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    private Image loadImage(String path) {
        try {
            return new Image(path);
        } catch (Exception e) {
            return new Image("Assets/icons/questionMark.jpg");
        }
    }


    public int getMainImageXPos() {
        return mainImageXPos;
    }

    public void setMainImageXPos(int mainImageXPos) {
        this.mainImageXPos = mainImageXPos;
    }

    public int getMainImageYPos() {
        return mainImageYPos;
    }


    public Image getIcon() {
        return icon;
    }

    public Image getMainImage() {
        return mainImage;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
    //    Image icon;

    public int getBaseCPs() {
        return baseCPs;
    }

    public int getCookiesPerSecond() {
        return cookiesPerSecond;
    }

    public void setCookiesPerSecond(int cookiesPerSecond) {
        this.cookiesPerSecond = cookiesPerSecond;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

//    Image image;

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean CanUpgrade() {
        return upgradeStatus;
    }
    public void setStatus(boolean status){
        this.upgradeStatus = status;
    }


    public String toString() {
        return "Building Name: " + name +
                "Building Price: " + price +
                "Buildings Purchased: " + count +
                "1 Building's Cookies per second: " + cookiesPerSecond;
    }

}
