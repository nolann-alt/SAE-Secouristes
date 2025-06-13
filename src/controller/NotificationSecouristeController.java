package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class NotificationSecouristeController {


    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the ListeDesAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleContacterAdmin(ActionEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/ListeDesAdmin.fxml", (Node) event.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ListeDesAdmin : " + e.getMessage());
        }
    }

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
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierSecouristeSemaine : " + e.getMessage());
        }
    }


}
