package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import metier.graphe.model.dao.SecouristeDAO;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfosSecouristeController implements Initializable {

    @FXML private ScrollPane scrollPane;
    @FXML private Button backButton;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField adresseField;
    @FXML private TextField telephoneField;

    private Secouriste utilisateur;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilisateur = GlobalController.getCurrentUser();

        if (utilisateur != null) {
            nomField.setText(utilisateur.getNom());
            prenomField.setText(utilisateur.getPrenom());
            adresseField.setText(utilisateur.getEmail());
            telephoneField.setText(utilisateur.getTelephone() != null ? utilisateur.getTelephone() : "");
        }

        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY() * 3;
            scrollPane.setVvalue(scrollPane.getVvalue() - deltaY / scrollPane.getContent().getBoundsInLocal().getHeight());
            event.consume();
        });
    }

    @FXML
    private void handleBack(ActionEvent event) {
        GlobalController.switchView("../ressources/fxml/ProfilSecouriste.fxml", (Node) event.getSource());
    }

    @FXML
    private void handleDeconnexion(ActionEvent event) {
        GlobalController.switchView("../ressources/fxml/Accueil.fxml", (Node) event.getSource());
    }

    @FXML
    private void handleSaveProfil(ActionEvent event) {
        utilisateur.setNom(nomField.getText());
        utilisateur.setPrenom(prenomField.getText());
        utilisateur.setEmail(adresseField.getText());
        utilisateur.setTelephone(telephoneField.getText());

        SecouristeDAO dao = new SecouristeDAO();
        dao.update(utilisateur);

        System.out.println("Coordonnées mises à jour.");

        // Afficher un popup de confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Les coordonnées ont été mises à jour avec succès.");
        alert.showAndWait();
    }
}
