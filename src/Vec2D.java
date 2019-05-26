public class Vec2D {

    private double x;
    private double y;

    // Skapar en vektor utifrån x- och y-komponenter
    public Vec2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    // Skapar en vektor utifrån magnitud och vinkel
    public Vec2D(float dist, double v){
        this.x = dist * Math.cos(v);
        this.y = dist * Math.sin(v);
    }

    // Adderar två vektorer och returnar den resulterande vektorn
    public Vec2D add(Vec2D V){
        double tempX = this.x;
        double tempY = this.y;
        tempX += V.x;
        tempY += V.y;
        return new Vec2D(tempX, tempY);
    }

    // Subtraherar två vektorer och returnar den resulterande vektorn
    public Vec2D sub(Vec2D V){
        double tempX = this.x;
        double tempY = this.y;
        tempX -= V.x;
        tempY -= V.y;
        return new Vec2D(tempX, tempY);
    }
    
    // Multiplicerar en vektorer och en konstant , k, och returnerar den resulterande vektorn
    public Vec2D mul(double K){
        double tempX = this.x;
        double tempY = this.y;
        tempX *= K;
        tempY *= K;
        return new Vec2D(tempX, tempY);
    }

    // Returnerar vektorns riktning i form av en vinkel
    public double angle (){
        double rad = Math.atan2(y,x);
        return rad;
    }

    // Returnerar vektorns storlek/magnitud
    public float distance(){
        float distance = (int) Math.sqrt(x*x + y*y);
        return distance;
    }

    // Dividerar en vektorer och en konstant , k, och returnerar den resulterande vektorn
    public Vec2D div(double K){
        double tempX = this.x;
        double tempY = this.y;
        tempX /= K;
        tempY /= K;
        return new Vec2D(tempX, tempY);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double xnew) {
        this.x = xnew;
    }

    public void setY(double ynew) {
        this.y = ynew;
    }
}
