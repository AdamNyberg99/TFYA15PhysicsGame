import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;


@SuppressWarnings("MagicConstant")
public class ParticleTwo {

    private Vec2D prevPos, curPos, vel, acel, F_net;

    private double dt;
    private double mass;

    private double r;
    private Color color;
    private ArrayList<Vec2D> forces = new ArrayList<>();

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
        WallColission();

    }

    public void Colission(ParticleTwo p2){
        int K = 10000000;
        double L0 = this.r + p2.r;
        Vec2D dPos = curPos.sub(p2.curPos);
        double L = dPos.distance();

        if( L < L0){
            double a1 = vel.angle();
            double a2 = p2.vel.angle();
            double V1 = vel.distance();
            double V2 = p2.vel.distance();
            double m1 = this.mass;
            double m2 = p2.mass;
            double dp = Math.PI/2;

            double V1x = (((V1*Math.cos(a1-dp)*(m1-m2))+(2*m2*V2*Math.cos(a2-dp)))/(m1+m2))*Math.cos(dp)+V1*Math.sin(a1-dp)*Math.sin(dp);
            /*
            System.out.println("hej");
            double F_s = -K*(L0-L)/2;
            Vec2D F_spring = new Vec2D((float)F_s, dPos.angle());
            forces.add(F_spring);
            //calcNewPos();
             */
        }

    }

    public void WallColission(){

        if(curPos.getX()<r){
            double prevDxr = prevPos.getX()-r;
            double curDxr = curPos.getX()-r;
            //Vec2D sPoint = new Vec2D(r, Math.tan(prevPos.angle())*dx);
            Vec2D newPos = new Vec2D(r-curDxr,curPos.getY());
            Vec2D rePrePOS  = new Vec2D(r-prevDxr,prevPos.getY());

            prevPos = rePrePOS;
            curPos = newPos;

            vel = newPos.sub(rePrePOS).div(dt);

        } else if(curPos.getX()> PhysicsCanvas.Width -r){
            double prevDxr = prevPos.getX() - (PhysicsCanvas.Width - r);
            double curDxr = curPos.getX() - (PhysicsCanvas.Width - r);

            Vec2D newPos = new Vec2D((PhysicsCanvas.Width - r)- curDxr, curPos.getY());
            Vec2D rePrePOS  = new Vec2D((PhysicsCanvas.Width - r)+prevDxr, prevPos.getY());

            prevPos = rePrePOS;
            curPos = newPos;
            vel = newPos.sub(rePrePOS).div(dt);

        }
        if(curPos.getY() < r){
            double prevDyr = prevPos.getY() -r;
            double curDyr = curPos.getY() -r;
            //Vec2D sPoint = new Vec2D(r, Math.tan(prevPos.angle())*dx);
            Vec2D newPos = new Vec2D(curPos.getX(),r-curDyr);
            Vec2D rePrePOS  = new Vec2D(prevPos.getX(),r-prevDyr);

            prevPos = rePrePOS;
            curPos = newPos;
            vel = newPos.sub(rePrePOS).div(dt);
        } else if(curPos.getY()> PhysicsCanvas.Height -r){
            double prevDyr = prevPos.getY() - (PhysicsCanvas.Height - r);
            double curDyr = curPos.getY() - (PhysicsCanvas.Height - r);

            Vec2D newPos = new Vec2D(curPos.getX(), (PhysicsCanvas.Height - r)- curDyr);
            Vec2D rePrePOS  = new Vec2D(prevPos.getX(), (PhysicsCanvas.Height - r)+prevDyr);

            prevPos = rePrePOS;
            curPos = newPos;
            vel = newPos.sub(rePrePOS).div(dt);

        }
    }
    public void calcNewPos(){
        Vec2D tempPos;
        calcFnet();
        acel = F_net.div(mass);
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
        dxy = pt.curPos.sub(curPos);

        double rad = dxy.angle();
        double radius = dxy.distance();
        double force;
        double G = 6.67338 * 0.005;

        force = -G * mass*mass2 / (radius*radius);

        // a = force/mass1 * cos or sin (rad + PI)
        Vec2D Fg = new Vec2D((float)(force),rad);
        forces.add(Fg);

    }

    public void calcFnet(){
        F_net = new Vec2D(0,0);

        for(int i=0; i < forces.size(); i++){
            Vec2D F_temp = F_net.add(forces.get(i));
            F_net = F_temp;
        }
        forces.clear();
    }

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

    public double getR() {return r;}

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(curPos.getX()-r), (int)Math.round(curPos.getY()-r), (int)Math.round(2*r), (int)Math.round(2*r));

    }
}

