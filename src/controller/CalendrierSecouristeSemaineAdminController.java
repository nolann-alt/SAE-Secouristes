/**
 * Controller for the weekly calendar view for a specific first-aid worker (admin view).
 * Displays each day's schedule and supports navigation back to other admin views.
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import metier.graphe.model.EventData;
import metier.graphe.model.dao.DPSDAO;
import metier.persistence.DPS;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalendrierSecouristeSemaineAdminController {

    @FXML private Label timeLabel;               // Affiche l'heure actuelle
    @FXML private HBox daySelector;              // Contient les boutons de jour (lun-dim)
    @FXML private AnchorPane calendarPane;       // Zone d'affichage du calendrier horaire

    @FXML private Label labelNomSecouriste;      // Affiche le prénom du secouriste sélectionné
    @FXML private Label labelMoisActuel;         // (optionnel, utilisé si on veut le mois affiché en haut)

    private final int startHour = 7;             // Heure de début du planning
    private final int endHour = 22;              // Heure de fin du planning
    private final int hourHeight = 60;           // Hauteur en pixels d'une heure

    private final Map<LocalDate, List<EventData>> eventMap = new HashMap<>();

    /**
     * Initializes the view with current date, hour labels and secouriste events.
     */
    public void initialize() {
        HeureController.afficherHeure(timeLabel); // Affichage dynamique de l'heure

        LocalDate today = LocalDate.now();

        // Calcule le lundi de la semaine en cours
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);

        // Crée les boutons de chaque jour de la semaine
        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            Button bouton = createDayButton(day, today);
            bouton.setOnAction(e -> displayDay(day)); // Affiche les événements du jour
            daySelector.getChildren().add(bouton);
        }

        // Récupère le secouriste actuellement sélectionné
        Secouriste s = GlobalController.getSelectedSecouriste();
        if (s != null) {
            labelNomSecouriste.setText("Calendrier de " + s.getPrenom());

            long currentUser = s.getId();

            // Récupère les DPS (événements) affectés à ce secouriste
            DPSDAO dao = new DPSDAO();
            List<DPS> affectes = dao.findBySecouriste(currentUser);

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
        }

        // Affiche le jour actuel par défaut
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
        vbox.setSpacing(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E", Locale.FRENCH);
        String jour = date.format(formatter).substring(0, 3).toUpperCase();

        Label dayLabel = new Label(jour);
        Label numLabel = new Label(String.valueOf(date.getDayOfMonth()));

        // Style du bouton selon qu'il représente aujourd'hui ou non
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
     * Affiche les événements programmés pour un jour donné.
     */
    private void displayDay(LocalDate day) {
        calendarPane.getChildren().clear();

        // Crée l'échelle horaire
        for (int h = startHour; h <= endHour; h++) {
            Text hourText = new Text(10, (h - startHour) * hourHeight + 15, h + "h00");
            calendarPane.getChildren().add(hourText);

            Line hourLine = new Line(60, (h - startHour) * hourHeight, 363, (h - startHour) * hourHeight);
            hourLine.setStroke(Color.LIGHTGRAY);
            hourLine.setStrokeWidth(1);
            calendarPane.getChildren().add(hourLine);
        }

        // Ajuste la hauteur totale du calendrier pour l'affichage
        double totalHeight = (endHour - startHour) * hourHeight + 200;
        calendarPane.setMinHeight(totalHeight);

        List<EventData> events = eventMap.get(day);
        if (events != null) {
            for (EventData event : events) {
                createEvent(event.getLabel(), event.getStart(), event.getEnd(), event.getColor(), day);
            }
        }
    }

    /**
     * Crée un événement graphique (rectangle + texte) dans le calendrier.
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
        labelText.setAlignment(Pos.CENTER);
        labelText.setMaxWidth(195);

        textContainer.getChildren().add(labelText);
        calendarPane.getChildren().addAll(rect, textContainer);
    }

    // === MÉTHODES DE NAVIGATION ===

    /**
     * Retour à la vue Calendrier mensuel.
     */
    @FXML
    private void handleRetourMois(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouristeMoisAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retour au tableau de bord administrateur.
     */
    @FXML
    private void handleAccueil(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/TableauDeBordAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigation vers la liste des secouristes.
     */
    @FXML
    private void handleEffectif(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/ListeDesSecouristesAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigation vers la page des alertes.
     */
    @FXML
    private void handleAlertesAdmin(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/AlertesAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
