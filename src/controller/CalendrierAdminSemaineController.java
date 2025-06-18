package controller;

import javafx.event.Event;
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
import java.util.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import metier.graphe.model.EventData;

/**
 * Controller for the admin week calendar view.
 * Handles the display of events in a weekly calendar layout.
 *
 * @author M. Weis, N. Lescop, M. Gouelo, A. Jan
 * @version 1.0
 */
public class CalendrierAdminSemaineController {

    @FXML
    private Label timeLabel; // Affiche l'heure courante dans l'en-tête

    @FXML
    private HBox daySelector; // Conteneur pour les boutons de jours de la semaine

    @FXML
    private AnchorPane calendarPane; // Zone d'affichage principale du calendrier

    private final int startHour = 7;   // Heure de début de l'affichage (7h)
    private final int endHour = 22;   // Heure de fin (22h)
    private final int hourHeight = 60; // Hauteur d'un créneau horaire en pixels

    private final Map<LocalDate, List<EventData>> eventMap = new HashMap<>();

    /**
     * Initialize the week view and setup events.
     * Creates day buttons, assigns actions, and populates test events.
     */
    public void initialize() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);

        // Génère les boutons pour chaque jour de la semaine
        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            Button bouton = createDayButton(day, today);
            bouton.setOnAction(e -> {
                displayDay(day);
                timeLabel.setText(day.equals(today) ?
                        "Aujourd'hui" :
                        day.format(DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRENCH)));
            });
            daySelector.getChildren().add(bouton);
        }

        // Événements de test
        addEvent(today, "DPS 1", LocalTime.of(9, 0), LocalTime.of(10, 0), Color.BLUE);
        addEvent(today, "DPS 2", LocalTime.of(12, 0), LocalTime.of(13, 0), Color.GREEN);

        displayDay(today);
    }

    /**
     * Crée un bouton représentant un jour de la semaine.
     */
    private Button createDayButton(LocalDate date, LocalDate today) {
        VBox vbox = new VBox();
        Button btn = new Button();

        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(41, 50);
        vbox.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        vbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        vbox.setSpacing(0);

        // Étiquette du jour (ex: "LUN")
        DateTimeFormatter frenchDayFormatter = DateTimeFormatter.ofPattern("E", Locale.FRENCH);
        String jourAbrege = date.format(frenchDayFormatter).substring(0, 3).toUpperCase();
        Label dayLabel = new Label(jourAbrege);
        Label numLabel = new Label(String.valueOf(date.getDayOfMonth()));

        if (today.equals(date)) {
            vbox.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
            dayLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
            numLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
        } else {
            vbox.setStyle("-fx-background-color: rgba(171, 171, 171, 0.51); -fx-background-radius: 10;");
            dayLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");
            numLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");
        }

        vbox.getChildren().addAll(dayLabel, numLabel);
        btn.setGraphic(vbox);
        btn.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: transparent;");
        btn.setPrefSize(41, 50);
        return btn;
    }

    /**
     * Affiche les événements d’un jour donné dans le calendrier.
     */
    private void displayDay(LocalDate day) {
        calendarPane.getChildren().clear();

        // Création de l'échelle horaire
        for (int h = startHour; h <= endHour; h++) {
            Text hourText = new Text(10, (h - startHour) * hourHeight + 15, h + "h00");
            calendarPane.getChildren().add(hourText);

            Line hourLine = new Line(60, (h - startHour) * hourHeight, 363, (h - startHour) * hourHeight);
            hourLine.setStroke(Color.LIGHTGRAY);
            hourLine.setStrokeWidth(1);
            calendarPane.getChildren().add(hourLine);
        }

        calendarPane.setMinHeight((endHour - startHour) * hourHeight + 200);

        List<EventData> events = eventMap.get(day);
        if (events != null) {
            for (EventData event : events) {
                createEvent(event.getLabel(), event.getStart(), event.getEnd(), event.getColor(), day);
            }
        }
    }

    /**
     * Crée un événement visuel sur le calendrier.
     */
    private void createEvent(String label, LocalTime start, LocalTime end, Color color, LocalDate day) {
        double startY = (start.getHour() + (start.getMinute() / 60.0) - startHour) * hourHeight;
        double height = ((end.toSecondOfDay() - start.toSecondOfDay()) / 3600.0) * hourHeight;

        Rectangle rect = new Rectangle(100, startY, 200, height);
        rect.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.2));
        rect.setStroke(color);

        VBox textContainer = new VBox();
        textContainer.setLayoutX(100);
        textContainer.setLayoutY(startY);
        textContainer.setPrefSize(200, height);
        textContainer.setAlignment(Pos.CENTER);

        Label labelText = new Label(start + " - " + end + "\n" + label);
        labelText.setStyle("-fx-text-fill: black;");
        labelText.setWrapText(true);
        labelText.setTextAlignment(TextAlignment.CENTER);
        labelText.setMaxWidth(195);

        textContainer.getChildren().add(labelText);
        calendarPane.getChildren().addAll(rect, textContainer);
    }

    /**
     * Ajoute un événement au planning.
     */
    private void addEvent(LocalDate day, String label, LocalTime start, LocalTime end, Color color) {
        List<EventData> events = eventMap.computeIfAbsent(day, d -> new ArrayList<>());

        // Vérifie si l'événement est en conflit
        for (EventData event : events) {
            if (start.isBefore(event.getEnd()) && end.isAfter(event.getStart())) {
                System.out.println("L'événement chevauche un événement existant, il ne sera pas ajouté.");
                return;
            }
        }

        events.add(new EventData(label, start, end, color));
        System.out.println("Événement ajouté : " + label + " de " + start + " à " + end);
    }

    // === Méthodes de navigation entre vues ===

    @FXML
    private void handleAccueil(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/TableauDeBordAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue TableauDeBordAdmin : " + e.getMessage());
        }
    }

    @FXML
    private void handleEffectif(MouseEvent mouseEvent) {
        try {
            GlobalController.switchView("../ressources/fxml/ListeDesSecouristesAdmin.fxml", (Node) mouseEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ListeDesSecouristesAdmin : " + e.getMessage());
        }
    }

    @FXML
    private void handleAlertesAdmin(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/AlertesAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue AlertesAdmin : " + e.getMessage());
        }
    }

    @FXML
    private void handleRetourMois(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierAdminMois.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du retour au calendrier mois : " + e.getMessage());
        }
    }
}
