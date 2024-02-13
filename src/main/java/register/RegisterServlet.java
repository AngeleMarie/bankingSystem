package register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    String url = "jdbc:mysql://localhost:3306/students";
    String username = "root";
    String password = "";
    String sql = "INSERT INTO users(code, name, age, school) VALUES (?, ?, ?, ?)";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // get print writer
        PrintWriter pw = res.getWriter();
        // content type
        res.setContentType("text/html");
        // read values
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String code = req.getParameter("code");
        String school = req.getParameter("school");

        try {
            // Parse 'age' to Integer
            Integer ageInt = Integer.parseInt(age);

            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection to MySQL database
            try (Connection connection = DriverManager.getConnection(url, username, password);
                    PreparedStatement ps = connection.prepareStatement(sql)) {

                // Set the values
                ps.setString(1, code);
                ps.setString(2, name);
                ps.setInt(3, ageInt); // Set as Integer
                ps.setString(4, school);

                int count = ps.executeUpdate();
                if (count == 0) {
                    pw.println("Record not submitted");
                } else {
                    pw.println("Record stored in the database");
                }

            } catch (SQLException se) {
                System.out.println(se);
            }
        } catch (NumberFormatException ne) {
            // Handle the case where 'age' is not a valid integer
            pw.println("Invalid age value. Please provide a valid integer.");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            pw.close();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Handling POST requests if needed
    }
}
