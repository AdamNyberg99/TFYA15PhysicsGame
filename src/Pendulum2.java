import java.awt.*;
import java.util.ArrayList;


public class Pendulum2 extends ParticleTwo {

    double angleAccel, angleVelocity, dt, length, xanchor, yanchor, angle;
    private ArrayList<ParticleTwo> string = new ArrayList<>();

    public Pendulum2( double xanchor, double yanchor, Color color, double length) {
        // INSTANTIATE VARIABLES
        this.angle = Math.PI / 3;
        this.xanchor = xanchor;
        this.yanchor = yanchor;
        this.dt = 0.06;
        this.angleVelocity = 0;
         this.mass = 10000;

        this.r = 45;
        this.color = color;

        this.length = length;

        // INSTANTIATE NECESSARY VECTORS
        this.curPos = new Vec2D(xanchor + (int) (Math.sin(angle) * length), yanchor + (int) (Math.cos(angle) * length));
        this.vel = new Vec2D(0, 0);
        this.prevPos = curPos.sub(vel.mul(dt));

        this.acel = new Vec2D(0,0);
       
        // GENERATES A ROPE CONNECTING THE PENDELUM BALL TO THE ANCHOR_POINT
        generateString();
    }

    // GENERATS A STRING/ROPE WITH THE LENGTH OF THE VARIABLE length, THE ROPE CONCISTS OF PARTICLE OBJECTS
    public void generateString(){
        for(int i = 0; i < length; i++){
            string.add(new ParticleTwo(xanchor,i,1,10,0,0,Color.RED));
        }
       rotateString(angle);
    }

    @Override
    public void calcNewPos() {
        calcFnet();
        acel = F_net.div(mass);
        
        // UPDATE ANGLE ACCELERATION        
        angleAccel = -acel.getY() / length * Math.sin(angle);
        // UPDATE ANGLE VELOCITY
        angleVelocity += angleAccel * dt;
        // UPDATE ANGLE
        angle += angleVelocity * dt;
        // UPDATE X AND Y COORDINATES OF THE PENDULUM
        curPos.setX(xanchor + (int) (Math.sin(angle) * length));
        curPos.setY(yanchor + (int) (Math.cos(angle) * length));
        
        // ROTATES THE ROPE CONECTING THE BALLA ANC ANCHOR-POINT
       rotateString(angleVelocity*dt);
    }

    // DETECTS COLLISION WITH PARTICLE P1 AND THE PENDELUMS ROPE
    public boolean stringColission(ParticleTwo p1){
        for(ParticleTwo p2: string){
            Vec2D dPos = p2.curPos.sub(p1.curPos);
            double L = dPos.distance();
            double L0 = p1.r+p2.r;

            if( L < L0){
               return true;
            }
        }
        return false;
    }

    // MOVES THE ROPE TO ORIGO, AND THEN ROTATES IT AROUND ORIGO WITH THE GIVEN ANGLE toRotate, THEN MOVES IT BACK TO ITS
    // ORIGINAL POSITION
    public void rotateString(double toRotate){
        for(ParticleTwo p: string){
            double tempX = p.curPos.getX() - xanchor;
            double tempY = p.curPos.getY()- yanchor;

            // Roterar h�rnen
            double rotatedX = (tempX * Math.cos(-toRotate)) - (tempY * Math.sin(-toRotate));
            double rotatedY = (tempX * Math.sin(-toRotate)) + (tempY * Math.cos(-toRotate));

            // Flyttar tillbaks rektangeln till dess ursprungliga position
            p.curPos = new Vec2D(rotatedX+xanchor,rotatedY+yanchor);
        }
    }
    
    // RENDERS EVERY PARTICLE IN THE ROPE/STRING
    public void renderString(Graphics2D g){
        for(ParticleTwo p: string){
            p.render(g);
        }
    }


    public void update(){
        calcNewPos();
    }

    @Override
    public void render(Graphics2D g) {
        // SET THE COLOR OF THE PENDULUM
        g.setColor(color);
        // DRAW THE BIG CIRCLE AT THE END OF THE PENDULUM
        g.fillOval((int)Math.round(curPos.getX()-r), (int)Math.round(curPos.getY()-r), (int)Math.round(2*r), (int)Math.round(2*r));
        // RENDERS THE ROPE
        renderString(g);
    }
}
