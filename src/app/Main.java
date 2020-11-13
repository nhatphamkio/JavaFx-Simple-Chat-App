package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Main singleton;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        singleton = this;
        stage = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("1J Chat App Login");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("css/sample.css")));
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
            stage.setTitle("1J Chat Application");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("css/client.css")));
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void prevScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            stage.setTitle("1J Chat App Login");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("css/sample.css")));
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static Main getInstance(){
        return singleton;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
