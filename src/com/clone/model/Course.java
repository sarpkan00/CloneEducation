package com.clone.model;

import com.clone.helper.DbConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int education_id;
    private String name;
    private String language;

    private Education education;
    private User user;

    public Course(int id, int user_id, int education_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.education_id = education_id;
        this.name = name;
        this.language = language;
        this.education = Education.getFetch(education_id);
        this.user = User.getFetch(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEducation_id() {
        return education_id;
    }

    public void setEducation_id(int education_id) {
        this.education_id = education_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList = new ArrayList<>();

        Course obj;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                int education_id = resultSet.getInt("education_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                obj = new Course(id,user_id,education_id,name,language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList = new ArrayList<>();

        Course obj;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course WHERE user_id = " + user_id);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int user_idd = resultSet.getInt("user_id");
                int education_id = resultSet.getInt("education_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                obj = new Course(id,user_id,education_id,name,language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static  boolean add(int user_id,int education_id,String name,String language){
            String query = "INSERT INTO course (user_id, education_id, name, language) VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,education_id);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,language);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public static boolean delete(int id){
        String query = "Delete from course Where id = ?";
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
