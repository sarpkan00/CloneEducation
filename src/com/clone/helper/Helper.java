package com.clone.helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static int screenCenter(String axis, Dimension size){
        int point =0;
        switch (axis) {
            case "x" :
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) /2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) /2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str){
        optionPaneLang();
        String message;
        String title;
        switch (str){
            case "fill":
                message = "Lütfen Tüm Alanları Doldurunuz";
                title = "Hata";
                break;
            case "success":
                message = "İşlem Başarılı";
                title = "Sonuç";
                break;
            case "error":
                message = "Bir Hata Oluştu";
                title = "Hata";
                break;
            default:
                message = str;
                title = "Mesaj";

        }

        JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str){
        optionPaneLang();
        String message;

        switch (str){
            case "sure":
                message = "Bu İşlemi Gerçekleştirmek İstediğinize Emin Misiniz?!";
                break;
            default:
                message = str;
        }
        return JOptionPane.showConfirmDialog(null, message, "Son Kararın Mı?", JOptionPane.YES_NO_OPTION) == 0;

    }

    public static void optionPaneLang(){
        UIManager.put("OptionPane.okButtonText" , "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }
}
