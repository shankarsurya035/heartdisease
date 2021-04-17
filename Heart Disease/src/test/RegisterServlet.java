package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Connection con = Database.getConnection();
		try
		{
			
			PreparedStatement ps = con.prepareStatement("insert into login(username,password) values(?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			
			ps.executeUpdate();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write("<script>");
			out.write("alert(\"User registered successfully\")");
			out.write("</script>");
			response.sendRedirect("welcome.jsp");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			Database.close(con);
		}
	}

}
