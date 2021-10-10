package com.clone.model;

import com.clone.helper.DbConnector;
import com.clone.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String userName;
    private String password;
    private String type;

    public User(){

    }

    public User(int id, String name, String userName, String password, String type) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public static ArrayList<User> getUserList(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "Select * From user";
        User obj;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUserName(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                userList.add(obj);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public static boolean adduser(String name, String userName, String password, String type){
        String query = "INSERT INTO user (name,username,password,type) VALUES(?,?,?,?)";
        User findUser = User.getFetch(userName);

        if(findUser != null){
            Helper.showMessage("Bu Kullanıcı Adı Kullanılıyor!!!");
            return false;
        }

        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,userName);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,type);

            int response = preparedStatement.executeUpdate();
            if(response == -1){
                Helper.showMessage("error");
            }

            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true ;
    }

    public static User getFetch(String userName){
        User obj = null;
        String query = "SELECT * FROM user WHERE username = ?";


        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUserName(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(int id){
        User obj = null;
        String query = "SELECT * FROM user WHERE id = ?";


        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUserName(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(String userName, String password){
        User obj = null;
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";


        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                switch (resultSet.getString("type")){
                    case "operator":
                        obj = new Operator();
                        break;
                    default:
                        obj= new User();
                }
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUserName(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean deleteUser(int id){
        String query = "Delete from user Where id = ?";
        ArrayList<Course> courses = Course.getListByUser(id);
        for(Course c : courses){
            Course.delete(c.getId());
        }
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean updateUser(int id,String name,String userName,String password,String type){
        String query = "UPDATE user SET name = ?, username = ?, password = ?,type = ? WHERE id = ?";

        User findUser = User.getFetch(userName);

        if(findUser != null && findUser.getId() != id){
            Helper.showMessage("Bu Kullanıcı Adı Kullanılıyor!!!");
            return false;
        }

        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,userName);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,type);
            preparedStatement.setInt(5,id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;

    }

    public static List<User> searchUserList(String query){
        List<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                obj = new User();
                obj.setId(resultSet.getInt("id"));
                obj.setName(resultSet.getString("name"));
                obj.setUserName(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                userList.add(obj);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }


    public static String searchQuery(String name, String userName, String type){
        String query= "SELECT * FROM user WHERE username LIKE '%{{username}}%' AND name LIKE '%{{name}}%' ";
        query = query.replace("{{username}}", userName);
        query = query.replace("{{name}}", name);
        if(!type.isEmpty() ){
            query += "AND type '{{type}}'";
            query.replace("{{type}}", type);
        }

        return query;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
