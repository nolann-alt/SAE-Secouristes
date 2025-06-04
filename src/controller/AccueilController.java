package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.io.IOException;

public class AccueilController {

    @FXML
    private void handleConnexion(ActionEvent event) throws IOException {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        GlobalController.switchView("../vue/ConnexionView.fxml", (Node) event.getSource());
    }

    @FXML
    private void handleInscription(ActionEvent event) throws IOException {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        GlobalController.switchView("../vue/CreationView.fxml", (Node) event.getSource());
    }

}
