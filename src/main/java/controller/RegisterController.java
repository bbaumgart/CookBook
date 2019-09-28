package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.RegisterService;
import service.WindowService;
import utility.InMemoryDB;

import java.io.IOException;
import java.util.Optional;

public class RegisterController {

    @FXML
    private TextField tf_login;
    @FXML
    private PasswordField pf_password;
    @FXML
    private Button btn_register;
    @FXML
    private Button btn_back;
    @FXML
    private Label lbl_error;
    @FXML
    private CheckBox cb_showPassword;

    private WindowService windowService;
    private RegisterService registerService;

    // metoda implementująca instrukcje rozpoczynające działanie aplikacji
    public void initialize() {
        windowService = new WindowService();
        registerService = new RegisterService();
    }

    @FXML
    private PasswordField pf_password_confirm;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_password_confirm;

    @FXML
    void backAction(ActionEvent event) throws IOException {
        windowService.openNewWindow("/view/loginView.fxml", "Panel logowania");
        windowService.closeCurrentWindow(pf_password_confirm);
    }

    @FXML
    void enterMouseAction(MouseEvent mouseEvent) {
        registerService.setButtonColor(btn_register, "orange");
    }

    @FXML
    void enterRegisterMouseAction(MouseEvent event) {
        registerService.setButtonColor(btn_back, "orange");
    }

    @FXML
    void exitMouseAction(MouseEvent event) {
        registerService.setButtonColor(btn_register, "lightgrey");
    }

    @FXML
    void exitRegisterMouseAction(MouseEvent event) {
        registerService.setButtonColor(btn_back, "lightgrey");
    }

    @FXML
    void keyRegisterAction(KeyEvent event) {

    }

    @FXML
    void registerAction(ActionEvent event) {
        // sprawdzenie czy pola są niepuste gdy cb jest zaznaczony
        if (cb_showPassword.isSelected() && (registerService.FieldIsEmpty(tf_login.getText(), lbl_error) ||
                registerService.FieldIsEmpty(tf_password.getText(), lbl_error) ||
                registerService.FieldIsEmpty(tf_password_confirm.getText(), lbl_error))) {
            System.out.println("puste");
            // sprawdzenie czy pola są niepuste gdy cb nie jest zaznaczony
        } else if (!cb_showPassword.isSelected() && (registerService.FieldIsEmpty(tf_login.getText(), lbl_error) ||
                registerService.FieldIsEmpty(pf_password.getText(), lbl_error) ||
                registerService.FieldIsEmpty(pf_password_confirm.getText(), lbl_error))) {
            System.out.println("puste");
            // gdy pola nie są puste
        } else {
            if (registerService.loginCheck(tf_login, lbl_error)) {
                if (registerService.passwordsEquality(
                        cb_showPassword, tf_password, pf_password, tf_password_confirm, pf_password_confirm, lbl_error)) {
                    System.out.println("rejestrujemy");
                    Optional<ButtonType> confirm = windowService.getConfirmationAlert("Potwierdzenie rejestracji", "Potwierdz dane rejestracji\n login: " + tf_login.getText() + "\nhasło: " + pf_password.getText());
                    if (confirm.get() == ButtonType.OK) {
                        registerService.saveUser(tf_login, cb_showPassword, tf_password, pf_password);
                        registerService.clearField(tf_login, tf_password, tf_password_confirm, pf_password, pf_password_confirm);
                        lbl_error.setVisible(true);
                        lbl_error.setText("ZAREJESTROWANO UŻYTKOWNIKA: " + tf_login.getText().toUpperCase());
                        lbl_error.setStyle("-fx-text-fill: orange");
                    } else {
                        lbl_error.setVisible(true);
                        lbl_error.setText("ZAREJESTROWANO ODRZUCONA");
                        lbl_error.setStyle("-fx-text-fill: lightred");
                    }
                }
            }
        }
    }

    @FXML
    void showPasswordAction(ActionEvent event) {
        registerService.showPassword(cb_showPassword, tf_password, tf_password_confirm, pf_password, pf_password_confirm);

    }

}
