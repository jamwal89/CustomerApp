import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class TestOracleJDBC {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		PreparedStatement pstmt;
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
			}
		
			
			System.out.println("Do you want to search another Customer or Edit Customer Details. Press 1 to search or 2 to edit");
			
			
			
			
			
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
	}

	
}