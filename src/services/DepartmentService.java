package services;

import java.util.List;

import daos.DaoFactory;
import daos.DepartmentDAO;
import db.DbException;
import entities.Department;
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class DepartmentService {
	
	private DepartmentDAO departmentDAO = DaoFactory.createDepartamentDAO();
	
	public List<Department> findAll(){
		return departmentDAO.findAll();
	}
	
	public void saveOrUpdate(Department entity) {
		try {
			if(entity.getId() == null) {
				departmentDAO.insert(entity);
				return;
			}
			Department dep = departmentDAO.findById(entity.getId());
			if(dep != null) {
				dep.setName(entity.getName());
				departmentDAO.update(dep);
			}
		}catch(DbException e) {
			Alerts.showAlert("Error saving entity", null, e.getMessage(), AlertType.ERROR);
		}
	}

}
