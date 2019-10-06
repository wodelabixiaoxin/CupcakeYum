package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import user.domain.Blog;
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
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

		    String sql = "select * from user where username=?";
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
		    		user.setFirstName(resultSet.getString("first_name"));
		    		user.setLastName(resultSet.getString("last_name"));
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
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

		    String sql = "select * from user_follows where following_name=?";
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
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

		    String sql = "select * from user_follows where follower_name=?";
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
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

			String sql = "insert into user values(?,?,?,?,?)";
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
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

			if (!x.equals(y)) {
				String sql = "insert ignore into user_follows values(?,?)";
				PreparedStatement preparestatement = connect.prepareStatement(sql);
			    preparestatement.setString(1,x);
			    preparestatement.setString(2,y);
			    preparestatement.executeUpdate();

			}

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String addBlog(Blog blog, String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		try {
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";
			String myname = null;

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

			String sql = "insert into blog(subject, description) values(?,?)";
			String blog_id = null;
			User currentuser = null;
			PreparedStatement preparestatement = connect.prepareStatement(sql);
			ResultSet resultSet;

			//inserting new blog
			preparestatement.setString(1,blog.getBlogTitle());
			preparestatement.setString(2,blog.getBlogDescription());
			preparestatement.executeUpdate();

			//get the id of the blog
			String get_last_blog_id_sql = "SELECT LAST_INSERT_ID();";
			preparestatement = connect.prepareStatement(get_last_blog_id_sql);
			resultSet = preparestatement.executeQuery();

			while(resultSet.next()){
				blog_id = resultSet.getString("LAST_INSERT_ID()");
			}

			// Update the user_blog table (username, blog_id)
			String insert_into_user_blog_sql = "insert into user_blog(username, blog_id) values(?, ?)";
			preparestatement = connect.prepareStatement(insert_into_user_blog_sql);

		    preparestatement.setString(1, username);
		    preparestatement.setString(2, blog_id);

			preparestatement.executeUpdate();

			// Update the blog_has_tags table (tag_id, blog_id)
			if(blog.getBlogTag() != null){
				String insert_tag_into_blog_has_tags_table = "INSERT INTO blog_has_tags (blog_id, tag_id) " +
															  "SELECT " + blog_id + ", id " +
															  "FROM tags WHERE description='" + blog.getBlogTag() + "';";

				System.out.println("the sql query is: ");
				System.out.println(insert_tag_into_blog_has_tags_table);

	  		 preparestatement = connect.prepareStatement(insert_tag_into_blog_has_tags_table);
			 preparestatement.executeUpdate();
			}

			System.out.println("returning blog id in userdao.java!");
			return blog_id;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Object> findall() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Object> list = new ArrayList<>();
		try {
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

			String sql = "select * from user";
			PreparedStatement preparestatement = connect.prepareStatement(sql);
			ResultSet resultSet = preparestatement.executeQuery();

			while(resultSet.next()){
				User user = new User();
				user.setUsername(resultSet.getString("username"));
	    		user.setPassword(resultSet.getString("password"));
	    		user.setEmail(resultSet.getString("email"));
	    		user.setFirstName(resultSet.getString("first_name"));
	    		user.setLastName(resultSet.getString("last_name"));

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
			String dbName = "sampledb2";
			String userName = "john";
			String password = "pass1234";
			String hostname = "sampledb2.cpvy4fmhbooi.us-west-2.rds.amazonaws.com";
			String port = "3306";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connect = DriverManager.getConnection(
				"jdbc:mysql://" + hostname + ":" + port + "/" + dbName, userName, password);

			String sql = "select * from user where not username=?";
			PreparedStatement preparestatement = connect.prepareStatement(sql);
			preparestatement.setString(1,username);
			ResultSet resultSet = preparestatement.executeQuery();

			while(resultSet.next()){
				User user = new User();
				user.setUsername(resultSet.getString("username"));
	    		user.setPassword(resultSet.getString("password"));
	    		user.setEmail(resultSet.getString("email"));
	    		user.setFirstName(resultSet.getString("first_name"));
	    		user.setLastName(resultSet.getString("last_name"));

	    		list.add(user);
			 }

		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
