import java.sql.*;

public class ServiceEngineer extends User {
	public void viewTickets(String userName) throws Exception
	{
		Connection con = super.createCon();
		Statement st = con.createStatement();
		String query1 = "select * from ticket where serviceeng=\""+userName+"\"";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("TicketName\tTicketType\tRaised EndUser\tTicketStatus");
		while(rs.next())
		{
			System.out.println(rs.getString(1)+"\t\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t\t"+rs.getString(5));
		}
	}
	public void closeTicket(String ticketName) throws Exception
	{
		Ticket t = new Ticket();
		t.changeStatus(ticketName, "close");
	}
	public void changeStatus(String ticketName,String changeStatusTo) throws Exception
	{
		Ticket t = new Ticket();
		t.changeStatus(ticketName, changeStatusTo);
	}
	
}
