@author Dillon Vaughn, Anthony Welter
File Name: Client.java
COP4027	Project #: 5

****************************************************/

package VaughanWelter.Proj5;

import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application{
	
	private static final int WINDOW_WIDTH = 520;
	private static final int WINDOW_HEIGHT = 350;
	private static final int HEADER_SPACE = 80;
	private static final int SPACING1 = 40;
	private static final int SPACING2 = 90;
	private ComboBox<String> instrument;
	private ComboBox<String> brand;
	private ComboBox<String> local;
	private TextField priceRange;
	private double price;
	private boolean validPrice;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		priceRange = new TextField();
		Label textErrorLabel = new Label();
	    textErrorLabel.setTextFill(Color.RED);
	    
	    Button submit = new Button("Submit Request");
		
		submit.setOnAction((ActionEvent event) -> {
			try {
				price = Double.parseDouble(priceRange.getText());
				textErrorLabel.setText("");
				priceRange.setStyle(null);
				validPrice = true;
				//System.out.println(getPrice());
			}
			catch(NumberFormatException e){
				validPrice = false;
				textErrorLabel.setText("Invalid entry, please enter a value.");
				priceRange.setStyle("-fx-control-inner-background: red;");
			}
			if(validPrice) {
	    		Platform.runLater(new Runnable(){
	        		@Override
	        		public void run() {
	        			Alert alert = new Alert(AlertType.INFORMATION);
	                    alert.setTitle("Instrument Information");
	                    alert.setHeaderText("Results");
	                    alert.setContentText("data");
	                    alert.showAndWait();
	        		}
	        	});
			}
        });
		
		instrument = new ComboBox<String>();
		instrument.getItems().add("guitar");
		instrument.getItems().add("bass");
		instrument.getItems().add("keyboard");
		instrument.getItems().add("drums");
		instrument.getItems().add("All");
		
		instrument.setOnAction((ActionEvent event) -> {
    		//to be completed
        });
		
		brand = new ComboBox<String>();
		brand.getItems().add("yamaha");
		brand.getItems().add("gibson");
		brand.getItems().add("fender");
		brand.getItems().add("roland");
		brand.getItems().add("alesis");
		brand.getItems().add("ludwig");
		
		brand.setOnAction((ActionEvent event) -> {
			//to be completed
        });
		
		local = new ComboBox<String>();
		local.getItems().add("PNS");
		local.getItems().add("CLT");
		local.getItems().add("DFW");
		local.getItems().add("All");
		
		local.setOnAction((ActionEvent event) -> {
			//to be completed
        });
		
		BorderPane panes = new BorderPane();
		
    	HBox type = new HBox(new Text("Instrument Type: "), instrument);
    	type.setMinHeight(HEADER_SPACE);
    	
    	HBox label = new HBox(new Text("Instrument Brand: "), brand);
    	label.setMinHeight(SPACING1);
    	
    	HBox cost = new HBox(new Text("Maximum Cost: "), priceRange);
    	VBox costFinal = new VBox(cost, textErrorLabel);
    	costFinal.setMinHeight(SPACING2);
    	
    	HBox location = new HBox(new Text("Warehouse Location: "), local);
    	
    	HBox btn = new HBox(submit);
    	btn.setMinHeight(SPACING2);
    	
		VBox categories = new VBox();
		
		costFinal.setAlignment(Pos.CENTER);
		type.setAlignment(Pos.CENTER);
		label.setAlignment(Pos.CENTER);
		cost.setAlignment(Pos.CENTER);
		location.setAlignment(Pos.CENTER);
		btn.setAlignment(Pos.CENTER);
    	
		categories.getChildren().addAll(type, label, costFinal, location, btn);
		
    	panes.setTop(categories);
    	
    	Scene scene = new Scene(panes, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Musical Instrument Lookup");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    	
	}
	
	public double getPrice() {
		return price;
	}
}
