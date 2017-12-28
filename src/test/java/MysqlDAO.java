import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MysqlDAO {
	
	 /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:followers.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("done");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
   
    public void insert(String nickname, int isFollwed) {
        String sql = "INSERT INTO followers(nickname,isFollowed) VALUES(?,?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setString(1, nickname);
            pstmt.setInt(2, isFollwed);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<String> selectAllFollowed(){
        String sql = "SELECT nickname FROM followers WHERE isFollowed = 1";
        ArrayList<String> followed = new ArrayList<String>(); 
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                followed.add(rs.getString("nickname"));        
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return followed;
    }
    
    
    
    public static void main (String [] args){
    	
    	MysqlDAO mysql = new MysqlDAO();
    	ArrayList list = mysql.selectAllFollowed();
    	System.out.println(list);
    }
 
}
