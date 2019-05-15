import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;


public class PhysicsCanvas extends Canvas implements Runnable, ActionListener {
	
	private boolean running;
	private ArrayList<ParticleTwo> planets = new ArrayList<>();
	public Spring spring;

	public static final int Width = 1600;
	public static final int Height = 900;

	public PhysicsCanvas() {
		this.addMouseListener(new MouseInput(this));
		Dimension d = new Dimension(Width, Height);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		ParticleTwo p2 = new ParticleTwo(600,420,20, 200, 0,-100, Color.BLUE);
		planets.add(p2);
		planets.add(new ParticleTwo(300,100,25, 190, 250,-40, Color.GREEN));
		planets.add(new ParticleTwo(1500,100,35, 3500, -180,10, Color.magenta));
		planets.add(new ParticleTwo(300,250,65,1000000*1000000, 60, 40, Color.YELLOW));
		spring = new Spring(new Vec2D(170,600),50,250, p2);

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
		spring.render(g);
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
		spring.update();
	}
	public void actionPerformed(ActionEvent e) {

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
