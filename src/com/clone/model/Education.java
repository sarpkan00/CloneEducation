package com.clone.model;

import com.clone.helper.DbConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Education {
    private int id;
    private String name;

    public Education(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static ArrayList<Education> getList(){
        ArrayList<Education> educations = new ArrayList<>();
        Education obj;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from education");
            while (resultSet.next()){
                obj = new Education(resultSet.getInt("ID"), resultSet.getString("name"));
                educations.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return educations;
    }

    public static boolean add(String name){
        String query = "INSERT INTO education (name) Values (?)";
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name){
        String query = "UPDATE education SET name = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Education getFetch(int id){
        Education obj = null;
        String query = "Select * from education where id = ?";
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                obj = new Education(resultSet.getInt("id"), resultSet.getString("name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM education WHERE id = ?";
        ArrayList<Course> courseList = Course.getList();
        for(Course obj : courseList){
            if(obj.getEducation().getId() == id){
                Course.delete(obj.getId());
            }
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
}
