import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/GoDb")
public class DbServlet extends HttpServlet {
    private static Connection connection;

    @SneakyThrows
    @Override
    public void init() throws ServletException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:8088;databaseName=java_labs",
                "SA", "Pasword_01");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        int idParam;
        // String username;
        if (tryParseInt(request.getParameter("id"))) {
            idParam = Integer.parseInt(request.getParameter("id"));
            try (CallableStatement callableStatement = connection.prepareCall("{call getUserById(?,?)}")) {
                callableStatement.registerOutParameter(1, Types.VARCHAR);
                callableStatement.setLong(2, idParam);
                callableStatement.execute();
                String username = callableStatement.getString(1);
                pw.println(idParam + " - " + username);
                pw.flush();
            }
        } else {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String email = resultSet.getString(3);
                pw.println("User Id: " + id + " Username: " + username + "Email: " + email);
                pw.flush();
            }
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement statement = connection.prepareStatement("insert into users values (?, ?);");
        statement.setString(1, request.getParameter("username"));
        statement.setString(2, request.getParameter("email"));
        statement.executeUpdate();
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
