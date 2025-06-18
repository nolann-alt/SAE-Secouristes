/**
 * Controller for the login (connexion) view.
 * Handles password visibility toggle, "remember me" feature, and user authentication
 * (both admin and secouriste).
 *
 * @author M. Weis, N. Lescop, M. Gouelo, A. Jan
 * @version 1.0
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import metier.graphe.model.dao.AdminDAO;
import metier.persistence.Admin;
import metier.persistence.Secouriste;
import metier.graphe.model.dao.SecouristeDAO;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnexionController {

    // Label pour afficher l'heure courante
    @FXML private Label timeLabel;

    // Champ masqué pour le mot de passe (par défaut)
    @FXML private PasswordField passwordField;

    // Champ visible pour afficher le mot de passe en clair
    @FXML private TextField visiblePasswordField;

    // Icône œil pour basculer visibilité du mot de passe
    @FXML private ImageView toggleEye;

    // État de visibilité du mot de passe
    private boolean passwordVisible = false;

    @FXML public Button backButton; // Bouton retour vers Accueil

    @FXML private CheckBox customCheckbox; // Checkbox "se souvenir de moi"
    @FXML private ImageView checkboxImage; // Image associée à la checkbox

    private Image checkedImage;   // Image case cochée
    private Image uncheckedImage; // Image case non cochée

    @FXML private TextField emailField;

    // Chemin du fichier local pour sauvegarder l'email
    private final String FILE_PATH = "remember_me.txt";

    /**
     * Initializes the controller by displaying the current time,
     * setting up the checkbox image, and loading a remembered email if any.
     */
    @FXML
    public void initialize() {
        HeureController.afficherHeure(timeLabel); // Affichage heure dynamique
        loadRememberedEmail(); // Préremplit le champ email si enregistré

        // Chargement des icônes
        checkedImage = new Image(getClass().getResource("/ressources/img/case_coche.png").toExternalForm());
        uncheckedImage = new Image(getClass().getResource("/ressources/img/case_non_coche.png").toExternalForm());

        checkboxImage.setImage(uncheckedImage); // Par défaut : décoché

        // Lien dynamique entre checkbox et son image
        customCheckbox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            checkboxImage.setImage(isNowSelected ? checkedImage : uncheckedImage);
        });
    }

    /**
     * Navigates back to the welcome view (Accueil).
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/Accueil.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur de retour à Accueil : " + e.getMessage());
        }
    }

    // ==== Gestion des effets visuels du bouton retour ====

    @FXML private void onBackHover()    { backButton.setOpacity(0.7); }
    @FXML private void onBackExit()     { backButton.setOpacity(1.0); }
    @FXML private void onBackPress()    { backButton.setTranslateY(2); backButton.setOpacity(0.5); }
    @FXML private void onBackRelease()  { backButton.setTranslateY(0); backButton.setOpacity(0.7); }

    /**
     * Toggles the visibility of the password field (masqué <-> visible).
     */
    @FXML
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Passe du champ visible (TextField) au champ masqué (PasswordField)
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
            toggleEye.setImage(new Image(getClass().getResourceAsStream("../ressources/img/oeil_ferme.png")));
            passwordVisible = false;
        } else {
            // Passe du champ masqué au champ visible
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            toggleEye.setImage(new Image(getClass().getResourceAsStream("../ressources/img/oeil_ouvert.png")));
            passwordVisible = true;
        }
    }

    /**
     * Handles user login for both Admin and Secouriste roles.
     */
    @FXML
    private void handleConnexion(ActionEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordVisible ? visiblePasswordField.getText() : passwordField.getText();

        // === Tentative admin ===
        Admin admin = new AdminDAO().findByEmailAndPassword(email, password);
        if (admin != null) {
            GlobalController.setCurrentAdmin(admin);
            if (customCheckbox.isSelected()) saveEmail(email);
            else new File(FILE_PATH).delete();

            GlobalController.switchView("../ressources/fxml/TableauDeBordAdmin.fxml", (Node) event.getSource());
            return;
        }

        // === Tentative secouriste ===
        Secouriste s = new SecouristeDAO().findByEmail(email);
        try {
            if (s != null && s.getMotDePasse().equals(password)) {
                GlobalController.currentUser = s;
                GlobalController.switchView("../ressources/fxml/TableauDeBord.fxml", (Node) event.getSource());
            } else {
                showAlert("Email ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur d'authentification : " + e.getMessage());
        }

        // Mise à jour "se souvenir de moi"
        if (customCheckbox.isSelected()) saveEmail(email);
        else new File(FILE_PATH).delete();
    }

    /**
     * Enregistre l’email localement si la case "se souvenir de moi" est cochée.
     */
    private void saveEmail(String email) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Préremplit le champ email si un email a été enregistré précédemment.
     */
    private void loadRememberedEmail() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String savedEmail = reader.readLine();
                if (savedEmail != null) {
                    emailField.setText(savedEmail);
                    customCheckbox.setSelected(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Affiche une alerte simple à l’utilisateur.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
