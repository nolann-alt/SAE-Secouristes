package vue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.stage.StageStyle;
import javafx.scene.text.Font;


public class SecouristeView extends Application{

    @Override
    public void start(Stage primaryStage ) throws Exception {
        // Chargement du fichier fxml
        Parent root = FXMLLoader.load(getClass().getResource("/vue/SecouristeView.fxml"));

        // Charger la police custom
        Font font = Font.loadFont(getClass().getResourceAsStream("/vue/fonts/SF-Pro-Display-Black.otf"), 12);
        System.out.println("Font loaded: " + (font != null));

        // Création de la scene
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT); // Fond transparent

        // Configurer la fenêtre
        primaryStage.initStyle(StageStyle.TRANSPARENT); // Supprime la décoration native
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
