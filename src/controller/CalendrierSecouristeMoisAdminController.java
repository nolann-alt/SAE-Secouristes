package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class CalendrierSecouristeMoisAdminController {

    @FXML private Label titreCalendrier;
    @FXML private Label labelMoisActuel;
    @FXML private GridPane gridMois;
    @FXML private HBox moisSelector;

    @FXML
    public void initialize() {
        // Affichage du nom du mois
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();

        // Extrait le nom du secouriste depuis une variable (ex : récupérée depuis un bouton admin)
        String nomSecouriste = "Marin"; // à remplacer dynamiquement plus tard
        titreCalendrier.setText("Calendrier de " + nomSecouriste);

        String moisEnLettres = today.getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.FRENCH);
        labelMoisActuel.setText(moisEnLettres.substring(0, 1).toUpperCase() + moisEnLettres.substring(1));

        // Création des boutons pour les mois
        for (int i = 1; i <= 12; i++) {
            final int mois = i;
            Button btn = new Button(String.format("%s\n%02d", getMonthAbbr(mois), mois));
            btn.setStyle("-fx-background-color: rgba(171, 171, 171, 0.5); -fx-background-radius: 10;");
            btn.setOnAction(e -> {
                afficherMois(mois);
                String nom = LocalDate.of(2000, mois, 1).getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.FRENCH);
                labelMoisActuel.setText(nom.substring(0, 1).toUpperCase() + nom.substring(1));
            });
            moisSelector.getChildren().add(btn);
        }

        afficherMois(currentMonth);
    }

    private void afficherMois(int month) {
        gridMois.getChildren().clear();

        java.time.YearMonth ym = java.time.YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate first = ym.atDay(1);
        int offset = (first.getDayOfWeek().getValue() + 6) % 7;
        int daysInMonth = ym.lengthOfMonth();

        int day = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 0 && col < offset) {
                    gridMois.add(new Label(""), col, row);
                } else if (day <= daysInMonth) {
                    Label jourLabel = new Label(String.valueOf(day));
                    jourLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    gridMois.add(jourLabel, col, row);
                    day++;
                }
            }
        }
    }

    private String getMonthAbbr(int month) {
        return java.time.Month.of(month).getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.FRENCH);
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
    public void handleEffectif(MouseEvent mouseEvent) {
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
}
