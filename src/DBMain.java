import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
public class DBMain {
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		boolean searchfun=true;
		
		UpdatePSMT up= new UpdatePSMT();
		con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
		
		searchfun=up.searchCustomer(con);
		
		if (!searchfun) {
			up.CreateRecord(con);
		}
		
		
		System.out.println("Do you want to search another customer or edit record.Press 1 to search or 2 edit or 3 to quit");
		Scanner scan= new Scanner(System.in);
		int option=scan.nextInt();
		
		if(option==1){
			up.searchCustomer(con);
		}
		else if(option==2){
			up.EditCustomer(con);
		}
		
		else{
			System.out.println("Thank You");
		}

	}

}
