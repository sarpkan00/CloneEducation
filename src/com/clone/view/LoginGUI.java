package com.clone.view;

import com.clone.helper.Helper;
import com.clone.model.Operator;
import com.clone.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_username;
    private JPasswordField fld_user_password;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenter("x" , getSize()), Helper.screenCenter("y" , getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Login");
        setResizable(false);
        setVisible(true);


        btn_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_password)){
                Helper.showMessage("fill");
            }else{
                User user = User.getFetch(fld_user_username.getText(), fld_user_password.getText());
                if(user == null){
                    Helper.showMessage("Kullanıcı Bulunamadı!!");
                }else{
                    switch (user.getType()){
                        case "operator":
                            OperatorGUI operatorGUI = new OperatorGUI((Operator) user);
                            break;
                        case "educator":
                            EducatorGUI educatorGUI = new EducatorGUI();
                            break;
                        case "student":
                            StudentGUI studentGUI = new StudentGUI();
                            break;
                    }
                    dispose();
                }
            }

        });
    }
}
