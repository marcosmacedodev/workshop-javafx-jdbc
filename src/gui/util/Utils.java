package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node)event.getSource()).getScene().getWindow();
	}
	
	public static boolean isParseToInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static Integer tryParseToInt(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			return null;
		}
	}

}
