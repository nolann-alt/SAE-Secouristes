/**
 * Controller for the monthly calendar view (admin side) for first-aid workers.
 * Allows month navigation, highlights current month, and displays day grid.
 * Handles navigation back to admin dashboard and other views.
 *
 * @author M. Weis, N. Lescop, M. Gouelo, A. Jan
 * @version 1.0
 */
package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendrierSecouristeMoisAdminController implements Initializable {

    // Label affichant l'heure actuelle
    @FXML private Label timeLabel;

    // Conteneur pour les boutons des mois
    @FXML private HBox moisSelector;

    // Affiche le nom du mois en cours (ex: "Juin")
    @FXML private Label labelMoisActuel;

    // Grille contenant les jours du mois
    @FXML private GridPane gridMois;

    // Mémorise le bouton de mois sélectionné pour réinitialisation de style
    private Button boutonSelectionne = null;

    /**
     * Initializes the calendar by displaying the current month and time.
     *
     * @param url not used
     * @param resourceBundle not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Affiche l'heure en haut de l'interface
        HeureController.afficherHeure(timeLabel);

        // Récupère la date actuelle
        LocalDate today = LocalDate.now();

        // Affiche le nom du mois courant en français
        String moisEnLettres = today.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
        labelMoisActuel.setText(moisEnLettres.substring(0, 1).toUpperCase() + moisEnLettres.substring(1));

        int currentMonth = today.getMonthValue();

        // Crée les 12 boutons de mois et ajoute leurs actions
        for (int i = 1; i <= 12; i++) {
            Button bouton = createMonthButton(i, currentMonth, today.getDayOfMonth());
            final int selectedMonth = i;

            bouton.setOnAction(e -> {
                afficherMois(selectedMonth);   // Affiche les jours du mois choisi
                mettreAJourSelection(bouton);  // Met à jour le style du bouton sélectionné
            });

            if (i == currentMonth) {
                boutonSelectionne = bouton;
            }

            moisSelector.getChildren().add(bouton);
        }

        // Affiche le mois courant au démarrage
        afficherMois(currentMonth);
    }

    /**
     * Crée un bouton stylisé représentant un mois.
     *
     * @param month le mois à représenter (1 = Janvier, 12 = Décembre)
     * @param currentMonth le mois actuellement sélectionné
     * @param currentDay le jour du mois en cours (non utilisé ici)
     * @return un bouton JavaFX
     */
    private Button createMonthButton(int month, int currentMonth, int currentDay) {
        VBox vbox = new VBox(); // Conteneur vertical pour texte
        Button btn = new Button();

        // Applique le style selon que le mois est sélectionné ou non
        if (month == currentMonth) {
            // Style sélectionné (rouge)
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
            vbox.setPrefSize(41, 50);
            vbox.setSpacing(0);

            Label monthLabel = new Label(getMonthAbbr(month));
            monthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
            Label numLabel = new Label(String.format("%02d", month));
            numLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");

            vbox.getChildren().addAll(monthLabel, numLabel);

        } else {
            // Style non sélectionné (gris)
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: rgba(171, 171, 171, 0.51); -fx-background-radius: 10;");
            vbox.setPrefSize(41, 50);
            vbox.setSpacing(0);

            Label monthLabel = new Label(getMonthAbbr(month));
            monthLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");
            Label numLabel = new Label(String.format("%02d", month));
            numLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");

            vbox.getChildren().addAll(monthLabel, numLabel);
        }

        // Applique le style final au bouton
        btn.setGraphic(vbox);
        btn.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-background-color: transparent;");
        btn.setPrefSize(41, 50);

        return btn;
    }

    /**
     * Récupère l'abréviation en français du mois donné.
     *
     * @param month numéro du mois (1 = Janvier)
     * @return abréviation du mois (ex: janv., févr.)
     */
    private String getMonthAbbr(int month) {
        return LocalDate.of(2000, month, 1)
                .getMonth()
                .getDisplayName(java.time.format.TextStyle.SHORT, Locale.FRENCH);
    }

    /**
     * Affiche les jours du mois donné dans la grille.
     *
     * @param month numéro du mois à afficher
     */
    private void afficherMois(int month) {
        gridMois.getChildren().clear();

        // Met à jour le label du mois affiché
        String nomMois = LocalDate.of(2000, month, 1)
                .getMonth()
                .getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
        labelMoisActuel.setText(nomMois.substring(0, 1).toUpperCase() + nomMois.substring(1));

        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        DayOfWeek dayOfWeek = firstOfMonth.getDayOfWeek();
        int offset = (dayOfWeek.getValue() + 6) % 7;
        int lengthOfMonth = yearMonth.lengthOfMonth();

        // Affiche chaque jour du mois dans la bonne case de la grille
        int jour = 1;
        for (int ligne = 0; ligne < 6; ligne++) {
            for (int colonne = 0; colonne < 7; colonne++) {
                if (ligne == 0 && colonne < offset) {
                    // Cases vides avant le 1er du mois
                    gridMois.add(new Label(""), colonne, ligne);
                } else if (jour <= lengthOfMonth) {
                    // Crée une cellule de jour
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

    /**
     * Met à jour l'apparence du bouton de mois sélectionné.
     *
     * @param boutonClique le nouveau bouton cliqué
     */
    private void mettreAJourSelection(Button boutonClique) {
        if (boutonSelectionne != null) {
            // Réinitialise le style du bouton précédent
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

    // ==== MÉTHODES DE NAVIGATION ENTRE LES VUES ====

    /**
     * Redirige vers la vue TableauDeBordAdmin.fxml
     */
    @FXML
    private void handleAccueil(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/TableauDeBordAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue TableauDeBordAdmin : " + e.getMessage());
        }
    }

    /**
     * Redirige vers la vue ListeDesSecouristesAdmin.fxml
     */
    @FXML
    public void handleEffectif(MouseEvent mouseEvent) {
        try {
            GlobalController.switchView("../ressources/fxml/ListeDesSecouristesAdmin.fxml", (Node) mouseEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue ListeDesSecouristesAdmin : " + e.getMessage());
        }
    }

    /**
     * Redirige vers la vue AlertesAdmin.fxml
     */
    @FXML
    private void handleAlertesAdmin(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/AlertesAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue AlertesAdmin : " + e.getMessage());
        }
    }

    /**
     * Redirige vers la vue CalendrierSecouristeSemaineAdmin.fxml
     */
    @FXML
    private void handleRetourSemaine(MouseEvent event) {
        try {
            GlobalController.switchView("../ressources/fxml/CalendrierSecouristeSemaineAdmin.fxml", (Node) event.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue CalendrierSecouristeSemaineAdmin : " + e.getMessage());
        }
    }
}
