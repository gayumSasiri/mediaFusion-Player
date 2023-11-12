package lk.ijse.dep11.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class MainUiSceneController {

    public AnchorPane root;
    public MediaView mvPlayerViewport;
    public JFXButton btnBrowse;
    public JFXButton btnPlay;
    public JFXButton btnPause;
    public ImageView imgView;
    public Slider slrTimeLine;
    public JFXButton btnStop;
    public JFXButton btnTenSecBack;
    public JFXButton btnTenSecForward;
    public JFXButton btnSlower;
    public JFXButton btnFaster;
    public JFXSlider slrVolume;
    public JFXButton btnMaximize;
    public Label lblVolume;
    public AnchorPane mediaRoot;
    MediaPlayer videoPlayer;
    String fileName;

    public void initialize(){
        btnPlay.setTooltip(new Tooltip("Play"));
        btnFaster.setTooltip(new Tooltip("2X speed"));
        btnBrowse.setTooltip(new Tooltip("Browse media"));
        btnMaximize.setTooltip(new Tooltip("Full Screen view"));
        btnSlower.setTooltip(new Tooltip("0.5X slower"));
        btnPause.setTooltip(new Tooltip("Pause"));
        btnStop.setTooltip(new Tooltip("Stop"));
        btnTenSecBack.setTooltip(new Tooltip("Ten Seconds Back"));
        btnTenSecForward.setTooltip(new Tooltip("Ten Seconds Forward"));
    }

    public void btnBrowseOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mp3 Files", "*.mp3"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wave Files", "*.wav"));

        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (selectedFile != null) {
            fileName = selectedFile.getName();
            String fileExtension = getExtension(fileName);

            if (isVideoFile(fileExtension)) {
                mediaRoot.getChildren().remove(imgView);
            } else if (isAudioFile(fileExtension)) {
                mediaRoot.getChildren().remove(mvPlayerViewport);
                mediaRoot.getChildren().add(imgView);
            } else {
                new Alert(Alert.AlertType.ERROR,"Unsupported file format!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION,"No file selected!").show();
        }

        if (selectedFile != null) {
            Media media = new Media(selectedFile.toURI().toString());
            videoPlayer = new MediaPlayer(media);
            setVideoOnScreen();
            btnPlay.fire();
        }
    }

    private String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    private boolean isVideoFile(String fileExtension) {
        return fileExtension.equalsIgnoreCase("mp4") ||
                fileExtension.equalsIgnoreCase("avi") ||
                fileExtension.equalsIgnoreCase("mkv");
    }

    private boolean isAudioFile(String fileExtension) {
        return fileExtension.equalsIgnoreCase("mp3") ||
                fileExtension.equalsIgnoreCase("wav") ;
    }

    //---------------------features are below here------------------------

    public void btnPlayOnAction(ActionEvent actionEvent) {
        if ( videoPlayer != null){
            mvPlayerViewport.setMediaPlayer(videoPlayer);
            videoPlayer.play();
        }
    }

    public void btnPauseOnAction(ActionEvent actionEvent) {
        if (videoPlayer != null){
            mvPlayerViewport.setMediaPlayer(videoPlayer);
            videoPlayer.stop();
            videoPlayer.setRate(1.0);
        }
    }

    public void btnStopOnAction(ActionEvent actionEvent) {
        if (videoPlayer != null){
            mvPlayerViewport.setMediaPlayer(videoPlayer);
            videoPlayer.pause();
            videoPlayer.setRate(1.0);
        }
    }

    public void btnTenSecBackOnAction(ActionEvent actionEvent) {
        if (videoPlayer != null) {
            videoPlayer.seek(videoPlayer.getCurrentTime().add(Duration.seconds(-10)));
        }
    }

    public void btnTenSecForwardOnAction(ActionEvent actionEvent) {
        if (videoPlayer != null) {
            videoPlayer.seek(videoPlayer.getCurrentTime().add(Duration.seconds(10)));
        }
    }

    public void btnSlowerOnAction(ActionEvent actionEvent) {
        if (videoPlayer != null) {
            videoPlayer.setRate(0.5);
        }
    }

    public void btnFasterOnAction(ActionEvent actionEvent) {
        if (videoPlayer != null) {
            videoPlayer.setRate(2.0);
        }
    }

    public void btnMaximizeOnAction(ActionEvent actionEvent) {
        //todo
    }


    public void slrVolumeOnClick(MouseEvent mouseEvent) {
        if(videoPlayer != null) {
            videoPlayer.setVolume(slrVolume.getValue() /100);
            lblVolume.setText(String.format("%.0f",slrVolume.getValue()).concat("%"));
        }
    }

    public void slrVolumeOnDragged(MouseEvent mouseEvent) {
        if(videoPlayer != null) {
            videoPlayer.setVolume(slrVolume.getValue() /100);
            lblVolume.setText(String.format("%.0f",(slrVolume.getValue())).concat("%"));
        }
    }

    private void setVideoOnScreen() {
        mvPlayerViewport.fitWidthProperty().bind(mediaRoot.widthProperty());
        mvPlayerViewport.fitHeightProperty().bind(mediaRoot.heightProperty());

        videoPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                slrTimeLine.setValue(t1.toSeconds());
            }
        });

        videoPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration totalDuration = videoPlayer.getTotalDuration();
                slrTimeLine.setMax(totalDuration.toSeconds());
            }
        });
    }

    public void slrTimeLineOnClick(MouseEvent mouseEvent) {
        if (videoPlayer != null) {
            videoPlayer.seek(Duration.seconds(slrTimeLine.getValue()));
        }else {
            slrTimeLine.setValue(0);
        }
    }

    public void slrTimeLineOnDragged(MouseEvent mouseEvent) {
        if (videoPlayer != null) {
            videoPlayer.seek(Duration.seconds(slrTimeLine.getValue()));
        }else {
            slrTimeLine.setValue(0);
        }
    }
}
