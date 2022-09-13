import java.sql.*;

public class EndUser extends User{
	public void viewTickets(String userName) throws Exception
	{
		Connection con = super.createCon();
		Statement st = con.createStatement();
		String query1 = "select * from ticket where enduser=\""+userName+"\"";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("TicketName\tTicketType\tService Engineer Assigned\tTicket Status");
		while(rs.next())
		{
			System.out.println(rs.getString(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(4)+"\t\t\t"+rs.getString(5));
		}
	}
	public void closeTicket(String ticketName) throws Exception
	{
		User u = new User();
		Connection con = u.createCon();
		Statement st = con.createStatement();
		String query1 = "SELECT EXISTS(SELECT * from ticket WHERE ticketname=\""+ticketName+"\")";
		ResultSet rs = st.executeQuery(query1);
		rs.next();
		if(rs.getBoolean(1))
		{
			query1 = "select * from ticket where ticketname=\""+ticketName+"\"";
			rs = st.executeQuery(query1);
			rs.next();
			String query2 = "update ticket set ticketstatus=? where ticketname=?";
			PreparedStatement ps = con.prepareStatement(query2);
			ps.setString(1, "close");
			ps.setString(2,ticketName);
			ps.executeUpdate();
			String seName = rs.getString(4);
			String query3 = "SELECT EXISTS(SELECT * from user WHERE username=\""+seName+"\")";
			ResultSet rs1 = st.executeQuery(query3);
			rs1.next();
			if(rs1.getBoolean(1))
			{
				query3="update user set availabilty=1 where username=?";
				PreparedStatement ps1 = con.prepareStatement(query3);
				ps1.setString(1,seName);
				ps1.executeUpdate();
				System.out.println("Ticket closed");
			}
		}
	}
	public void raiseTicket(String userName,String ticketName, String ticketType) throws Exception
	{
		Connection con = super.createCon();
		Statement st = con.createStatement();
		String query1 = "SELECT EXISTS(SELECT * from ticket WHERE ticketname=\""+ticketName+"\")";
		ResultSet rs = st.executeQuery(query1);
		rs.next();
		if(!rs.getBoolean(1))
		{
			String query2 = "SELECT EXISTS(SELECT * from user WHERE setype=\""+ticketType+"\")";
			rs = st.executeQuery(query2);
			rs.next();
			String query3 = "insert into ticket values(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query3);
			ps.setString(1, ticketName);
			ps.setString(2, ticketType);
			ps.setString(3, userName);
			if(rs.getBoolean(1))
			{
				query2 = "select * from user where setype=\""+ticketType+"\"";
				rs = st.executeQuery(query2);
				boolean availability = false;
				while(rs.next())
				{
					if(rs.getBoolean(6))
					{
						availability = true;
						ps.setString(4,rs.getString(2));
						ps.setString(5, "open");
						System.out.println("Ticket has been raised and assigned to Service Engineer");
						String query4 = "update user set availabilty = 0 where username = ?";
						PreparedStatement ps2 = con.prepareStatement(query4);
						ps2.setString(1, rs.getString(2));
						ps2.executeUpdate();
						break;
					}
				}
				if(!availability)
				{
					ps.setString(4, null);
					ps.setString(5, "not assigned");
					System.out.println("Ticket has been raised but not assigned to Service Engineer");
				}
			}
			else
			{
				ps.setString(4, null);
				ps.setString(5, "not assigned");
				System.out.println("Ticket has been raised but not assigned to Service Engineer");
			}
			ps.executeUpdate();
		}
		else
		{
			System.out.println("Ticket name already exsists");
		}
	}
}
