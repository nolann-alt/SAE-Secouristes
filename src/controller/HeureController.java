package controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HeureController {

    /**
     * Met à jour en continu une Label avec l'heure actuelle (HH:mm).
     * @param timeLabel le label à mettre à jour
     */
    public static void afficherHeure(Label timeLabel) {
        //Pour le chargement
        if (timeLabel == null) {
            return;
        }

        //Affichage immédiat de l’heure à l’init
        LocalTime currentTime = LocalTime.now();
        timeLabel.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm")));

        //Puis mise à jour toutes les secondes
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalTime now = LocalTime.now();
            timeLabel.setText(now.format(DateTimeFormatter.ofPattern("HH:mm")));
        }));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
