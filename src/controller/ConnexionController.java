package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * This class allows to manage different elements of the connexion view.
 */
public class ConnexionController {
    @FXML
    /* This button is used to go back to the previous view. */
    public Button backButton;

    @FXML
    /** This checkbox is used to select the custom option in the creation view. */
    private CheckBox customCheckbox;  // checkbox du fxml

    @FXML
    /** This image view is used to display the checkbox image in the creation view. */
    private ImageView checkboxImage; // image de la checkbox dans le fxml

    /** This qttrribut is used to checked if the checkbox is ticked */
    private Image checkedImage; // Si la checkbox est coché
    /** This qttrribut is used to checked if the checkbox is unticked */
    private Image uncheckedImage; // Si la checkbox n'est pas coché

    @FXML
    /**
     * This method is called to initialize the controller after its root element has been
     * processed. It is used to set up the initial state of the controller and
     * to bind the checkbox state to the image displayed.
     */
    public void initialize() {
        // Charger les images
        checkedImage = new Image(getClass().getResource("/vue/img/Checkboxes.png").toExternalForm());
        uncheckedImage = new Image(getClass().getResource("/vue/img/caseunchecked.png").toExternalForm());


        // Définir image de départ
        checkboxImage.setImage(uncheckedImage);

        // Mettre à jour l’image dynamiquement selon l’état
        // obs c'est ce qui permet de surveiller les changements de la propriété
        // wasSelected c'est l'état précédent de la checkbox
        // isNowSelected c'est l'état actuel de la checkbox
        customCheckbox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            // Changement de l'image en fonction de isNowSelected
            if (isNowSelected == true) {
                checkboxImage.setImage(checkedImage);
            } else {
                checkboxImage.setImage(uncheckedImage);
            }
        });
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the AccueilView.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleBack(ActionEvent event) throws IOException {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        GlobalController.switchView("../vue/AccueilView.fxml", (Node) event.getSource());
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
}
