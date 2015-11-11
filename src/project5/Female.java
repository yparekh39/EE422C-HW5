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

public class Female extends Critter {

	public String toString() { return "F"; }
	
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		walk(Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		Critter child;
		if (Critter.getRandomInt(2) == 0) { child = new Male(); }
		else { child = new Female(); }
		
		if (oponent.equals("M")) { //Reproduce if you encounter a male
			reproduce(child, Critter.getRandomInt(8));
		}
		return true;
	}
	
	public static void runStats(java.util.List<Critter> females) {
		System.out.println("There are " + females.size() + " females alive.");
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.CIRCLE;
	}
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.PINK; }


}
