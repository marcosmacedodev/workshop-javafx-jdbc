package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.DepartmentService;

public class DepartmentListController implements Initializable{

	private DepartmentService service;
	@FXML
	private Button btNewDepartment;
	@FXML
	private TableView<Department> tbViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tbColumnIdDepartment;
	@FXML
	private TableColumn<Department, String> tbColumnNameDepartment;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tbColumnIdDepartment.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbColumnNameDepartment.setCellValueFactory(new PropertyValueFactory<>("name"));
	}

	public void setService(DepartmentService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> departments =  service.findAll();
		obsList = FXCollections.observableArrayList(departments);
		tbViewDepartment.setItems(obsList);
	}

}
