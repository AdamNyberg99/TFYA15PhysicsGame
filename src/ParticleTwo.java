import java.awt.Graphics2D;
import java.awt.Color;


@SuppressWarnings("MagicConstant")
public class ParticleTwo {

    private double vx;
    private double vy;

    private double prev_x;
    private double next_x;
    private double prev_y;
    private double next_y;
    private double ax;
    private double ay;
    private double dt;
    private double mass;

    private double r;
    private Color color;

    private double x;
    public double getX() {
        return x;
    }

    private double y;
    public double getY() {
        return y;
    }

    public ParticleTwo(double x, double y, double r, double mass, double vx, double vy, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
        this.mass = mass;
        this.vx = vx;
        this.vy = vy;
        this.dt = 0.01;
        this.prev_x = x - vx*dt;
        this.prev_y = y - vy*dt;
    }


    public void update() {
        calcNewPos();




        System.out.println(this.color);
        System.out.println("ax = " + ax);
        System.out.println("ay = " + ay);


    }

    public void calcNewPos(){
        next_x = 2 * x - prev_x + ax*dt*dt;
        next_y = 2 * y - prev_y + ay *dt*dt;

        prev_x = x;
        prev_y = y;

        x = next_x;
        y = next_y;

        vx += ax*dt;
        vy += ay *dt;
    }


    public void gravitation(ParticleTwo pt){
        double mass2 = pt.mass;
        double mass1 = mass;
        double dx = pt.x - x;
        double dy = pt.y - y;
        double rad = Math.atan(y/x);
        double radius = Math.sqrt(dx * dx + dy * dy);
        double force;
        double G = 6.67338 * 0.005;
        System.out.println("vinkel " + rad);
        System.out.println("dx " + dx);
        System.out.println("dy " + dy);
        force = G * mass1*mass2 / (radius*radius);
        ax = force/mass1 * Math.cos(rad);
        ay = force/mass1 * Math.sin(rad);

       if(dx > 0){
            ax *=-1;
        }
        if(dy > 0){
            ay *= -1;
        }
    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
    }
}

