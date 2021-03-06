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

public class Longhorn extends Critter {
	private String identity;
	private int lastDir;
	private int age;
	
	
	public Longhorn(){
		int isBevo = getRandomInt(10);
		identity = "";
		if(isBevo == 9)
			identity = "I am Bevo";
		lastDir = 0;
		age = 0;
	}
	
	//Graze
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		if(lastDir == 0)
			lastDir = 4;
		else
			lastDir = 0;
		
		walk(lastDir);
		//there can only be one! or a few...
		if(!identity.equals("I am Bevo")){
			if(age == 20 || age == 40){
				Longhorn offspring = new Longhorn();
				//reproduce(offspring, 0);
			}
			
		}
		
		age++;

	}

	//Bevo never runs from a fight
	@Override
	public boolean fight(String oponent) {
		if(identity.equals("I am Bevo")){
			return true;
		} 
		else{
			for(int i = 0; i < 8; i++){
				if(look(i, true) != null && look(i, true).equals("B")){
					return true;
				}
			}
		}
			
		return false;
		
	}
	
	public String toString(){
		if(identity.equals("I am Bevo"))
			return "B";
		else
			return "L";
	}
	
	public static void runStats(java.util.List<Critter> longhorns){
		System.out.println("There are " + longhorns.size() + " longhorns.");
		
		for(Critter c: longhorns){
			Longhorn current = (Longhorn) c;
			if(current.identity.equals("I am Bevo"))
			System.out.println("At least one of them is Bevo-worthy!");
		}		
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}
	
	public javafx.scene.paint.Color viewColor() { 
		if(identity.equals("I am Bevo")){
		   return javafx.scene.paint.Color.ORANGE;
		}
		return javafx.scene.paint.Color.BROWN;
	}


}
