package project5;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setTitle("Java FX Critters");
			Group world = new Group();
			Shape s = new Rectangle(800, 800);
			s.setFill(Color.LIGHTBLUE);
			s.setStroke(Color.BLACK);
			s.setStrokeDashOffset(10);
			s.setStrokeWidth(2);
			world.getChildren().add(s);
			// Add a grid pane to lay out the buttons and text fields.
			GridPane grid = new GridPane();
			BorderPane window = new BorderPane();
			window.setLeft(grid);
			window.setCenter(world);
			grid.setAlignment(Pos.CENTER_LEFT);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			int row = 0;
			
			// Add Field for Critter type.
			Label critName = new Label("Critter Name (e.g. Algae):");
			grid.add(critName, 0, row);
			TextField critNameField = new TextField();
			//row++;
			grid.add(critNameField, 1, row);
			
			// Add Field for No. of Critters
			Label numCrits = new Label("No of critters:");
			row++;
			grid.add(numCrits, 0, row);
			TextField critNumField = new TextField();
			//row++;
			grid.add(critNumField, 1, row);
			
			// Add Button to add Critters.
			Button addBtn = new Button("Add critters");
			HBox hbAddBtn = new HBox(10);
			hbAddBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbAddBtn.getChildren().add(addBtn);
			row += 2;
			grid.add(hbAddBtn, 1, row);
			
			// Action when Add Critters Button is pressed.
			final Text actionTarget = new Text();
			row += 2;
			grid.add(actionTarget, 1, row);
			Scene scene = new Scene(window, 2000, 1000);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Label timeStep = new Label("Time Step:");
			grid.add(timeStep, 0, row);
			row++;
			
			// Action when add critters button is pressed. Call makeCritter.
			// Uses something called an anonymous class of type EventHandler<ActionEvent>, which is a class that is
			// defined inline, in the curly braces.
			addBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String name = critNameField.getText();
					String numString = critNumField.getText();
					//TODO: Call Critter.makeCritter as many times as requested.		
					actionTarget.setFill(Color.FIREBRICK);
					actionTarget.setText("Added " + numString + " " + name + " Critters.");	
					//Critter.displayWorld(); // Optional
				}			
			});
			

		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}