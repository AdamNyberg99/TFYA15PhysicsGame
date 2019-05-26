import java.awt.*;
import java.awt.Rectangle;

public class Spring{

    private Vec2D springPos;
    private double footWidth, footHeight, restLength, kastarm, kastarmX, kastarmY, mouseX, mouseY, hypotenusaMus, ext, angle, invAngle, force;
    private double k = 0.005;
    private int springWidth = 10;
    private ParticleTwo p2;
    private Vec2D newAcel, newBallPos;
    private boolean ballInAir;

    /**
     * Hookes law:
     *      F = -(k * ext)
     */

    public Spring(Vec2D springPos, double footWidth, double footHeight, ParticleTwo p2){
        this.springPos = springPos;
        this.footWidth = footWidth;
        this.footHeight = footHeight;
        restLength = 100;
        ext = 0;
        kastarm = restLength;
        this.p2 = p2;
        setBallPos();
        ballInAir = false;
    }

    // Uppdaterar objektet och skjuter iväg bollen om musen har släpts
    public void update(){
        calcAngle();
        calcNewLength();
        calcForce();

        if (ballInAir && (p2.getX()>(springPos.getX()-kastarmX)) && ext==0){
            if ((p2.getY()>(springPos.getY()+kastarmY))) {
                p2.setAcel(newAcel);
            }
        }else {
            setBallPos();
        }
    }

    // Äkar fjäderns längd i den riktning som muspekaren beffiner sig
    public void calcNewLength(){
        kastarm += ext;
        kastarmX = Math.cos(angle)*kastarm;
        kastarmY = Math.sin(angle)*kastarm;
        hypotenusaMus = (Math.sqrt((mouseY-springPos.getY()) * (mouseY-springPos.getY()) + (springPos.getX()-mouseX) * (springPos.getX()-mouseX)));
        if(kastarm>hypotenusaMus)
            stopExtend();
    }

    // Fäster bollen på fjädern
    public void setBallPos(){

        p2.setAcel(new Vec2D(0,0));
        newBallPos= new Vec2D(springPos.getX()-kastarmX, springPos.getY()+kastarmY);
        p2.setPos(newBallPos);
    }

    // Beräknar kraften och utifrån den accelerationen som fjädern ger bollen
    public void calcForce(){
        force = -k*(kastarm-restLength);
        newAcel  = new Vec2D(-force/p2.getMass() * Math.cos(angle),-force/p2.getMass() * Math.sin(angle));
    }

    // Beräknar vinkeln som muspekaren drar ut bollen i
    public void calcAngle(){
        angle = Math.atan2(mouseY-springPos.getY(), springPos.getX()-mouseX);
        invAngle = -angle + Math.PI/2;
    }

    // Mplar alla komponenter i fjäder-kanonen.
    public void render(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.CYAN);
        Rectangle foot = new Rectangle((int)(springPos.getX()-footWidth/2), (int)(springPos.getY()), (int)footWidth, (int) footHeight);
        g2d.draw(foot);
        g2d.fill(foot);

        g2d.translate((int)(springPos.getX()), (int)(springPos.getY()));
        Rectangle spring = new Rectangle(0,0, springWidth, (int)kastarm);
        g2d.rotate(invAngle);
        g2d.setColor(Color.GRAY);
        g2d.draw(spring);
        g2d.fill(spring);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(-springWidth/2,-springWidth/2,(2*springWidth),(2*springWidth));
    }

    public void extend(){ ext+=1; }

    private void stopExtend(){ ext=0; }

    // Minskar längden på fjädern när muspekaren har släppts
    public void decrease(){
        ext -= 2;
        kastarm=restLength+ext;
        if (restLength > kastarm){
            ext= 0;
            kastarm = restLength;
        }
        ballInAir = true;
    }

    public void setMouseX(int x){
        mouseX = x;
    }
    public void setMouseY(int y){
        mouseY = y;
    }

    public boolean ballInAir() {
        return ballInAir;
    }
}
