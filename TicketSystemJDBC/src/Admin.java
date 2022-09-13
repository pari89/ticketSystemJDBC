import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin extends User{
	public void addAdmin(String Username, String Password) throws Exception
	{
		super.register(Username, Password,"Admin");
	}
	public void addEndUser(String Username, String Password) throws Exception
	{
		super.register(Username, Password,"EndUser");
	}
	public void addServiceEngineer(String Username, String Password) throws Exception
	{
		super.register(Username, Password,"ServiceEngineer");
	}
	public void viewUsers() throws Exception
	{
		Connection con = createCon();
		Statement st = con.createStatement();
		String query1 = "SELECT * from user";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("System Code\t\t\t\tUsername\t\tPassword\t\tUsertype");
		while(rs.next())
		{
			System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t\t"+rs.getString(4));
		}
	}
}
