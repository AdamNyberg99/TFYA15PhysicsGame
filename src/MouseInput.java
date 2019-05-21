import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    private PhysicsCanvas canvas;


    public MouseInput(PhysicsCanvas canvas){
        this.canvas = canvas;

    }

    @Override

    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        canvas.spring.setMouseX(mx);
        canvas.spring.setMouseY(my);
        canvas.spring.setBallPos();
        canvas.spring.extend();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.spring.decrease();

    }

    @Override
    public void mouseEntered(MouseEvent e) {


    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
