package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Department;
import entities.Seller;
import exceptions.ValidationException;
import gui.listener.DataChangeListener;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.SellerService;

public class SellerFormController implements Initializable {

	private SellerService sellerService;
	
	private Seller entity;
	
	List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	@FXML
	private Label lbErrorName;
	@FXML
	private Label lbErrorEmail;
	@FXML
	private Label lbErrorBirthDate;
	@FXML
	private Label lbErrorBaseSalary;
	@FXML
	private Label lbErrorDepartment;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	@FXML
	private TextField txtFieldId;
	@FXML
	private TextField txtFieldName;
	@FXML
	private TextField txtFieldEmail;
	@FXML
	private DatePicker dtPickerBirthDate;
	@FXML
	private TextField txtFieldBaseSalary;
	@FXML
	private ComboBox<Department> cbDepartments;
	@FXML
	public void onBtSaveAction() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (sellerService == null) {
			throw new IllegalStateException("SellerService was null");
		}
		try {
			entity = getFormData();
			sellerService.saveOrUpdate(entity);
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
	private Seller getFormData() {
		ValidationException exception = new ValidationException();
		Integer id = Utils.tryParseToInt(txtFieldId.getText());
		String name = txtFieldName.getText();
		String email = txtFieldEmail.getText();
		LocalDate birthDate = dtPickerBirthDate.getValue();
		Double baseSalary = Utils.tryParseToDouble(txtFieldBaseSalary.getText());
		Department department = cbDepartments.getSelectionModel().getSelectedItem();
		if(name == null || name.trim().equals("")) {
			exception.addError("name", "Field can't be empty.");
		}
		if(email == null || email.trim().equals("")) {
			exception.addError("email", "Field can't be empty.");
		}
		if(birthDate == null) {
			exception.addError("birthDate", "Field can't be empty.");
		}
		if(baseSalary == null) {
			exception.addError("baseSalary", "Field can't be empty.");
		}
		if(department == null) {
			exception.addError("department", "Field can't be empty.");
		}
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		return new Seller(id, name, email, Date.from(birthDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), baseSalary, null);
	}
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Constraints.setTextFieldInteger(txtFieldId);
		Constraints.setTextFieldMaxLength(txtFieldName, 64);
		Constraints.setTextFieldDouble(txtFieldBaseSalary);
		Constraints.setTextFieldMaxLength(txtFieldEmail, 64);
		Utils.formatDatePicker(dtPickerBirthDate, "dd/MM/yyyy");
	}

	public void setEntity(Seller entity) {
		this.entity = entity;
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtFieldId.setText(String.valueOf(entity.getId()));
		txtFieldName.setText(entity.getName());
		txtFieldEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtFieldBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if(entity.getBirthDate() != null) {
			LocalDate localDate = LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault());
			dtPickerBirthDate.setValue(localDate);
		}
	}
	
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	public void subscribeDataChange(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("name")) {
			lbErrorName.setText(errors.get("name"));
		}
		if(fields.contains("email")) {
			lbErrorEmail.setText(errors.get("email"));
		}
		if(fields.contains("birthDate")) {
			lbErrorBirthDate.setText(errors.get("birthDate"));
		}
		if(fields.contains("baseSalary")) {
			lbErrorBaseSalary.setText(errors.get("baseSalary"));
		}
		if(fields.contains("department")) {
			lbErrorDepartment.setText(errors.get("department"));
		}
	}
}
