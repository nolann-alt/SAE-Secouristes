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

/**
 * This class allows to manage different elements of the creation view.
 */
public class CreationController {

    @FXML
    /** This boolean is used to check if the password is visible or not. */
    private PasswordField passwordField;

    @FXML
    /** This boolean is used to check if the password is visible or not. */
    private TextField visiblePasswordField;

    @FXML
    /** This boolean is used to check if the password is visible or not. */
    private ImageView toggleEye;

    /** This boolean is used to check if the password is visible or not. */
    private boolean passwordVisible = false;

    @FXML
    /* This button is used to go back to the previous view. */
    private Button backButton;

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

    // pour Marin
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;

    //label erreur
    @FXML private Label errorLabel;

    @FXML
    /**
     * This method is called to initialize the controller after its root element has been
     * processed. It is used to set up the initial state of the controller and
     * to bind the checkbox state to the image displayed.
     */
    public void initialize() {
        // Charger les images
        checkedImage = new Image(getClass().getResource("/ressources/img/case_coche.png").toExternalForm());
        uncheckedImage = new Image(getClass().getResource("/ressources/img/case_non_coche.png").toExternalForm());


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
     * It loads the Accueil.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleBack(ActionEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/Accueil.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue Accueil : " + e.getMessage());
        }
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

    @FXML
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // CAS 1 : mot de passe visible → on repasse en mode masqué

            // Copier le texte du champ visible vers le champ masqué
            passwordField.setText(visiblePasswordField.getText());

            // Afficher le champ masqué (PasswordField)
            passwordField.setVisible(true);
            passwordField.setManaged(true);

            // Masquer le champ visible (TextField)
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);

            // Changer l’image de l’icône en "œil fermé"
            toggleEye.setImage(new Image(getClass().getResourceAsStream("../ressources/img/oeil_ferme.png")));

            // Mettre à jour l’état
            passwordVisible = false;

        } else {
            // CAS 2 : mot de passe masqué → on le rend visible

            // Copier le texte du champ masqué vers le champ visible
            visiblePasswordField.setText(passwordField.getText());

            // Afficher le champ visible (TextField)
            visiblePasswordField.setVisible(true); // On le rend visible
            visiblePasswordField.setManaged(true); // Et on lui laisse sa place dans le layout

            // Masquer le champ masqué (PasswordField)
            passwordField.setVisible(false); // On le cache
            passwordField.setManaged(false); // Et on lui enlève sa place dans le layout

            // Changer l’image de l’icône en "œil ouvert"
            toggleEye.setImage(new Image(getClass().getResourceAsStream("../ressources/img/oeil_ouvert.png")));

            // Mettre à jour l’état
            passwordVisible = true;
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the Accueil.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleCreationCompte(ActionEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password;

        if (visiblePasswordField.isVisible()) {
            password = visiblePasswordField.getText();
        } else {
            password = passwordField.getText();
        }

        //Programmation défensive
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println(" Tous les champs sont requis ");
            showAlert("Tous les champs sont requis.");
            return;
        }

        SecouristeDAO dao = new SecouristeDAO();

        //Programmation défensive
        if (dao.findByEmail(email) != null) {
            System.out.println("Email déjà utilisé !");
            showAlert("Un compte avec cet email existe déjà !");
            return;
        }

        //Programmation défensive
        if (!email.contains("@")) {
            System.out.println("L'email doit contenir un @ !!");
            showAlert("L'email doit contenir un '@' !");
            return;
        }

        Secouriste secouriste = new Secouriste(0, nom, prenom, email, password, null);
        int result = dao.create(secouriste);


        if (result > 0) { //vérifier si l'inscription a marché
            try {
                GlobalController.switchView("../ressources/fxml/Accueil.fxml", (Node) event.getSource());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur lors du chargement de la vue Accueil : " + e.getMessage());
            }
        } else {
            System.out.println("Inscription Echoué !");
            showAlert("L'inscription a échoué. Veuillez réessayer.");
        }
    }
    //pour les pop up
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur d'inscription");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
