package com.clone.view;

import com.clone.helper.Helper;

import javax.swing.*;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;

    public EducatorGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenter("x" , getSize()), Helper.screenCenter("y" , getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Login");
        setResizable(false);
        setVisible(true);
    }
}
