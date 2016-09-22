import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class UpdatePSMT {
	@SuppressWarnings("resource")

	//Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	ResultSet rl = null;
	ResultSet rk = null;
	ResultSet rn=null;
	PreparedStatement pstmt;



	public boolean searchCustomer(Connection con){
		boolean search=false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			System.out.println("Enter the lastname of the customer");
			Scanner scan= new Scanner  (System.in);
			String answer= scan.nextLine();
			pstmt = con.prepareStatement("Select Cust.CompanyID,Cust.Title,Cust.Fullname,cust.StreetAddress, Cust.City, Cust.Emailaddress,Comp.Company from Customers cust inner join Company comp On cust.CompanyID=comp.CompanyID where cust.Lastname=?");
			//pstmt.setString(1,"Hill");
			pstmt.setString(1,answer);
			rs =pstmt.executeQuery();


			while(rs.next()){

				System.out.print(rs.getString("CompanyID")+"\n"+rs.getString("Title") + rs.getString("Fullname")+"\n"+rs.getString("StreetAddress")+rs.getString("City")+"\n"+rs.getString("EmailAddress")+"\n"+rs.getString("Company")+"\n");

				System.out.println();
				search=true;
			}

			if(!rs.next()) {
				return search;
				
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				//pstmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return search;
	}


	public void EditCustomer(Connection con){
		
		try {
			System.out.println("Enter the Lastname");
			Scanner scan =new Scanner(System.in);
			String Ln=scan.nextLine();
			
			
			
			System.out.println("Enter the Customers Address");
			String ca= scan.nextLine();
			
			System.out.println("Enter the City");
			String City=scan.nextLine();
			System.out.println("Enter the Zip");
			String zip=scan.nextLine();
			System.out.println("Enter the State");
			String State= scan.nextLine();
			
			
			pstmt=con.prepareStatement("Select State_ID from States  where Statename=?");
			pstmt.setString(1, State);
			rs=pstmt.executeQuery();
			
			
			if(rs.next()){
			String newStateID=rs.getString("State_ID");
			
			pstmt=con.prepareStatement("Update Customers Set StateID=?, StreetAddress=?, City=?, ZipCode=? where Lastname=?");
			pstmt.setString(1,newStateID);
			pstmt.setString(2,ca);
			pstmt.setString(3,City);
			pstmt.setString(4,zip);
			pstmt.setString(5,Ln);
			
			rs=pstmt.executeQuery();
			if(rs.next()){
			System.out.println("record updated");
			}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void CreateRecord(Connection con){
		try {
			
		
		System.out.println("Enter the Customer's name");
		Scanner scan= new Scanner(System.in);
		String name = scan.nextLine();
		
		
		pstmt=con.prepareStatement("Select * from Customers where fullname=?");
		pstmt.setString(1, name);
		rs=pstmt.executeQuery();
		
		if(!rs.next()) {
			System.out.println(" Record not found.We are creating a record for the customer");
			System.out.println("Enter the Customer First and Lastname");
			String fn =scan.nextLine();
			String ln=scan.nextLine();
			System.out.println("Enter the Customer Title");
			String tl =scan.nextLine();
			System.out.println("Enter the Customer's Company Name");
			String comp=scan.nextLine();
			
			System.out.println("Enter the Customers Address");
			String ca= scan.nextLine();
			
			System.out.println("Enter the City, Zip, State");
			String City=scan.nextLine();
			String zip=scan.nextLine();	
			String State= scan.nextLine();
			
			System.out.println("Enter the email");
			String email= scan.nextLine();
			
			pstmt=con.prepareStatement("Select State_ID from States  where Statename=?");
			pstmt.setString(1, State);
			rk=pstmt.executeQuery();
			
			
		
			
			pstmt=con.prepareStatement("Select CompanyID from Company  where Company=?");
			pstmt.setString(1, comp);
			rl=pstmt.executeQuery();
			
			if( rk.next() && rl.next()){
			
		String newStateID=rk.getString("State_ID");
		//System.out.println(newStateID);
		String newCompanyID=rl.getString("CompanyID");
		//System.out.println(newCompanyID);
		pstmt=con.prepareStatement("Insert into Customers (FullName, Title, firstname,lastname, streetaddress, city, zipcode, emailaddress, companyid, stateid) values (?,?,?,?,?,?,?,?,?,?)");

		pstmt.setString(1, fn+" "+ln);
		pstmt.setString(2, tl);
		pstmt.setString(3, fn);
		pstmt.setString(4, ln);
		pstmt.setString(5, ca);
		pstmt.setString(6, City);
		pstmt.setString(7, zip);
		pstmt.setString(8, email);
		pstmt.setString(9, newStateID);
		pstmt.setString(10, newCompanyID);
		rn=pstmt.executeQuery();
		con.commit();
		
		if(rn.next()){
		System.out.println("record created");
		}
	
			}
		
		
	
		
				
			
				
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

