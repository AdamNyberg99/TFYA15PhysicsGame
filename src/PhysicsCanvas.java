import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;


public class PhysicsCanvas extends Canvas implements Runnable {
	
	private boolean running;
	private Particle p1;
	private Particle p2;
	private Particle p3;
	private Particle p4;


	public PhysicsCanvas() {
		Dimension d = new Dimension(1600, 900);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		
		p1 = new Particle(750, 450, 30, Color.RED);
		p2 = new Particle(550, 350, 20, Color.BLUE);
		p3 = new Particle(300, 800, 40, Color.GREEN);
		p4 = new Particle(400, 200, 50, Color.YELLOW);
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
		
		p1.render(g);
		p2.render(g);
		p3.render(g);
		p4.render(g);
				
		strategy.show();
	}


	private void update() {
		p1.update();
		p2.update();
		p3.update();
		p4.update();
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
