package Code;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class View extends Application {

    public static final int SCREEN_WIDTH = 1600;
    public static final int SCREEN_HEIGHT = 1000;
    public static final DropShadow dropShadow = new DropShadow();
    public static double cookies = 10000;
    public static int cookiesPerSecond = 0;
    public static List<Building> buildings;
    public static List<Upgrade> upgradeBoosts;
    public static Pane root;
    public static int clickValue = 1;
    public static int clicks = 0;
    public static Label outputLabel;
    private static double totalCookies = 0;
    Button cookieButton;
    private Label hoverLabel;
    private static boolean lockStatusLocked = true;


    public static void main(String[] args) {
        launch(args);
    }

    private static void cookiesPerSecondUpdate() {

        cookies += (double) cookiesPerSecond / 100;
        totalCookies += (double) cookiesPerSecond / 100;
        Building lowestLockedBuilding = buildings.getFirst();

        for (Building building : buildings) {
            if (cookies <= building.getPrice()) {
//                button.setStyle("-fx-background-color: #ea9999;");
                building.setStatus(false);
            } else {
//                button.setStyle("-fx-background-color: #b6d7a8;");
                building.setStatus(true);
            }
            if (!building.CanUpgrade()) {
                lowestLockedBuilding = building;
                lockStatusLocked = false;
                break;

            }


        }
        System.out.println(lowestLockedBuilding.getPrice());
        if (cookies >= lowestLockedBuilding.getPrice()) {
            System.out.println("Colors updated");
            GUI.updateColors();
        }

    }

    public void clickUpdate(ActionEvent event) {

        clicks += 1;
        cookies += clickValue;
        totalCookies += clickValue;
    }

    private void initializeBuildings() {

        buildings = new ArrayList<>();
        buildings.add(new Building("Clicks", 0, 0, 0, "", ""));
        buildings.add(new Building("Cursor", 15, 1, 0, "Assets/icons/CursorIcon.jpg", "Assets/building/Cursor.jpg", true));
        buildings.add(new Building("Grandma", 100, 5, 144, "Assets/icons/GrandmaIcon.jpg", "Assets/building/Grandma.jpg"));
        buildings.add(new Building("Farm", 1100, 20, 288, "Assets/icons/FarmIcon.jpg", "Assets/building/Farm.jpg"));
        buildings.add(new Building("Mine", 12000, 47, 432, "Assets/icons/MineIcon.jpg", "Assets/building/Mine.jpg"));
        buildings.add(new Building("Factory", 130000, 260, 576, "Assets/icons/FactoryIcon.jpg", "Assets/building/Factory.jpg"));
        buildings.add(new Building("Bank", 1400000, 1400, 720, "Assets/icons/BankIcon.jpg", "Assets/building/Bank.jpg"));
        buildings.add(new Building("Temple", 2000000, 7800, 864, "Assets/icons/TempleIcon.jpg", "Assets/building/Temple.jpg"));
        buildings.add(new Building("Wizard", 33000000, 44000, 1008, "Assets/icons/WizIcon.jpg", "Assets/building/Wiz.jpg"));
    }

    private void initializeUpgrades() {

        upgradeBoosts = new ArrayList<>();
        upgradeBoosts.add(new Upgrade("Clicks", 500));
        upgradeBoosts.add(new Upgrade("Cursor", 1000));
        upgradeBoosts.add(new Upgrade("Grandma", 5000));
        upgradeBoosts.add(new Upgrade("Farm", 10000));
        upgradeBoosts.add(new Upgrade("Mine", 30000));
        upgradeBoosts.add(new Upgrade("Factory", 600000));
        upgradeBoosts.add(new Upgrade("Bank", 1200000));
        upgradeBoosts.add(new Upgrade("Temple", 6000000));
        upgradeBoosts.add(new Upgrade("Wizard", 90000000));
    }

//    private void onButtonMouseEnter() {
//
//        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), cookieButton);
//        scaleTransition.setToX(1.2);
//        scaleTransition.setToY(1.25);
//        scaleTransition.play();
//    }

//    private void onButtonMouseExit() {
//
//        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), cookieButton);
//        scaleTransition.setToX(1.1);
//        scaleTransition.setToY(1.1);
//        scaleTransition.play();
//    }

    private void onButtonMouseRelease() {

//        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), cookieButton);
//        scaleTransition.setToX(1.2);
//        scaleTransition.setToY(1.2);
//        scaleTransition.play();
        GUI.updateColors();

    }

//    private void onButtonMousePress() {

//        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), cookieButton);
//        scaleTransition.setToX(1.1);
//        scaleTransition.setToY(1.1);
//        scaleTransition.play();
//        GUI.updateColors();
//    }

    private void initializeNodes() {

        // Adds a drop shadow to the labels when the user hovers over them.
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.GRAY);

        // Draws all the backgrounds for each building.
        String[] backgroundPaths = {"GrandmaBackground", "FarmBackground", "MineBackground", "FactoryBackground", "BankBackground", "TempleBackground"};
        ImageView[] backgroundViews = new ImageView[backgroundPaths.length];
        for (int i = 0; i < backgroundPaths.length; i++) {
            String path = "src/Assets/Backgrounds/" + backgroundPaths[i] + ".jpg";
            Image backgroundSRC = new Image(new File(path).toURI().toString());
            backgroundViews[i] = new ImageView(backgroundSRC);
            backgroundViews[i].relocate(345, 130 + i * 144);
        }
        root.getChildren().addAll(backgroundViews);
        // Draws the separation panel between each building Background.
        int panelPrint = buildings.size();
        for (int i = 0; i < panelPrint; i++) {
            Image panelHorizontalSRC = new Image(new File("src/Assets/panelHorizontal.jpg").toURI().toString());
            ImageView panelHorizontal = new ImageView(panelHorizontalSRC);
            panelHorizontal.relocate(345, 114 + i * (144));
            root.getChildren().add(panelHorizontal);
        }

        // Draws the cookie background.
        Image cookieBackgroundSRC = new Image(new File("src/Assets/Backgrounds/CookieBackground.jpg").toURI().toString());
        ImageView cookieBackground = new ImageView(cookieBackgroundSRC);
        cookieBackground.relocate(0, 0);
        root.getChildren().add(cookieBackground);

        // Adds and sizes the cookie image.
        Image cookieImage = new Image((new File("src/Assets/Cookie.jpg").toURI().toString()));
        ImageView cookie = new ImageView(cookieImage);
        cookie.setFitWidth(180);
        cookie.setFitHeight(175);


        // Creates key labels that are  titles for the game.
        Label gameTitle = new Label("Cookie Clicker");
        Label upgradesLabel = new Label("Upgrades:");
        Label buildingsLabel = new Label("Buildings:");
        Label statsLabel = new Label("Stats");

        // Initializes the label and button (left side of the screen).
        outputLabel = new Label("");
        cookieButton = new Button("");

        // Positions and styles the output label.
        outputLabel.relocate(10, 80);
        outputLabel.setFont(new Font("Arial", 22));
        outputLabel.setPrefWidth(300);
        outputLabel.setOpacity(0.7);
        outputLabel.setStyle("-fx-background-color: black;-fx-text-fill:white;-fx-alignment: center;");

        // Positions and styles the title of the game.
        gameTitle.relocate(625, 27);
        gameTitle.setFont(new Font("Arial", 50));
        gameTitle.setPrefWidth(400);
        gameTitle.setPrefHeight(60);
        gameTitle.setStyle("-fx-text-fill:white;-fx-alignment: center;");

        // Positions and styles the upgrades header.
        upgradesLabel.relocate(1300, 25);
        upgradesLabel.setFont(new Font("Arial", 30));
        upgradesLabel.setPrefWidth(250);
        upgradesLabel.setPrefHeight(50);
        upgradesLabel.setStyle("-fx-background-color: black;-fx-background-radius: 5;-fx-text-fill:white;-fx-alignment: center;");

        // Positions and styles the buildings header.
        buildingsLabel.relocate(1300, 240);
        buildingsLabel.setFont(new Font("Arial", 30));
        buildingsLabel.setPrefWidth(250);
        buildingsLabel.setPrefHeight(50);
        buildingsLabel.setStyle("-fx-background-color: black;-fx-background-radius: 5;-fx-text-fill:white;-fx-alignment: center;");

        // Positions and styles and sets the graphics for the cookie.
        cookieButton.relocate(70, 310);
        cookieButton.setGraphic(cookie);
        cookieButton.setOnAction(this::clickUpdate);
        cookieButton.setStyle("-fx-background-radius: 90; -fx-min-width: 70px; -fx-min-height: 70px; -fx-background-color: transparent;");

        // Positions and styles the Stats Label
        statsLabel.relocate(450, 50);
        statsLabel.setFont(new Font("Arial", 20));
        statsLabel.setPrefWidth(60);
        statsLabel.setPrefHeight(30);
        statsLabel.setStyle("-fx-background-color: black;-fx-background-radius: 5;-fx-text-fill:white;-fx-alignment: center;");

        statsLabel.setOnMouseEntered(event -> {
            hoverLabel = new Label("");
            hoverLabel.relocate(350, 90);
            hoverLabel.setStyle("-fx-background-color: black;-fx-text-fill:white; -fx-alignment: center;");
            hoverLabel.setPrefWidth(260);
            hoverLabel.setEffect(dropShadow);
            int buildingsOwned = 0;
            for (Building building : buildings) {
                buildingsOwned += building.getCount();
            }
            hoverLabel.setText("Cookies in Bank: " + GUI.formatString(cookies) + "\nCookies per second: " + GUI.formatString(cookiesPerSecond) + "\nTotal cookies baked: " + GUI.formatString(totalCookies) + "\nCookie clicks: " + GUI.formatString(clicks) + "\nBuildings owned: " + GUI.formatString(buildingsOwned));
            root.getChildren().add(hoverLabel);
        });
        statsLabel.setOnMouseExited(event -> root.getChildren().remove(hoverLabel));

        // Adds the rest of the elements to the screen.
        root.getChildren().addAll(outputLabel, cookieButton, gameTitle, upgradesLabel, buildingsLabel, statsLabel);
//        cookieButton.setOnMouseEntered((MouseEvent event) -> onButtonMouseEnter());
//        cookieButton.setOnMouseExited((MouseEvent event) -> onButtonMouseExit());
//        cookieButton.setOnMousePressed((MouseEvent event) -> onButtonMousePress());
        cookieButton.setOnMouseReleased((MouseEvent event) -> onButtonMouseRelease());
    }

    @Override
    public void start(Stage stage) {

        root = new Pane();
        root.setStyle("-fx-background-color: #173346;");
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("Cookie Clicker");
        // Updates the label that shows the user how many cookies they have
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> GUI.updateLabel()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) System.exit(0);
        });
        stage.setOnCloseRequest(event -> System.exit(0));

        // Initializes and updates key aspects of the game.
//        scene.setOnMouseMoved(event -> GUI.updateColors());
        initializeBuildings();
        initializeUpgrades();
        GUI.updateBuildings();
        GUI.updateUpgrades();
        initializeNodes();


        // Creates a scheduled event that occurs once every second.
        ScheduledExecutorService UpdateCPS = Executors.newScheduledThreadPool(1);
        UpdateCPS.scheduleAtFixedRate(View::cookiesPerSecondUpdate, 1, 10, TimeUnit.MILLISECONDS);

        // Show the stage
        stage.setScene(scene);
        stage.show();
    }
}