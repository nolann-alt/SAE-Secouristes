package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * This class allows to manage the global view switching in the application.
 * It provides a method to switch between different views using FXML files.
 */
public class GlobalController {

    /**
     * Switches the current view to a new view specified by the FXML file path.
     *
     * @param fxmlPath The path to the FXML file of the new view.
     * @param source   The node that triggered the action to switch views.
     * @throws IOException If there is an error loading the FXML file.
     */
    public static void switchView(String fxmlPath, Node source) throws IOException {
        // Charge le fichier FXML spécifié
        FXMLLoader loader = new FXMLLoader(GlobalController.class.getResource(fxmlPath));
        // Charge le parent à partir du fichier FXML
        Parent root = loader.load();

        // Crée une nouvelle scène avec fond transparent
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        // Récupère l'ancienne scène à partir de la source (le noeud qui a déclenché l'action)
        Stage oldStage = (Stage) source.getScene().getWindow();

        // Crée une nouvelle fenêtre (Stage) avec style transparent
        Stage newStage = new Stage();
        newStage.initStyle(StageStyle.TRANSPARENT);
        newStage.setScene(scene);

        // Taille identique à l'ancien stage
        newStage.setWidth(oldStage.getWidth());
        newStage.setHeight(oldStage.getHeight());
        System.out.println("Nouvelle largeur de la fenêtre : " + newStage.getWidth());
        System.out.println("Nouvelle hauteur de la fenêtre : " + newStage.getHeight());

        // Positionne la nouvelle fenêtre à la même position que l'ancienne
        // Affiche la nouvelle scène et ferme l'ancienne
        newStage.show();
        oldStage.close();
    }
}
