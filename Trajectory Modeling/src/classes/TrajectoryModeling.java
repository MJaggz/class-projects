package classes;
import java.util.Scanner;
/**
 * In this project building upon the lab that was due last week, this code will prompt a user for a distance
 * and calculate the best angle and speed that the object travled based upon the distance. This code will also 
 * show the distance traveled and by how much the target was missed by.
 * 
 * @author Mohamed Jama
 * @version 10/13/23
 **/

public class TrajectoryModeling {
	public static void main(String[] args) {
	      Scanner keyboard = new Scanner(System.in);

	        System.out.println("Enter a distance to target: "); //prompt the user to enter a distance.
	        double dis = keyboard.nextDouble();

	        double bestAngle = 0.0;
	        double bestSpeed = 0.0;
	        double bestDistance = 0.0;

	        for (double speed = 1.0; speed <= 30.0; speed += 1.0) {
	            for (double angle = 25.0; angle <= 90.0; angle += 5.0) {
	                double radians = Math.toRadians(angle);
	                double sine = Math.sin(radians);
	                double cosine = Math.cos(radians);
	                double g = 9.8;
	                double totalDistance = (2.0 * speed * sine) / g;
	                double distance = speed * totalDistance * cosine;

	                if (Math.abs(distance - dis) < Math.abs(bestDistance - dis)) {
	                    bestAngle = angle;
	                    bestSpeed = speed;
	                    bestDistance = distance;
	                }
	            }
	        }

	         System.out.format("Best angle: %.2f", bestAngle); //print out the best angle based on the distance.//
	         System.out.println();
	         System.out.format("Best speed: %.2f", bestSpeed); //print out the best speed based on the distance.//
	         System.out.println();
	         System.out.format("Distance travelled: %.2f", bestDistance); //print out the distance traveled.//
	         System.out.println();
	         System.out.format("Missed the target center by: %.2f", Math.abs(bestDistance - dis)); //print out how
	         //much the target center was missed by.//
	         System.out.println();

	        keyboard.close();
	    }
	

}
