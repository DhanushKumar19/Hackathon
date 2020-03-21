import java.io.*;
import java.sql.*;
import java.util.*;

//Importing jar file for json
import org.json.simple.*;

	
public class Main
{
	static String temp;
	static BufferedReader br = null;
	static int itr = 0;
	static double max=0;
    	static double avg=0;
   	static double sum=0;
    
    //Declaring JSON object and JSON array
	static JSONObject obj = new JSONObject();
	static JSONArray array = new JSONArray();
	static double[] arr = new double[1000];
	
	public static void main(String[] args) throws SQLException 
	{
		//JDBC CONNECTION
		Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/dkdb","root","root123");
		Statement st= connect.createStatement();
		System.out.println("JDBC Connected to Database Successfully");
		try {
			String Eachline;
			
			//Reading the input file sampleinput.txt
			
			br = new BufferedReader(new FileReader("C:\\Users\\Admin\\Desktop\\hackathon\\sampleinput.txt"));
			while ((Eachline = br.readLine()) != null) 
			{
				StringTokenizer stringTokenizer = new StringTokenizer(Eachline, " ");
				while (stringTokenizer.hasMoreElements()) 
				{
					int a=0;
					while(a<8) 
					{
						stringTokenizer.nextElement().toString();
						a++;
					}
					
                   //Required CPU Value
					
					Double reqCPU = Double.parseDouble(stringTokenizer.nextElement().toString());
					if(max<reqCPU)
						max=reqCPU;
					while(a<11) 
					{
						stringTokenizer.nextElement().toString();
						a++;
					}
					 itr++;
					 //Inserting the values to the db
					 st.executeUpdate("insert into response(iteration,time) values ("+itr+","+reqCPU+")");
					temp = itr+"st";
					obj.put(temp,reqCPU);
					arr[itr]=reqCPU;
				}
			}
			System.out.println("CPU Values inserted into the db");
			for (int i = 0; i < arr.length; i++) 
			{
				sum = sum + arr[i];
			}
					avg = sum/arr.length;
					obj.put("Total",sum);
					obj.put("Max",max);
					obj.put("Avg",avg);
					array.add(obj);
					System.out.println("JSON FORMAT");
					System.out.println(array);
					PreparedStatement pt2=con.prepareStatement("SELECT max(time),avg(time) FROM response");
					ResultSet result1=pt2.executeQuery();
					while(result1.next()) {
						String maximum=result1.getString(1);
						String average=result1.getString(2);
						 PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\Admin\\Desktop\\hackathon\\finalpage.html"));
						 
				         out.println("<h1>CPU VALUES</h1>");
				         out.println("<h2>MAXIMUM CPU TIME</h2>  <p>"+ maximum+"</p> <br>");
				         out.println("<h2>AVERAGE CPU TIME</h2>  <p>"+average+"</p>");     
				      
				         out.close();
					}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)
					br.close();

			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}

}
