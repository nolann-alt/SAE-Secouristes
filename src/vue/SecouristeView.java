package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import javafx.scene.text.Font;


public class SecouristeView extends Application{

    @Override
    public void start(Stage primaryStage ) throws Exception {
        // Chargement du fichier fxml
        Parent root = FXMLLoader.load(getClass().getResource("/vue/AccueilView.fxml"));

        // Charger la police custom
        Font font = Font.loadFont(getClass().getResourceAsStream("/vue/fonts/SF-Pro-Display-Medium.otf"), 12);
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
