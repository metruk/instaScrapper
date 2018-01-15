import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlDAO {
	
	 /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:instagram.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("done");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
   
    public void insert(String query) {
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public List<String> selectMultiply( String sql,String returnedValue){
        
        ArrayList<String> followed = new ArrayList<String>(); 
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                followed.add(rs.getString(returnedValue));        
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return followed;
    }
    
    void updateField(String number,String followHref) throws SQLException{          
    	   String query = "UPDATE followers SET isFollowed = ? WHERE nickname = ?";
    	   Connection conn = this.connect();
    	   PreparedStatement update = conn.prepareStatement(query);
    	   update.setString(1, number);
    	   update.setString(2, followHref);
    	   update.executeUpdate();
    	
    }
    
    String select(String query,String returnedValue) throws SQLException{
         String value = "";
          Connection conn = this.connect();
          Statement stmt  = conn.createStatement();
          try{
        	  ResultSet rs    = stmt.executeQuery(query);
             
             // loop through the result set
             while (rs.next()) {
                 value = rs.getString(returnedValue);
             }
          }catch(org.sqlite.SQLiteException ex){
        	  ex.printStackTrace();
        	  
        	  value = "";
          
         } 
         
         return value;
    }
        // return followed;
    	
    
    public static void main (String [] args) throws SQLException{
    	 MysqlDAO mysql = new MysqlDAO();
 		
    	String username = "nesquic";
    	//String sql = "SELECT username FROM users WHERE username = '"+username+"';";
 		String sql = "SELECT id FROM users WHERE username = '"+username+"';";
 		
 		String id =mysql.select(sql,"id");
 		System.out.println(id);
 		
 		String sql1 = "SELECT login FROM followedusers WHERE user_id ='"+id+"';";
 		List<String> users1 =mysql.selectMultiply(sql1,"login");
 		System.out.println(users1);
    }

}
