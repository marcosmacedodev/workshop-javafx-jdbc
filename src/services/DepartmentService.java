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
		try {
			return departmentDAO.findAll();
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving entity", null, e.getMessage(), AlertType.ERROR);
			return null;
		}
	}
	
	public void saveOrUpdate(Department entity) {
		try {
			if(entity.getId() == null) {
				departmentDAO.insert(entity);
			}
			else{
				departmentDAO.update(entity);
			}
		}catch(DbException e) {
			Alerts.showAlert("Error saving entity", null, e.getMessage(), AlertType.ERROR);
		}
	}

	public void delete(Department entity) {
		try {
			departmentDAO.deleteById(entity.getId());
		}
		catch(DbException e) {
			Alerts.showAlert("Error deleting entity", null, e.getMessage(), AlertType.ERROR);
		}
	}
}
