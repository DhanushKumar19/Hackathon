import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;

import org.json.simple.*;
public class Main {

   	
	public static void main(String[] args)  throws NumberFormatException, IOException, SQLException {
		
		BufferedReader br=null;
		DecimalFormat df = new DecimalFormat("#.00");
		JSONObject obj=new JSONObject();
		JSONObject obj2=new JSONObject();
		
		int count = 0;
		double kbVal,mbVal=0,Total=0,avg=0,max=0;
		String val;
		
		File memory_file = new File("C:\\Users\\Admin\\Desktop\\Final_Hackathon\\Memory.txt"); 
		br= new BufferedReader(new FileReader(memory_file));	
		
		 int flag = 0;
		 String line = null;
		 
		 while ( (line = br.readLine() ) != null )
		 {
			 
			flag++;
			line=line.trim();
		  //Fetching values from the odd lines
		    if ( flag % 2 == 1 )
		    {
		    	String[] array = line.split("   ");
		    	kbVal=Double.parseDouble(array[1].trim());
		    	mbVal = kbVal/1024 ;
		    	mbVal = Double.parseDouble(df.format(mbVal));
		    	//Checking the maximum value
		    	if(max < mbVal)
		    	{
		    		max=mbVal;
		    	}
		   
		    	Total += mbVal;
		    	count++;
		  // Adding values to json
		    	val = Integer.toString(count)+ "S";
		  		obj2.put(val, mbVal);
		  
		 }
		 }
		 
		 avg = (Total/count);
		 avg = Double.parseDouble(df.format(avg));
		 
	  // Final json file with max , avg and values
		 obj.put("AverageMemory(MB)", avg);
		 obj.put("MaximumMemory(MB)", max);
		 obj.put("Values",obj2);

	 // Writing	json as separate file
		 FileWriter file1=new FileWriter("C:\\Users\\Admin\\Desktop\\Final_Hackathon\\mem_json.json");
		 file1.write(obj.toJSONString());
			
		 
	  // Adding data to the db
		 Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root123");
		 Statement st= connect.createStatement();
		 st.executeUpdate("create table Mem_table(Maximum_Value DECIMAL(10,2),Average_Value DECIMAL(10,2));");
		 String str="INSERT into Mem_table (Maximum_Value,Average_Value) values('"+max+"','"+avg+"')";
		 st.executeUpdate(str);
	}

}
