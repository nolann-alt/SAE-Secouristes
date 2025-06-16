package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import metier.graphe.model.dao.SecouristeDAO;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.util.List;

public class ListeDesSecouristesAdminController {

    @FXML private VBox eventList;

    public void initialize() {
        loadSecouristes();
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the ProfilAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleProfilClick(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/ProfilAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ProfilAdmin : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the ProfilSecouristeAdmin.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void showProfil(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/ProfilSecouristeAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ProfilSecouristeAdmin : " + e.getMessage());
        }
    }

    @FXML
    /**
     * This method is called when the back button is clicked.
     * It loads the CalendrierAdminSemaine.fxml and sets it as the new scene with rounded corners and transparency.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If there is an error loading the FXML file.
     */
    private void handleCalendrierAdminSemaine(MouseEvent event) {
        // On récupère la scène actuelle à partir de l'élément source de l'événement
        // event.getSource() est le bouton qui a été cliqué (la source)
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierAdminSemaine.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierAdminSemaine : " + e.getMessage());
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
        card.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

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

        // Bouton flèche (image)
        ImageView arrow = new ImageView(new Image(getClass().getResourceAsStream("/ressources/img/bouton_liste_admin.png")));
        arrow.setFitWidth(28);
        arrow.setFitHeight(28);
        arrow.setOnMouseClicked(event -> {
            try {
                GlobalController.setSelectedSecouriste(s);
                GlobalController.switchView("../ressources/fxml/ProfilSecouristeAdmin.fxml", (Node) event.getSource());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        card.getChildren().addAll(avatar, infoBox, spacer, arrow);
        return card;
    }

}
