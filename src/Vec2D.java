public class Vec2D {

    private double x;
    private double y;

    public Vec2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void add(Vec2D V){
        this.x += V.x;
        this.y += V.y;
    }

    public void sub(Vec2D V){
        this.x -= V.x;
        this.y -= V.y;
    }

    public void mul(Vec2D V){
        this.x *= V.x;
        this.y *= V.y;
    }

    public void div(Vec2D V){
        this.x /= V.x;
        this.y /= V.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
