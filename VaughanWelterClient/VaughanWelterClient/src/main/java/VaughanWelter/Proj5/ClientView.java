/****************************************************

@author Dillon Vaughn, Anthony Welter
File Name: ClientView.java
COP4027	Project #: 5

****************************************************/

package VaughanWelter.Proj5;

import javafx.scene.text.Text;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientView extends Application{
	
	private static final int DIALOG_WIDTH = 500;
	private static final int WINDOW_WIDTH = 520;
	private static final int WINDOW_HEIGHT = 350;
	private static final int HEADER_SPACE = 80;
	private static final int SPACING1 = 40;
	private static final int SPACING2 = 90;
	private static final int INTIAL_VAL = 0;
	private static final String DEVICES[] = {"All", "guitar", "bass", "keyboard", "drums"};
	private static final String COMPANIES[] = {"All", "yamaha", "gibson", "fender", "roland", "alesis", "ludwig"};
	private static final String PLACES[] = {"All", "PNS", "CLT", "DFW"};
	private ComboBox<String> instrument;
	private ComboBox<String> brand;
	private ComboBox<String> local;
	private TextField priceRange;
	private double price;
	private String tool;
	private String mfr;
	private String storage;
	private StringBuffer command = new StringBuffer();
	String formattedAlert = "";
    static Socket s;
    static InputStream instream;
    static OutputStream outstream;
    static Scanner in;
    static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
	    final int SBAP_PORT = 8888;
	    try {
	    	s = new Socket("localhost", SBAP_PORT);
	    	instream = s.getInputStream();
		    outstream = s.getOutputStream();
		    in = new Scanner(instream);
		    out = new PrintWriter(outstream);
		    Application.launch(args);
	    }catch(IOException e) {
	    	System.err.println("Unable to connect to server.");
	    	System.exit(1);
	    }
	    in.close();
        instream.close();
        outstream.close();
        out.close();
	    s.close();
	}
	
	/*
	 * Starts GUI window with parameter stage for desired instrument queries and results
	 * @param Stage containing contents for GUI window
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
	    priceRange = new TextField();
	    
	    Button submit = new Button("Submit Request");
		
		submit.setOnAction((ActionEvent event) -> {
			if(priceRange.getText().isEmpty())
				price = 0.0;
			else
				price = Double.parseDouble(priceRange.getText());

			tool = instrument.getValue();
			mfr = brand.getValue();
			storage = local.getValue();
			int location = setLocation(storage);
			
			command.append("RETRIEVE " + tool + " " + mfr + " " + price + " " + location + "\n");
			
			try {
	           out.print(command);
	           out.flush();
	           String response = in.nextLine();
	           formattedAlert = formatAlert(response);
			} catch (Exception e) {
				System.out.println(e);
			} 
			
			clearStringBuffer();
    		Platform.runLater(new Runnable(){
        		@Override
        		public void run() {
        			Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Instrument Information");
                    alert.setHeaderText("Results");
                    alert.setContentText(formattedAlert);
                    alert.getDialogPane().setMinWidth(DIALOG_WIDTH);
                    alert.showAndWait();
        		}
        	});
        });
		
		instrument = new ComboBox<String>();
		instrument.getItems().addAll(DEVICES);
		instrument.getSelectionModel().select(INTIAL_VAL);
		
		brand = new ComboBox<String>();
		brand.getItems().addAll(COMPANIES);
		brand.getSelectionModel().select(INTIAL_VAL);
		
		local = new ComboBox<String>();
		local.getItems().addAll(PLACES);
		local.getSelectionModel().select(INTIAL_VAL);
		
		BorderPane panes = new BorderPane();
		
    	HBox type = new HBox(new Text("Instrument Type: "), instrument);
    	type.setMinHeight(HEADER_SPACE);
    	
    	HBox label = new HBox(new Text("Instrument Brand: "), brand);
    	label.setMinHeight(SPACING1);
    	
    	HBox cost = new HBox(new Text("Maximum Cost: "), priceRange);
    	cost.setMinHeight(SPACING2);
    	
    	HBox location = new HBox(new Text("Warehouse Location: "), local);
    	
    	HBox btn = new HBox(submit);
    	btn.setMinHeight(SPACING2);
    	
		VBox categories = new VBox();
	
		type.setAlignment(Pos.CENTER);
		label.setAlignment(Pos.CENTER);
		cost.setAlignment(Pos.CENTER);
		location.setAlignment(Pos.CENTER);
		btn.setAlignment(Pos.CENTER);
	
		categories.getChildren().addAll(type, label, cost, location, btn);
		
    	panes.setTop(categories);
    	
    	Scene scene = new Scene(panes, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Musical Instrument Lookup");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    	
	}
	
	/*
	 * Clear StringBuffer
     */
	public void clearStringBuffer() {
		command.setLength(0);
	}	
	
	/*
	 * Returns converted String warehouse location to corresponding int value
	 * @param String of warehouse location in airport code abbreviation
	 * @return Corresponding int value depending String, 0 if "ALL" locations desired 
     */
	public int setLocation(String loc) {
		// {"PNS" = 1, "CLT" = 2, "DFW" = 3, "All"};
		if (loc.contains("PNS")) {
			return 1;
		}
		if (loc.contains("CLT")) {
			return 2;
		}
		if (loc.contains("DFW")) {
			return 3;
		}
		return 0;
	}
	
	/*
	 * Formats SQL query results to be properly displayed in alert dialog box
	 * @param String of query results received by the server to be properly formatted by method 
	 * @return Formatted SQL query results to be properly displayed in the alert dialog box
     */
	public String formatAlert(String returnString) {
		StringBuffer stringBuffer = new StringBuffer();
		String returnObjects[] = returnString.split(",");
		
		for (int i = 0; i < returnObjects.length; i ++) {
			stringBuffer.append(returnObjects[i] + "\n");
		}
		return stringBuffer.toString();
	}
}