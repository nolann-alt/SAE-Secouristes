package controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import metier.persistence.Admin;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.util.Stack;

/**
 * This class allows to manage the global view switching in the application.
 * It provides a method to switch between different views using FXML files.
 */
public class GlobalController {

    public static Secouriste currentUser; // ← ajouté pour stocker l'utilisateur connecté

    private static Admin currentAdmin;

    private static Secouriste selectedSecouriste;

    /**
     * This stack is used to keep track of the view history.
     * It allows the application to navigate back to previous views.
     */
    private static final Stack<Parent> viewHistory = new Stack<>();


    /**
     * Switches the current view to a new view specified by the FXML file path.
     *
     * @param fxmlPath The path to the FXML file of the new view.
     * @param source   The node that triggered the action to switch views.
     * @throws IOException If there is an error loading the FXML file.
     */
    public static void switchView(String fxmlPath, Node source) throws IOException {
            Scene scene = source.getScene();
            if (scene != null) {
                // Sauvegarde de la vue actuelle dans l'historique
                Parent currentView = source.getScene().getRoot();
                // On la met dans l'historique
                viewHistory.push(currentView);

                // Chargement de la nouvelle vue à partir du fichier FXML
                FXMLLoader loader = new FXMLLoader(GlobalController.class.getResource(fxmlPath));
                // On charge la nouvelle vue
                Parent newView = loader.load();

                // On récupère la scène actuelle à partir de l'élément source de l'événement
                newView.translateXProperty().set(scene.getWidth()); // Positionne la nouvelle vue hors écran à droite
                scene.setRoot(newView); // Remplace la vue

                // Crée une transition de droite vers la position normale (0)
                TranslateTransition transition = new TranslateTransition(Duration.millis(300), newView);
                transition.setFromX(scene.getWidth());
                transition.setToX(0);
                transition.play();
            } else {
                System.err.println("Erreur : le Node source n'est pas attaché à une scène.");
            }
    }

    /**
     * * This method is called when the back button is clicked.
     * It switches the view to the previous view stored in the history stack.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    public void goToProfil(ActionEvent event) {
        try {
            Parent currentView = ((Node) event.getSource()).getScene().getRoot();
            viewHistory.push(currentView); // On sauvegarde la vue actuelle

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfilSecouriste.fxml"));
            Parent profilView = loader.load();

            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(profilView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ProfilSecouriste : " + e.getMessage());
        }

    }

    /**
     * This method returns the view history stack.
     * It allows access to the previous views for navigation purposes.
     *
     * @return The stack containing the history of views.
     */
    public static Stack<Parent> getViewHistory() {
        return viewHistory;
    }

    /**
     * This method returns the current user of the application.
     * @return The current Secouriste user.
     */
    public static Secouriste getCurrentUser() {
        return currentUser;
    }

    // Setter pour l'admin
    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    // Getter pour l'admin
    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setSelectedSecouriste(Secouriste s) {
        selectedSecouriste = s;
    }

    public static Secouriste getSelectedSecouriste() {
        return selectedSecouriste;
    }
}
