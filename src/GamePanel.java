import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    static final int Screen_Width=600;
    static final int Screen_Height=600;
    static final int Unit_Size=25;
    static final int Game_units=(Screen_Width*Screen_Height)/Unit_Size;
    static final int DELAY=75;
    final int []x=new int[Game_units];
     final int []y=new int[Game_units];
     int bodyParts=6;
     int appleEat;
     int appleX;
     int appleY;
     char direction ='R';
     boolean running=false;
     Timer timer;
     Random random;
     GamePanel(){
      random=new Random();
      this.setPreferredSize(new Dimension(Screen_Width,Screen_Height));
      this.setBackground(Color.BLACK);
      this.setFocusable(true);
      this.addKeyListener(new MyKeyAdapter());
      startGame();
    }
     public void startGame(){
       newApple();
       running=true;
       timer=new Timer(DELAY,this);
        timer.start();

     }

     public void paintComponent(Graphics g){
       super.paintComponent(g);
       draw(g);
     }
    public void draw(Graphics g){
         if(running){
    /* for(int i=0;i<Screen_Height/Unit_Size;i++){
         g.drawLine(i*Unit_Size,0,i*Unit_Size,Screen_Height);
         g.drawLine(0,i*Unit_Size,Screen_Width,i*Unit_Size);
     }*/
     g.setColor(Color.RED);
     g.fillOval(appleX,appleY,Unit_Size,Unit_Size);

     for(int i=0;i<bodyParts;i++) {
         if (i == 0) {
             g.setColor(Color.green);
             g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
         } else {
             g.setColor(new Color(45, 100, 0));
             g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
         }
     }
             g.setColor(Color.red);
             g.setFont(new Font("Ink Free",Font.BOLD,30));
             FontMetrics metrics=getFontMetrics(g.getFont());
             g.drawString("Score "+appleEat,(Screen_Width-metrics.stringWidth("Score "+appleEat))/2,g.getFont().getSize());
         }

         else{
              gameOver(g);
         }
    }

    public void newApple(){
     appleX=random.nextInt((int)Screen_Width/Unit_Size)*Unit_Size;
     appleY=random.nextInt((int)Screen_Height/Unit_Size)*Unit_Size;

    }
    public void move(){
              for(int i=bodyParts;i>0;i--){
                  x[i]=x[i-1];
                  y[i]=y[i-1];
              }
              switch (direction){
                  case 'U':
                  y[0]=y[0]-Unit_Size;
                  break;

                  case 'D':
                      y[0]=y[0]+Unit_Size;
                      break;
                  case 'L':
                      x[0]=x[0]-Unit_Size;
                      break;
                  case 'R':
                      x[0]=x[0]+Unit_Size;
                      break;
              }
    }
    public void cheackApple(){
           if(x[0]==appleX && y[0]==appleY){
               bodyParts++;
               appleEat++;
               newApple();

           }
    }
    public void cheackCollision(){

          for (int i=bodyParts;i>0;i--){
             if((x[0]==x[i]) && (y[0]==y[i])){
                   running=false;
              }
             if(x[0]<0){
                 running=false;
             }
              if(x[0]>Screen_Width){
                  running=false;
              }
              if(y[0]<0){
                  running=false;
              }
              if(y[0]>Screen_Height){
                  running=false;
              }
              if(!running){
                  timer.stop();
              }

          }
    }
    public void gameOver(Graphics g){
         g.setColor(Color.red);
         g.setFont(new Font("Ink Free",Font.BOLD,40));
         FontMetrics metrics1=getFontMetrics(g.getFont());
         g.drawString("Score "+appleEat,(Screen_Width-metrics1.stringWidth("Score "+appleEat))/2,Screen_Height-250);
        /////////////////////////////////////////////////////////
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("Game Over",(Screen_Width-metrics2.stringWidth("Game over"))/2,Screen_Height-300);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
         if(running){
             move();
             cheackApple();
             cheackCollision();
         }
         repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public  void keyPressed(KeyEvent e){
           switch (e.getKeyCode()){
               case KeyEvent.VK_LEFT:
                   if(direction!='R'){
                       direction='L';
                   }
                   break;
               case KeyEvent.VK_RIGHT:
                   if(direction!='L'){
                       direction='R';
                   }
                   break;
               case KeyEvent.VK_UP:
                   if(direction!='D'){
                       direction='U';
                   }
                   break;
               case KeyEvent.VK_DOWN:
                   if(direction!='U'){
                       direction='D';
                   }
                   break;
           }
        }
    }
}
