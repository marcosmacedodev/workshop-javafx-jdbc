package services;

import java.util.List;

import daos.DaoFactory;
import daos.DepartmentDAO;
import entities.Department;

public class DepartmentService {
	
	private DepartmentDAO departmentDAO = DaoFactory.createDepartamentDAO();
	
	public List<Department> findAll(){
		return departmentDAO.findAll();
	}

}
