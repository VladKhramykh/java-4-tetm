package dao;

import entity.Link;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LinkRepo {
    private Connection connection;

    public LinkRepo(FileInputStream filepath) throws IOException {
        try {
            Properties properties = new Properties();
            properties.load(filepath);
            Class.forName(properties.getProperty("db.driverClassName"));

            connection = DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.username"), properties.getProperty("db.password"));
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Link> getReferences(String filter) throws  SQLException{
        String[] searchWords;
        String sql = "select * from `references` where";
        if (filter == null)
            sql += " description like '%%'";
        else {
           searchWords = filter.split(" ");
           for (int i = 0; i<searchWords.length;i++){
               if (i!= 0)
                   sql+=" or ";
               sql += " (description like '%"+searchWords[i]+"%') ";
           }
        }
        Statement preparedStatement = connection.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery(sql);
        List<Link> links = new ArrayList<Link>();
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String url = resultSet.getString(2);
            String description = resultSet.getString(3);
            int minus = resultSet.getInt(4);
            int plus = resultSet.getInt(5);
            links.add(new Link(id,url,description,minus,plus));
        }
        return links;
    }

    public void addReference(Link link) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall("{call AddReference(?,?,?,?,?)}");
        callableStatement.setInt(1, link.getId());
        callableStatement.setString(2, link.getUrl());
        callableStatement.setString(3, link.getDescription());
        callableStatement.setInt(4, link.getMinus());
        callableStatement.setInt(5, link.getPlus());
        callableStatement.execute();
    }

    public void  updateReference(Link link) throws SQLException{
        String sqlRequest = "update `references` set ";
        if (link.getUrl() != null && link.getDescription() != null)
            sqlRequest += "url = '"+ link.getUrl()+"', description = '"+ link.getDescription()+"'";
        if (link.getPlus() != -1)
            sqlRequest += " plus = "+(link.getPlus()+1);
        if (link.getMinus() != -1)
            sqlRequest += " minus = "+(link.getMinus()+1);
        sqlRequest += " where id ="+ link.getId();
        connection.createStatement().executeUpdate(sqlRequest);
    }

    public void  deleteReference(int id) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from `comment` where refId = "+id);
        statement.executeUpdate("delete from `references` where id = "+id);
    }
}