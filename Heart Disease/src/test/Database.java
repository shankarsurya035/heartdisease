package test;
import java.sql.*;
public class Database {

	public static Connection getConnection()
	{
		Connection con;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/heart_disease","root","");
			return con;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(Connection con)
	{
		try
		{
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
