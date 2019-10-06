package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




import java.util.ArrayList;
import java.util.List;

import user.domain.User;



/**
 * DDL functions performed in database
 * @author changxin bai
 *
 */
public class UserDao {
	
	
	/**
	 * get the search result with username 
	 * @param username
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public User findByUsername(String username) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		User user = new User();
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager
				//    .getConnection("jdbc:mysql://141.217.48.128:3306/dataview?"
					.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
		    String sql = "select * from tb_user where username=?";
		    PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,username);
		    ResultSet resultSet = preparestatement.executeQuery();
		    //ResultSet resultSet  = preparestatement.executeUpdate();
		    while(resultSet.next()){
		    	String user_name = resultSet.getString("username");
		    	if(user_name.equals(username)){
		    		user.setUsername(resultSet.getString("username"));
		    		user.setPassword(resultSet.getString("password"));
		    		user.setEmail(resultSet.getString("email"));
		    		user.setFirstName(resultSet.getString("FirstName"));
		    		user.setLastName(resultSet.getString("LastName"));
		    		
		    		
		    	}
		    }
		
		    
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}
	
	
	
	public List<Object> findUserbyfollow(String follower, String following) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		
		ArrayList<String> u1 = new ArrayList<String>(); 
		ArrayList<String> u2 = new ArrayList<String>(); 
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager
				//    .getConnection("jdbc:mysql://141.217.48.128:3306/dataview?"
					.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
		    String sql = "select * from tb_follow where following_name=?";
		    PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,following);
		    ResultSet resultSet = preparestatement.executeQuery();
		    //ResultSet resultSet  = preparestatement.executeUpdate();
		    while(resultSet.next()){
		    	String f1 = resultSet.getString("follower_name");
		    	
		    	u1.add(f1);
		    	
		    }
		
		    
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
        try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager
				//    .getConnection("jdbc:mysql://141.217.48.128:3306/dataview?"
					.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
		    String sql = "select * from tb_follow where follower_name=?";
		    PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,follower);
		    ResultSet resultSet = preparestatement.executeQuery();
		    //ResultSet resultSet  = preparestatement.executeUpdate();
		    while(resultSet.next()){
		    	String f2 = resultSet.getString("following_name");
		    	
		    	u2.add(f2);
		    	
		    }
		
		    
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		
        List<String> common = new ArrayList<String>(u1);
        common.retainAll(u2);
        List<Object> list = new ArrayList<>();
        
		
        for (String s: common) {
        	
        	User u = new User();
        	u = findByUsername(s);
        	list.add(u);
        	


        	}
        
        
		
		
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * insert User
	 * @param user
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void add(User user) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager
			    //      .getConnection("jdbc:mysql://141.217.48.128:3306/dataview?"
				//              + "user=shiyong&password=view1234");
					.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
			
			String sql = "insert into tb_user values(?,?,?,?,?)";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,user.getUsername());
		    preparestatement.setString(2,user.getPassword());
		    preparestatement.setString(3,user.getEmail());
		    preparestatement.setString(4,user.getFirstName());
		    preparestatement.setString(5,user.getLastName());
		    
		    preparestatement.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	public void addfollow(String x, String y) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// This function insert follow(x,y) into table follow, where x is follower, and y is following
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager

					.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
			if (!x.equals(y)) {
				String sql = "insert ignore into tb_follow values(?,?)";
				PreparedStatement preparestatement = connect.prepareStatement(sql); 
			    preparestatement.setString(1,x);
			    preparestatement.setString(2,y);
			    preparestatement.executeUpdate();
				
			}
			

		    
		    
		    
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	
	public void addfollow(String x, String y) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// This function insert follow(x,y) into table follow, where x is follower, and y is following
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager

					.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
			
			String sql = "insert ignore into tb_follow values(?,?)";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,x);
		    preparestatement.setString(2,y);

		    
		    preparestatement.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	 */
	
	public List<Object> findall() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Object> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager
			      // .getConnection("jdbc:mysql://141.217.48.128:3306/dataview?"
				//              + "user=shiyong&password=view1234");
			    		   	.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
			
			String sql = "select * from tb_user";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
			ResultSet resultSet = preparestatement.executeQuery();
			
			while(resultSet.next()){
				User user = new User();
				user.setUsername(resultSet.getString("username"));
	    		user.setPassword(resultSet.getString("password"));
	    		user.setEmail(resultSet.getString("email"));
	    		user.setFirstName(resultSet.getString("FirstName"));
	    		user.setLastName(resultSet.getString("LastName"));
	    		
	    		list.add(user);
			 }
			 
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
		
	}
	
	
	public List<Object> findOthers(String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Object> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager
			      // .getConnection("jdbc:mysql://141.217.48.128:3306/dataview?"
				//              + "user=shiyong&password=view1234");
			    		   	.getConnection("jdbc:mysql://localhost/sampledb?"
				              + "user=john&password=pass1234");
			
			String sql = "select * from tb_user where not username=?";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
			preparestatement.setString(1,username);
			ResultSet resultSet = preparestatement.executeQuery();
			
			while(resultSet.next()){
				User user = new User();
				user.setUsername(resultSet.getString("username"));
	    		user.setPassword(resultSet.getString("password"));
	    		user.setEmail(resultSet.getString("email"));
	    		user.setFirstName(resultSet.getString("FirstName"));
	    		user.setLastName(resultSet.getString("LastName"));
	    		
	    		list.add(user);
			 }
			 
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
		
	}

	
}
