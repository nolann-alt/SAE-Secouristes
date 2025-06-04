package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.io.IOException;

public class AccueilController {

    @FXML
    private void handleConnexion(ActionEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/Connexion.fxml", (Node) event.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue Connexion : " + e.getMessage());
        }
    }

    @FXML
    private void handleInscription(ActionEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/Creation.fxml", (Node) event.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue Inscription : " + e.getMessage());
        }
    }

}
