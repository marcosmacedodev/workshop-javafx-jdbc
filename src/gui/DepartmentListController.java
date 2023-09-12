package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Department;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DepartmentListController implements Initializable{

	@FXML
	private Button btNewDepartment;
	@FXML
	private TableView<Department> tbViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tbColumnIdDepartment;
	@FXML
	private TableColumn<Department, String> tbColumnNameDepartment;
	
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
