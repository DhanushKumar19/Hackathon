import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

public class ReadingFile {
	static BufferedReader br = null;
	static int i = 0;

	public static void main(String[] args) {
		double sum=0;
		double avg=0;
		double max=0;
		Connection myConn = null;
    	Statement myStmt = null;
    	ResultSet myRs = null;
		try {
			
			// 1. Get a connection to database
    		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hack", "hack" , "hack123");
    		
    		//System.out.println("Database connection successful!\n");
			
    		
    		myStmt = myConn.createStatement();
    		
    		
    	PreparedStatement pStmt = myConn.prepareStatement("INSERT into hack (transactionname, average, maximum) values(?,?,?)");
    	
    	
			String row;
			br = new BufferedReader(new FileReader("C:\\Users\\Admin\\Desktop\\JTL\\hackathon\\Sample.txt"));
			while ((row = br.readLine()) != null) {
//				System.out.println(line);

				StringTokenizer stringTokenizer = new StringTokenizer(row, " ");
				pStmt.setString(1,"Transaction1");
				while (stringTokenizer.hasMoreElements()) {

					int x = 0;
					while(x < 8) {
						stringTokenizer.nextElement().toString();
						x++;
					}
					
					
	double reqCPU = Double.parseDouble(stringTokenizer.nextElement().toString());
					if(max<reqCPU)
						max=reqCPU;
						sum+=reqCPU;
					
					while(x<11) {
						stringTokenizer.nextElement().toString();
						x++;
					}

					StringBuilder stbr = new StringBuilder();
					stbr.append(i + "s" +" " +reqCPU);
					i++;
//					System.out.println(sb.toString());
				}
				
			}
			avg=(double)sum/(double)i;
			pStmt.setDouble(2,avg);
			pStmt.setDouble(3,max);
			 pStmt.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
	}
	

}
