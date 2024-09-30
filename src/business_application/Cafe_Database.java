package business_application;

import java.sql.Connection;
import java.sql.DriverManager;

public class Cafe_Database 
{
	public static Connection connectDB()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx_schema", "root", "root");
			return connect;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
