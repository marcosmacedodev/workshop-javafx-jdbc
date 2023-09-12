package services;

import java.util.ArrayList;
import java.util.List;

import entities.Department;

public class DepartmentService {
	
	public List<Department> findAll(){
		List<Department> departments = new ArrayList<Department>();
		departments.add(new Department(1, "Books"));
		departments.add(new Department(2, "Computers"));
		departments.add(new Department(1, "Electronics"));
		return departments;
	}

}
