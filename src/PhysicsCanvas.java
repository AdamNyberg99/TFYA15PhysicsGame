import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;


public class PhysicsCanvas extends Canvas implements Runnable {
	
	private boolean running;
	private ArrayList<ParticleTwo> planets = new ArrayList<>();
	/*private Particle p1;
	private Particle p2;
	private Particle p3;
	private Particle p4;*/


	public PhysicsCanvas() {
		Dimension d = new Dimension(1600, 900);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);

		planets.add(new ParticleTwo(600,250,20, 120, 140,0, Color.BLUE));
		planets.add(new ParticleTwo(800,450,50,1000000*1000000, 0, 0, Color.YELLOW));
		/*
		p1 = new Particle(750, 450, 30, Color.RED);
		p2 = new Particle(550, 350, 20, Color.BLUE);
		p3 = new Particle(300, 800, 40, Color.GREEN);
		p4 = new Particle(400, 200, 50, Color.YELLOW);
		 */
	}



	@Override
	public void run() {
		while(running) {
			update();
			render();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				running = false;
			}
		}
	}
	
	public void start() {
		if(!running) {
			Thread t = new Thread(this);
			createBufferStrategy(3);
			running = true;
			t.start();
		}
	}

	private void render() {
		BufferStrategy strategy = getBufferStrategy();
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(int i = 0; i < planets.size(); i++){
			planets.get(i).render(g);
		}
		/*
		p1.render(g);
		p2.render(g);
		p3.render(g);
		p4.render(g);
		 */
				
		strategy.show();
	}


	private void update() {
		for(int i = 0; i < planets.size(); i++){
			for (int j = 0; j < planets.size(); j++){

				if(planets.get(i) != planets.get(j)){
					planets.get(i).gravitation(planets.get(j));
				}
			}
			planets.get(i).update();
		}
		/*
		p1.update();
		p2.update();
		p3.update();
		p4.update();
		 */
	}

	public static void main(String[] args) {
		JFrame myFrame = new JFrame("My physics canvas");
		PhysicsCanvas physics = new PhysicsCanvas();
		myFrame.add(physics);
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		physics.start();
		myFrame.setLocationRelativeTo(null);
	}


}
