package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TableauDeBordController implements Initializable {
    @FXML
    /** This ScrollPane is used to display the content of the dashboard. */
    private ScrollPane scrollPane;

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
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the CalendrierSecouriste.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleCalendrierSecouriste(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouriste.fxml", (Node) event.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierSecouriste : " + e.getMessage());
        }
    }
}

