package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Recipe;
import service.LoginService;
import service.WindowService;


import java.io.IOException;

public class LoginController {
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_register;
    @FXML
    private TextField tf_login;
    @FXML
    private PasswordField pf_password;
    @FXML
    private Label lbl_error;
    // pola globalne
    private LoginService loginService;
    private WindowService windowService;


    // metoda implementująca instrukcje rozpoczynające działanie aplikacji
    public void initialize(){
        loginService = new LoginService();
        windowService = new WindowService();
    }
    @FXML
    void keyLoginAction(KeyEvent keyEvent) {
        // nasłuchiwanie na enter
        if(keyEvent.getCode() == KeyCode.ENTER) {
            loginService.login(lbl_error, tf_login, pf_password);
        }
    }
    @FXML
    void loginAction(ActionEvent event) throws IOException {
        if (loginService.login(lbl_error,tf_login,pf_password)){
            windowService.openNewWindow("/view/cookbookView.fxml","Książka kucharska");
            windowService.closeCurrentWindow(lbl_error);
        }

    }
    @FXML
    void registerAction(ActionEvent event) throws IOException {

        windowService.openNewWindow("/view/registerView.fxml","Panel rejestracji");
        windowService.closeCurrentWindow(lbl_error);
    }

    @FXML
    void enterMouseAction(MouseEvent mouseEvent) {
        loginService.setButtonColor(btn_login, "orange");
    }
    @FXML
    void enterRegisterMouseAction(MouseEvent event) {
        loginService.setButtonColor(btn_register, "orange");
    }
    @FXML
    void exitMouseAction(MouseEvent mouseEvent) {
        loginService.setButtonColor(btn_login, "lightgrey");

    }
    @FXML
    void exitRegisterMouseAction(MouseEvent event) {
        loginService.setButtonColor(btn_register, "lightgrey");
    }

}
