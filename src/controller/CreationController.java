/**
 * Controller for the account creation view.
 * Manages password visibility, checkbox visuals, and form validation before account creation.
 * Handles navigation back to the welcome view after successful registration.
 *
 * @author M. Weis, N. Lescop, M. Gouelo, A. Jan
 * @version 1.0
 */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metier.persistence.Secouriste;
import metier.graphe.model.dao.SecouristeDAO;

import java.io.IOException;

public class CreationController {

    // Label d'affichage de l'heure
    @FXML private Label timeLabel;

    // Champ masqué pour entrer le mot de passe
    @FXML private PasswordField passwordField;

    // Champ visible pour afficher le mot de passe en clair
    @FXML private TextField visiblePasswordField;

    // Icône "œil" pour basculer entre mot de passe masqué/visible
    @FXML private ImageView toggleEye;

    // Booléen pour suivre l’état de visibilité du mot de passe
    private boolean passwordVisible = false;

    // Bouton pour revenir à la vue précédente (Accueil)
    @FXML private Button backButton;

    // Checkbox personnalisée (ex. : se souvenir, condition, etc.)
    @FXML private CheckBox customCheckbox;

    // Image associée à l'état de la checkbox
    @FXML private ImageView checkboxImage;

    // Images pour les états cochée et non cochée
    private Image checkedImage;
    private Image uncheckedImage;

    // Champs de saisie pour nom, prénom et email
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;

    // Label pour afficher des messages d’erreur
    @FXML private Label errorLabel;

    /**
     * Initializes the controller by setting up clock, checkbox visuals, and default states.
     */
    @FXML
    public void initialize() {
        HeureController.afficherHeure(timeLabel); // Affichage dynamique de l'heure

        // Chargement des images d'état de la checkbox
        checkedImage = new Image(getClass().getResource("/ressources/img/case_coche.png").toExternalForm());
        uncheckedImage = new Image(getClass().getResource("/ressources/img/case_non_coche.png").toExternalForm());

        checkboxImage.setImage(uncheckedImage); // Par défaut, décoché

        // Mise à jour dynamique de l'image selon la sélection de la checkbox
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
            System.out.println("Erreur de retour vers Accueil : " + e.getMessage());
        }
    }

    // === Gestion visuelle du bouton retour ===

    @FXML private void onBackHover()   { backButton.setOpacity(0.7); }
    @FXML private void onBackExit()    { backButton.setOpacity(1.0); }
    @FXML private void onBackPress()   { backButton.setTranslateY(2); backButton.setOpacity(0.5); }
    @FXML private void onBackRelease() { backButton.setTranslateY(0); backButton.setOpacity(0.7); }

    /**
     * Toggles password visibility between masked and plain text.
     */
    @FXML
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Masquer à nouveau le mot de passe
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setVisible(true); passwordField.setManaged(true);
            visiblePasswordField.setVisible(false); visiblePasswordField.setManaged(false);
            toggleEye.setImage(new Image(getClass().getResourceAsStream("../ressources/img/oeil_ferme.png")));
            passwordVisible = false;
        } else {
            // Afficher le mot de passe
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setVisible(true); visiblePasswordField.setManaged(true);
            passwordField.setVisible(false); passwordField.setManaged(false);
            toggleEye.setImage(new Image(getClass().getResourceAsStream("../ressources/img/oeil_ouvert.png")));
            passwordVisible = true;
        }
    }

    /**
     * Handles account creation after validating the form fields.
     */
    @FXML
    private void handleCreationCompte(ActionEvent event) {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String password = visiblePasswordField.isVisible() ? visiblePasswordField.getText() : passwordField.getText();

        // Vérification des champs obligatoires
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Tous les champs sont requis.");
            return;
        }

        // Vérification de la validité du nom/prénom
        if (!estNomValide(nom) || !estNomValide(prenom)) {
            showAlert("Le nom et le prénom ne doivent contenir que des lettres, des tirets ou des espaces.");
            return;
        }

        // Vérification de l'adresse email
        if (!email.contains("@")) {
            showAlert("L'email doit contenir un '@'.");
            return;
        }

        // Vérifie que l'email n'existe pas déjà
        SecouristeDAO dao = new SecouristeDAO();
        if (dao.findByEmail(email) != null) {
            showAlert("Un compte avec cet email existe déjà !");
            return;
        }

        // Création d’un nouveau secouriste sans téléphone
        Secouriste secouriste = new Secouriste(0, nom, prenom, email, password, null);
        int result = dao.create(secouriste);

        // Si création réussie, retour à l’accueil
        if (result > 0) {
            try {
                GlobalController.switchView("../ressources/fxml/Accueil.fxml", (Node) event.getSource());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur lors du chargement de la vue Accueil : " + e.getMessage());
            }
        } else {
            showAlert("L'inscription a échoué. Veuillez réessayer.");
        }
    }

    /**
     * Checks if a name or first name is valid (only letters, hyphens and spaces).
     */
    private boolean estNomValide(String nom) {
        return nom.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\-\\s]+$");
    }

    /**
     * Displays an error alert with the provided message.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur d'inscription");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
