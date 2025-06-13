package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class ListeDesAdminController {

    @FXML
    /**
     * This VBox is used to hold the popup pane that can be shown or hidden.
     * It is defined in the FXML file and is used to display additional information or options.
     */
    private VBox popupPane;

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
     * This method is called when the "Show Popup" button is clicked.
     * It makes the popup pane visible.
     */
    private void showPopup() {
        popupPane.setVisible(true);
    }

    @FXML
    /**
     * This method is called when the "Hide Popup" button is clicked.
     * It hides the popup pane.
     */
    private void hidePopup() {
        popupPane.setVisible(false);
    }
}
