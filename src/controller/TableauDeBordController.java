package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import metier.graphe.model.EventData;
import metier.graphe.model.dao.DPSDAO;
import metier.persistence.DPS;


public class TableauDeBordController {

    @FXML
    /** This ScrollPane is used to display the content of the dashboard. */
    private ScrollPane scrollPane;

    @FXML
    private Label prenomLabel;

    @FXML
    /**
     * This label is used to display the current time in the application.
     * It is updated every second to show the current time in the format "HH:mm:ss".
     */
    private Label timeLabel;

    @FXML
    /**
     * This HBox is used to hold the buttons for selecting days of the week.
     * It is defined in the FXML file and is used to display the buttons for each day.
     */
    private HBox daySelector;

    @FXML
    /**
     * This AnchorPane is used to display the calendar for the selected week.
     * It is defined in the FXML file and is used to show the schedule for each day.
     */
    private AnchorPane calendarPane;

    /** This attributes define the start hours of the calendar display */
    private final int startHour = 7;

    /** This attributes define the end hours of the calendar display */
    private final int endHour = 22;

    /** This attributes define the height of each hour slot in pixels */
    private final int hourHeight = 60; // 60 pixels par heure

    /**
     * This list holds the events for the current day.
     * Each event is represented by a LocalTime array containing the start and end times.
     */
    private final Map<LocalDate, List<EventData>> eventMap = new HashMap<>();

    /**
     * This method is called to initialize the controller after its root element has been
     * processed. It is used to set up the initial state of the controller and
     * to handle the scroll event for the ScrollPane.
     */
    public void initialize() {
        // Afficher les jours (par ex : Lun 24, Mar 25...)
        LocalDate today = LocalDate.now(); // Charge la date du jour

        long curentUser = GlobalController.getCurrentUser().getId();

        DPSDAO dao = new DPSDAO();
        List<DPS> affectes = dao.findBySecouriste(curentUser);

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

        displayDay(today);

        HeureController.afficherHeure(this.timeLabel);

        prenomLabel.setText(GlobalController.currentUser.getPrenom());

        // Multiplie la vitesse de scroll
        this.scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY() * 3; // Multiplier la vitesse de scroll par 3
            // Ajuste la position de défilement du ScrollPane en fonction de la vitesse de scroll
            scrollPane.setVvalue(this.scrollPane.getVvalue() - deltaY / this.scrollPane.getContent().getBoundsInLocal().getHeight());
            event.consume(); // empêche le scroll par défaut
        });
    }

    /**
     * This method displays the calendar for the specified day.
     * It clears the current calendar pane and populates it with the schedule for the given day.
     *
     * @param day The day to display in the calendar.
     */
    private void displayDay(LocalDate day) {
        calendarPane.getChildren().clear();

        // Affiche les lignes horaires
        for (int h = startHour; h <= endHour; h++) {

            // 10 : 10 pixels depuis la gauche
            // (h - startHour) : Calcul du numéro d'heure relatif
            // * hourHeight : Conversion en pixels (hauteur d'un créneau horaire)
            // + 15 : Décalage vertical supplémentaire
            // h + "h00" : Formatage du texte de l'heure
            Text hourText = new Text(10, (h - startHour) * hourHeight + 15, h + "h00");
            calendarPane.getChildren().add(hourText);

            // Ligne horizontale pour chaque heure
            Line hourLine = new Line();
            hourLine.setStartX(60); // Position de début sur l'axe X (après le texte de l'heure)
            hourLine.setEndX(1000); // Largeur du planning
            hourLine.setStartY((h - startHour) * hourHeight);
            hourLine.setEndY((h - startHour) * hourHeight);
            hourLine.setStroke(Color.LIGHTGRAY); // Couleur des lignes
            hourLine.setStrokeWidth(1); // Épaisseur fine

            calendarPane.getChildren().add(hourLine);
        }

        // Ajustement de la hauteur minimale pour le ScrollPane
        double totalHeight = (endHour - startHour) * hourHeight + 200; // Heures + espace
        calendarPane.setMinHeight(totalHeight);
        calendarPane.setPrefHeight(totalHeight);

        List<EventData> events = eventMap.get(day);
        if (events != null) {
            for (EventData event : events) {
                createEvent(event.getLabel(), event.getStart(), event.getEnd(), event.getColor());
            }
        }
    }

    /**
     * This method creates an event rectangle on the calendar pane.
     * It calculates the position and height based on the start and end times of the event.
     *
     * @param label The label for the event.
     * @param start The start time of the event.
     * @param end The end time of the event.
     * @param color The color of the event rectangle border.
     */
    private void createEvent(String label, LocalTime start, LocalTime end, Color color) {
        // Calcul de la position verticale (Y) du début de l'événement
        double startY = (start.getHour() + (start.getMinute() / 60.0) - startHour) * hourHeight;
        // Explication :
        // 1. start.getHour() : heure de début (ex: 14 pour 14h25)
        // 2. start.getMinute() / 60.0 : conversion des minutes en fraction d'heure (ex: 25/60 = 0.416)
        // 3. - startHour : soustrait l'heure de début de l'affichage (ex: -8 si le planning commence à 8h)
        // 4. * hourHeight : convertit en pixels (ex: si hourHeight=60px/h, (14.416-8)*60 = 385px)

        // Calcul de la hauteur de l'événement en pixels
        double height = ((end.toSecondOfDay() - start.toSecondOfDay()) / 3600.0) * hourHeight;
        // Explication :
        // 1. end.toSecondOfDay() - start.toSecondOfDay() : durée totale en secondes
        // 2. / 3600.0 : conversion secondes → heures (3600s = 1h)
        // 3. * hourHeight : conversion en pixels selon l'échelle d'affichage
        // Exemple pour 14h30-15h45 :
        // (56700 - 52200) = 4500 secondes (1h15)
        // 4500/3600 = 1.25 heures
        // 1.25 * 60px/h = 75px de hauteur

        // 100 : Position X (100px depuis la gauche)
        // startY : Position Y calculée dynamiquement
        // 200 : Largeur fixe (200px)
        // height : Hauteur calculée en fonction de la durée
        Rectangle rect = new Rectangle(100, startY, 200, height);
        rect.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.2));
        rect.setStroke(color);

        VBox textContainer = new VBox();
        // Même dimension que rect
        textContainer.setLayoutX(100);
        textContainer.setLayoutY(startY);
        textContainer.setPrefSize(200, height);
        textContainer.setAlignment(Pos.CENTER);

        Label labelText = new Label(start + " - " + end + "\n" + label);
        labelText.setStyle("-fx-text-fill: black;");
        labelText.setWrapText(true); // Permet de revenir à la ligne
        labelText.setTextAlignment(TextAlignment.CENTER); // Centre le texte à l'intérieur du Label
        labelText.setAlignment(Pos.CENTER); // Centre dans la VBox
        labelText.setMaxWidth(195); // Limite la largeur pour forcer les retours à la ligne

        textContainer.getChildren().add(labelText);
        calendarPane.getChildren().addAll(rect, textContainer);

    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the CalendrierSecouristeSemaine.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleCalendrierSecouriste(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouristeSemaine.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierSecouriste : " + e.getMessage());
        }
    }


    @FXML
    /**
     * This method is called when the "Logout" button is clicked.
     * It sets the current user to null and switches the view to Accueil.fxml.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    private void handleLogout(ActionEvent event) {
        try {
            GlobalController.currentUser = null;
            GlobalController.switchView("../ressources/fxml/Accueil.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue Accueil : " + e.getMessage());
        }

    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the NotificationSecouriste.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleAlertes(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/NotificationSecouriste.fxml", (Node) event.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue NotificationSecouriste : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the ProfilSecouriste.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleProfilClick(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/ProfilSecouriste.fxml", (Node) event.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ProfilSecouriste : " + e.getMessage());
        }
    }
}

