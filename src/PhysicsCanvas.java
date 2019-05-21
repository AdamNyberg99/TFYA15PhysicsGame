import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;


public class PhysicsCanvas extends Canvas implements Runnable, ActionListener {

	private boolean running;
	private ArrayList<ParticleTwo> planets = new ArrayList<>();
	private Pendulum2 pendel;
	private Pendulum2 pendel2;
	private Pendulum2 pendel3;

	public Spring spring;
	private ParticleTwo ball;
	private ParticleTwo sun;
	private boolean startOver;
	private Goal target;

	public static final int Width = 1600;
	public static final int Height = 900;

	public PhysicsCanvas() {
		// ADD MOUSE LISTENER
		this.addMouseListener(new MouseInput(this));

		// FIX WINDOW
		Dimension d = new Dimension(Width, Height);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);

		// ADD GAME COMPONENTS
		target = new Goal(1300, 700, 100, 100);
		planets.add(pendel = new Pendulum2(800,0, Color.RED, 440));
		planets.add(pendel2 = new Pendulum2(800,0, Color.RED, 320));
		planets.add(pendel3 = new Pendulum2(800,0, Color.RED, 200));
		planets.add(ball = new ParticleTwo(600,420,20, 10, 0,-100, Color.BLUE));
		planets.add(sun = new ParticleTwo(800,700,200,1000000000*1000000000, 0, 0, Color.green));
		spring = new Spring(new Vec2D(170,500),50,350, ball);

		startOver = false;
	}

	@Override
	public void run() {
		while(running) {
			update();
			render();
			getStatus();		// CHECK IF GAME OVER

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				running = false;
			}
		}
	}

	private void getStatus() {
		if (startOver) {
			resetGame();
			startOver = false;
		}
	}

	private void resetGame() {
		// CLEAR GAME CANVAS
		planets.remove(ball);

		// ADD GAME COMPONENTS
		planets.add(ball = new ParticleTwo(600,420,20, 10, 0,-100, Color.BLUE));
		spring = new Spring(new Vec2D(170,500),50,350, ball);

		//

		//running = false;
		//start();
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

		target.render(g);

		spring.render(g);

		strategy.show();
	}


	private void update() {


		updateComponents();

		if (hitWithSun() || hitWithPendulums()) {
			startOver = true;
		}

		if (target.checkVictory(ball)) {
			startOver = true;
		}
	}

	private void updateGravitation() {

		pendel.gravitation(sun);
		pendel2.gravitation(sun);
		pendel3.gravitation(sun);


		if (spring.ballInAir()) {
			ball.gravitation(sun);
		}

/*
		for(int i = 0; i < planets.size(); i++){
			for (int j = 0; j < planets.size(); j++){

				if(planets.get(i) != planets.get(j)){
					planets.get(i).gravitation(planets.get(j));
					//planets.get(i).Colission(planets.get(j));
				}

			}
		}*/
	}

	private void updateComponents() {

		updateGravitation();

		for(int i = 0; i < planets.size(); i++){
			planets.get(i).update();

		}

		spring.update();
	}

	private boolean hitWithSun() {

		if (distance(ball, sun) <= ball.getR() + sun.getR()) {
			return true;
		}
		return false;
	}

	private boolean hitWithPendulums() {
		if (distance(ball, pendel) <= ball.getR() + pendel.getR()) {
			return true;
		}
		if (distance(ball, pendel2) <= ball.getR() + pendel.getR()) {
			return true;
		}
		if (distance(ball, pendel3) <= ball.getR() + pendel.getR()) {
			return true;
		}
		return false;
	}

	private double distance(ParticleTwo p1, ParticleTwo p2) {
		return Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(),2));
	}

	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "True");

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
