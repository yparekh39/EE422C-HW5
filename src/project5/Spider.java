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

public class Spider extends Critter {
	private int lastDir;
	private int age;
	private String ageAppearance;
	
	public Spider(){
		lastDir = 0;
		age = 0;
		ageAppearance = "X";
	}
	
	
	@Override
	public void doTimeStep() {
		//if a newborn, offset 2 to the right
		if(age == 0)
			run(lastDir);
		
		lastDir = (lastDir + 2) %8;
		walk(lastDir);

		
		//reproduce every 4 steps
		if(age % 20 == 0){
			Critter offspring = new Spider();
			this.reproduce(offspring, 0);
		}
		
		
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
