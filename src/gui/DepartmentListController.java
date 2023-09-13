package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Department;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener{

	private DepartmentService departmentService;
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
	public void onBtNewAction(ActionEvent event) {
		Department entity = new Department();
		this.createDialogForm(entity, "/gui/DepartmentForm.fxml", Utils.currentStage(event));
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tbColumnIdDepartment.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbColumnNameDepartment.setCellValueFactory(new PropertyValueFactory<>("name"));
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public void updateTableView() {
		if(departmentService == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> departments =  departmentService.findAll();
		obsList = FXCollections.observableArrayList(departments);
		tbViewDepartment.setItems(obsList);
	}

	private void createDialogForm(Department entity, String absolutePath, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutePath));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();
			controller.setEntity(entity);
			controller.setDepartmentService(departmentService);
			controller.subscribeDataChange(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entrar com novo departamento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}
}
