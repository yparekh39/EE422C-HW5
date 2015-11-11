package project5;
/* CRITTERS <MyClass.java>
 * EE422C Project 5 submission by
 * Matt Owens
 * mo8755
 * 16340
 * Yash Parekh
 * yjp246
 * 16345
 * Slip days used: 1
 * Fall 2015
 */
	

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	static Group world = new Group();
	static Set<String> critterTypes = new HashSet<String>();
	static GridPane grid = new GridPane();
	static BorderPane window = new BorderPane();
	static int row = 0;
	static int worldWidthGUI = 700;
	static int worldHeightGUI = 700;
	static Shape s;
	static boolean stopTimers = true;
	static boolean stopFast = false;
	static boolean stopSlow = false;
	Animator fast_animator;
	Animator slow_animator;


	@Override
	public void start(Stage primaryStage) {
		try {
			Stage stage = new Stage();
			stage.setTitle("Change World Parameters");
			
			stage.setAlwaysOnTop(true);
			GridPane settings = new GridPane();
			settings.setAlignment(Pos.TOP_CENTER);
			settings.setHgap(5);
			settings.setVgap(5);
			settings.setPadding(new Insets(25, 25, 25, 25));
			
			Label widthName = new Label("World Width:");
			settings.add(widthName, 0, 0);
			TextField widthField = new TextField();
			settings.add(widthField, 2, 0);
			
			Label heightName = new Label("World Height:");
			settings.add(heightName, 0, 2);
			TextField heightField = new TextField();
			settings.add(heightField, 2, 2);
			
			Label refreshName = new Label("Algae Refresh Rate:");
			settings.add(refreshName, 0, 4);
			TextField refreshField = new TextField();
			settings.add(refreshField, 2, 4);
			
			Button settingConfirm = new Button("Update");
			HBox hbConfirm = new HBox(10);
			hbConfirm.setAlignment(Pos.BOTTOM_RIGHT);
			hbConfirm.getChildren().add(settingConfirm);
			settings.add(hbConfirm, 0, 10);
			
			Button cancel = new Button("Default");
			HBox hbCancel = new HBox(10);
			hbCancel.setAlignment(Pos.BOTTOM_LEFT);
			hbCancel.getChildren().add(cancel);
			settings.add(hbCancel, 1, 10);
			
			Scene settingScene = new Scene(settings, 500, 200);
			stage.setScene(settingScene);
			stage.show();
						
			settingConfirm.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					String width = widthField.getText();
					String height = heightField.getText();
					String refresh = refreshField.getText();
					if(!width.isEmpty()) {
						Params.world_width = Integer.parseInt(width);
					}
					if(!height.isEmpty()) {
						Params.world_height = Integer.parseInt(height);
					}
					if(!refresh.isEmpty()) {
						Params.refresh_algae_count = Integer.parseInt(refresh);
					}
					stage.close();
				}
				
			});
			
			cancel.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stage.close();
				}
			});
			
		
			primaryStage.setTitle("Java FX Critters");
			s = new Rectangle(worldWidthGUI, worldHeightGUI);
			s.setFill(Color.LIGHTBLUE);
			s.setStroke(Color.BLACK);
			s.setStrokeDashOffset(10);
			s.setStrokeWidth(2);
			world.getChildren().add(s);
			
			// Add a grid pane to lay out the buttons and text fields.


			window.setLeft(grid);
			window.setCenter(world);
			grid.setAlignment(Pos.CENTER_LEFT);
			grid.setHgap(5);
			grid.setVgap(5);
			grid.setPrefWidth(400);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			
			
			// Add Field for Critter type.
			Label critName = new Label("Critter Name:");
			grid.add(critName, 0, row);
//			TextField critNameField = new TextField();
//			//row++;
//			grid.add(critNameField, 1, row);

			ComboBox critNameField = new ComboBox();
			ObservableList<String> critterList = FXCollections.observableArrayList(
					"Algae", 
					"Craig",
					"Female",
					"Male",
					"Longhorn",
					"Spider",
					"AlgaephobicCritter",
					"TragicCritter");
			critNameField.setItems(critterList);
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
			Scene scene = new Scene(window, 1400, 800);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Label timeStep = new Label("# of Time Steps:");
			row++;
			grid.add(timeStep, 0, row);
			TextField timeField = new TextField();
			grid.add(timeField, 1, row);
			
			Button stepBtn = new Button("Step");
			HBox hbStepBtn = new HBox(10);
			hbStepBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbStepBtn.getChildren().add(stepBtn);
			row += 2;
			grid.add(hbStepBtn, 1, row);
			
			Button quitBtn = new Button("Quit");
			quitBtn.setPrefWidth(70);
			HBox hbQuitBtn = new HBox(10);
			hbQuitBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbQuitBtn.getChildren().add(quitBtn);
			row += 8;
			grid.add(hbQuitBtn, 0, row);
			
			Button resetButton = new Button("Clear");
			resetButton.setPrefWidth(70);
			HBox hbResetBtn = new HBox(10);
			hbResetBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbResetBtn.getChildren().add(resetButton);
			grid.add(hbResetBtn, 1, row);
			
			Button playSlowButton = new Button("Slow");
			playSlowButton.setPrefWidth(70);
			HBox hbPSBtn = new HBox(10);
			hbPSBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbPSBtn.getChildren().add(playSlowButton);
			grid.add(hbPSBtn, 2, row+5);
			
			Button playFastButton = new Button("Fast");
			playFastButton.setPrefWidth(70);
			HBox hbPFBtn = new HBox(10);
			hbPFBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbPFBtn.getChildren().add(playFastButton);
			grid.add(hbPFBtn, 2, row+6);
			
			Button stopAnmtButton = new Button("Pause");
			stopAnmtButton.setPrefWidth(70);
			HBox hbStpAnmtBtn = new HBox(10);
			hbStpAnmtBtn.setAlignment(Pos.BOTTOM_LEFT);
			hbStpAnmtBtn.getChildren().add(stopAnmtButton);
			grid.add(hbStpAnmtBtn, 2, row+7);
			
			//grid.setGridLinesVisible(true);
			
			updateStats();
			
			// Action when add critters button is pressed. Call makeCritter.
			// Uses something called an anonymous class of type EventHandler<ActionEvent>, which is a class that is
			// defined inline, in the curly braces.
			addBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(stopTimers){
						String name = (String) critNameField.getValue();
						String numString = critNumField.getText();
						//TODO: Call Critter.makeCritter as many times as requested.
						String critterClassName = "project5."+ name;
						int newCount = 1;
						if(!numString.isEmpty()){ newCount = Integer.parseInt(numString); }
						try{
							for (int i = 0; i < newCount; i++) {
								Critter.makeCritter(critterClassName);
							}
							critterTypes.add(name);
							actionTarget.setFill(Color.FIREBRICK);
							actionTarget.setText("Added " + Integer.toString(newCount) + " " + name + " Critters.");	
							Critter.displayWorld(); // or Whatever
							updateStats();
							
						} catch(InvalidCritterException e) {
							actionTarget.setFill(Color.FIREBRICK);
							actionTarget.setText("Invalid Critter!");
						}
					}
				}			
			});
			stepBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String timeSteps = timeField.getText();
					int timeStepsCount = 1;
					if(!timeSteps.isEmpty()) { timeStepsCount = Integer.parseInt(timeSteps); } 
					for(int i = 0; i < timeStepsCount; i++){
						Critter.worldTimeStep();
					}
					critterTypes.add("Algae");
					Critter.displayWorld(); // or whatever
					updateStats();
				}
			});
			quitBtn.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					Platform.exit();
				}
				
			});
			
			resetButton.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Critter.clear();
					Critter.displayWorld();
					updateStats();
				}
				
			});
			//Play Fast Button
			playFastButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopTimers = false;
					stopSlow = true;
					stopFast = false;
					fast_animator = new Animator(100, "fast");
				}
				
			});
			//Play Slow
			playSlowButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopTimers = false;
					stopFast = true;
					stopSlow = false;
					slow_animator = new Animator(300, "slow");
				}
				
			});
			//Pause
			stopAnmtButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					stopTimers = true;
					
				}
				
			});

		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void displayWorldGUI(ArrayList<Shape> population){
			population.add(0, s);
			world.getChildren().setAll(population);
	}
	
	public static void updateStats() {
		GridPane stats = new GridPane();
		int statsRow = 0;
		Text title = new Text("Stats:");
		title.setUnderline(true);
		title.setFill(Color.FIREBRICK);
		stats.add(title, 0, statsRow);
		statsRow++;
		
		ComboBox critNameField = new ComboBox();
		ObservableList<String> critterList = FXCollections.observableArrayList(
				"Algae", 
				"Craig",
				"Female",
				"Male",
				"Longhorn",
				"Spider",
				"AlgaephobicCritter",
				"TragicCritter");
		critNameField.setItems(critterList);
		stats.add(critNameField, 0, statsRow);
		statsRow++;
		Button statBtn = new Button("Get Stats");
		HBox hbStatBtn = new HBox(10);
		hbStatBtn.setAlignment(Pos.CENTER_LEFT);
		hbStatBtn.getChildren().add(statBtn);
		row += 2;
		stats.add(hbStatBtn, 0, statsRow);
		
		statsRow++;
		Iterator<String> iterator = critterTypes.iterator();
		while(iterator.hasNext()) {
			String stat;
			int critterCount = 0;
			String critter = iterator.next();
			try {
				critterCount = Critter.getInstances("project5." + critter).size();
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
			stat = critterCount + " " + critter + " Critters";
			Text statText = new Text("  " + stat);
			stats.add(statText, 0, statsRow);
			statsRow++;
		}
		stats.setHgap(5);
		stats.setVgap(5);
		//stats.setGridLinesVisible(true);
		stats.setAlignment(Pos.CENTER_LEFT);
		stats.setPadding(new Insets(25, 25, 25, 25));
		stats.setPrefWidth(300);
		window.setRight(stats);
		
		statBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				critterTypes.add((String) critNameField.getValue());
				updateStats();
			}
			
		});
	}
	
	//Handles animation
	class Animator {
	    Timer timer;
	    String speed;

	    public Animator(long milliseconds, String label) {
	        timer = new Timer();
	        speed = label;
	        timer.schedule(new AnimateTask(), 0, milliseconds);
		}
	    
	    class AnimateTask extends TimerTask {
	        public void run() {
	        	Platform.runLater(new Runnable() {
	        		public void run() {
	        			critterTypes.add("Algae");
	        			if(stopTimers)
	        				timer.cancel();
	        			else if(speed.equals("fast") && stopFast)
	        				timer.cancel();
	        			else if(speed.equals("slow") && stopSlow)
	        				timer.cancel();
	        			else{
	        				Critter.worldTimeStep();
		                    Critter.displayWorld();
		                    updateStats();
	        			}
	        		}
	        	});

	        }
	    }

	}

}




