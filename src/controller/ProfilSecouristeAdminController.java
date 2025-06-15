package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;

public class ProfilSecouristeAdminController {
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

    public void handleEditInfos(ActionEvent actionEvent) {
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the CalendrierSecouristeSemaineAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    public void handleConsulterCalendrier(MouseEvent mouseEvent) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouristeSemaineAdmin.fxml", (Node) mouseEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierSecouristeSemaineAdmin : " + e.getMessage());
        }
    }
}
