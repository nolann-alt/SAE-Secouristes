package controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;
import metier.graphe.model.dao.SecouristeDAO;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilSecouristeController implements Initializable {

    @FXML
    /** This ScrollPane is used to display the content of the dashboard. */
    private ScrollPane scrollPane;

    @FXML
    /* This button is used to go back to the previous view. */
    public Button backButton;

    //Pour les modifications d'informations personnelles
    @FXML private Label nomField;
    @FXML private Label prenomField;
    @FXML private Label adresseField;
    @FXML private Label telephoneField;


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

        Secouriste user = GlobalController.currentUser;
        nomField.setText(user.getNom());
        prenomField.setText(user.getPrenom());
        adresseField.setText(user.getEmail());
        telephoneField.setText(user.getTelephone() != null ? user.getTelephone() : "");
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

    @FXML
    /**
     * This method is called when the back button is hovered over.
     * It changes the opacity of the back button to indicate hover state.
     */
    private void onBackHover() {
        backButton.setOpacity(0.7);
    }

    @FXML
    /**
     * This method is called when the mouse exits the back button area.
     * It resets the opacity of the back button to its normal state.
     */
    private void onBackExit() {
        backButton.setOpacity(1.0);
    }

    @FXML
    /**
     * This method is called when the back button is pressed.
     * It changes the position and opacity of the back button to indicate press state.
     */
    private void onBackPress() {
        backButton.setTranslateY(2);
        backButton.setOpacity(0.5);
    }

    @FXML
    /**
     * This method is called when the back button is released.
     * It resets the position and opacity of the back button to its normal state.
     */
    private void onBackRelease() {
        backButton.setTranslateY(0);
        backButton.setOpacity(0.7); // ou 1.0 selon ton besoin
    }

    @FXML
    private void handleSaveModifications(ActionEvent event) {
        String nouveauNom = nomField.getText();
        String nouveauPrenom = prenomField.getText();
        String nouvelEmail = adresseField.getText();
        String nouveauTelephone = telephoneField.getText();

        // Appel au DAO pour mettre à jour
        Secouriste secouriste = GlobalController.getCurrentUser();
        secouriste.setNom(nouveauNom);
        secouriste.setPrenom(nouveauPrenom);
        secouriste.setEmail(nouvelEmail);
        secouriste.setTelephone(nouveauTelephone);

        SecouristeDAO dao = new SecouristeDAO();
        dao.update(secouriste);
        System.out.println("Profil mis à jour !");
    }

    @FXML
    private void handleEditInfos(ActionEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/InfosSecouriste.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }
}
