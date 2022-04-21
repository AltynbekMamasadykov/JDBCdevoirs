package peaksoft.dao;
import peaksoft.exceptions.SomethingWentWrongException;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private static final String createTable = "create table if not exists users(id serial primary key," +
            "name varchar not null," +
            "lastName varchar not null," +
            "age smallint not null)";
    private static final String dropTable = "drop table if exists";



    public UserDaoJdbcImpl() {

    }



    public void createUsersTable() {
        String sql = createTable;
        try(Connection connect = Util.connection();
            Statement statement = connect.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Success Table created!");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void dropUsersTable() {

        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement()) {
            String sql = "drop table users";
            statement.executeUpdate(sql);
            System.out.println("Table deleted!");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "insert into users (name, lastName, age) values (?,?,?)";
        try (Connection conn = Util.connection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            System.out.println("Successfully added "+ name);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";
        try(Connection connection = Util.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            System.out.println("User successfully deleted...");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "select * from users";
        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement()) {
            String sql = "truncate table users";
            statement.executeQuery(sql);
            System.out.println("All users deleted...");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public boolean existsByFirstName(String firstName) {
        String sqlQuery = """
        select case when count(name) > 0 then true else false end
        from users
        where name = ?; 
        """;
        try (PreparedStatement preparedStatement = Util.connection().prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            resultSet.close();
            throw new SomethingWentWrongException(
                    "Something went wrong database does not return any value"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}