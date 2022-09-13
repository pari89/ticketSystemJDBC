import java.sql.*;
import java.util.Scanner;
public class Ticket {
	@SuppressWarnings("resource")
	public void changeStatus(String ticketName,String changeStatusTo) throws Exception
	{
		Scanner sc = new Scanner(System.in);
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
			String ticketType = rs.getString(2);
			String query2 = "update ticket set ticketstatus=? where ticketname=?";
			PreparedStatement ps = con.prepareStatement(query2);
			ps.setString(1, changeStatusTo);
			ps.setString(2,ticketName);
			ps.executeUpdate();
			String seName = rs.getString(4);
			String query3 = "SELECT EXISTS(SELECT * from user WHERE username=\""+seName+"\")";
			ResultSet rs1 = st.executeQuery(query3);
			rs1.next();
			if(changeStatusTo.equalsIgnoreCase("close")||changeStatusTo.equalsIgnoreCase("hold"))
			{
				if(rs1.getBoolean(1))
				{
					query3="update user set availabilty=1 where username=?";
					PreparedStatement ps1 = con.prepareStatement(query3);
					ps1.setString(1,seName);
					ps1.executeUpdate();
					String query5 = "select exists(select * from ticket where tickettype=\""+ticketType+"\" and serviceeng is null)";
					ResultSet rs2 = st.executeQuery(query5);
					rs2.next();
					if(rs2.getBoolean(1))
					{
						query5 = "select * from ticket where tickettype=\""+ticketType+"\" and serviceeng is null";
						rs2 = st.executeQuery(query5);
						System.out.println("Pick a ticket:");
						System.out.println("Ticket name\tTicket Type\tEnd User who raised the tickte");
						while(rs2.next())
						{
							System.out.println(rs2.getString(1)+"\t"+rs2.getString(2)+"\t"+rs2.getString(3));
						}
						System.out.println("Name of the ticket you want to pick:");
						String tN = sc.next();
						do
						{
							query5 = "select exists(select * from ticket where ticketname=\""+tN+"\")";
							rs2 = st.executeQuery(query5);
							rs2.next();
							if(rs2.getBoolean(1))
							{
								query5 = "update ticket set serviceeng=?,ticketstatus='open' where ticketname=?";
								String query6 = "update user set availabilty=0 where username=?";
								PreparedStatement ps2 = con.prepareStatement(query5);
								ps2.setString(1, seName);
								ps2.setString(2, tN);
								ps2.executeUpdate();
								PreparedStatement ps3 = con.prepareStatement(query6);
								ps3.setString(1,seName);
								ps3.executeUpdate();
								break;
							}
							else
							{
								System.out.println("Wrong Ticket name please re-enter or if you are not interested to pick a ticket type no");
								tN = sc.next();
								if(tN.equalsIgnoreCase("no"))
								{
									break;
								}
							}
						}while(true);
					}
					else
					{
						System.out.println("No tickets left");
					}
				}
				if(changeStatusTo.equalsIgnoreCase("close"))
					System.out.println("Ticket closed");
				else
					System.out.println("Ticket on hold");
			}
			else if(changeStatusTo.equalsIgnoreCase("open"))
			{
				if(rs1.getBoolean(1))
				{
					query3 = "select availabilty from user where username=\""+seName+"\"";
					rs1 = st.executeQuery(query3);
					rs1.next();
					if(rs1.getBoolean(1))
					{
						query3="update user set availabilty=0 where username=?";
						PreparedStatement ps1 = con.prepareStatement(query3);
						ps1.setString(1,seName);
						ps1.executeUpdate();
					}
				}
				System.out.println("Ticket opened");
			}
			else
			{
				System.out.println("Ticket status changed");
			}
		}
		else
		{
			System.out.println("Ticket does not exist");
		}
	}
}
