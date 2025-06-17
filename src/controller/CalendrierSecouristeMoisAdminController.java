// CalendrierSecouristeMoisAdminController.java
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
import metier.persistence.Secouriste;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendrierSecouristeMoisAdminController implements Initializable {

    @FXML private Label labelMoisActuel;
    @FXML private GridPane gridMois;
    @FXML private HBox moisSelector;

    @FXML
    private Label labelNomSecouriste;

    private Button boutonSelectionne = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();


        Secouriste secouriste = GlobalController.getSelectedSecouriste();

        //programmation défensive
        String nomSecouriste = secouriste != null ? secouriste.getPrenom() : "";
        labelNomSecouriste.setText("Calendrier de " + nomSecouriste);

        // Initialise les boutons de mois
        for (int i = 1; i <= 12; i++) {
            Button bouton = createMonthButton(i, currentMonth, today.getDayOfMonth());
            final int selectedMonth = i;

            bouton.setOnAction(e -> {
                afficherMois(selectedMonth);
                mettreAJourSelection(bouton);
            });

            if (i == currentMonth) boutonSelectionne = bouton;

            moisSelector.getChildren().add(bouton);
        }

        afficherMois(currentMonth);
    }

    private Button createMonthButton(int month, int currentMonth, int currentDay) {
        VBox vbox = new VBox();
        Button btn = new Button();

        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(41, 50);
        vbox.setSpacing(0);
        vbox.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        vbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Label monthLabel = new Label(getMonthAbbr(month));
        Label numLabel = new Label(String.format("%02d", month));

        if (month == currentMonth) {
            vbox.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
            monthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
            numLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
        } else {
            vbox.setStyle("-fx-background-color: rgba(171, 171, 171, 0.51); -fx-background-radius: 10;");
            monthLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");
            numLabel.setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");
        }

        vbox.getChildren().addAll(monthLabel, numLabel);
        btn.setGraphic(vbox);
        btn.setStyle("-fx-background-color: transparent; -fx-background-radius: 10; -fx-border-radius: 10;");
        btn.setPrefSize(41, 50);

        return btn;
    }

    private void mettreAJourSelection(Button boutonClique) {
        if (boutonSelectionne != null) {
            VBox ancienContenu = (VBox) boutonSelectionne.getGraphic();
            ancienContenu.setStyle("-fx-background-color: rgba(171, 171, 171, 0.51); -fx-background-radius: 10;");
            for (Node node : ancienContenu.getChildren()) {
                if (node instanceof Label) {
                    ((Label) node).setStyle("-fx-text-fill: black; -fx-font-size: 10.49;");
                }
            }
        }

        VBox nouveauContenu = (VBox) boutonClique.getGraphic();
        nouveauContenu.setStyle("-fx-background-color: #E60023; -fx-background-radius: 10;");
        for (Node node : nouveauContenu.getChildren()) {
            if (node instanceof Label) {
                ((Label) node).setStyle("-fx-text-fill: white; -fx-font-size: 10.49;");
            }
        }

        boutonSelectionne = boutonClique;
    }

    private void afficherMois(int month) {
        gridMois.getChildren().clear();

        String nomMois = LocalDate.of(2000, month, 1).getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
        labelMoisActuel.setText(nomMois.substring(0, 1).toUpperCase() + nomMois.substring(1));

        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        DayOfWeek dayOfWeek = firstOfMonth.getDayOfWeek();
        int offset = (dayOfWeek.getValue() + 6) % 7;
        int lengthOfMonth = yearMonth.lengthOfMonth();

        LocalDate today = LocalDate.now();
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

                    if (today.getDayOfMonth() == jour && today.getMonthValue() == month) {
                        labelJour.setStyle(
                                "-fx-alignment: center; -fx-font-size: 14px; -fx-font-weight: bold; " +
                                        "-fx-text-fill: white; -fx-background-color: #E60023; -fx-background-radius: 15; -fx-padding: 5;"
                        );
                    }

                    gridMois.add(labelJour, colonne, ligne);
                    jour++;
                }
            }
        }
    }

    private String getMonthAbbr(int month) {
        return LocalDate.of(2000, month, 1).getMonth().getDisplayName(java.time.format.TextStyle.SHORT, Locale.FRENCH);
    }

    @FXML
    private void handleAccueil(MouseEvent event) throws IOException {
        GlobalController.switchView("../ressources/fxml/TableauDeBordAdmin.fxml", (Node) event.getSource());
    }

    @FXML
    private void handleAlertesAdmin(MouseEvent event) throws IOException {
        GlobalController.switchView("../ressources/fxml/AlertesAdmin.fxml", (Node) event.getSource());
    }

    @FXML
    private void handleEffectif(MouseEvent mouseEvent) throws IOException {
        GlobalController.switchView("../ressources/fxml/ListeDesSecouristesAdmin.fxml", (Node) mouseEvent.getSource());
    }

    @FXML
    private void handleRetourSemaine(MouseEvent event) throws IOException {
        GlobalController.switchView("../ressources/fxml/CalendrierSecouristeSemaineAdmin.fxml", (Node) event.getSource());
    }
}
