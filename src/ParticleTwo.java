import java.awt.Graphics2D;
import java.awt.Color;


@SuppressWarnings("MagicConstant")
public class ParticleTwo {

    protected Vec2D prevPos, curPos, vel, acel;

    protected double dt;
    protected double mass;

    protected double r;
    protected Color color;

    public ParticleTwo(){

    }

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
        this.curPos = new Vec2D(x, y);
        this.vel = new Vec2D(V0x,V0y);
        this.prevPos = curPos.sub(vel.mul(dt));

        this.acel = new Vec2D(0,0);
        this.mass = mass;

        this.r = r;
        this.color = color;

    }


    public void update() {
        calcNewPos();
        //System.out.println("H = " + PhysicsCanvas.Width);
        //System.out.println("W = " + PhysicsCanvas.Height);

        Colission();


    }

    public void Colission(){

        if(curPos.getX()<r){
            double prevDxr = prevPos.getX() - r;
            double curDxr = curPos.getX() - r;
            //Vec2D sPoint = new Vec2D(r, Math.tan(prevPos.angle())*dx);
            Vec2D newPos = new Vec2D(curDxr*-1,curPos.getY());
            Vec2D rePrePOS  = new Vec2D(prevDxr*-1,prevPos.getY());

            prevPos = rePrePOS;
            curPos = newPos;

        } else if(curPos.getX()> PhysicsCanvas.Width -r){
            double prevDxr = prevPos.getX() - (PhysicsCanvas.Width - r);
            double curDxr = curPos.getX() - (PhysicsCanvas.Width - r);

            Vec2D newPos = new Vec2D((PhysicsCanvas.Width - r)- curDxr, curPos.getY());
            Vec2D rePrePOS  = new Vec2D((PhysicsCanvas.Width - r)+prevDxr, prevPos.getY());

            prevPos = rePrePOS;
            curPos = newPos;
        }
        if(curPos.getY()<r){
            double prevDyr = prevPos.getY() - r;
            double curDyr = curPos.getY() - r;
            //Vec2D sPoint = new Vec2D(r, Math.tan(prevPos.angle())*dx);
            Vec2D newPos = new Vec2D(curPos.getX(),curDyr*-1);
            Vec2D rePrePOS  = new Vec2D(prevPos.getX(),prevDyr*-1);

            prevPos = rePrePOS;
            curPos = newPos;

        } else if(curPos.getY()> PhysicsCanvas.Height -r){
            double prevDyr = prevPos.getY() - (PhysicsCanvas.Height - r);
            double curDyr = curPos.getY() - (PhysicsCanvas.Height - r);

            Vec2D newPos = new Vec2D(curPos.getX(), (PhysicsCanvas.Height - r)- curDyr);
            Vec2D rePrePOS  = new Vec2D(prevPos.getX(), (PhysicsCanvas.Height - r)+prevDyr);

            prevPos = rePrePOS;
            curPos = newPos;
        }
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

        double rad = dxy.angle();
        double radius = dxy.distance();
        double force;
        double G = 6.67338 * 0.005;

        force = G * mass1*mass2 / (radius*radius);

        // a = force/mass1 * cos or sin (rad + PI)
        acel = new Vec2D(-force/mass1 * Math.cos(rad+Math.PI),-force/mass1 * Math.sin(rad+Math.PI));

         }
    public Vec2D getAcel(){
        return acel;
    }

    public void setAcel(Vec2D newAcel) {
        acel = newAcel;
    }

    public void setPos(Vec2D newPos){
        curPos = newPos;
    }
    public double getMass(){
        return mass;
    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(curPos.getX()-r), (int)Math.round(curPos.getY()-r), (int)Math.round(2*r), (int)Math.round(2*r));

       // System.out.println("UP.x = " + curPos.getX());
       // System.out.println("UP.y = " + curPos.getY());
    }
}

