package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.util.Duration;
import metier.graphe.model.EventData;


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
        CalendrierSecouristeSemaineController calendrierSecouriste = new CalendrierSecouristeSemaineController();

        calendarPane.getChildren().add(calendrierSecouriste.getCalendarDayNow(today));

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

