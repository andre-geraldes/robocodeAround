package sample;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Rock - a robot by (your name here)
 */

public class Rock extends Robot
{
	private double angulo; 
	private double maxWidth;
	private double maxHeight;	
	private double valor;
	public void run() {
		maxWidth = getBattleFieldWidth();
		maxHeight = getBattleFieldHeight();
		angulo = getHeading();
		turnLeft(angulo);
		System.out.println("x - " + getX() + " y - " + getY());

		//se bater o rock fica no sitio errado
		if(getY() < 200){
			valor = 200-getY();
			System.out.println("avançar y " + valor);
			ahead(valor);}
		else if(getY() > (maxHeight - 200)){
			valor = maxHeight - getY();
			System.out.println("para tras y " + valor);			
			back(valor);}
        turnLeft(90);
		if(getX() < 200){
			valor = 200-getX();			
			System.out.println("avançar x " + valor);
		    back(valor);
		}
		else if(getX() > (maxWidth - 200)){
			valor = maxWidth - getX();
			System.out.println("para tras x " + valor);
		    ahead(valor);	
		}
	
		System.out.println("x = " + getX() + " |y= " + getY());
		turnRight(90);
		turnRight(angulo);
	}

}
