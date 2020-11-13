package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ClientController implements Serializable{
    //Alert
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //Date
//    Date date = new Date();
//
//    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//
//    String dateString  = df.format(date);

    @FXML
    private Label userName;
    @FXML
    private Label src;
    @FXML
    void SelectOne(ActionEvent event) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images","*.png"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images","*.jpg"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null){
            src.setText(selectedFile.getAbsolutePath());
            System.out.println("Selected: "+src.getText());
            System.out.println(IP.getText());
        }else {
            System.out.println("File is not valid");
        }
    }
    public void SetUserName(String username){
        userName.setText(username);
    }
    @FXML
    private TextField DataText;
    @FXML
    private TextArea areaText;
    @FXML
    private Button btnEmit;
    @FXML
    void ClientConnect(ActionEvent event) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        //DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        String str = dataInputStream.readUTF();
//        areaText.setText(DataText.getText()+areaText.getText()+"Server Say:"+str);
        areaText.appendText("Client : "+str+"\n");
        Calendar c = Calendar.getInstance();
        areaText.appendText(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+
                c.get(Calendar.DAY_OF_MONTH)+"    "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"
                +c.get(Calendar.SECOND)+"\n");
        //areaText.appendText(c.getCalendarType()+"\n");
        //dataOutputStream.close();
        dataInputStream.close();
        socket.close();
        serverSocket.close();

    }
    @FXML
    void ClientSendServer(ActionEvent event) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(IP.getText(),8888);
        } catch (IOException e) {
            e.printStackTrace();
            alert.setTitle("エラー");
            alert.setContentText("入力したIPアドレス再度確認してください。");
            alert.show();

        }
        //DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        //Text
        String str = DataText.getText();
        areaText.appendText("Me : "+str+"\n");
        Calendar c = Calendar.getInstance();
        areaText.appendText(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+
                c.get(Calendar.DAY_OF_MONTH)+"    "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"
                +c.get(Calendar.SECOND)+"\n");
        //areaText.appendText(dateString+"\n");
        areaText.setStyle("-fx-text-fill: blue;");
        dataOutputStream.writeUTF(str);
        dataOutputStream.flush();
        DataText.setText(null);
        //DataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }
    @FXML
    private ImageView img;
    @FXML
    void ClientSaveImages() throws IOException {
        Random random = new Random();
        int generatedString = random.nextInt(1000);
        //DirectorChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        if (file!=null){
            String receiveFilepath = file.getAbsolutePath()+"\\"+generatedString+".png";
            System.out.println(receiveFilepath);
            byte[] buffer = new byte[40000];
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            OutputStream out = new FileOutputStream(receiveFilepath);
            int fileLength = in.read(buffer);
            out.write(buffer, 0, fileLength);
            Image image = new Image("file:\\"+receiveFilepath);
            img.setImage(image);

            areaText.appendText("Client : 画像を送りました\n");
            //areaText.appendText(dateString+"\n");
            Calendar c = Calendar.getInstance();
            areaText.appendText(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+
                    c.get(Calendar.DAY_OF_MONTH)+"    "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"
                    +c.get(Calendar.SECOND)+"\n");
            System.out.println("画像を保存しました。");
            out.flush();
            out.close();
            in.close();
            socket.close();
            serverSocket.close();
        }else {
            alert.setTitle("エラー");
            alert.setContentText("保存のフォルダが必要です。");
            alert.show();
        }


    }
    @FXML
    void ClientSendImages(ActionEvent event) throws IOException {
        //Images
        //String filePath = "C:\\Users\\G0945\\Documents\\images\\bay.jpg";
        String filePath = src.getText();
        File file = new File(filePath);
        byte[] buffer = new byte[40000];
        Socket socket = new Socket(IP.getText(),8888);
        InputStream inputStream  = new FileInputStream(file);
        OutputStream outputStream = socket.getOutputStream();
        int fileLength = inputStream.read(buffer);
        outputStream.write(buffer, 0, fileLength);
        outputStream.flush();
        areaText.appendText("Me:Clientに画像を送りました。\n");
        Calendar c = Calendar.getInstance();
        areaText.appendText(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+
                c.get(Calendar.DAY_OF_MONTH)+"    "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"
                +c.get(Calendar.SECOND)+"\n");
        //areaText.appendText(dateString+"\n");
        src.setText(null);
        outputStream.close();
        inputStream.close();
        socket.close();

    }
    @FXML
    private TextField IP;
    @FXML
    void TestAreaText(){

        String str = DataText.getText();
        areaText.appendText("Me : "+str+"\n");
        Calendar c = Calendar.getInstance();
        areaText.appendText(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+
                c.get(Calendar.DAY_OF_MONTH)+"    "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"
                +c.get(Calendar.SECOND)+"\n");
    }


    //End Socket
    @FXML
    void prevScene(ActionEvent event) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("ログアウトしました。");
//        alert.setContentText("ご利用ありがとうございました。");
//        alert.show();
        Main.getInstance().prevScene();

    }

}
