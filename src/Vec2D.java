public class Vec2D {

    private double x;
    private double y;

    public Vec2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vec2D add(Vec2D V){
        double tempX = this.x;
        double tempY = this.y;
        tempX += V.x;
        tempY += V.y;
        return new Vec2D(tempX, tempY);
    }

    public Vec2D sub(Vec2D V){
        double tempX = this.x;
        double tempY = this.y;
        tempX -= V.x;
        tempY -= V.y;
        return new Vec2D(tempX, tempY);
    }

    public Vec2D mul(double K){
        double tempX = this.x;
        double tempY = this.y;
        tempX *= K;
        tempY *= K;
        return new Vec2D(tempX, tempY);
    }

    public double angle (){
        double rad = Math.atan2(y,x);
        return rad;
    }

    public double distance(){
        double distance = Math.sqrt(x*x + y*y);
        return distance;
    }

    public Vec2D div(Vec2D V){
        double tempX = this.x;
        double tempY = this.y;
        tempX /= V.x;
        tempY /= V.y;
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
