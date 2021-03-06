package authentication.forgetPassword;

import authentication.homePage.Launcher;
import authentication.registerPage.registerController;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForgetPasswordController extends Tools {
    static String randomCode;
    static String username;

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtNewPass;

    @FXML
    private TextField txtCode;

    public void retrievePassword(ActionEvent event) throws MessagingException, IOException {
        this.randomCode = ""+((int)(Math.random()*9000)+1000);
        this.username = txtUserName.getText();
        String name = Launcher.userList.getName(username);
    if(Launcher.userList.findUserName(username)){
        switchToEmailVerification(event);
        String content = String.format("Hello %s\nYour requested code for resetting your password is %s\n\nThanks" +
                " for using event management system",name,this.randomCode);
        System.out.println(content);
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            public void run() {
                try {
                   sendMail(Launcher.userList.findEmail(username),"Password Reset",content);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    else
        createAlertError("Error","Username does not exist");
    }

    public void confirmPassword(ActionEvent event) throws IOException {
        String codeToCheck = txtCode.getText();
        String newPass = txtNewPass.getText();
        if (!randomCode.equals(codeToCheck)){
            createAlertError("Error","Invalid Code");
        }else if (!registerController.passwordRegex(newPass)){
            createAlertError("Error", "Invalid Password, Please check the rules");
        }else{
            Launcher.userList.changePass(username,newPass);
            createAlertInfo("Success","Password changed");
            switchToLauncher(event);
        }

    }


    public void switchToEmailVerification(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ForgetPasswordEmailCode.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLauncher(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../homePage/Launcher.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("../homePage/Launcher.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }


}
