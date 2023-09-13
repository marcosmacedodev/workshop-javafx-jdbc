package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Department;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private DepartmentService departmentService;
	
	private Department entity;
	@FXML
	private Label lbError;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	@FXML
	private TextField txtFieldId;
	@FXML
	private TextField txtFieldName;
	@FXML
	public void onBtSaveAction() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (departmentService == null) {
			throw new IllegalStateException("DepartmentService was null");
		}
		entity = getFormData();
		departmentService.saveOrUpdate(entity);
		updateFormData();
		//Utils.currentStage(event).close();
	}
	private Department getFormData() {
		// TODO Auto-generated method stub
		Integer id = Utils.tryParseToInt(txtFieldId.getText());
		String name = txtFieldName.getText();
		return new Department(id, name);
	}
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldInteger(txtFieldId);
		Constraints.setTextFieldMaxLength(txtFieldName, 32);
	}

	public void setEntity(Department entity) {
		this.entity = entity;
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtFieldId.setText(String.valueOf(entity.getId()));
		txtFieldName.setText(entity.getName());
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
}
