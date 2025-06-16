package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;



public class ListeDesAdminController implements Initializable {

    @FXML private Label timeLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HeureController.afficherHeure(timeLabel);
    }

    @FXML
    /**
     * This VBox is used to hold the popup pane that can be shown or hidden.
     * It is defined in the FXML file and is used to display additional information or options.
     */
    private VBox popupPane;

    @FXML
    /**
     * This Rectangle is used as an overlay to darken the background when the popup is visible.
     * It is defined in the FXML file and is used to create a modal effect.
     */
    private Rectangle overlay;

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the TableauDeBord.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleTableauDeBord(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/TableauDeBord.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue TableauDeBord : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the "Calendrier Secouriste" button is clicked.
     * It loads the CalendrierSecouristeSemaine.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param mouseEvent The MouseEvent triggered by the button click.
     */
    public void handleCalendrierSecouriste(MouseEvent mouseEvent) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouristeSemaine.fxml", (Node) mouseEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierSecouristeSemaine : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the "Show Popup" button is clicked.
     * It makes the popup pane visible.
     */
    private void showPopup() {
        popupPane.setVisible(true);
        overlay.setVisible(true);
    }

    @FXML
    /**
     * This method is called when the "Hide Popup" button is clicked.
     * It hides the popup pane.
     */
    private void hidePopup() {
        popupPane.setVisible(false);
        overlay.setVisible(false);
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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ProfilSecouriste : " + e.getMessage());
        }
    }
}
