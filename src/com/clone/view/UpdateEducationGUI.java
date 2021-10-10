package com.clone.view;

import com.clone.helper.Helper;
import com.clone.model.Education;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateEducationGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_education_name;
    private JButton btn_update;
    private Education education;

   public UpdateEducationGUI(Education education){
        this.education = education;
        add(wrapper);
        setSize(300,150);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Güncelle");
        setVisible(true);

       fld_education_name.setText(education.getName());


       btn_update.addActionListener(e -> {
           if(Helper.isFieldEmpty(fld_education_name)){
               Helper.showMessage("fill");
           }else {
               if(Education.update(education.getId(), fld_education_name.getText())){
                   Helper.showMessage("success");

               }
               dispose();
           }
       });
   }
}
