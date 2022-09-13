//import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.UUID;

public class User {
	Scanner sc = new Scanner(System.in);
	public Connection createCon() throws Exception
	{
		String url = "jdbc:mysql://localhost:3306/tickets";
		String uname = "root";
		String pass="$riNamu89";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url,uname,pass);
		return con;
	}
	@SuppressWarnings("resource")
	public void register(String userName, String password, String userType) throws Exception
	{
		Connection con = createCon();
		Statement st = con.createStatement();
		String query1 = "SELECT EXISTS(SELECT * from user WHERE username=\""+userName+"\")";
		ResultSet rs = st.executeQuery(query1);
		rs.next();
		if(!rs.getBoolean(1))
		{
			ResultSet rs1;
			UUID uniqueKey;
			do
			{
				uniqueKey = UUID.randomUUID();
				String query2 = "SELECT EXISTS(SELECT * from user WHERE sysid=\""+uniqueKey.toString()+"\")";
				rs1 = st.executeQuery(query2);
				rs1.next();
			}while(rs1.getBoolean(1));
			if(!rs1.getBoolean(1))
			{
				String query = "insert into user values(?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, uniqueKey.toString());
				ps.setString(2,userName);
				ps.setString(3, password);
				ps.setString(4, userType);
				if(userType=="Admin")
				{
					
					ps.setBoolean(5, false);
					ps.setBoolean(6,true);
					ps.setString(7, null);
					ps.executeUpdate();
					System.out.println("Registered user");
				}
				else if(userType=="EndUser")
				{
					ps.setBoolean(5, false);
					ps.setBoolean(6,true);
					ps.setString(7, null);
					ps.executeUpdate();
					System.out.println("Registered user");
				}
				else if(userType=="ServiceEngineer")
				{
					System.out.println("Service Engineer Type:");
					String seType = sc.next();
					ps.setBoolean(5, false);
					ps.setBoolean(6,true);
					ps.setString(7,seType);
					ps.executeUpdate();
					System.out.println("Registered user");
				}
				ps.close();
			}
			
		}
		else
		{
			System.out.println("Username already exists!");
		}
		st.close();
	}
	public String login(String userName, String password) throws Exception
	{
		Connection con = createCon();
		Statement st = con.createStatement();
		String query1 = "SELECT EXISTS(SELECT * from user WHERE username=\""+userName+"\")";
		ResultSet rs = st.executeQuery(query1);
		rs.next();
		if(rs.getBoolean(1))
		{
			query1 = "SELECT * from user WHERE username=\""+userName+"\"";
			rs = st.executeQuery(query1);
			rs.next();
			if(rs.getString(3).equals(password))
			{
				String query2 = "update user set userstatus = 1 where username=?";
				PreparedStatement ps = con.prepareStatement(query2);
				ps.setString(1, userName);
				ps.executeUpdate();
				System.out.println("Logged in successfully");
				return rs.getString(4);
			}
			else
			{
				System.out.println("Wrong password!");
				return null;
			}
		}
		else
		{
			System.out.println("User does not exsist!");
			return null;
		}
	}
	public void logout(String userName) throws Exception
	{
		Connection con = createCon();
		Statement st = con.createStatement();
		String query1 = "SELECT EXISTS(SELECT * from user WHERE username=\""+userName+"\")";
		ResultSet rs = st.executeQuery(query1);
		rs.next();
		if(rs.getBoolean(1))
		{
			query1 = "SELECT * from user WHERE username=\""+userName+"\"";
			rs = st.executeQuery(query1);
			rs.next();
			String query2 = "update user set userstatus = 0 where username=?";
			PreparedStatement ps = con.prepareStatement(query2);
			ps.setString(1, userName);
			ps.executeUpdate();
			System.out.println("Logged out successfully");
			
		}
		else
		{
			System.out.println("User does not exsist!");
		}
	}
}
