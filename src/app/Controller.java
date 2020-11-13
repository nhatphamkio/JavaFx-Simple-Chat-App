package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.Serializable;

public class Controller implements Serializable {
    @FXML
    public TextField Text;



    @FXML
    void nextServerView(ActionEvent event) throws IOException {
        if (Text.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("エラー");
            alert.setContentText("ユーザーが必要");
            alert.show();

            System.out.println("ユーザーが入力されていない");
        }else {
            Main.getInstance().nextScene();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
//            Parent x = (Parent) loader.load();
//            ClientController clientController = loader.getController();
//            clientController.SetUserName(Text.getText());
//            Stage stage = new Stage();
//            stage.setScene(new Scene(x));
//            stage.setTitle("Chat");
//            stage.show();
            }
        }
}
