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

public class Spider extends Critter {
	private int lastDir;
	private int age;
	private int webLength;
	private int webLengthWalked;
	private int webPerimeterWalked;
	private boolean onFirstLeg;
	private String ageAppearance;
	
	public Spider(){
		lastDir = 0;
		age = 0;
		webLength = 1;
		webLengthWalked = 0;
		webPerimeterWalked = 0;
		onFirstLeg = true;
		ageAppearance = "X";
	}
	
	
	@Override
	public void doTimeStep() {
		//GOAL: WALK IN INCREASING SQUARE SPIRAL PATTERN
		String troubleAhead = null;
		
		//if entire square walked, increase length of square legs and go again
		if(webPerimeterWalked == webLength * 4){
			webLength++;
			webPerimeterWalked = 0;
			webLengthWalked = 0;
			onFirstLeg = true;
			lastDir = (lastDir + 2) % 8;
		}
			
		//if length of square leg has been walked in current direction, change direction
		if(webLengthWalked == webLength){
			lastDir = (lastDir + 2) % 8;
			webLengthWalked = 0;
		}
		//energy high enough to fight whatever is ahead?
		if(this.getEnergy() < 10)
			troubleAhead = this.look(lastDir, false);
		
		//only walk if it's safe and not low energy
		if(troubleAhead == null){
			//walk, make note that we've walked one more unit of the leg
			walk(lastDir);
			webLengthWalked++;
			webPerimeterWalked++;
		}
		
		if(webLength % 3 == 0 && onFirstLeg){
			Spider offspring = new Spider();
			reproduce(offspring, lastDir + 1);
		}
		
		onFirstLeg = false;
		
		age++;
	}

	//Gotta defend the "web"
	@Override
	public boolean fight(String oponent) {
		return true;
	}
	
	public String toString(){
		return ageAppearance;
	}
	
	public static void runStats(java.util.List<Critter> spiders){
		System.out.println("There are " + spiders.size() + " spiders.");
		int ageCount = 0;
		for(Critter c: spiders){
			Spider current = (Spider) c;
			if(current.age >= 20)
				ageCount++;
		}
		
		System.out.println("" + ageCount + " spiders are old and " + (spiders.size() - ageCount) + " are young." );
	}


	@Override
	public CritterShape viewShape(){ return CritterShape.STAR; }
	
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.BLACK; }
	

}
