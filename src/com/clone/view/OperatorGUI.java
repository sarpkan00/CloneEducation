package com.clone.view;

import com.clone.helper.Helper;
import com.clone.helper.Item;
import com.clone.model.Course;
import com.clone.model.Education;
import com.clone.model.Operator;
import com.clone.model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class OperatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane pnl_education_list;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logOut;
    private JPanel pnl_userList;
    private JScrollPane scrl_userList;
    private JTable tbl_user_List;
    private JPanel pnl_userForm;
    private JTextField fld_user_name;
    private JLabel fld_user_e;
    private JTextField fld_user_username;
    private JLabel fld_user_pa;
    private JPasswordField fld_user_password;
    private JLabel fld_user_t;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_src_user_name;
    private JTextField fld_src_user_userName;
    private JComboBox cmb_src_userType;
    private JButton btn_user_src;
    private JScrollPane scrl_education_list;
    private JTable tbl_education_list;
    private JPanel pnl_education_add;
    private JTextField fld_education_name;
    private JButton btn_education_add;
    private JPanel pnl_couse_list;
    private JScrollPane srcl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_language;
    private JComboBox cmb_course_education;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private DefaultTableModel modelUserList;
    private Object[] rowUserList;
    private DefaultTableModel modelEducationList;
    private Object[] rowEducationList;
    private JPopupMenu educationMenu;
    private DefaultTableModel modelCourseList;
    private Object[] rowCourseList;


    private final Operator operator;

    public OperatorGUI(Operator operator){
        this.operator = operator;

        add(wrapper);
        setSize(1000,500);
        int x = Helper.screenCenter("x",getSize());
        int y = Helper.screenCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Education");
        setVisible(true);

        lbl_welcome.setText("Hoşgeldiniz " + operator.getName());

        //MODELUSERLIST ####
        modelUserList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] callUserList = {"ID", "Ad Soyad", "Kullanıcı Adı" ,"Şifre", "Üyelik Tipi"};
        modelUserList.setColumnIdentifiers(callUserList);
        rowUserList = new Object[callUserList.length];
        loadUserModel();


        tbl_user_List.setModel(modelUserList);
        tbl_user_List.getTableHeader().setReorderingAllowed(false);

        tbl_user_List.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selectedUserİd = tbl_user_List.getValueAt(tbl_user_List.getSelectedRow(), 0).toString();
                fld_user_id.setText(selectedUserİd);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        });

        tbl_user_List.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                int userİd = Integer.parseInt(tbl_user_List.getValueAt(tbl_user_List.getSelectedRow(),0).toString());
                String user_name = tbl_user_List.getValueAt(tbl_user_List.getSelectedRow(),1).toString();
                String user_user_name = tbl_user_List.getValueAt(tbl_user_List.getSelectedRow(),2).toString();
                String user_password = tbl_user_List.getValueAt(tbl_user_List.getSelectedRow(),3).toString();
                String user_type = tbl_user_List.getValueAt(tbl_user_List.getSelectedRow(),4).toString();

                if(User.updateUser(userİd,user_name,user_user_name,user_password,user_type)){
                    Helper.showMessage("success");

                }
                loadUserModel();
                loadEducatorInCombo();
                loadCourseModel();

            }

        });
        //MODELUSERLIST ###


        //EDUCATIONLIST

        educationMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        educationMenu.add(updateMenu);
        educationMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selectRowİd = Integer.parseInt(tbl_education_list.getValueAt(tbl_education_list.getSelectedRow(),0).toString());
            UpdateEducationGUI updateEducationGUI = new UpdateEducationGUI(Education.getFetch(selectRowİd));
            updateEducationGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                   loadEducationModel();
                   loadEducationInCombo();
                   loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if(Helper.confirm("sure")){
                int selectRowID = Integer.parseInt(tbl_education_list.getValueAt(tbl_education_list.getSelectedRow(),0).toString());
                if(Education.delete(selectRowID)){
                    Helper.showMessage("success");
                    loadEducationModel();
                    loadEducationInCombo();
                    loadCourseModel();
                }else {
                    Helper.showMessage("error");
                }
            }

        });

        modelEducationList = new DefaultTableModel();
           Object[] columnEducationList = {"ID","Education name"};
           modelEducationList.setColumnIdentifiers(columnEducationList);
           rowEducationList = new Object[columnEducationList.length];
           loadEducationModel();

           tbl_education_list.setModel(modelEducationList);
           tbl_education_list.setComponentPopupMenu(educationMenu);
           tbl_education_list.getTableHeader().setReorderingAllowed(false);
           tbl_education_list.getColumnModel().getColumn(0).setMaxWidth(100);

           tbl_education_list.addMouseListener(new MouseAdapter() {
               @Override
               public void mousePressed(MouseEvent e) {
                   Point point = e.getPoint();
                   int selectedRow = tbl_education_list.rowAtPoint(point);
                   tbl_education_list.setRowSelectionInterval(selectedRow,selectedRow);
               }
           });
           //EDUCATIONLIST##

        //###### COURSELIST #######

        modelCourseList = new DefaultTableModel();
        Object[] columnCourseList = {"ID", "Ders Adı", "Programlama Dili", "Eğitim" , "Eğitmen"};
        modelCourseList.setColumnIdentifiers(columnCourseList);
        rowCourseList = new Object[columnCourseList.length];
        loadCourseModel();
        tbl_course_list.setModel(modelCourseList);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(100);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadEducationInCombo();
        loadEducatorInCombo();



        //###### COURSELIST ######





        btn_user_add.addActionListener(e -> {

            if(Helper.isFieldEmpty(fld_user_name)  || Helper.isFieldEmpty(fld_user_username)
                    || Helper.isFieldEmpty(fld_user_password)){
              Helper.showMessage("fill");
            }else{
                String name = fld_user_name.getText();
                String userName = fld_user_username.getText();
                String password = String.valueOf(fld_user_password.getText());
                String type =  cmb_user_type.getSelectedItem().toString();
                if(User.adduser(name,userName,password,type)){
                    Helper.showMessage("success");
                    loadUserModel();
                    loadEducatorInCombo();
                    fld_user_name.setText(null);
                    fld_user_username.setText(null);
                    fld_user_password.setText(null);
                    cmb_user_type.setSelectedItem("operator");


                }
            }

        });
        btn_user_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_id)){
                Helper.showMessage("fill");
            }else{
                if(Helper.confirm("sure")){
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if(User.deleteUser(user_id)){
                        Helper.showMessage("success");
                        loadUserModel();
                        loadEducatorInCombo();
                        loadCourseModel();
                        fld_user_id.setText(null);
                    }else{
                        Helper.showMessage("error");
                    }
                }
            }

        });
        btn_user_src.addActionListener(e -> {
            String name = fld_src_user_name.getText();
            String userName = fld_src_user_userName.getText();
            String type = cmb_src_userType.getSelectedItem().toString();
            String query = User.searchQuery(name, userName,type);
            List<User> searching = User.searchUserList(query);
            loadUserModel(searching);

        });
        btn_logOut.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();

        });
        btn_education_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_education_name)){
                Helper.showMessage("fill");
            }else {
                if(Education.add(fld_education_name.getText())){
                    Helper.showMessage("success");
                    loadEducationModel();
                    loadEducationInCombo();
                    fld_education_name.setText(null);
                }else {
                    Helper.showMessage("error");
                }

            }

        });
        btn_course_add.addActionListener(e -> {
            Item educationItem = (Item) cmb_course_education.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if(Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_language)){
                Helper.showMessage("fill");
            }else{
                if(Course.add(userItem.getKey(), educationItem.getKey(), fld_course_name.getText(),fld_course_language.getText())){
                    Helper.showMessage("success");
                    loadCourseModel();
                    fld_course_language.setText(null);
                    fld_course_name.setText(null);
                }else{
                    Helper.showMessage("error");
                }

            }

        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        for (Course obj : Course.getList()){
            rowCourseList[0] = obj.getId();
            rowCourseList[1] = obj.getName();
            rowCourseList[2] = obj.getLanguage();
            rowCourseList[3] = obj.getEducation().getName();
            rowCourseList[4] = obj.getUser().getName();

            modelCourseList.addRow(rowCourseList);

        }
    }

    private void loadEducationModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_education_list.getModel();
        clearModel.setRowCount(0);
        for(Education obj : Education.getList()){
            rowEducationList[0] = obj.getId();
            rowEducationList[1] = obj.getName();
            modelEducationList.addRow(rowEducationList);
        }
    }

    public void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_List.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getUserList()){
            rowUserList[0] = obj.getId();
            rowUserList[1] = obj.getName();
            rowUserList[2] = obj.getUserName();
            rowUserList[3] = obj.getPassword();
            rowUserList[4] = obj.getType();
            modelUserList.addRow(rowUserList);

        }

    }

    public void loadUserModel(List<User> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_List.getModel();
        clearModel.setRowCount(0);

        for (User obj : list){
            rowUserList[0] = obj.getId();
            rowUserList[1] = obj.getName();
            rowUserList[2] = obj.getUserName();
            rowUserList[3] = obj.getPassword();
            rowUserList[4] = obj.getType();
            modelUserList.addRow(rowUserList);

        }

    }
    public void loadEducationInCombo(){
        cmb_course_education.removeAllItems();
        for(Education obj : Education.getList()){
            cmb_course_education.addItem(new Item(obj.getId(), obj.getName()));

        }
    }

    public void loadEducatorInCombo(){
        cmb_course_user.removeAllItems();
        for(User obj : User.getUserList()){
            if(obj.getType().equals("educator")){
                cmb_course_user.addItem(new Item(obj.getId(),obj.getName()));
            }
        }
    }
}
