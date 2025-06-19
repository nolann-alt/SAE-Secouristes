// CalendrierSecouristeMoisController.java
package controller;

// Importations nécessaires à JavaFX et au traitement des dates
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import metier.graphe.model.dao.AffectationDAO;
import metier.graphe.model.dao.DPSDAO;
import metier.graphe.model.dao.SecouristeDAO;
import metier.persistence.Affectation;
import metier.persistence.Competences;
import metier.persistence.DPS;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendrierMoisAdminController implements Initializable {

    /** This DatePicker is used to select a date in the calendar view. */
    @FXML private DatePicker datePickerStart;

    /** This DatePicker is used to select a date in the calendar view. */
    @FXML private DatePicker datePickerEnd;

    /** This AnchorPane is used to hold the calendar view and its components. */
    @FXML private ComboBox<String> hourComboBoxStart;

    /** This AnchorPane is used to hold the calendar view and its components. */
    @FXML private ComboBox<String> hourComboBoxEnd;

    @FXML
    private VBox eventList; // VBox pour afficher les événements du calendrier

    // Label affichant l'heure dans la barre supérieure
    @FXML private Label timeLabel;

    // HBox contenant les boutons représentant les mois
    @FXML private HBox moisSelector;

    // Label du nom du mois affiché sous forme complète (ex: Juin)
    @FXML private Label labelMoisActuel;

    // Grille affichant les jours du mois sélectionné
    @FXML private GridPane gridMois;

    // Référence au bouton actuellement sélectionné pour gérer le style
    private Button boutonSelectionne = null;

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

    @FXML private ComboBox<String> hourComboBoxEnd1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Affichage dynamique de l'heure dans la barre supérieure
        HeureController.afficherHeure(timeLabel);

        // Récupération de la date du jour
        LocalDate today = LocalDate.now();
        String moisEnLettres = today.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
        labelMoisActuel.setText(moisEnLettres.substring(0, 1).toUpperCase() + moisEnLettres.substring(1));

        // Numéro du mois actuel
        int currentMonth = today.getMonthValue();

        // Création des boutons de chaque mois
        for (int i = 1; i <= 12; i++) {
            Button bouton = createMonthButton(i, currentMonth, today.getDayOfMonth());
            final int selectedMonth = i;

            // Action lors du clic sur un bouton de mois
            bouton.setOnAction(e -> {
                afficherMois(selectedMonth);           // Met à jour le calendrier
                mettreAJourSelection(bouton);          // Met à jour le style sélectionné
            });

            // Sauvegarde le bouton du mois actuel comme sélectionné au démarrage
            if (i == currentMonth) {
                boutonSelectionne = bouton;
            }

            // Ajoute le bouton à l'interface
            moisSelector.getChildren().add(bouton);
        }

        // Affiche les jours du mois courant au chargement
        afficherMois(currentMonth);

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

        // Initialiser les heures (7h-22h)
        ObservableList<String> hours = FXCollections.observableArrayList();
        for (int i = 7; i <= 22; i++) {
            hours.add(String.format("%02d:00h", i));
        }
        hourComboBoxStart.setItems(hours);
        hourComboBoxEnd.setItems(hours);
        hourComboBoxStart.getSelectionModel().selectFirst(); // Sélection par défaut
        hourComboBoxEnd.getSelectionModel().selectFirst(); // Sélection par défaut

        hourComboBoxStart.setVisibleRowCount(5); // 5 éléments visibles dans la liste déroulante
        hourComboBoxEnd.setVisibleRowCount(5);

        loadSecouristes();
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

    private void initialiserCheckboxesDepuisCompetences(List<Competences> competences) {
        List<String> competencesPossedees = new ArrayList<>();
        for (Competences c : competences) {
            competencesPossedees.add(c.getIntitule());
        }

        //Vérifier si la compétences n'est pas déjà acquise -> coché
        if (competencesPossedees.contains("CO")) {
            customCheckbox.setSelected(true);
            checkboxImage.setImage(checkedImage);
        }
        if (competencesPossedees.contains("CP")) {
            customCheckbox1.setSelected(true);
            checkboxImage1.setImage(checkedImage);
        }
        if (competencesPossedees.contains("CE")) {
            customCheckbox2.setSelected(true);
            checkboxImage2.setImage(checkedImage);
        }
        if (competencesPossedees.contains("PBC")) {
            customCheckbox3.setSelected(true);
            checkboxImage3.setImage(checkedImage);
        }
        if (competencesPossedees.contains("PBF")) {
            customCheckbox4.setSelected(true);
            checkboxImage4.setImage(checkedImage);
        }
        if (competencesPossedees.contains("PSE1")) {
            customCheckbox5.setSelected(true);
            checkboxImage5.setImage(checkedImage);
        }
        if (competencesPossedees.contains("PSE2")) {
            customCheckbox6.setSelected(true);
            checkboxImage6.setImage(checkedImage);
        }
        if (competencesPossedees.contains("SSA")) {
            customCheckbox7.setSelected(true);
            checkboxImage7.setImage(checkedImage);
        }
        if (competencesPossedees.contains("VPSP")) {
            customCheckbox8.setSelected(true);
            checkboxImage8.setImage(checkedImage);
        }
    }

    // Crée un bouton de mois avec son apparence selon qu'il est sélectionné ou non
    private Button createMonthButton(int month, int currentMonth, int currentDay) {
        VBox vbox = new VBox(); // Conteneur vertical pour afficher le mois + chiffre
        Button btn = new Button();

        if (month == currentMonth) {
            // Style pour le mois sélectionné (rouge)
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
            vbox.setPrefSize(41, 50);
            vbox.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setSpacing(0);

            // Nom abrégé + numéro du mois
            Label monthLabel = new Label(getMonthAbbr(month));
            monthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");

            Label numLabel = new Label(String.format("%02d", month));
            numLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");

            vbox.getChildren().addAll(monthLabel, numLabel);

        } else {
            // Style pour les autres mois (gris clair)
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: rgba(171, 171, 171, 0.51); -fx-background-radius: 10;");
            vbox.setPrefSize(41, 50);
            vbox.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            vbox.setSpacing(0);

            Label monthLabel = new Label(getMonthAbbr(month));
            monthLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");

            Label numLabel = new Label(String.format("%02d", month));
            numLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");

            vbox.getChildren().addAll(monthLabel, numLabel);
        }

        // Bouton sans fond ni bord, contenant le VBox
        btn.setGraphic(vbox);
        btn.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: transparent;");
        btn.setPrefSize(41, 50);
        btn.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        btn.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        return btn;
    }

    // Renvoie l'abréviation du mois (ex: janv., févr.) en français
    private String getMonthAbbr(int month) {
        return LocalDate.of(2000, month, 1)
                .getMonth()
                .getDisplayName(java.time.format.TextStyle.SHORT, Locale.FRENCH);
    }

    // Remplit la grille avec les jours du mois sélectionné
    private void afficherMois(int month) {
        gridMois.getChildren().clear();

        // Mettre à jour le label avec le nom du mois sélectionné
        String nomMois = LocalDate.of(2000, month, 1)
                .getMonth()
                .getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
        labelMoisActuel.setText(nomMois.substring(0, 1).toUpperCase() + nomMois.substring(1));

        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        DayOfWeek dayOfWeek = firstOfMonth.getDayOfWeek();
        int offset = (dayOfWeek.getValue() + 6) % 7;
        int lengthOfMonth = yearMonth.lengthOfMonth();

        int jour = 1;
        for (int ligne = 0; ligne < 6; ligne++) {
            for (int colonne = 0; colonne < 7; colonne++) {
                if (ligne == 0 && colonne < offset) {
                    gridMois.add(new Label(""), colonne, ligne);
                } else if (jour <= lengthOfMonth) {
                    Label labelJour = new Label(String.valueOf(jour));
                    labelJour.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
                    labelJour.setAlignment(Pos.CENTER);
                    labelJour.setMaxWidth(Double.MAX_VALUE);
                    GridPane.setFillWidth(labelJour, true);
                    gridMois.add(labelJour, colonne, ligne);
                    jour++;
                }
            }
        }
    }

    // Met à jour le style du bouton sélectionné en rouge, et désélectionne l'ancien
    private void mettreAJourSelection(Button boutonClique) {
        if (boutonSelectionne != null) {
            // Réinitialise l'ancien bouton (remet en gris)
            VBox ancienContenu = (VBox) boutonSelectionne.getGraphic();
            ancienContenu.setStyle("-fx-background-color: rgba(171, 171, 171, 0.51); -fx-background-radius: 10;");
            for (Node node : ancienContenu.getChildren()) {
                if (node instanceof Label) {
                    ((Label) node).setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");
                }
            }
        }

        // Applique le style rouge au bouton sélectionné
        VBox nouveauContenu = (VBox) boutonClique.getGraphic();
        nouveauContenu.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
        for (Node node : nouveauContenu.getChildren()) {
            if (node instanceof Label) {
                ((Label) node).setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
            }
        }

        boutonSelectionne = boutonClique;
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the TableauDeBordAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleAccueil(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/TableauDeBordAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue TableauDeBordAdmin : " + e.getMessage());
        }
    }
    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the ListeDesSecouristesAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleEffectif(MouseEvent mouseEvent) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/ListeDesSecouristesAdmin.fxml", (Node) mouseEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ListeDesSecouristesAdmin : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the AlertesAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleAlertesAdmin(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/AlertesAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue AlertesAdmin : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the CalendrierAdminSemaine.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    private void handleRetourSemaine(MouseEvent event) {
        try {
            // On récupère la scène actuelle à partir de l'élément source de l'événement
            // event.getSource() est le bouton qui a été cliqué (la source)
            GlobalController.switchView("../ressources/fxml/CalendrierAdminSemaine.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierAdminSemaine : " + e.getMessage());
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
    public void handleValiderCompetences(ActionEvent event) {
        // 1. Récupérer les infos du formulaire
        LocalDate date = datePickerStart.getValue();
        String heureDebutStr = hourComboBoxStart.getValue().replace("h", "");
        String heureFinStr = hourComboBoxEnd.getValue().replace("h", "");
        String couleur = hourComboBoxEnd1.getValue();

        // 2. Vérifier que les champs sont remplis
        if (date == null || heureDebutStr == null || heureFinStr == null || couleur == null) {
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

        try {
            // 3. Convertir les heures
            LocalTime timeDebut = LocalTime.parse(heureDebutStr);
            LocalTime timeFin = LocalTime.parse(heureFinStr);
            Time debutSQL = Time.valueOf(timeDebut);
            Time finSQL = Time.valueOf(timeFin);

            // 4. Créer le DPS
            DPS dps = new DPS(
                    0,
                    "Nouveau DPS",
                    Date.valueOf(date),
                    debutSQL,
                    finSQL,
                    "Marathon",
                    1,
                    "Lieu générique",
                    "Créé depuis l'IHM"
            );
            dps.setCouleur(couleur);

            DPSDAO dpsDAO = new DPSDAO();
            dpsDAO.create(dps);

            // 5. Récupérer le dernier DPS inséré
            List<DPS> tous = dpsDAO.findAll();
            DPS dernier = tous.get(tous.size() - 1);

            // 6. Ajouter les affectations des secouristes cochés
            AffectationDAO affectationDAO = new AffectationDAO();
            for (Node node : eventList.getChildren()) {
                if (node instanceof HBox box) {
                    for (Node child : box.getChildren()) {
                        if (child instanceof CheckBox check && check.isSelected()) {
                            Secouriste s = (Secouriste) box.getUserData();
                            if (s != null) {
                                Affectation aff = new Affectation((int) s.getId(), "", (int) dernier.getId());
                                affectationDAO.create(aff);
                            }
                        }
                    }
                }
            }

            // 7. Fermer la pop-up
            hidePopup();
            System.out.println("DPS et affectations enregistrés.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la création du DPS : " + e.getMessage());
        }
    }


    public LocalDateTime getSelectedDateTimeStart() {
        LocalDate date = datePickerStart.getValue();
        String hourStr = hourComboBoxStart.getValue().replace("h", "");
        LocalTime time = LocalTime.parse(hourStr);
        return LocalDateTime.of(date, time);
    }

    public LocalDateTime getSelectedDateTimeEnd() {
        LocalDate date = datePickerEnd.getValue();
        String hourStr = hourComboBoxEnd.getValue().replace("h", "");
        LocalTime time = LocalTime.parse(hourStr);
        return LocalDateTime.of(date, time);
    }

    //Pour ajouter une carte
    private void loadSecouristes() {
        SecouristeDAO secouristeDAO = new SecouristeDAO();
        List<Secouriste> secouristes = secouristeDAO.findAll();

        for (Secouriste s : secouristes) {
            eventList.getChildren().add(createSecouristeCard(s));
        }
    }

    private Node createSecouristeCard(Secouriste s) {
        HBox card = new HBox(15);
        card.setStyle("-fx-background-color: #f2f2f2; -fx-background-radius: 25;");
        card.setPadding(new Insets(10, 20, 10, 10));
        card.setPrefWidth(360);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setUserData(s); // Important pour récupérer le secouriste sélectionné plus tard

        // Avatar
        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/ressources/img/avatar.png")));
        avatar.setFitWidth(60);
        avatar.setFitHeight(60);
        avatar.setClip(new javafx.scene.shape.Circle(30, 30, 30)); // rond

        // Infos : Nom + Rôle
        Label nomPrenom = new Label(s.getPrenom() + "  " + s.getNom());
        nomPrenom.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: black;");

        Label role = new Label("Secouriste professionnel");
        role.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        VBox infoBox = new VBox(5, nomPrenom, role);

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Nouvelle CheckBox pour sélection
        CheckBox checkBox = new CheckBox();

        // Ajout des éléments à la carte
        card.getChildren().addAll(avatar, infoBox, spacer, checkBox);
        return card;
    }
}