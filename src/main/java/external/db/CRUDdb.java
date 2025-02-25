package external.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import presentation.model.EmployeeModel;

public class CRUDdb {

	private static Connection con = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet res = null;

	private static String sqlReadAll = "SELECT * FROM employees;";
	private static String sqlReadById = "SELECT * FROM employees WHERE id = ?;";
	private static String sqlInsert = "INSERT INTO employees (name, gender, dob, phonenumber, hobbies) VALUES (?, ?, ?, ?, string_to_array(?, ','));";
	private static String sqlUpdate = "UPDATE employees SET name = ?, gender = ?, dob = ?, phonenumber = ?, hobbies = ARRAY[?] WHERE id = ?;";
	private static String sqlDelete = "DELETE FROM employees WHERE id = ?;";

	public static void start() throws ClassNotFoundException, SQLException {
		con = DBConnectionProvider.getConect();
		System.out.println("Connected to the database! V2");
	}

	public static Map<Integer, EmployeeModel> getResReadRow() throws SQLException {
		Map<Integer, EmployeeModel> employeeList = new HashMap<>();
		pstmt = con.prepareStatement(sqlReadAll);
		res = pstmt.executeQuery();

		while (res.next()) {
			int id = res.getInt("id");
			String name = res.getString("name");
			String gender = res.getString("gender");
			String dob = res.getString("dob");
			String phoneNumber = res.getString("phonenumber");
			String hobbiesStr = res.getString("hobbies");
			List<String> hobbies = hobbiesStr != null ? List.of(hobbiesStr.split(",")) : new ArrayList<>();
			EmployeeModel emp = new EmployeeModel(id, name, gender, dob, phoneNumber, hobbies);
			employeeList.put(id, emp);
		}
		return employeeList;
	}

	public static EmployeeModel getResReadById(int id) throws SQLException {
		pstmt = con.prepareStatement(sqlReadById);
		pstmt.setInt(1, id);
		res = pstmt.executeQuery();

		if (res.next()) {
			String name = res.getString("name");
			String gender = res.getString("gender");
			String dob = res.getString("dob");
			String phoneNumber = res.getString("phonenumber");
			String hobbiesStr = res.getString("hobbies");
			List<String> hobbies = hobbiesStr != null ? List.of(hobbiesStr.split(",")) : new ArrayList<>();
			return new EmployeeModel(id, name, gender, dob, phoneNumber, hobbies);
		} else {
			return null;
		}
	}

	public static boolean insert(EmployeeModel emp) throws SQLException {
		pstmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, emp.getName());
		pstmt.setString(2, emp.getGender());
		pstmt.setString(3, emp.getDob());
		pstmt.setString(4, emp.getPhoneNumber());
		pstmt.setString(5, String.join(",", emp.getHobbies()));
		int rowsAffected = pstmt.executeUpdate();

		if (rowsAffected > 0) {
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				emp.setId(generatedKeys.getInt(1));
			}
			return true;
		}
		return false;
	}

	public static boolean update(EmployeeModel emp) throws SQLException {
		pstmt = con.prepareStatement(sqlUpdate);
		pstmt.setString(1, emp.getName());
		pstmt.setString(2, emp.getGender());
		pstmt.setString(3, emp.getDob());
		pstmt.setString(4, emp.getPhoneNumber());
		pstmt.setString(5, String.join(",", emp.getHobbies()));
		pstmt.setInt(6, emp.getId());
		int rowsAffected = pstmt.executeUpdate();
		return rowsAffected > 0;
	}

	public static boolean delete(int id) throws SQLException {
		pstmt = con.prepareStatement(sqlDelete);
		pstmt.setInt(1, id);
		int rowsAffected = pstmt.executeUpdate();
		return rowsAffected > 0;
	}
}
