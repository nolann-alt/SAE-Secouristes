package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import metier.graphe.model.dao.SecouristeDAO;
import metier.persistence.Secouriste;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class InfosSecouristeController implements Initializable {

    @FXML private Label timeLabel;

    @FXML private ScrollPane scrollPane;
    @FXML private Button backButton;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField adresseField;
    @FXML private TextField telephoneField;

    private Secouriste utilisateur;
    private Secouriste utilisateurOriginal;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HeureController.afficherHeure(timeLabel);


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
        try {
            // On récupère la scène actuelle à partir de l'élément source de l'événement
            // event.getSource() est le bouton qui a été cliqué (la source)
            GlobalController.switchView("../ressources/fxml/ProfilSecouriste.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ProfilSecouriste : " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveProfil(ActionEvent event) {

        String nouveauNom = nomField.getText().trim();
        String nouveauPrenom = prenomField.getText().trim();
        String nouveauTelephone = telephoneField.getText().trim();

        // Vérification NOM & PRÉNOM
        if (!estNomValide(nouveauNom) || !estNomValide(nouveauPrenom)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le nom et le prénom ne doivent contenir que des lettres, des tirets ou des espaces.");
            alert.showAndWait();
            return;
        }

        // Vérification TÉLÉPHONE
        if (!nouveauTelephone.isEmpty() && !estTelephoneValide(nouveauTelephone)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de téléphone doit être au format : XX XX XX XX XX.");
            alert.showAndWait();
            return;
        }

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

        try {
            GlobalController.switchView("../ressources/fxml/ProfilSecouriste.fxml", (Node) event.getSource());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ProfilSecouriste : " + e.getMessage());
        }
    }

    private boolean estNomValide(String nom) {
        return nom.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\-\\s]+$");
    }

    private boolean estTelephoneValide(String numero) {
        return numero.matches("^\\d{2}( \\d{2}){4}$");
    }
}
