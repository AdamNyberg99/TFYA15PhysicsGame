import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;


public class PhysicsCanvas extends Canvas implements Runnable {
	
	private boolean running;
	private ArrayList<ParticleTwo> planets = new ArrayList<>();

	public static final int Width = 1600;
	public static final int Height = 900;

	public PhysicsCanvas() {
		Dimension d = new Dimension(Width, Height);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);

		planets.add(new ParticleTwo(600,420,20, 120, 0,-100, Color.BLUE));
		planets.add(new ParticleTwo(300,100,25, 190, 250,-40, Color.GREEN));
		planets.add(new ParticleTwo(1500,100,35, 3500, -180,10, Color.magenta));
		planets.add(new ParticleTwo(300,250,65,1000000*1000000, 60, 40, Color.YELLOW));

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
