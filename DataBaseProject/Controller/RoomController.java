package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Database;
import Model.Room;

public class RoomController implements RoomInterface {
	
	//override get method to get room object from parameters passed in
	@Override
	public Room get(String type, String number) throws SQLException {
		
		//try
		try {
			//create connection 1
			Connection con = Database.getConnection1();
			
			//Instantiate course
			Room room= null;
			
			//create sql statement
			String sql = "SELECT Type, Number, Size, Assigned_Course FROM roomtable WHERE Type = ? AND Number = ?";
			
			//create prepared statement
			PreparedStatement ps = con.prepareStatement(sql);
			
			//set prepared statement with parameters
			ps.setString(1, number);
			
			//create result set and assign ps excuted query
			ResultSet rs = ps.executeQuery();
			
			//if result set has next do
			if(rs.next()) {
				
				//create parameters
				String roomType = rs.getString("Type");
				String roomNumber = rs.getString("Number");
				int roomSize = rs.getInt("Size");
				String assignedCourse = rs.getString("Assigned_Course");
				
				//create room object with parameters
				room = new Room(roomType, roomNumber, roomSize, assignedCourse);
			}
			
			//close
			Database.closeResultSet(rs);
			Database.closePreparedStatement(ps);
			Database.closeConnection(con);
			
			//return room
			return room;
		
			//catch
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		//return null
		return null;

	}
	//method to get all rooms
	@Override
	public List<Room> getAll() throws SQLException {
		
		//create connection to database
		Connection con = Database.getConnection1();
		
		//create sql query
		String sql = "SELECT Type, Number, Size, Assigned_Course FROM roomtable";
		
		//create room list
		List<Room> rooms = new ArrayList<>();
		
		//create statement
		Statement s = con.createStatement();
		
		//create result set and execute query sql
		ResultSet rs = s.executeQuery(sql);
		
		//while result set has next do
		while(rs.next()) {
			
			//create vars for result set
			String roomType = rs.getString("Type");
			String roomNumber = rs.getString("Number");
			int roomSize = rs.getInt("Size");
			String assignedCourse = rs.getString("Assigned_Course");
			
			//create room object
			Room room = new Room(roomType, roomNumber, roomSize, assignedCourse);
			
			//add room to list
			rooms.add(room);
			
		}
		
		//close
		Database.closeConnection(con);
		Database.closeStatement(s);
		Database.closeResultSet(rs);
		
		//return rooms list
		return rooms;
		
		
	}

	//insert method to insert/add room to database
	@Override
	public int insert(Room room) throws SQLException {
		
		//create connection to database
		Connection con = Database.getConnection1();
		
		//create sql statement
		String sql  = "INSERT INTO roomtable (Type, Number, Size, Assigned_Course) VALUES (?,?,?,?)";
		
		//create prepared statement
		PreparedStatement ps = con.prepareStatement(sql);
		
		//set prepared statement with parameters
		ps.setString(1, room.getType());
		ps.setString(2, room.getRoomNumber());
		ps.setInt(3, room.getSize());
		ps.setString(4, room.getAssignedCourse());
		
		//create result var and update prepared statement
		int result = ps.executeUpdate();
		
		//close
		Database.closePreparedStatement(ps);
		Database.closeConnection(con);
		
		//return result
		return result;
	}

}
