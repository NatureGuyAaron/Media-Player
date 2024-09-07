import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javafx.scene.control.Slider;
import javafx.scene.layout.*;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.util.Duration;
import javafx.scene.image.Image;


import java.io.*;

import java.nio.file.Paths;


public class MediaDemo extends Application  {

   String videoPath = "C:\\Users\\Jigz2\\Videos\\Captures\\(12) HPAC - Oh What a Change - 14th Jan 2024 - YouTube - Google Chrome 2024-07-12 02-21-44.mp4";




    MediaPlayer mediaPlayer;

    MediaView mediaView;
    Slider timeSlider;


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {

        mediaView = new MediaView();
        FileChooser fileChooser = new FileChooser();
        BorderPane borderPane = new BorderPane();


        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem menuItem = new MenuItem("Open ");
        menu.getItems().add(menuItem);
        menuBar.getMenus().add(menu);

        Functionality func = new Functionality();




//        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Mp4", "*.mp4");





        menuItem.setOnAction(e ->
        {
            File selectedfile = fileChooser.showOpenDialog(primaryStage);

            initializeMedia(selectedfile);

        });
        double w = primaryStage.getWidth(); // player.getMedia().getWidth();
        double h = primaryStage.getHeight(); // player.getMedia().getHeight();

        primaryStage.setMinWidth(w);
        primaryStage.setMinHeight(h);
        // make the video conform to the size of the stage now...
        mediaView.setPreserveRatio(true); // Maintain the video’s aspect ratio
        mediaView.fitWidthProperty().bind(primaryStage.widthProperty()); // Bind width to scene's width
        mediaView.fitHeightProperty().bind(primaryStage.heightProperty());

        VBox vBox = new VBox(menuBar);
        vBox.setAlignment(Pos.TOP_LEFT);
        borderPane.setTop(vBox);
        borderPane.setCenter(mediaView);


        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        Button playBtn = new Button(" > ");
        Button rewindBtn = new Button("<<");
        rewindBtn.setOnAction(e -> mediaPlayer.seek(Duration.seconds(5)));
        Button fastForwardButton = new Button(">>");
//        fastForwardButton.setOnAction(e -> mediaPlayer.);
        Slider volumeSlider = new Slider();

        volumeSlider.valueProperty().addListener( e ->
        {

        });



        playBtn.setOnAction(e -> {
            if (playBtn.getText().equals(">")) {
                mediaPlayer.play();
                playBtn.setText("||");
            } else {
                mediaPlayer.pause();
                playBtn.setText(">");
            }
        });

        Region regionForVolumeSliderToBePushedRight = new Region();
        Region regionForVolumeSliderToBePushedRight2 = new Region();

        HBox.setHgrow(regionForVolumeSliderToBePushedRight, Priority.ALWAYS);
        HBox.setHgrow(regionForVolumeSliderToBePushedRight2, Priority.ALWAYS);
        HBox.setHgrow(rewindBtn, Priority.SOMETIMES);
        HBox.setHgrow(playBtn, Priority.SOMETIMES);
        HBox.setHgrow(fastForwardButton, Priority.SOMETIMES);


        controls.getChildren().addAll(regionForVolumeSliderToBePushedRight2, rewindBtn, playBtn, fastForwardButton, regionForVolumeSliderToBePushedRight, volumeSlider);

        controls.setAlignment(Pos.CENTER);
         timeSlider = new Slider();


        timeSlider.setMin(0);
        timeSlider.setMax(100);
        timeSlider.setPrefHeight(20);
        timeSlider.setValue(0);


        VBox bottomControls = new VBox(5);
        bottomControls.setAlignment(Pos.CENTER);
        bottomControls.getChildren().addAll(timeSlider, controls);

        borderPane.setBottom(bottomControls);
        borderPane.setCenter(mediaView);

//        menu.setStyle("-fx-color: #EB0D1B");
//        menu.setValue("File");


        Image image = new Image("MusicImage.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,  // No repeat
                BackgroundRepeat.NO_REPEAT,  // No repeat
                BackgroundPosition.CENTER,   // Centered in the pane
                new BackgroundSize(                   // Size settings
                        1.0, 1.0,                          // Set width and height to 100% of the pane size
                        true, true,                        // Cover entire pane (stretch to fit)
                        false, false)        // Default background size
        );

        borderPane.setBackground(new Background(backgroundImage));
        Scene scene = new Scene(borderPane, 800, 600);
        scene.getStylesheets().add("/style.css");
        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void initializeMedia(File mediaFile) {
        // If a media player exists, dispose of the old one
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }


        Media media = new Media(mediaFile.toURI().toString());


        mediaPlayer = new MediaPlayer(media);


        mediaView.setMediaPlayer(mediaPlayer);


        mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue(newTime.toSeconds());
            }
        });


        mediaPlayer.setOnReady(() -> {
            timeSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
        });


        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (timeSlider.isValueChanging()) {
                    // Seek the video to the slider's value
                    mediaPlayer.seek(javafx.util.Duration.seconds(timeSlider.getValue()));
                }
            }
        });
    }
    }














