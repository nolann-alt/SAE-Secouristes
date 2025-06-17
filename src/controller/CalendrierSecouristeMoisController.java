// CalendrierSecouristeMoisController.java
package controller;

// Importations nécessaires à JavaFX et au traitement des dates
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendrierSecouristeMoisController implements Initializable {

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
    }

    // Crée un bouton de mois avec son apparence selon qu'il est sélectionné ou non
    public Button createMonthButton(int month, int currentMonth, int currentDay) {
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

    // Gère le clic sur l’icône d’accueil : redirige vers le tableau de bord
    @FXML
    private void handleAccueil(MouseEvent event) throws IOException {
        GlobalController.switchView("../ressources/fxml/TableauDeBord.fxml", (Node) event.getSource());
    }

    // Gère le clic sur l’icône d’alertes : redirige vers les notifications
    @FXML
    private void handleAlertes(MouseEvent event) throws IOException {
        GlobalController.switchView("../ressources/fxml/NotificationSecouriste.fxml", (Node) event.getSource());
    }

    @FXML
    private void handleRetourSemaine(MouseEvent event) throws IOException {
        GlobalController.switchView("../ressources/fxml/CalendrierSecouristeSemaine.fxml", (Node) event.getSource());
    }

}
