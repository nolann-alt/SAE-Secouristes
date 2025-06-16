package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;
import metier.persistence.Admin;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilAdminController implements Initializable {

    @FXML
    /** This ScrollPane is used to display the content of the dashboard. */
    private ScrollPane scrollPane;

    @FXML
    /* This button is used to go back to the previous view. */
    public Button backButton;

    //Pour les modifications d'informations personnelles
    @FXML private javafx.scene.control.Label nomField;
    @FXML private javafx.scene.control.Label prenomField;
    @FXML private javafx.scene.control.Label adresseField;
    @FXML private javafx.scene.control.Label telephoneField;

    @Override
    /**
     * This method is called to initialize the controller after its root element has been
     * processed. It is used to set up the initial state of the controller and
     * to handle the scroll event for the ScrollPane.
     */
    public void initialize(URL location, ResourceBundle resources) {

        // Multiplie la vitesse de scroll
        this.scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY() * 3; // Multiplier la vitesse de scroll par 3
            // Ajuste la position de défilement du ScrollPane en fonction de la vitesse de scroll
            scrollPane.setVvalue(this.scrollPane.getVvalue() - deltaY / this.scrollPane.getContent().getBoundsInLocal().getHeight());
            event.consume(); // empêche le scroll par défaut
        });

        Admin admin = GlobalController.getCurrentAdmin();
        if (admin != null) {
            nomField.setText(admin.getNom());
            prenomField.setText(admin.getPrenom());
            adresseField.setText(admin.getEmail());
            telephoneField.setText("Non disponible");
        }

//        Secouriste user = GlobalController.currentUser;
//        nomField.setText(user.getNom());
//        prenomField.setText(user.getPrenom());
//        adresseField.setText(user.getEmail());
//        telephoneField.setText(user.getTelephone() != null ? user.getTelephone() : "");
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It retrieves the previous view from the GlobalController's view history
     * and sets it as the new root of the current scene.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    private void handleBack(ActionEvent event) {
        if (!GlobalController.getViewHistory().isEmpty()) {
            // Permet de revenir à la vue précédente, viewHistory est une Stack qui contient les vues précédentes
            Parent previousView = GlobalController.getViewHistory().pop();
            // On récupère la scène actuelle à partir de l'élément source de l'événement
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(previousView);

            // Crée une transition de gauche vers la position normale (0)
            TranslateTransition transition = new TranslateTransition(Duration.millis(300), previousView);
            transition.setFromX(-scene.getWidth());
            transition.setToX(0);
            transition.play();
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the Accueil.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleDeconnexion(ActionEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/Accueil.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue Accueil : " + e.getMessage());
        }
    }
}
