package presentation.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import external.db.CRUDdb;
import presentation.model.EmployeeModel;

@WebServlet("/api/v1/employees/*")
public class EmployeeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ObjectMapper employeeMapper = new ObjectMapper();

	@Override
	public void init() throws ServletException {
		try {
			CRUDdb.start();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("NOT Connected !\n" + e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter out = resp.getWriter()) {
			resp.setContentType("application/json");
			String empID = req.getPathInfo();

			if (empID != null && empID.length() > 1) {
				String urlNumber = empID.substring(1);
				int empId = Integer.parseInt(urlNumber);
				EmployeeModel emp = CRUDdb.getResReadById(empId);

				if (emp != null) {
					out.write(employeeMapper.writeValueAsString(emp));
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					out.write("{\"message\": \"Employee not found\"}");
				}
			} else {
				Map<Integer, EmployeeModel> employees = CRUDdb.getResReadRow();
				if (employees.isEmpty()) {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					out.write("{\"message\": \"No employees found\"}");
				} else {
					out.write(employeeMapper.writeValueAsString(employees));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\": \"Database error occurred\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter out = resp.getWriter()) {
			BufferedReader read = req.getReader();
			StringBuilder text = new StringBuilder();
			String line;

			while ((line = read.readLine()) != null) {
				text.append(line);
			}

			EmployeeModel newEmp = employeeMapper.readValue(text.toString(), EmployeeModel.class);
			boolean success = CRUDdb.insert(newEmp);

			if (success) {
				EmployeeModel addedEmp = CRUDdb.getResReadById(newEmp.getId());
				out.write(employeeMapper.writeValueAsString(addedEmp));
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.write("{\"message\": \"Failed to insert employee\"}");
			}

			read.close();
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\": \"Database error occurred\"}");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter out = resp.getWriter()) {
			resp.setContentType("application/json");
			String empID = req.getPathInfo();

			if (empID != null && empID.length() > 1) {
				String urlNumber = empID.substring(1);
				int empId = Integer.parseInt(urlNumber);
				EmployeeModel emp = CRUDdb.getResReadById(empId);

				if (emp != null) {
					out.write(employeeMapper.writeValueAsString(emp));
					BufferedReader read = req.getReader();
					StringBuilder text = new StringBuilder();
					String line;

					while ((line = read.readLine()) != null) {
						text.append(line);
					}

					EmployeeModel upEmp = employeeMapper.readValue(text.toString(), EmployeeModel.class);
					CRUDdb.update(upEmp);
					read.close();

				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					out.write("{\"message\": \"Employee not found\"}");
				}
			} else {
				out.write("There is NOT any Input");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\": \"Database error occurred\"}");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter out = resp.getWriter()) {
			resp.setContentType("application/json");
			String empID = req.getPathInfo();

			if (empID != null && empID.length() > 1) {
				String urlNumber = empID.substring(1);
				int empId = Integer.parseInt(urlNumber);
				boolean success = CRUDdb.delete(empId);

				if (success) {
					resp.setStatus(HttpServletResponse.SC_OK);
					out.write("{\"message\": \"Employee was removed!\"}");
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					out.write("{\"message\": \"Employee not found\"}");
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.write("{\"message\": \"There is NOT any Input\"}");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\": \"Database error occurred\"}");
		}
	}
}

//try (PrintWriter out = resp.getWriter()) {
//	BufferedReader read = req.getReader();
//	StringBuilder text = new StringBuilder();
//	String line;
//	
//	while ((line = read.readLine()) != null) {
//		text.append(line);
//	}
//	
//	EmployeeModel upEmp = employeeMapper.readValue(text.toString(), EmployeeModel.class);
//	boolean success = CRUDdb.update(upEmp);
//	
//	if (success) {
//		out.write(employeeMapper.writeValueAsString(upEmp));
//		resp.setStatus(HttpServletResponse.SC_OK);
//	} else {
//		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//		out.write("{\"message\": \"Employee not found\"}");
//	}
//	
//	read.close();
//} catch (SQLException e) {
//	e.printStackTrace();
//	resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	resp.getWriter().write("{\"error\": \"Database error occurred\"}");
//}