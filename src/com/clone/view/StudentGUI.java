package com.clone.view;

import com.clone.helper.Helper;

import javax.swing.*;

public class StudentGUI extends JFrame{
    private JPanel wrapper;

    public StudentGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenter("x" , getSize()), Helper.screenCenter("y" , getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Login");
        setResizable(false);
        setVisible(true);
    }
}
