package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
/**
 * Servlet implementation class AuthenticateUser
 */
@WebServlet("/AuthenticateUser")
public class AuthenticateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/heart_disease","root","");
			Statement st = con.createStatement();
			String query = "select * from login where username='"+username+"' and password='"+password+"'";
			ResultSet rs = st.executeQuery(query);
			if(rs.next())
				response.sendRedirect("decide.jsp");
			else
			{
				try
				{
					PrintWriter out = response.getWriter();
					response.setContentType("text/html");
					out.write("<script type=\"text/javascript\">");
					out.write("alert('Username or password is incorrect')");
					out.write("</script>");
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				response.sendRedirect("index.jsp");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
