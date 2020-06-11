package Xuan.NMRShiftPrediction;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HoseDB {
	
	public static String current_dir = System.getProperty("user.dir");
	public static String DbName = "HoseDB.db";
	
	
	
	/**
	 * connect to db and insert data into the table
	 * @param query
	 * @throws Exception
	 */
	public void AddQuery(String[] query, Connection conn, String tableName) throws Exception {
		
		PreparedStatement prep = conn.prepareStatement(String.format("insert into %s values (?,?,?,?,?);", tableName));
        prep.setString(1,query[0]);
        prep.setString(2,query[1]);
        prep.setString(3,query[2]);
        prep.setString(4,query[3]);
        prep.setString(5,query[4]);
        prep.setString(1,query[0]);
        prep.addBatch();

//        conn.setAutoCommit(false);
        prep.executeBatch();
//        conn.setAutoCommit(true);
        

//        ResultSet rs = stat.executeQuery("select * from people;");
//        while (rs.next()) {
//            System.out.println("name = " + rs.getString("name"));
//            System.out.println("job = " + rs.getString("occupation"));
//        }
       conn.setAutoCommit(false);
    }
	
	
	/**
	 * 
	 * @param hosecode
	 * @param solvent
	 * @param sphere
	 * @return
	 * @throws SQLException 
	 */
	public Double FindMatchingHoseCode(String hosecode, String solvent, int sphere, Connection conn, String table) throws SQLException {
		
		String query = String.format("SELECT Shift FROM Hose%sTable WHERE HoseCode like '%%%s%%' and Solvent = '%s' and Sphere = %d ;",table, hosecode,solvent, sphere);
		PreparedStatement prep = conn.prepareStatement(query);
		ResultSet result = prep.executeQuery();
		
		
		// possible situation: same solvent, and sphere, and same hosecode has more than one shift
		ArrayList<Double> shift = new ArrayList<Double>();
		
		while(result.next()) {
			shift.add(result.getDouble("Shift"));
		}
		
		if(shift.size() == 0) {
			return null;
		}
		else if(shift.size() == 1) {
			return shift.get(0);
		}
		else if(shift.size() > 1) {
			Double shiftvalue = ResolveMultipleShift(shift,1.0);
			if(shiftvalue != null) {
				return shiftvalue;
				
			}else {
				
				return null;
			}
		}
		
		return null;
		
		
		
	}
	
	/**
	 * resolve the situation where there are multiple shift for same solvent, sphere and hosecode
	 * if multiple shift are close enough (shrehold = 1.0)
	 * @param shift
	 * @return
	 */
	public Double ResolveMultipleShift(ArrayList<Double> shift, Double threhold) {
		Double mean = calculateAverage(shift);
		int ok_flag = 1;
		for(Double sh : shift) {
			if((Math.abs(sh-mean)) <= threhold) {
				continue;
			}else {
				ok_flag = 0;
			}
			
		}
		
		if(ok_flag == 0) {
			// not ok 
			return null;
		}else if (ok_flag == 1) {
			
			// ok
			return mean;
			
		}
		
		
		return null;

	}
	
	
	/**
	 * calculate the mean
	 * @param marks
	 * @return
	 */
	private double calculateAverage(ArrayList<Double> marks) {
		Double sum = 0.0;
		if(!marks.isEmpty()) {
			for (Double mark : marks) {
		        sum += mark;
		    }
		    return sum.doubleValue() / marks.size();
		}
		return sum;
	}
	
	/**
	 * connect to db
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public Connection ConnectToDB() {
		
		
		try{
			Connection conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s/HoseDB/%s", current_dir, DbName));
			return conn;
		}catch (Exception e) {
			e.printStackTrace();
			
			
		}
		return null;
		
		
	}
	
}
