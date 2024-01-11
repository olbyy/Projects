package Controller;
import java.sql.SQLException;
import java.util.List;

//generic interface
public interface GenericInterface<T> {
	
	//generic method get
	T get(String type, String number) throws SQLException;
	
	//generic method getAll
	List<T> getAll() throws SQLException;
	
	//generic method insert
	int insert(T t) throws SQLException;	
	
}
