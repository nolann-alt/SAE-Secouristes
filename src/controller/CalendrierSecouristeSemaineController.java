/**
 * Controller for the weekly calendar view for rescuers.
 * This class allows users to view and interact with their schedule for the current week,
 * navigate between days, and access other views like notifications and dashboard.
 *
 * @author M. Weis, N. Lescop, M. Gouelo, A. Jan
 * @version 1.0
 */
package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import metier.graphe.model.EventData;
import metier.graphe.model.dao.DPSDAO;
import metier.persistence.DPS;
import metier.service.PlanningMngtSec;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalendrierSecouristeSemaineController {

    @FXML private Label timeLabel;        // Label pour l'heure affichée en haut
    @FXML private HBox daySelector;       // Contient les boutons pour les 7 jours
    @FXML private AnchorPane calendarPane;// Zone d'affichage du calendrier

    private final int startHour = 7;      // Heure de début du planning
    private final int endHour = 22;       // Heure de fin du planning
    private final int hourHeight = 60;    // Hauteur d'une heure en pixels

    private final Map<LocalDate, List<EventData>> eventMap = new HashMap<>(); // Événements par jour

    /**
     * Initializes the calendar by creating day buttons, loading events,
     * and displaying the current day’s schedule.
     */
    public void initialize() {
        HeureController.afficherHeure(timeLabel); // Met à jour l'heure dynamiquement

        LocalDate today = LocalDate.now(); // Date du jour
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1); // Lundi de la semaine

        // Création des boutons de jours
        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            Button bouton = createDayButton(day, today);
            bouton.setOnAction(e -> {
                displayDay(day); // Affiche le jour sélectionné

                // Met à jour l'affichage du label d'heure
                if (!day.equals(today)) {
                    timeLabel.setText(day.format(DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRENCH)));
                } else {
                    timeLabel.setText("Aujourd'hui");
                }
            });
            daySelector.getChildren().add(bouton);
        }

        // Récupère les événements de l'utilisateur courant
        long curentUser = GlobalController.getCurrentUser().getId();
        DPSDAO dao = new DPSDAO();
        List<DPS> affectes = dao.findBySecouriste(curentUser);

        // Ajoute chaque événement au calendrier
        for (DPS dps : affectes) {
            LocalDate date = dps.getDate().toLocalDate();
            LocalTime debut = dps.getHeureDebut().toLocalTime();
            LocalTime fin = dps.getHeureFin().toLocalTime();
            String label = dps.getLabel();
            String couleurTexte = dps.getCouleur();
            Color couleur = Color.valueOf(couleurTexte != null ? couleurTexte : "ORANGE");

            EventData event = new EventData(label, debut, fin, couleur);
            eventMap.computeIfAbsent(date, d -> new ArrayList<>()).add(event);
        }

        // Affiche la journée courante
        displayDay(today);
    }

    /**
     * Crée un bouton représentant un jour (ex: LUN 24).
     */
    private Button createDayButton(LocalDate date, LocalDate today) {
        VBox vbox = new VBox();
        Button btn = new Button();

        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(41, 50);
        vbox.setSpacing(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E", Locale.FRENCH);
        String jourAbrege = date.format(formatter).substring(0, 3).toUpperCase();
        Label dayLabel = new Label(jourAbrege);
        Label numLabel = new Label(String.valueOf(date.getDayOfMonth()));

        if (today.equals(date)) {
            // Style pour aujourd'hui
            vbox.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
            dayLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
            numLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
        } else {
            // Style par défaut
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
     * Affiche les événements pour un jour donné dans le calendrier.
     */
    private void displayDay(LocalDate day) {
        calendarPane.getChildren().clear(); // Réinitialise l'affichage

        // Affiche les lignes horaires
        for (int h = startHour; h <= endHour; h++) {
            Text hourText = new Text(10, (h - startHour) * hourHeight + 15, h + "h00");
            calendarPane.getChildren().add(hourText);

            Line hourLine = new Line(60, (h - startHour) * hourHeight, 1000, (h - startHour) * hourHeight);
            hourLine.setStroke(Color.LIGHTGRAY);
            hourLine.setStrokeWidth(1);
            calendarPane.getChildren().add(hourLine);
        }

        calendarPane.setMinHeight((endHour - startHour) * hourHeight + 200); // Ajustement global

        List<EventData> events = eventMap.get(day);
        if (events != null) {
            for (EventData event : events) {
                createEvent(event.getLabel(), event.getStart(), event.getEnd(), event.getColor());
            }
        }
    }

    /**
     * Crée et affiche visuellement un événement sur le calendrier.
     */
    private void createEvent(String label, LocalTime start, LocalTime end, Color color) {
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
     * Ajoute un nouvel événement au jour concerné s’il ne chevauche pas d’autres.
     */
    private void addEvent(LocalDate day, String label, LocalTime start, LocalTime end, Color color) {
        List<EventData> events = eventMap.computeIfAbsent(day, d -> new ArrayList<>());

        // Vérifie chevauchement
        for (EventData event : events) {
            if (start.isBefore(event.getEnd()) && end.isAfter(event.getStart())) {
                System.out.println("L'événement chevauche un événement existant.");
                return;
            }
        }

        EventData newEvent = new EventData(label, start, end, color);
        events.add(newEvent);

        // Ajout dans le service métier
        new PlanningMngtSec().addEvent(day, newEvent);

        System.out.println("Événement ajouté : " + label + " de " + start + " à " + end);
    }

    // === NAVIGATION ===

    @FXML
    private void handleTableauDeBord(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/TableauDeBord.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur de chargement du tableau de bord : " + e.getMessage());
        }
    }

    @FXML
    private void handleAlertes(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/NotificationSecouriste.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement des alertes : " + e.getMessage());
        }
    }

    @FXML
    private void handleRetourMois(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouristeMois.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du retour au calendrier mensuel : " + e.getMessage());
        }
    }
}
