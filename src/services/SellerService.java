package services;

import java.util.List;

import daos.DaoFactory;
import daos.SellerDAO;
import db.DbException;
import entities.Seller;
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class SellerService {
	
	private SellerDAO sellerDAO = DaoFactory.createSellerDAO();

	public List<Seller> findAll(){
		return sellerDAO.findAll();
	}
	
	public void saveOrUpdate(Seller entity) {
		try {
			if(entity.getId() == null) {
				sellerDAO.insert(entity);
			}
			else{
				sellerDAO.update(entity);
			}
		}catch(DbException e) {
			Alerts.showAlert("Error saving entity", null, e.getMessage(), AlertType.ERROR);
		}
	}

	public void delete(Seller entity) {
		try {
			sellerDAO.deleteById(entity.getId());
		}
		catch(DbException e) {
			Alerts.showAlert("Error deleting entity", null, e.getMessage(), AlertType.ERROR);
		}
	}
}
