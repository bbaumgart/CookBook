package service;

import javafx.scene.Node;
import javafx.scene.control.*;
import model.User;
import utility.InMemoryDB;

import java.util.Optional;

public class RegisterService {

    public void showPassword(CheckBox cb_showPassword, TextField tf_passowrd, TextField tf_password_confirm, TextField pf_passowrd, TextField pf_password_confirm) {
        if (cb_showPassword.isSelected()) {
            tf_passowrd.setVisible(true);
            tf_password_confirm.setVisible(true);
            pf_passowrd.setVisible(false);
            pf_password_confirm.setVisible(false);
            tf_passowrd.setText(pf_passowrd.getText());
            tf_password_confirm.setText(pf_password_confirm.getText());
        } else {
            tf_passowrd.setVisible(false);
            tf_password_confirm.setVisible(false);
            pf_passowrd.setVisible(true);
            pf_password_confirm.setVisible(true);
            pf_passowrd.setText(tf_passowrd.getText());
            pf_password_confirm.setText(tf_password_confirm.getText());
        }
    }

    public boolean passwordsEquality(CheckBox cb_showPassword, TextField tf_passowrd, TextField tf_password_confirm, TextField pf_passowrd, TextField pf_password_confirm, Label lbl_error) {

        if (cb_showPassword.isSelected()) {
            if (!tf_passowrd.getText().equals(pf_passowrd.getText())) {
                lbl_error.setVisible(true);
                lbl_error.setText("hasła nie są jednakowe");
                return false;
            }
        } else {
            if (!tf_password_confirm.getText().equals(pf_password_confirm.getText())) {
                lbl_error.setVisible(true);
                lbl_error.setText("hasła nie są jednakowe");
                return false;
            }
        }
        lbl_error.setVisible(false);
        return true;
    }

    public boolean loginCheck(TextField tf_login, Label lbl_error) {
        Optional<User> user_valid = InMemoryDB.users.stream().filter(user -> user.getLogin().equals(tf_login.getText())).findAny();
        if (user_valid.isPresent()) {
            lbl_error.setVisible(true);
            lbl_error.setText("Istnieje już użytkowanik o padanym logonie");
            return false;
        }
        lbl_error.setVisible(false);
        return true;
    }

    public boolean FieldIsEmpty(String text, Label lbl_error) {
        if (text.equals("")) {
            lbl_error.setVisible(true);
            lbl_error.setText("pola formularza nie mogą być puste!");
            return true;
        }
        lbl_error.setVisible(false);
        return false;
    }

    public void saveUser(TextField tf_login, CheckBox cb_showPassword, TextField tf_password, PasswordField pf_password) {
        if (cb_showPassword.isSelected()) {
            InMemoryDB.users.add(new User(tf_login.getText(), tf_password.getText()));
        } else {
            InMemoryDB.users.add(new User(tf_login.getText(), pf_password.getText()));
        }
    }
    public void clearField(TextField tf_login, TextField tf_password, TextField tf_pasword_confirm,PasswordField pf_password, PasswordField pf_password_confirm){
        tf_login.clear();
        tf_password.clear();
        tf_pasword_confirm.clear();
        pf_password.clear();
        pf_password_confirm.clear();
    }

    public void setButtonColor(Button button, String color) {
        button.setStyle("-fx-background-color: " + color);
    }

}
