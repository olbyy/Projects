package Controller;
import java.sql.SQLException;

import Model.Course;

//Course Interface that extends Generic Interface
public interface CourseInterface extends GenericInterface<Course> {
	
	//method assignRoom
	Course assignRoom(String type, String number, String rType, String rNum) throws SQLException;
	
	//method registerStudents
	Course registerStudents(String type, String level, int students) throws SQLException;
	
	//method getStudents
	int getStudents(String type, String number) throws SQLException;
}
