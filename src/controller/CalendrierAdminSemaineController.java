package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class CalendrierAdminSemaineController {

    @FXML
    private Label timeLabel;

    @FXML
    private HBox daySelector;

    @FXML
    private AnchorPane calendarPane;

    private final int startHour = 7;
    private final int endHour = 22;
    private final int hourHeight = 60; // 60 pixels par heure

    /**
     * This method is called to display the current time in the specified label.
     */
    public void initialize() {
        // Afficher les jours (par ex : Lun 24, Mar 25...)
        LocalDate today = LocalDate.now(); // Charge la date du jour

        // Calcule le début de la semaine en faisant le calcul à partir du jour actuel
        // Exemple : Si aujourd'hui est Mercredi, on va afficher les jours de Lundi à Dimanche
        // -1 car getDayOfWeek().getValue() retourne 1 pour Lundi, 2 pour Mardi, etc.
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1); // .minusday permet de retirer le nombre de jour

        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            Button bouton;
            bouton = createDayButton(day, today);
            bouton.setOnAction(e -> displayDay(day));
            daySelector.getChildren().add(bouton);
        }

        displayDay(today);
    }

    public Button createDayButton(LocalDate date, LocalDate today) {
        // Clone la structure en pur Java
        VBox vbox = new VBox();
        Button btn = new Button();

        if (today == date) {
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
            vbox.setPrefSize(41, 50);
            vbox.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setSpacing(0);

            Label dayLabel = new Label();

            // Créer un formateur de date en français
            DateTimeFormatter frenchDayFormatter = DateTimeFormatter.ofPattern("E", Locale.FRENCH);

            // Appliquer au label
            String jourAbrege = date.format(frenchDayFormatter); // "lun.", "mar.", etc.
            dayLabel.setText(jourAbrege.substring(0, 3).toUpperCase()); // "LUN", "MAR", etc.
            dayLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");

            Label numLabel = new Label(String.valueOf(date.getDayOfMonth()));
            numLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");

            vbox.getChildren().addAll(dayLabel, numLabel);

            btn.setGraphic(vbox);
            btn.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: transparent;");
            btn.setPrefSize(41, 50);
            btn.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            btn.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        } else {
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: rgba(171, 171, 171, 0.51); -fx-background-radius: 10;");
            vbox.setPrefSize(41, 50);
            vbox.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setSpacing(0);

            Label dayLabel = new Label();

            // Créer un formateur de date en français
            DateTimeFormatter frenchDayFormatter = DateTimeFormatter.ofPattern("E", Locale.FRENCH);

            // Appliquer au label
            String jourAbrege = date.format(frenchDayFormatter); // "lun.", "mar.", etc.
            dayLabel.setText(jourAbrege.substring(0, 3).toUpperCase()); // "LUN", "MAR", etc.
            dayLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");

            Label numLabel = new Label(String.valueOf(date.getDayOfMonth()));
            numLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");

            vbox.getChildren().addAll(dayLabel, numLabel);

            btn.setGraphic(vbox);
            btn.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: transparent;");
            btn.setPrefSize(41, 50);
            btn.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            btn.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        }

        return btn;
    }
    private void displayDay(LocalDate day) {
        calendarPane.getChildren().clear();

        // Affiche les lignes horaires
        for (int h = startHour; h <= endHour; h++) {
            Text hourText = new Text(10, (h - startHour) * hourHeight + 15, h + "h00");
            calendarPane.getChildren().add(hourText);
        }

        // Ex : afficher un évènement de 7h à 10h30
        createEvent("DPS 1", LocalTime.of(7, 0), LocalTime.of(10, 30));
    }

    private void createEvent(String label, LocalTime start, LocalTime end) {
        double startY = (start.getHour() + start.getMinute() / 60.0 - startHour) * hourHeight;
        double height = ((end.toSecondOfDay() - start.toSecondOfDay()) / 3600.0) * hourHeight;

        Rectangle rect = new Rectangle(100, startY, 200, height);
        rect.setFill(Color.web("#C8FACC"));
        rect.setStroke(Color.GREEN);

        Text labelText = new Text(110, startY + 20, start + " - " + end + "\n" + label);

        calendarPane.getChildren().addAll(rect, labelText);
    }



    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the TableauDeBordAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleAccueil(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/TableauDeBordAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue TableauDeBordAdmin : " + e.getMessage());
        }
    }
    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the ListeDesSecouristesAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    public void handleEffectif(MouseEvent mouseEvent) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/ListeDesSecouristesAdmin.fxml", (Node) mouseEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ListeDesSecouristesAdmin : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the AlertesAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleAlertesAdmin(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/AlertesAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue AlertesAdmin : " + e.getMessage());
        }
    }
}
