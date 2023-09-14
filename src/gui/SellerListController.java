package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entities.Seller;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.SellerService;

public class SellerListController implements Initializable, DataChangeListener{

	private SellerService departmentService;
	@FXML
	private Button btNewSeller;
	@FXML
	private TableView<Seller> tbViewSeller;
	@FXML
	private TableColumn<Seller, Integer> tbColumnIdSeller;
	@FXML
	private TableColumn<Seller, String> tbColumnNameSeller;
	@FXML
	private TableColumn<Seller, String> tbColumnEmailSeller;
	@FXML
	private TableColumn<Seller, Date> tbColumnBirthDateSeller;
	@FXML
	private TableColumn<Seller, Double> tbColumnBaseSalarySeller;
	
	
	private ObservableList<Seller> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Seller entity = new Seller();
		createDialogForm(entity, "/gui/SellerForm.fxml", Utils.currentStage(event));
	}
	
	@FXML
	public void onBtEditAction(ActionEvent event) {
		Seller entity = tbViewSeller.getSelectionModel().getSelectedItem();
		if(entity == null) {
			Alerts.showAlert("Aviso", null, "Selecione algum item", AlertType.WARNING);
		}
		else {
			createDialogForm(entity, "/gui/SellerForm.fxml", Utils.currentStage(event));
		}
	}
	
	@FXML
	public void onBtDeleteAction(ActionEvent event) {
		Seller entity = tbViewSeller.getSelectionModel().getSelectedItem();
		if(entity == null) {
			Alerts.showAlert("Aviso", null, "Selecione algum item", AlertType.WARNING);
		}
		else {
			ButtonType buttonType = Alerts.showAlert(null, null, "Remover o item id " + entity.getId() + "?", AlertType.CONFIRMATION);
			if(buttonType == ButtonType.OK) {
				departmentService.delete(entity);
				updateTableView();
			}else if(buttonType == ButtonType.CANCEL) {
				Alerts.showAlert(null, null, "Ação cancelada pelo usuário", AlertType.INFORMATION);
			}
		}
	}
	
	@FXML
	public void onBtUpAction(ActionEvent event) {
	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tbColumnIdSeller.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbColumnNameSeller.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbColumnEmailSeller.setCellValueFactory(new PropertyValueFactory<>("email"));
		tbColumnBirthDateSeller.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tbColumnBirthDateSeller, "dd/MM/yyyy");
		tbColumnBaseSalarySeller.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tbColumnBaseSalarySeller, 2);
	}

	public void setSellerService(SellerService departmentService) {
		this.departmentService = departmentService;
	}
	
	public void updateTableView() {
		if(departmentService == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Seller> departments =  departmentService.findAll();
		obsList = FXCollections.observableArrayList(departments);
		tbViewSeller.setItems(obsList);
	}

	private void createDialogForm(Seller entity, String absolutePath, Stage parentStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutePath));
//			Pane pane = loader.load();
//			
//			SellerFormController controller = loader.getController();
//			controller.setEntity(entity);
//			controller.setSellerService(departmentService);
//			controller.subscribeDataChange(this);
//			controller.updateFormData();
//			
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Entrar com novo departamento");
//			dialogStage.setScene(new Scene(pane));
//			dialogStage.setResizable(false);
//			dialogStage.initOwner(parentStage);
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.showAndWait();
//		} catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
//		}
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}
}
