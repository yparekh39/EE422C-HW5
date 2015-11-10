package project5;
/* CRITTERS <MyClass.java>
 * EE422C Project 4 submission by
 * Matt Owens
 * mo8755
 * 16340
 * Yash Parekh
 * yjp246
 * 16345
 * Slip days used: 1
 * Fall 2015
 */

import java.util.List;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.LIGHTBLUE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	protected String look(int direction, boolean steps) {
		this.energy -= Params.look_energy_cost;
		int lookX = this.x_coord;
		int lookY = this.y_coord;
		int spotsAway = steps ? 2:1;
		
		for(int i = 1; i <= spotsAway; i++){
			switch (direction) {
				case 0:
					lookX = (lookX+1) % Params.world_width;
					break;
				case 1:
					lookX = (lookX+1) % Params.world_width;
					lookY = (lookY-1) % Params.world_height;
					if(lookY<0)
						lookY = Params.world_height - 1;
					break;
				case 2:
					lookY = (lookY-1) % Params.world_height;
					if(lookY<0)
						lookY = Params.world_height - 1;
					break;
				case 3:
					lookY = (lookY-1) % Params.world_height;
					if(lookY<0)
						lookY = Params.world_height - 1;
					
					lookX = (lookX-1) % Params.world_width;
					//wrap
					if(lookX<0)
						lookX = Params.world_width - 1;
					break;
				case 4:
					lookX = (lookX-1) % Params.world_width;
					//wrap
					if(lookX<0)
						lookX = Params.world_width - 1;
					break;
				case 5:
					lookX = (lookX-1) % Params.world_width;
					//wrap
					if(lookX<0)
						lookX = Params.world_width - 1;
					lookY = (lookY+1) % Params.world_height;
					break;
				case 6:
					lookY = (lookY+1) % Params.world_height;
					break;
				case 7:
					lookX = (lookX+1) % Params.world_width;
					lookY = (lookY+1) % Params.world_height;
					break;
			}
		}
		
		//search for a critter with matching coordinates
		for(Critter occupier : population){
			if(occupier.x_coord == lookX && occupier.y_coord == lookY)
				return occupier.toString();
		}
		
		return null;
	}
	
	/* rest is unchanged from Project 4 */

	private boolean hasMoved;
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	//Moves critter one spot in one direction, wraps around properly
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		
		if(hasMoved)
			return;
		switch (direction) {
			case 0:
				this.x_coord = (x_coord+1) % Params.world_width;
				break;
			case 1:
				this.x_coord = (x_coord+1) % Params.world_width;
				this.y_coord = (y_coord-1) % Params.world_height;
				if(y_coord<0)
					y_coord = Params.world_height - 1;
				break;
			case 2:
				this.y_coord = (y_coord-1) % Params.world_height;
				if(y_coord<0)
					y_coord = Params.world_height - 1;
				break;
			case 3:
				this.y_coord = (y_coord-1) % Params.world_height;
				if(y_coord<0)
					y_coord = Params.world_height - 1;
				
				this.x_coord = (x_coord-1) % Params.world_width;
				//wrap
				if(x_coord<0)
					x_coord = Params.world_width - 1;
				break;
			case 4:
				this.x_coord = (x_coord-1) % Params.world_width;
				//wrap
				if(x_coord<0)
					x_coord = Params.world_width - 1;
				break;
			case 5:
				this.x_coord = (x_coord-1) % Params.world_width;
				//wrap
				if(x_coord<0)
					x_coord = Params.world_width - 1;
				this.y_coord = (y_coord+1) % Params.world_height;
				break;
			case 6:
				this.y_coord = (y_coord+1) % Params.world_height;
				break;
			case 7:
				this.x_coord = (x_coord+1) % Params.world_width;
				this.y_coord = (y_coord+1) % Params.world_height;
				break;
		}
		hasMoved = true;
		
		/*Matt Debug Critters outside of window*/
		if(this.x_coord < 0 || this.y_coord<0)
			System.out.println("Walk outside world " + this.x_coord + " " + this.y_coord);
	}
	
	protected final void run(int direction) {
		if(hasMoved){
			this.energy -= Params.run_energy_cost;
			return;
		}
			
		walk(direction);
		hasMoved = false;
		walk(direction);
		this.energy += (2*Params.walk_energy_cost);
		this.energy -= Params.run_energy_cost;
		hasMoved = true;
		
		/*Matt Debug Critters outside of window*/
		if(this.x_coord < 0 || this.y_coord<0)
			System.out.println("Run outside world " + this.x_coord + " " + this.y_coord);
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy) {
			return;
		}
		offspring.energy = (this.energy/2);
		this.energy /= 2;
		//Determines what direction to put child (uses % to wrap around)
		switch (direction) {
			case 0:
				offspring.x_coord = (x_coord+1) % Params.world_width;
				break;
			case 1:
				offspring.x_coord = (x_coord+1) % Params.world_width;
				offspring.y_coord = (y_coord-1) % Params.world_height;
				if(offspring.y_coord<0)
					offspring.y_coord = Params.world_height - 1;
				break;
			case 2:
				offspring.y_coord = (y_coord-1) % Params.world_height;
				if(offspring.y_coord<0)
					offspring.y_coord = Params.world_height - 1;
				break;
			case 3:
				offspring.y_coord = (y_coord-1) % Params.world_height;
				offspring.x_coord = (x_coord-1) % Params.world_width;
				if(offspring.y_coord<0)
					offspring.y_coord = Params.world_height - 1;
				if(offspring.x_coord<0)
					offspring.x_coord = Params.world_width - 1;
				break;
			case 4:
				offspring.x_coord = (x_coord-1) % Params.world_width;
				if(offspring.x_coord<0)
					offspring.x_coord = Params.world_width - 1;
				break;
			case 5:
				offspring.x_coord = (x_coord-1) % Params.world_width;
				offspring.y_coord = (y_coord+1) % Params.world_height;
				if(offspring.x_coord<0)
					offspring.x_coord = Params.world_width - 1;
				break;
			case 6:
				offspring.y_coord = (y_coord+1) % Params.world_height;
				break;
			case 7:
				offspring.x_coord = (x_coord+1) % Params.world_width;
				offspring.y_coord = (y_coord+1) % Params.world_height;
				break;
		}
		babies.add(offspring);
		/*Matt Debug Critters outside of window*/
		if(offspring.x_coord < 0 || offspring.y_coord<0)
			System.out.println("Baby created outside world " + offspring.x_coord + " " + offspring.y_coord + " " + direction);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Class<?> critterClass;
		Object object;
		Constructor<?> constructor;
		try {
			critterClass = Class.forName(critter_class_name);
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (NoClassDefFoundError e) {
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			constructor = critterClass.getConstructor();
		} catch (NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			object = constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new InvalidCritterException(critter_class_name);
		}
		if (!(object instanceof Critter)) {
			throw new InvalidCritterException(critter_class_name);
		}
		Critter newCritter = (Critter) object;
		newCritter.energy = Params.start_energy;
		newCritter.x_coord = rand.nextInt(Params.world_width);
		newCritter.y_coord = rand.nextInt(Params.world_height);
		population.add(newCritter);
	}
	
	//generates list of critters of concrete subclass critter_class_name
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class<?> critterClass;
		try {
			critterClass = Class.forName(critter_class_name);
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (NoClassDefFoundError e) {
			throw new InvalidCritterException(critter_class_name);
		}
		for (Critter c : population) {
			if (critterClass.isInstance(c)) {
				result.add(c);
			}
		}
		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
		
	public static void worldTimeStep() {
		/*call doTimeStep for each critter*/
		Iterator<Critter> iterator = population.iterator();
		while(iterator.hasNext()) {
			iterator.next().doTimeStep();
		}		

		
		/*ENCOUNTER HANDLING SECOND ATTEMPT*/
		int i = 0;
		while(i < population.size()){
			Critter firstOccupier = population.get(i);
			for(int b = i+1; b < population.size(); b++){
				Critter secondOccupier = population.get(b);
				//ENCOUNTER HANDLING
				if(firstOccupier.x_coord == secondOccupier.x_coord && firstOccupier.y_coord == secondOccupier.y_coord){
					
					//firstOccupier still here and wants to fight?
					int oldX = firstOccupier.x_coord; int oldY = firstOccupier.y_coord;			
					boolean firstWantFight = firstOccupier.fight(secondOccupier.toString());					
					boolean firstStillHere = (oldX == firstOccupier.x_coord) && (oldY == firstOccupier.y_coord);
					
					//firstOccupier ran away and maybe moved into someone else's spot (move him/her back to old position)
					if(!firstStillHere){
						for(Critter current : population){
							if(firstOccupier.x_coord == current.x_coord && firstOccupier.y_coord == current.y_coord && firstOccupier != current){
								firstOccupier.x_coord = oldX;
								firstOccupier.y_coord = oldY;
								firstStillHere = true;
							}
						}
					}
						
					//secondOccupier still here and wants to fight?
					oldX = secondOccupier.x_coord; oldY = firstOccupier.y_coord;
					boolean secondWantFight = secondOccupier.fight(firstOccupier.toString());
					boolean secondStillHere = (oldX == secondOccupier.x_coord) && (oldY == secondOccupier.y_coord);
					
					//secondOccupier ran away and maybe moved into someone else's spot (move him/her back to old position)
					if(!secondStillHere){
						for(Critter current: population){
							if(secondOccupier.x_coord == current.x_coord && secondOccupier.y_coord == current.y_coord && secondOccupier != current){
								secondOccupier.x_coord = oldX;
								secondOccupier.y_coord = oldY;
								secondStillHere = true;
							}
						}
					}
					
					//both occupiers still alive after all that jazz?
					boolean bothAlive = firstOccupier.energy > 0 && secondOccupier.energy > 0;
					
					//FIGHT HANDLING
					//both critters still here and want to fight
					if(bothAlive && firstStillHere && secondStillHere){
						Critter winner, loser;
						//roll (critters that don't want to fight will always roll 0)
						int firstRoll = (firstWantFight ? 1:0) * getRandomInt(firstOccupier.energy+1);
						int secondRoll = (secondWantFight ? 1:0) * getRandomInt(secondOccupier.energy+1);
						//establish winner and loser
						if(firstRoll == secondRoll){
							winner = getRandomInt(2) == 1 ? firstOccupier:secondOccupier;//coin toss
							loser = winner == firstOccupier ? secondOccupier:firstOccupier;
						}	
						else{
							winner = firstRoll > secondRoll ? firstOccupier:secondOccupier;
							loser = winner == firstOccupier ? secondOccupier:firstOccupier;
						}
						winner.energy += (loser.energy/2);
						
						/*REMOVE LOSER DOES THIS WORK I THINK IT DOES BECAUSE IT REFERS TO THE ORIGINAL OBJECT*/
						population.remove(loser);
						if(loser == firstOccupier){
							b = population.size();//break out of inner loop because firstOccupier is gone
							i--;//reexamine position we just removed from (firstOccupier has been replaced)
						}
							
					}//END FIGHT HANDLING		
				}//END ENCOUNTER HANDLING
			}//end for loop b
			i++;
		}

		/*DEDUCT REST ENERGY*/
		for(Critter current: population)
			current.energy -= Params.rest_energy_cost;
		
		/*ADD ALGAE*/
		for(int algaeCount = 0; algaeCount < Params.refresh_algae_count; algaeCount++){
			Algae algaeToAdd = new Algae();
			algaeToAdd.setEnergy(Params.start_energy);
			algaeToAdd.setXCoord(rand.nextInt(Params.world_width));
			algaeToAdd.setYCoord(rand.nextInt(Params.world_height));
			population.add(algaeToAdd);
		}
		
		
		
		/*ADDING BABIES*/
		population.addAll(babies);
		babies.clear();
		
		/*ClEANING DEAD CRITTERS*/
		i = 0;
		while(i < population.size()){
			if(population.get(i).energy <= 0)
				population.remove(i);
			else
				i++;
		}
		

		
		
		/*RESET MOVE FLAG*/
		for(Critter current: population)
			current.hasMoved = false;
		
			
			
			
			
	}//end of worldTimeStep
	
	/**
	 * NEW DISPLAY WORLD FUNCTION
	 */
	public static void displayWorld(){
		List<Shape> crittersAsShapes = new ArrayList<Shape>();
			
		Iterator<Critter> iterator = population.iterator();
		while(iterator.hasNext()) {
			Critter c = iterator.next();
			Shape s;
			if(c.x_coord < 0 || c.y_coord < 0)
				System.out.println("coord error " + c.x_coord + " " + c.y_coord);
			int xGUI = c.x_coord * (Main.worldWidthGUI/Params.world_width);
			int yGUI = c.y_coord * (Main.worldHeightGUI/Params.world_height);
			int radiusGUI = Main.worldWidthGUI/Params.world_width/2;
			switch (c.viewShape()){
				case SQUARE: 
					s = new Rectangle(xGUI, yGUI, Main.worldWidthGUI/Params.world_width , Main.worldHeightGUI/Params.world_height);
					break;
				case TRIANGLE: 
					//created in bottom left, top, bottom right order
					s = new Polygon(xGUI, yGUI + 2*radiusGUI, xGUI + radiusGUI, yGUI, xGUI + 2*radiusGUI, yGUI + 2*radiusGUI);
					break;
				case DIAMOND: 
					//created left, top, right, bottom
					s = new Polygon(xGUI, yGUI + radiusGUI, xGUI + radiusGUI, yGUI, xGUI + 2*radiusGUI, yGUI + radiusGUI, xGUI + radiusGUI, yGUI + 2*radiusGUI);
					break;
				case STAR:
					s = new Polygon(xGUI, 					yGUI + .8*radiusGUI,
									xGUI + .7*radiusGUI, 	yGUI + .8*radiusGUI, 
									xGUI + radiusGUI, 		yGUI, 
									xGUI + 1.3*radiusGUI,	yGUI + .8*radiusGUI,
									xGUI + 2*radiusGUI, 	yGUI + .8*radiusGUI,
									xGUI + 1.4*radiusGUI, 	yGUI + 1.3*radiusGUI,
									xGUI + 1.6*radiusGUI, 	yGUI + 2*radiusGUI,
									xGUI + radiusGUI, 		yGUI + 1.6*radiusGUI,
									xGUI + .4*radiusGUI, 	yGUI + 2*radiusGUI,
									xGUI + .6*radiusGUI,	yGUI + 1.3*radiusGUI);
					break;
				default:
					s = new Circle(xGUI + radiusGUI, yGUI + radiusGUI, radiusGUI);
					break;
			
			}
			s.setFill(c.viewColor());
			s.setStroke(c.viewOutlineColor());
			crittersAsShapes.add(s);
		}
		
		
		Main.displayWorldGUI((ArrayList)crittersAsShapes);
				
	}
	
	public static void clear() {
		population.clear();
	}
	/**
	 * OLD DISPLAY WORLD FUNCTION
	 *//*
	public static void displayWorld() {
		//Builds outer grid
		char[][] world = new char[Params.world_width+2][Params.world_height+2];
		for (int i = 0; i < (Params.world_width+2); i++) {
			for (int j = 0; j < (Params.world_height+2); j++) {
				if ((i == 0 && j == 0) || (i == (Params.world_width+1) && j == 0) || (i == 0 && j == (Params.world_height+1)) || (i == (Params.world_width+1) && j == (Params.world_height+1))) {
					world[i][j] = '+';
				}
				else if (i == 0) {
					world[i][j] = '-';
				}
				else if (i == (Params.world_width+1)){
					world[i][j] = '-';
				}
				else if (j == 0) {
					world[i][j] = '|';
				}
				else if (j == (Params.world_height+1)) {
					world[i][j] = '|';
				}
				else {
					world[i][j] = ' ';
				}
			}
		}
		//Places critters into world
		Iterator<Critter> iterator = population.iterator();
		while(iterator.hasNext()) {
			Critter c = iterator.next();
			world[c.x_coord+1][c.y_coord+1] = c.toString().charAt(0);
		}
		//Prints world
		for (int i = 0; i < (Params.world_width+2); i++){
			for (int j = 0; j < (Params.world_height+2); j++) {
				System.out.print(world[i][j]);
			}
			System.out.println();
		}
	}*/
}
