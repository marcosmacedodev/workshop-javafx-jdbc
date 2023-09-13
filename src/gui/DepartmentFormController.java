package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Department;
import exceptions.ValidationException;
import gui.listener.DataChangeListener;
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
	
	List<DataChangeListener> dataChangeListeners = new ArrayList<>();
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
		try {
			entity = getFormData();
			departmentService.saveOrUpdate(entity);
			notifyDataChangeListener();
			updateFormData();
		}catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		//Utils.currentStage(event).close();
	}
	private void notifyDataChangeListener() {
		
		for(DataChangeListener listener: dataChangeListeners) {
			listener.onDataChange();
		}
		
	}
	private Department getFormData() {
		ValidationException exception = new ValidationException();
		Integer id = Utils.tryParseToInt(txtFieldId.getText());
		String name = txtFieldName.getText();
		if(name == null || name.trim().equals("")) {
			exception.addError("name", "Field can't be empty.");
		}
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
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
	
	public void subscribeDataChange(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("name")) {
			lbError.setText(errors.get("name"));
		}
	}
}
