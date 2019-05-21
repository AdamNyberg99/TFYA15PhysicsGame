import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;
public class Goal {

    private double xCoord, yCoord, width, height, counter;
    public Goal(double xCoord, double yCoord, double width, double height){

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = width;
        this.height = height;
        this.counter = 0;
    }

    public boolean ballInside(ParticleTwo ball){
        if (xCoord<=ball.getX() && ball.getX()<=(xCoord + width) && yCoord<= ball.getY() && ball.getY() <= (yCoord + height)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkVictory (ParticleTwo ball){
        if(ballInside(ball)) {
            {
                victory();
                return true;
            }
        }
        return false;
    }

    public void render(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.gray);
        g2.drawRect((int)xCoord, (int)yCoord, (int)width, (int)height);
        // g.fillRect((int)xCoord,(int)yCoord,(int)width,(int)height);
    }

    public void victory(){

        JFrame myFrame = new JFrame("Victory");
        JLabel vicLabel = new JLabel("Congratulations! You Won!");
        vicLabel.setFont(new Font("Serif", Font.TYPE1_FONT, 44));
        vicLabel.setMinimumSize(new Dimension(400, 400));
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setSize(new Dimension(550, 150));
        myFrame.add(vicLabel);
        myFrame.setVisible(true);
        vicLabel.setVisible(true);
        myFrame.setLocationRelativeTo(null);
    }
}
