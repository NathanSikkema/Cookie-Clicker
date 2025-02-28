package Code;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class GUI extends View {
    /**
     The label that is created when the user hovers over specific buttons
     */
    private static Label hoverLabel;

    /**
     Formats a string into a decimal, and adds the number range (millions or billions).

     @param num the number that is being formatted.
     @return the formatted number
     */
    public static String formatString(double num) {
        double numDec = num;
        num = (int) num;
        String formattedString = numStringConvert(num);
        if (numDec > 1000000 && numDec < 1000000000) {
            numDec /= 1000000;
            formattedString = String.format("%.3f", numDec) + " million";

        } else if (numDec >= 1000000000) {
            numDec /= 1000000000;
            formattedString = numStringConvert(numDec) + " billion";
        }
        return formattedString;
    }

    /**
     Updates the label that shows the amount of cookies, and the cookies per second
     */
    public static void updateLabel() {

        String cookieString = formatString(cookies);

        String cPsString = formatString(cookiesPerSecond);
        outputLabel.setText("Cookies: " + cookieString +
                " \nPer second: " + cPsString);
    }

    /**
     Updates and draws the data for the buildings.
     Includes:
     - 1 image
     - 2 buttons (buy and sell).
     - background Label (gray background with border).
     - Hover feature. When the user hovers over the building, it will show important stats.
     */
    public static void updateBuildings() {
        for (int i = 1; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            int x = 1300;
            int y = 250 + i * 75;
            Label buildingBackground = new Label("\t???");
            buildingBackground.relocate(1300, y - 5);
            buildingBackground.setPrefWidth(250);
            buildingBackground.setPrefHeight(70);
            buildingBackground.setFont(new Font("Arial", 30));
            buildingBackground.setStyle("-fx-background-color: gray;-fx-background-radius: 5; -fx-alignment: top-left; -fx-border-color: black; -fx-border-width: 2px;  -fx-border-insets: 2px;");
            ImageView icon = new ImageView("Assets/icons/questionMark.jpg");
            icon.relocate(1300 + 10, y);
            if ((building.getPrice() - building.getPrice() * 0.9) < cookies || building.isSeen()) {
                Button buyButton = new Button("???");
                buyButton.setOnAction(event -> buyBuilding(building));
                initiateButton(buyButton, x + 130, y + 35);
                buyButtonColor(building, buyButton);

                Button sellButton = new Button("???");
                sellButton.setOnAction(event -> sellBuilding(building));
                initiateButton(sellButton, x + 190, y + 35);
                sellButtonColor(building, sellButton);


                if (building.getPrice() - building.getPrice() * 0.5 < cookies || building.isSeen()) {
                    buildingBackground.setText("\t" + building.getCount() + " " + building.getName());
                    icon.setImage(building.getIcon());
                    building.setSeen(true);
                    buyButton.setText("Buy");
                    sellButton.setText("Sell");
                    Label[] labelArray = new Label[]{buildingBackground};
                    for (Label label : labelArray) {
                        buildingHover(label, building, y);
                    }
                    Button[] buttonArray = new Button[]{buyButton, sellButton};
                    for (Button button : buttonArray) {
                        buildingHover(button, building, y);
                    }
                    ImageView[] imagesArray = new ImageView[]{icon};
                    for (ImageView image : imagesArray) {
                        buildingHover(image, building, y);
                    }
                }
                root.getChildren().addAll(buildingBackground, buyButton, sellButton, icon);
            }
        }
    }

    private static void buyButtonColor(Building building, Button button) {

        if (cookies <= building.getPrice()) {
            button.setStyle("-fx-background-color: #ea9999;");
            building.setStatus(false);
        } else {
            button.setStyle("-fx-background-color: #b6d7a8;");
            building.setStatus(true);
        }
    }

    private static void sellButtonColor(Building building, Button button) {

        if (building.getCount() <= 0) {
            button.setStyle("-fx-background-color: #ea9999;");
        } else {
            button.setStyle("-fx-background-color: #b6d7a8;");
        }
    }

    private static void initiateButton(Button button, double x, double y) {
        button.relocate(x, y);
        button.setPrefWidth(50);

    }

    /**
     Updates and draws the data for the building purchases.
     Includes:
     - 1 image
     - 1 buy button.
     - Hover feature. When the user hovers over the building, it will show important stats.
     */
    public static void updateUpgrades() {

        String[] iconSRCPaths = {"mouse", "cursor", "grandma", "farm", "mine", "factory", "bank", "temple", "wizardTower"};
        ImageView[] iconView = new ImageView[iconSRCPaths.length];
        for (int i = 0; i < upgradeBoosts.size(); i++) {
            Upgrade upgrade = upgradeBoosts.get(i);
            Building building = buildings.get(i);
            int x = 1300 + 2 + i % 3 * 90;
            int y = 80 + 50 * (i / 3);
            Button buyButton = new Button("");


            String path = "src/Assets/upgradeIcons/" + iconSRCPaths[i] + ".jpg";
            Image iconSRC = new Image(new File(path).toURI().toString());
            iconView[i] = new ImageView(iconSRC);
            iconView[i].relocate(x, y);


            buyButton.setGraphic(iconView[i]);
            buyButton.relocate(x, y);
            buyButton.setOnAction(event -> buyUpgrade(upgrade, building));
            buyButton.setPrefHeight(40);
            buyButton.setPrefWidth(40);
            buyButton.setStyle("-fx-background-color: black;-fx-background-radius: 5;");
            buyButton.setOnMouseEntered(event -> {
                hoverLabel = new Label("");
                hoverLabel.relocate(1030, y);
                hoverLabel.setStyle("-fx-background-color: black;-fx-text-fill:white; -fx-alignment: center;");
                hoverLabel.setPrefWidth(260);
                hoverLabel.setEffect(dropShadow);
                if (!Objects.equals(building.getName(), "Clicks")) {
                    hoverLabel.setText("Title: " + building.getName() +
                            "\nPrice: $" + numStringConvert(upgrade.getPrice()) +
                            "\nTimes Purchased: " + upgrade.getPurchaseCount() +
                            "\n1 " + building.getName() + " produces " +
                            numStringConvert(building.getCookiesPerSecond()) + " cookie(s) per second" +
                            "\nBase cPs: " + numStringConvert(building.getBaseCPs()));
                } else {
                    hoverLabel.setText("Title: Mouse" + "\nPrice: $" + numStringConvert(upgrade.getPrice()) + "\nTimes Purchased: " + upgrade.getPurchaseCount() + "\nOne click produces " + numStringConvert(clickValue) + " cookies(s) per second" + "\nTotal clicks: " + numStringConvert(clicks));
                }
                root.getChildren().add(hoverLabel);
            });
            buyButton.setOnMouseExited(event -> root.getChildren().remove(hoverLabel));
            root.getChildren().addAll(buyButton);
        }

    }

    /**
     The method used to sell a building.
     Features:
     - Selling a building will return cookies, with a 15% - 50%

     @param building the building that is being sold
     */
    private static void sellBuilding(Building building) {
        if (building.getCount() > 0) {
            double minLoss = 1.15;
            double maxLoss = 1.5;
            Random random = new Random();
            double lossValue = (minLoss + (maxLoss - minLoss) * random.nextDouble());
            cookies += (int) (building.getPrice() / lossValue);
            cookiesPerSecond -= building.getCookiesPerSecond();
            building.setPrice((int) (building.getPrice() / 1.1));
            building.setCount(building.getCount() - 1);

            int count = building.getCount();
            if (count >= 14 && !Objects.equals(building.getName(), "Cursor")) {
                count = 14;
            } else if (count >= 100 && Objects.equals(building.getName(), "Cursor")) {
                count = 100;
            }
            for (int i = 0; i < count + 1; i++) {
                String buildingImageId = "buildingImage_" + building.getName() + (i + count + 1);
                root.getChildren().removeIf(node -> node.getId() != null && node.getId().equals(buildingImageId));
            }
            building.setMainImageXPos(360);
            updateUpgrades();
            updateBuildings();
        }
    }

    /**
     The method used to buy a building.
     Features:
     - Buying a building will add an image to the screen (if there is space)
     - Changes values: cookies (decrease), cookiesPerSecond (increase)

     @param building the building that is being bought
     */
    private static void buyBuilding(Building building) {
        if (cookies >= building.getPrice()) {

            cookies -= building.getPrice();
            cookiesPerSecond += building.getCookiesPerSecond();
            building.setCount(building.getCount() + 1);
            building.setPrice((int) (building.getCount() * building.getOriginalPrice() * 1.15));
            int count = building.getCount();
            if (count >= 14) {
                count = 14;
            }
            if (!Objects.equals(building.getName(), "Cursor")) {
                for (int i = 1; i < count + 1; i++) {
                    ImageView buildingImage = new ImageView(building.getMainImage());
                    buildingImage.relocate(building.getMainImageXPos(), building.getMainImageYPos());
                    building.setMainImageXPos(i * 65 + 360);
                    buildingImage.setId("buildingImage_" + building.getName() + i);
                    root.getChildren().add(buildingImage);
                }
                building.setMainImageXPos(360);
            } else {
                int cursorCount = building.getCount();
                double radius1 = 235 / 2.0;
                double radius2 = 270 / 2.0;
                Image image = new Image("Assets/building/Cursor.jpg");

                if (cursorCount >= 100) cursorCount = 100;
                for (int i = 0; i < cursorCount; i++) {
                    double centerX = 167;
                    double centerY = 400;
                    double theta = 2 * Math.PI * i / 50.0;
                    double radius = (i < 50) ? radius1:radius2;
                    double x = centerX + radius * Math.cos(theta);
                    double y = centerY + radius * Math.sin(theta);
                    ImageView cursorImage = new ImageView(image);
                    cursorImage.setFitWidth(20);
                    cursorImage.setFitHeight(20);
                    cursorImage.setTranslateX(x - cursorImage.getFitWidth() / 2);
                    cursorImage.setTranslateY(y - cursorImage.getFitHeight() / 2);
                    double angle = Math.toDegrees(Math.atan2(centerY - y, centerX - x));
                    cursorImage.setRotate(angle - 90);
                    cursorImage.setId("buildingImage_" + building.getName() + (i + 1));
                    root.getChildren().add(cursorImage);
                }
            }
            updateBuildings();
            updateUpgrades();
        }
    }

    /**
     The method that is used when the user hovers over an element (image, button or label).

     @param element  - Images, Buttons, or Labels
     @param building - The building that is being hovered over
     @param y        the Y coordinate of the building.
     */
    private static void buildingHover(Node element, Building building, int y) {
        element.setOnMouseEntered(event -> {
            hoverLabel = new Label("");
            hoverLabel.relocate(1030, y);
            hoverLabel.setStyle("-fx-background-color: black;-fx-text-fill:white; -fx-alignment: center;");
            hoverLabel.setPrefWidth(260);
            hoverLabel.setEffect(dropShadow);
            String buildingPrice = numStringConvert(building.getPrice());
            String production = numStringConvert(building.getCookiesPerSecond() * building.getCount());
            String cPs = numStringConvert(building.getCookiesPerSecond());
            if (!Objects.equals(building.getName(), "Clicks")) {
                double percentage = (cookiesPerSecond != 0) ? Math.round((building.getCookiesPerSecond() * building.getCount() * 100.0) / cookiesPerSecond * 100.0) / 100.0:0;
                hoverLabel.setText(
                        "Title: " + building.getName() +
                                "\nPrice: $" + buildingPrice +
                                "\n" + building.getCount() + " " + building.getName() + "s" + " producing " + production + " cookies" + " per second" + "\n1 building produces: " + cPs + "\n% of total cPs: " + percentage + "%");
            } else {
                hoverLabel.setText("Title: Mouse" +
                        "\nPrice: $" + buildingPrice +
                        "\nTimes purchased: " + building.getCount() +
                        "\nOne click: " + numStringConvert(clickValue) +
                        "\nTotal clicks: " + numStringConvert(clicks));
            }
            root.getChildren().add(hoverLabel);
        });
        element.setOnMouseExited(event -> root.getChildren().remove(hoverLabel));
    }

    /**
     The method used to buy an upgrade.
     Upgrades will boost the production of cookies per second for each building.
     Features:
     - Boosts 2x the current cookies per second.
     - Price 5x the current price of the boost.

     @param upgradeBoost - the upgrade being purchased.
     @param building     - the building that is being boosted.
     */
    private static void buyUpgrade(Upgrade upgradeBoost, Building building) {
        if (cookies >= upgradeBoost.getPrice()) {
            if (Objects.equals(upgradeBoost.getName(), "Clicks")) {
                clickValue *= 2;
            } else {
                cookiesPerSecond -= building.getCookiesPerSecond() * building.getCount();
                building.setCookiesPerSecond(2 * building.getCookiesPerSecond());
                cookiesPerSecond += building.getCookiesPerSecond() * building.getCount();

            }
            cookies -= upgradeBoost.getPrice();
            upgradeBoost.setPrice(5 * upgradeBoost.getPrice());
            upgradeBoost.setPurchaseCount(upgradeBoost.getPurchaseCount() + 1);

            updateUpgrades();
            updateBuildings();

        }
    }

    /**
     A simple method used to return a formatted number that has commas separating every 3 0's

     @param num the number that will be formatted.
     @return the formatted number.
     */
    private static String numStringConvert(double num) {
        return NumberFormat.getNumberInstance(Locale.US).format(num);
    }

    /**
     The method used to determine if the screen needs to be updated.
     Will change the colors of the buy and sell buttons if they are affordable.
     */
    public static void updateColors() {
        Building minBuilding = buildings.getFirst();
        boolean update = false;
        Upgrade minUpgrade = upgradeBoosts.getFirst();

        for (Upgrade upgrade : upgradeBoosts) {
            if (upgrade.getPrice() < minUpgrade.getPrice()) {
                minUpgrade = upgrade;
            }
        }
        for (Building building : buildings) {
            if (building.getPrice() < minBuilding.getPrice()) {
                minBuilding = building;
            }
        }
        if (cookies >= minBuilding.getPrice() || cookies >= minUpgrade.getPrice()) {
            update = true;
        }
        if (update) {
            GUI.updateBuildings();
            GUI.updateUpgrades();
        }
    }


}
