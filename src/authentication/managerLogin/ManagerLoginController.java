package authentication.managerLogin;

import authentication.homePage.Launcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.Tools;

import java.io.IOException;


public class ManagerLoginController extends Tools {

    private Stage stage;
    private Scene scene;


    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;


    public void userLogin(ActionEvent event) throws IOException {
        String nameToBeChecked = this.txtUserName.getText();
        String passToBeChecked = new String(this.txtPassword.getText());
        if(Launcher.userList.checkSignIn(nameToBeChecked,passToBeChecked)){
            if(Launcher.userList.isManager(nameToBeChecked,passToBeChecked)){
                Launcher.currentUser = Launcher.userList.returnUser(nameToBeChecked,passToBeChecked);
                createAlertInfo("Success","Manager signed in successfully");
                Parent root = FXMLLoader.load(getClass().getResource("../../mainApplication/managerMain/managerMain.fxml"));
                String css = getClass().getResource("../../mainApplication/managerMain/managerMain.css").toExternalForm();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.show();
            }else{
                createAlertError("Error","this is the manager portal , please use the user portal");
            }
        }else
            createAlertError("Error","Incorrect username or password");
    }

    public void switchToRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../registerPage/registerPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLauncher(ActionEvent event) throws  IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../homePage/Launcher.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("../homePage/Launcher.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToForgetPassword(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../forgetPassword/forgetPassword.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
