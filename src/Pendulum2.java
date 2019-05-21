import java.awt.*;

public class Pendulum2 extends ParticleTwo {

    double angleAccel, angleVelocity, dt, length, xanchor, yanchor, angle;

    public Pendulum2( double xanchor, double yanchor, Color color, double length) {

        this.angle = Math.PI / 3;
        this.xanchor = xanchor;
        this.yanchor = yanchor;
        this.dt = 0.06;
        this.angleVelocity = 0;

        this.curPos = new Vec2D(xanchor + (int) (Math.sin(angle) * length), yanchor + (int) (Math.cos(angle) * length));
        this.vel = new Vec2D(0, 0);
        this.prevPos = curPos.sub(vel.mul(dt));

        this.acel = new Vec2D(0,0);
        this.mass = 10000;

        this.r = 45;
        this.color = color;

        this.length = length;
    }

    @Override
    public void calcNewPos() {

        //angleAccel = -5000 / length * Math.sin(angle);
        angleAccel = -acel.getY() / length * Math.sin(angle);
        angleVelocity += angleAccel * dt;
        angle += angleVelocity * dt;

        curPos.setX(xanchor + (int) (Math.sin(angle) * length));
        curPos.setY(yanchor + (int) (Math.cos(angle) * length));
    }


    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(curPos.getX()-r), (int)Math.round(curPos.getY()-r), (int)Math.round(2*r), (int)Math.round(2*r));
        g.drawLine((int)xanchor, (int)yanchor, (int)curPos.getX(), (int)curPos.getY());

    }
}
