import java.awt.Graphics2D;
import java.awt.Color;


@SuppressWarnings("MagicConstant")
public class ParticleTwo {

    private Vec2D prevPos, curPos, vel, acel;

    private double vx;
    private double vy;
/*
    private double prev_x;
    private double next_x;
    private double prev_y;
    private double next_y;
    private double ax;
    private double ay;
 */
    private double dt;
    private double mass;

    private double r;
    private Color color;

    //private double x;
    public double getX() {
        return curPos.getX();
    }

    //private double y;
    public double getY() {
        return curPos.getY();
    }

    public ParticleTwo(double x, double y, double r, double mass, double V0x, double V0y, Color color) {
        this.dt = 0.01;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        this.curPos = new Vec2D(x, y);
        this.vel = new Vec2D(V0x,V0y);
        this.prevPos = curPos.sub(vel.mul(dt));
        System.out.println("prevx = " + prevPos.getX());
        System.out.println("prevy = " + prevPos.getY());
        /*
        this.prev_x = x - vx*dt;
        this.prev_y = y - vy*dt;
         */
        this.acel = new Vec2D(0,0);
        this.mass = mass;

        this.r = r;
        this.color = color;

    }


    public void update() {
        calcNewPos();

        /*
        System.out.println(this.color);
        System.out.println("ax = " + ax);
        System.out.println("ay = " + ay);
        */

    }

    public void calcNewPos(){
        Vec2D tempPos;

        // Calculates our new position ( next_Pos = 2* currentPos - PrevPos + aceleration*dt^2)
        tempPos = curPos.mul(2).sub(prevPos).add(acel.mul(dt*dt));

        prevPos = curPos;
        curPos = tempPos;
        // Updates the speed (V += a*dt)
        vel.add(acel.mul(dt));

    }

    public void verlet(){

    }


    public void gravitation(ParticleTwo pt){
        Vec2D dxy;

        double mass2 = pt.mass;
        double mass1 = mass;
        dxy = curPos.sub(pt.curPos);
        //double dx = pt.x - x;
        //double dy = pt.y - y;
        double rad = dxy.angle();
        double radius = dxy.distance();
        double force;
        double G = 6.67338 * 0.005;
        /*
        System.out.println("vinkel " + rad);
        System.out.println("dx " + dx);
        System.out.println("dy " + dy);
        */

        force = G * mass1*mass2 / (radius*radius);
    /*
        ax = force/mass1 * Math.cos(rad+Math.PI);
        ay = force/mass1 * Math.sin(rad+Math.PI);
     */
        acel = new Vec2D(-force/mass1 * Math.cos(rad+Math.PI),-force/mass1 * Math.sin(rad+Math.PI));

         }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(curPos.getX()-r), (int)Math.round(curPos.getY()-r), (int)Math.round(2*r), (int)Math.round(2*r));

        System.out.println("UP.x = " + curPos.getX());
        System.out.println("UP.y = " + curPos.getY());
    }
}

