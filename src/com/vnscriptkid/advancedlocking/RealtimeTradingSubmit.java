package com.vnscriptkid.advancedlocking;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class RealtimeTradingSubmit extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cryptocurrency Prices");

        GridPane grid = createGrid();
        Map<String, Label> cryptoLabels = createCryptoPriceLabels();

        addLabelsToGrid(cryptoLabels, grid);

        double width = 300;
        double height = 250;

        StackPane root = new StackPane();

        Rectangle background = createBackgroundRectangleWithAnimation(width, height);

        root.getChildren().add(background);
        root.getChildren().add(grid);

        primaryStage.setScene(new Scene(root, width, height));

        // shared resource: hold figures of prices
        PricesContainer pricesContainer = new PricesContainer();

        // thread 1: simulate calling network, then update local price in pricesContainer
        PriceUpdater priceUpdater = new PriceUpdater(pricesContainer);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            // 30 frames per sec => this fn is called 30 times per sec
            // this will run on UI thread: use shared resource, update current values to UI
            public void handle(long now) {
                // todo: lock resource, use kind that allows multiple tries, we don't want to block UI
                var lockObj = pricesContainer.getLock();

                try {
                    if (lockObj.tryLock()) {
                        // todo: get each price, conver to string, and feed to setText
                        Label bitcoinLabel = cryptoLabels.get("BTC");
                        bitcoinLabel.setText(Double.toString(pricesContainer.getBitcoinPrice()));

                        Label liteCoinLabel = cryptoLabels.get("LTC");
                        liteCoinLabel.setText(Double.toString(pricesContainer.getLitecoinPrice()));

                        Label etherLabel = cryptoLabels.get("ETH");
                        etherLabel.setText(Double.toString(pricesContainer.getEtherPrice()));

                        Label rippleLabel = cryptoLabels.get("XRP");
                        rippleLabel.setText(Double.toString(pricesContainer.getRipplePrice()));

                        Label bitcoinCashLabel = cryptoLabels.get("BCH");
                        bitcoinCashLabel.setText(Double.toString(pricesContainer.getBitcoinCashPrice()));
                    }
                } finally {
                    // todo: unlock resource whatever happens
                    lockObj.unlock();
                }
            }
        };

        addWindowResizeListener(primaryStage, background);

        animationTimer.start();

        priceUpdater.start();

        primaryStage.show();
    }

    private void addWindowResizeListener(Stage stage, Rectangle background) {
        ChangeListener<Number> stageSizeListener = ((observable, oldValue, newValue) -> {
            background.setHeight(stage.getHeight());
            background.setWidth(stage.getWidth());
        });
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    private Map<String, Label> createCryptoPriceLabels() {
        Label bitcoinPrice = new Label("0");
        bitcoinPrice.setId("BTC");

        Label etherPrice = new Label("0");
        etherPrice.setId("ETH");

        Label liteCoinPrice = new Label("0");
        liteCoinPrice.setId("LTC");

        Label bitcoinCashPrice = new Label("0");
        bitcoinCashPrice.setId("BCH");

        Label ripplePrice = new Label("0");
        ripplePrice.setId("XRP");

        Map<String, Label> cryptoLabelsMap = new HashMap<>();
        cryptoLabelsMap.put("BTC", bitcoinPrice);
        cryptoLabelsMap.put("ETH", etherPrice);
        cryptoLabelsMap.put("LTC", liteCoinPrice);
        cryptoLabelsMap.put("BCH", bitcoinCashPrice);
        cryptoLabelsMap.put("XRP", ripplePrice);

        return cryptoLabelsMap;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    private void addLabelsToGrid(Map<String, Label> labels, GridPane grid) {
        int row = 0;
        for (Map.Entry<String, Label> entry : labels.entrySet()) {
            String cryptoName = entry.getKey();
            Label nameLabel = new Label(cryptoName);
            nameLabel.setTextFill(Color.BLUE);
            nameLabel.setOnMousePressed(event -> nameLabel.setTextFill(Color.RED));
            nameLabel.setOnMouseReleased((EventHandler) event -> nameLabel.setTextFill(Color.BLUE));

            grid.add(nameLabel, 0, row);
            grid.add(entry.getValue(), 1, row);

            row++;
        }
    }

    private Rectangle createBackgroundRectangleWithAnimation(double width, double height) {
        Rectangle backround = new Rectangle(width, height);
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), backround, Color.LIGHTGREEN, Color.LIGHTBLUE);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
        return backround;
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static class PricesContainer {
        // todo: create lock of type ReentrantLock
        private ReentrantLock lock = new ReentrantLock();
        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        // todo: create getters, setters for all objects
        public ReentrantLock getLock() {
            return lock;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public double getLitecoinPrice() {
            return litecoinPrice;
        }

        public void setLitecoinPrice(double litecoinPrice) {
            this.litecoinPrice = litecoinPrice;
        }

        public double getBitcoinCashPrice() {
            return bitcoinCashPrice;
        }

        public void setBitcoinCashPrice(double bitcoinCashPrice) {
            this.bitcoinCashPrice = bitcoinCashPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }
    }

    public static class PriceUpdater extends Thread {
        private PricesContainer pricesContainer;
        private Random random = new Random();

        public PriceUpdater(PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while (true) {
                // todo: simulate network of 1 sec
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // todo: lock shared resource
                // why? we don't want other threads come in and take partially updated price
                var lockObj = this.pricesContainer.getLock();

                try {
                    lockObj.lock();

                    // todo: set each price to a random number
                    pricesContainer.setBitcoinCashPrice(random.nextInt(10000));
                    pricesContainer.setEtherPrice(random.nextInt(5000));
                    pricesContainer.setRipplePrice(random.nextDouble(4000));
                    pricesContainer.setLitecoinPrice(random.nextInt(1000));
                    pricesContainer.setBitcoinPrice(random.nextInt(7000));

                } finally {
                    // todo: unlock resource whatever happens
                    lockObj.unlock();
                }

                try {
                    // todo: simulate 2 secs processing
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
