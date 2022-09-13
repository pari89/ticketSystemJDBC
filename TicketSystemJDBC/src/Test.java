import java.util.*;
public class Test {
	public static void main(String[] args) throws Exception
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Admin ad = new Admin();
		String s;
		System.out.println("Register/Login");
		s=sc.next();
		do
		{
			if(s.equalsIgnoreCase("register"))
			{
				System.out.println("Only Admin can register!\n Username \t Password");
				String name, password;
				name = sc.next(); password = sc.next();
				ad.register(name, password, "Admin");
				System.out.println("To login type login");
				s = sc.next();
			}
			if(s.equalsIgnoreCase("login"))
			{
				System.out.println("Name\tPassword");
				String name, password;
				name = sc.next(); password = sc.next();
				String userType = ad.login(name, password);
				if(!(userType==null))
				{
					if(userType.equalsIgnoreCase("Admin"))
					{
						String func;
						do
						{
							System.out.println("Choose a Function\naddAdmin\taddServiceEngineer\taddEndUser\tviewUsers\tlogout");
							func = sc.next();
							if(func.equalsIgnoreCase("addAdmin"))
							{
								System.out.println("Name\tPassword");
								String name1, password1;
								name1 = sc.next(); password1 = sc.next();
								ad.addAdmin(name1, password1);
							}
							else if(func.equalsIgnoreCase("addServiceEngineer"))
							{
								System.out.println("Name\tPassword");
								String name1, password1;
								name1 = sc.next(); password1 = sc.next();
								ad.addServiceEngineer(name1, password1);
							}
							else if(func.equalsIgnoreCase("addEndUser"))
							{
								System.out.println("Name\tPassword");
								String name1, password1;
								name1 = sc.next(); password1 = sc.next();
								ad.addEndUser(name1, password1);
							}
							else if(func.equalsIgnoreCase("viewUsers"))
							{
								ad.viewUsers();
							}
							else if(func.equalsIgnoreCase("logout"))
							{
								ad.logout(name);
							}
						}while(!func.equalsIgnoreCase("logout"));
					}
					if(userType.equalsIgnoreCase("serviceengineer"))
					{
						ServiceEngineer sE = new ServiceEngineer();
						String func;
						do
						{
							System.out.println("Choose a Function\nviewTickets\tchangeStatus\tcloseTicket\tlogout");
							func = sc.next();
							if(func.equalsIgnoreCase("viewTickets"))
							{
								sE.viewTickets(name);
							}
							else if(func.equalsIgnoreCase("changeStatus"))
							{
								System.out.println("Ticket name\tChange Status to");
								String ticketName, statusChange;
								ticketName = sc.next();  statusChange = sc.next();
								sE.changeStatus(ticketName,statusChange);
							}
							else if(func.equalsIgnoreCase("closeTicket"))
							{
								System.out.println("Ticket name");
								String ticketName;
								ticketName = sc.next(); 
								sE.closeTicket(ticketName);
							}
							else if(func.equalsIgnoreCase("logout"))
							{
								sE.logout(name);
							}
						}while(!func.equalsIgnoreCase("logout"));
					}
					if(userType.equalsIgnoreCase("enduser"))
					{
						EndUser eD = new EndUser();
						String func;
						do
						{
							System.out.println("Choose a Function\nviewTickets\traiseTicket\tcloseTicket\tlogout");
							func = sc.next();
							if(func.equalsIgnoreCase("viewTickets"))
							{
								eD.viewTickets(name);
							}
							else if(func.equalsIgnoreCase("raiseTicket"))
							{
								System.out.println("Ticket name\tTicket Type");
								String ticketName,ticketType;
								ticketName = sc.next(); ticketType = sc.next(); 
								eD.raiseTicket(name,ticketName, ticketType);
							}
							else if(func.equalsIgnoreCase("closeTicket"))
							{
								System.out.println("Ticket name");
								String ticketName;
								ticketName = sc.next(); 
								eD.closeTicket(ticketName);
							}
							else if(func.equalsIgnoreCase("logout"))
							{
								eD.logout(name);
							}
						}while(!func.equalsIgnoreCase("logout"));
					}
				}

				}
			System.out.println("If you wanna login or register, type either login or register else no");
			s = sc.next();
		}while(s.equalsIgnoreCase("login")||s.equalsIgnoreCase("register"));
	}
	
}
