package sample;
import robocode.*;
import java.util.ArrayList;
import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;	
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Desviador - a robot by Geraldes e Cardoso
 */
public class Desviador extends AdvancedRobot {
	public ArrayList<String> robots = new ArrayList<String>();
	public double distance = 0.0;
	public double distanceRobot = 0.0;
	public double angulo;
  	public double lastX = 0.0;
  	public double lastY = 0.0;
	public boolean scanned = true;
	public boolean acabou = false;
	public int i = 0;
	public boolean reached;
	

  	public void run() {
		setAllColors(Color.BLACK);
		
    	lastX = getX();
    	lastY = getY();
		
		goTo();	
		distance = 0.0;
		scanned = false; //para nao dar scan em falso
		
		while(i<3){
			initialScan();
			i++;
		}
		
		goTo();
		acabou = true;
		System.out.println("Distancia: "+distance);
		initialScan();
  	}
  	
	private void goTo(){
		// go to 0,0
		while(getX() != 18 || getY() != 18){
			turnLeft(getHeading());
			double alpha = Math.toDegrees(Math.atan(getX()/getY()));
			turnLeft(180 - alpha);
			ahead(10000);
			
			if(alpha > 45) {
				ahead(lastX);
			}
			else {
				ahead(lastY);
			}
		}
	}
	
	public void initialScan(){
		boolean finalizado = false;
		while(!finalizado){
			if(!scanned){	
				while(!scanned){
					turnRight(4);
				}
			} else {
		    	scanned = false;
				finalizado = true;
			}
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent event){
		if(!scanned && !(compara(event.getName()))){		
			scanned = true;	
			//stop();
			
			if(robots.size()>=1){
				//voltar a posicao
				double h = getHeading() - angulo;
				turnLeft(h);
				//rodar 
				/*
				double ang = 0; 
				double move = h/10;
				while(ang < h){
					ahead(10);
					turnRight(move);
					ang += move;
				}
				*/
				double total = 300/(360/h);
				/*
				while(move <= total){
					
					turnRight(h/30);
					ahead(total/30);
					move += total/30;
				}
				*/
				setTurnRight(h-7);
				ahead(total);
				execute();
				
				distanceRobot = Math.sqrt(Math.pow(60,2) + Math.pow(event.getDistance(),2));
				ahead(distanceRobot);
			}
			else {
				distanceRobot = Math.sqrt(Math.pow(60,2) + Math.pow(event.getDistance(),2));
				angulo = Math.toDegrees(Math.atan(60/event.getDistance()));
				
				turnLeft(angulo);
				ahead(distanceRobot + 30);
			}
			
			robots.add(event.getName());
			//Guardar o angulo para onde esta virado
			angulo = getHeading();
		}
		
		if(acabou){
			scanned = true;	
			int o = getOthers();
			int n = o;
			while(o > n-1) {
				o = getOthers();
				fire(3);
			}
		}
	}
	
	/*
	 * Comparação dos veiculos
	 */
	
	public boolean compara(String nome){
		boolean flag = false;
		for(String value : robots){
    		if(nome.equals(value))
				flag = true;
		}
		return flag;
	}
	
	public void onHitRobot(HitRobotEvent event) {
		back(10);
        turnLeft(90);
		ahead(30);
		turnRight(90);
		ahead(30);
		
	}
	
	public void onHitWall(HitWallEvent event) {
	   	double ang = event.getBearing();
		if(robots.size() == 0){
	   		if(ang > 0){
				turnRight(ang);
			   	turnLeft(90);
	   		}
		   	else {
			   	turnLeft(-ang);
				turnRight(90);
		   	}
		}
		
		if(robots.size() == 3){
			if(ang > 0){
				turnRight(ang);
			   	turnLeft(90);
	   		}
		   	else {
			   	turnLeft(-ang);
				turnRight(90);
		   	}
			robots.add("Last");
			ahead(10000);
		}
   	}

	public void onRobotDeath(RobotDeathEvent event){
		scanned = false;
	}
	
	/*
	 * 2 funções de calculo de distancias
	 */
	public void onStatus(StatusEvent e){
    	RobotStatus s = e.getStatus();
    	distance += Math.sqrt(Math.pow((s.getX()-lastX), 2)+Math.pow((s.getY()-lastY), 2));
    	lastX = s.getX();
    	lastY = s.getY();
  	}

  	public void onRoundEnded(RoundEndedEvent e){
    	System.out.println("Distance this round: "+distance);
    	distance = 0;
  	}
	

	
}

