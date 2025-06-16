package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import metier.graphe.model.dao.PossedeDAO;
import metier.persistence.Competences;
import metier.persistence.Possede;
import metier.persistence.Secouriste;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class ProfilSecouristeAdminController implements Initializable{

    @FXML private Label timeLabel;

    @FXML
    /** This checkbox is used to select the custom option in the creation view. */
    private CheckBox customCheckbox;  // checkbox du fxml
    @FXML private CheckBox customCheckbox1;
    @FXML private CheckBox customCheckbox2;
    @FXML private CheckBox customCheckbox3;
    @FXML private CheckBox customCheckbox4;
    @FXML private CheckBox customCheckbox5;
    @FXML private CheckBox customCheckbox6;
    @FXML private CheckBox customCheckbox7;
    @FXML private CheckBox customCheckbox8;

    @FXML
    /** This image view is used to display the checkbox image in the creation view. */
    private ImageView checkboxImage;
    // image de la checkbox dans le fxml
    @FXML private ImageView checkboxImage1, checkboxImage2, checkboxImage3, checkboxImage4, checkboxImage5, checkboxImage6, checkboxImage7, checkboxImage8;


    /** This attribute is used to checked if the checkbox is ticked */
    private Image checkedImage; // Si la checkbox est coché

    /** This attribute is used to checked if the checkbox is unticked */
    private Image uncheckedImage; // Si la checkbox n'est pas coché

    @FXML
    /**
     * This VBox is used to hold the popup pane that can be shown or hidden.
     * It is defined in the FXML file and is used to display additional information or options.
     */
    private VBox popupPane;

    @FXML
    /**
     * This Rectangle is used as an overlay to darken the background when the popup is visible.
     * It is defined in the FXML file and is used to create a modal effect.
     */
    private Rectangle overlay;

    @FXML
    /** This ScrollPane is used to display the content of the dashboard. */
    private ScrollPane scrollPane;

    @FXML
    /* This button is used to go back to the previous view. */
    public Button backButton;

    //Pour les modifications d'informations personnelles
    @FXML private Label nomField;
    @FXML private Label prenomField;
    @FXML private Label adresseField;
    @FXML private Label telephoneField;

    private Secouriste secouriste;

    @FXML private HBox competenceContainer;


    @Override
    /**
     * This method is called to initialize the controller after its root element has been
     * processed. It is used to set up the initial state of the controller and
     * to handle the scroll event for the ScrollPane.
     */
    public void initialize(URL location, ResourceBundle resources) {
        HeureController.afficherHeure(timeLabel);

        secouriste = GlobalController.getSelectedSecouriste();

        // Recharger les compétences depuis la BDD
        PossedeDAO possedeDAO = new PossedeDAO();
        List<Competences> competences = possedeDAO.findCompetencesBySecouriste(secouriste.getId());
        secouriste.setCompetences(competences);
        afficherPastillesCompetences(competences);

        // Multiplie la vitesse de scroll
        this.scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY() * 3; // Multiplier la vitesse de scroll par 3
            // Ajuste la position de défilement du ScrollPane en fonction de la vitesse de scroll
            scrollPane.setVvalue(this.scrollPane.getVvalue() - deltaY / this.scrollPane.getContent().getBoundsInLocal().getHeight());
            event.consume(); // empêche le scroll par défaut
        });

        // Charger les images
        checkedImage = new Image(getClass().getResource("/ressources/img/case_coche.png").toExternalForm());
        uncheckedImage = new Image(getClass().getResource("/ressources/img/case_non_coche.png").toExternalForm());


        // Setup toutes les checkboxes
        setupCheckbox(customCheckbox, checkboxImage);
        setupCheckbox(customCheckbox1, checkboxImage1);
        setupCheckbox(customCheckbox2, checkboxImage2);
        setupCheckbox(customCheckbox3, checkboxImage3);
        setupCheckbox(customCheckbox4, checkboxImage4);
        setupCheckbox(customCheckbox5, checkboxImage5);
        setupCheckbox(customCheckbox6, checkboxImage6);
        setupCheckbox(customCheckbox7, checkboxImage7);
        setupCheckbox(customCheckbox8, checkboxImage8);

        nomField.setText(secouriste.getNom());
        prenomField.setText(secouriste.getPrenom());
        adresseField.setText(secouriste.getEmail());
        telephoneField.setText(secouriste.getTelephone() != null ? secouriste.getTelephone() : "");
    }

    /**
     * Définir l’image de départ et mettre à jour dynamiquement l’image associée à la checkbox.
     *
     * @param checkBox La case à cocher à configurer.
     * @param imageView L’image qui représente graphiquement l’état de la case.
     */
    private void setupCheckbox(CheckBox checkBox, ImageView imageView) {
        // Définir image de départ
        imageView.setImage(uncheckedImage);

        // Mettre à jour l’image dynamiquement selon l’état
        // obs c'est ce qui permet de surveiller les changements de la propriété
        // wasSelected c'est l'état précédent de la checkbox
        // isNowSelected c'est l'état actuel de la checkbox
        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            // Changement de l'image en fonction de isNowSelected
            if (isNowSelected == true) {
                imageView.setImage(checkedImage);
            } else {
                imageView.setImage(uncheckedImage);
            }
        });
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It retrieves the previous view from the GlobalController's view history
     * and sets it as the new root of the current scene.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    private void handleBack(ActionEvent event) {
        if (!GlobalController.getViewHistory().isEmpty()) {
            // Permet de revenir à la vue précédente, viewHistory est une Stack qui contient les vues précédentes
            Parent previousView = GlobalController.getViewHistory().pop();
            // On récupère la scène actuelle à partir de l'élément source de l'événement
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(previousView);

            // Crée une transition de gauche vers la position normale (0)
            TranslateTransition transition = new TranslateTransition(Duration.millis(300), previousView);
            transition.setFromX(-scene.getWidth());
            transition.setToX(0);
            transition.play();
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the CalendrierSecouristeSemaineAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    public void handleConsulterCalendrier(ActionEvent actionEvent) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouristeSemaineAdmin.fxml", (Node) actionEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierSecouristeSemaineAdmin : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the "Show Popup" button is clicked.
     * It makes the popup pane visible.
     */
    private void showPopup() {
        popupPane.setVisible(true);
        overlay.setVisible(true);
    }

    @FXML
    /**
     * This method is called when the "Hide Popup" button is clicked.
     * It hides the popup pane.
     */
    private void hidePopup() {
        popupPane.setVisible(false);
        overlay.setVisible(false);
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

    //pour les compétences sur le profil du secouriste
    private List<String> getCompetencesSelectionnees() {
        List<String> competences = new ArrayList<>();

        if (customCheckbox.isSelected()) competences.add("CO");
        if (customCheckbox1.isSelected()) competences.add("CP");
        if (customCheckbox2.isSelected()) competences.add("CE");
        if (customCheckbox3.isSelected()) competences.add("PBC");
        if (customCheckbox4.isSelected()) competences.add("PBF");
        if (customCheckbox5.isSelected()) competences.add("PSE1");
        if (customCheckbox6.isSelected()) competences.add("PSE2");
        if (customCheckbox7.isSelected()) competences.add("SSA");
        if (customCheckbox8.isSelected()) competences.add("VPSP");

        return competences;
    }

    @FXML
    private void handleValiderCompetences() {
        List<String> nouvellesCompetences = getCompetencesSelectionnees();

        // Nettoyage : supprimer toutes les anciennes compétences du secouriste
        PossedeDAO possedeDAO = new PossedeDAO();
        possedeDAO.deleteAllBySecouriste(secouriste.getId());

        // Ajout des nouvelles compétences sélectionnées
        for (String intitule : nouvellesCompetences) {
            Possede possede = new Possede(intitule, secouriste);
            possedeDAO.create(possede);
        }

        // Mise à jour compétences
        List<Competences> competences = new ArrayList<>();
        for (String c : nouvellesCompetences) {
            competences.add(new Competences(c));
        }
        secouriste.setCompetences(competences);

        //Affiche les compétences choisi
        afficherPastillesCompetences(competences);

        hidePopup(); // refermer le popup
    }

    //Pour afficher les compétences sur le profil sur secouriste
    private void afficherPastillesCompetences(List<Competences> competences) {
        // Vide le conteneur de pastilles existantes pour repartir proprement
        competenceContainer.getChildren().clear();

        // Pour chaque compétence du secouriste
        for (Competences comp : competences) {
            // Crée une pastille (Label) avec le nom de la compétence
            Label pastille = new Label(comp.getIntitule());

            // Applique un style visuel de pastille : texte rouge, bord rouge, fond blanc, arrondi
            pastille.setStyle("-fx-background-color: white; " +
                    "-fx-text-fill: #E60023; " +
                    "-fx-font-size: 20; " +
                    "-fx-border-color: #E60023; " +
                    "-fx-border-radius: 30; " +
                    "-fx-background-radius: 30;");

            // Ajoute un padding intérieur pour arrondir et espacer le texte
            pastille.setPadding(new Insets(10, 20, 10, 20));

            // Ajoute la pastille dans le HBox affiché dans le ScrollPane horizontal
            competenceContainer.getChildren().add(pastille);
        }
    }
}
