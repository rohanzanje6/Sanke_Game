import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
       
        static final int SCREEN_WIDTH = 600;
        static final int  SCREEN_HEIGHT = 600; 
        static final int UNIT_SIZE = 25;    // how big do we want items in the game to be is defined by unit size
        static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
        static final int DELAY = 75;

        final int x[] = new int[GAME_UNITS];
        final int y[] = new int[GAME_UNITS];

       

        int bodyParts = 6;
        int applesEaten;
        int appleX;
        int appleY;
        char direction = 'R';
        boolean running = false;
        Timer timer;
        Random random;


        GamePanel() 
        {
            
            random = new Random();
            this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new MYkeyAdapter());
            for (int i = 0; i < bodyParts; i++) {
                x[i] = 0;
                y[i] = 0;
            }
               startGame();


        }

        public void startGame()
        {
            newApple();
            running = true;
            timer = new Timer(DELAY,this);
            timer.start();            
        }

  @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
if(running)
 {         
    // for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//             g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//             g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//         }
        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);


        for(int i = 0; i<bodyParts;i++)
        {
            if(i==0){
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } 
            else{
              //  g.setColor(Color.WHITE);
                g.setColor( new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
         g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.PLAIN, 20));
            String scoreMessage = "Your Score: " + applesEaten;
            FontMetrics metrics = g.getFontMetrics();
            g.drawString(scoreMessage , (SCREEN_WIDTH - metrics.stringWidth(scoreMessage)) / 2,   g.getFont().getSize());

    }
else {
    gameOver(g);
}
        }
        
        public void newApple(){
                    
           appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
           appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;


        }

        public void move() {
       
             for(int i= bodyParts-1; i>0;i--)
             {
                x[i] = x[i-1];
                y[i] = y[i-1];
             }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }




        }

        public void checkApple() {
           if((x[0]==appleX) && (y[0]== appleY))
           {
            bodyParts++;
            applesEaten++;
            newApple();
           }
        }

        public void checkCollisions(){
            //head with body 
            for(int i= bodyParts;i>0;i--)
            {
                if((x[0]==x[i] && y[0]==y[i]))
                {
                    running= false;
                }
            }
            //head left border
            if(x[0]<0)
            {   running=false;}
            //head right boreder
            if(x[0]>SCREEN_WIDTH)   running=false;
            // head top border
            if(y[0]<0)  running=false;
            //head dwon bordee
            if(y[0]>SCREEN_HEIGHT)   running=false;


            if(!running)   timer.stop();


        }

        public void initGame()
        {
            applesEaten=0;
        }

        public void gameOver(Graphics g){
            g.setColor(Color.RED);
            g.setFont(new Font("Ink free", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            String gameOverMessage = "Game Over";
            g.drawString(gameOverMessage, (SCREEN_WIDTH - metrics.stringWidth(gameOverMessage)) / 2, SCREEN_HEIGHT / 2);
            // Display final score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.PLAIN, 75));
            String scoreMessage = "Your Score: " + applesEaten;
            int messageX = (SCREEN_WIDTH - metrics.stringWidth(scoreMessage)) / 2;
            int messageY = (SCREEN_HEIGHT - metrics.getHeight()) / 2 - metrics.getAscent();
            g.drawString(scoreMessage,messageX,messageY );
             
            JButton restartButton = new JButton("Restart");
            restartButton.setFont(new Font("Ink Free", Font.PLAIN, 40));
            int buttonWidth = 200;
            int buttonHeight = 100;
            int buttonX = (SCREEN_WIDTH - buttonWidth) / 2;
            int buttonY = messageY +200; // Adjust the Y position as needed
            restartButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
    
            // Add an ActionListener to handle the restart action
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        restartGame();
                        dispose();
                }
            });
    
            // Add the button to the panel
            add(restartButton);


        } 

        public void restartGame() {
            setVisible(false);
            new GameFrame();
        }
    
        public void dispose() {
            JFrame parent = (JFrame) this.getTopLevelAncestor();
            parent.dispose();
        }

        @Override
        public void actionPerformed(ActionEvent e )
        {
            if(running)
            {
                move();
                checkApple();
                checkCollisions();    
            }
            repaint();

        }

        public class MYkeyAdapter extends KeyAdapter{

            @Override
            public void keyPressed(KeyEvent e ){
                
                switch(e.getKeyCode())
                {
                    case KeyEvent.VK_LEFT:
                       if(direction !='R')   direction ='L';
                       break;
                    case KeyEvent.VK_RIGHT:
                       if(direction !='L')   direction ='R';
                       break;
                    case KeyEvent.VK_UP:
                       if(direction !='D')   direction ='U';
                       break;
                    case KeyEvent.VK_DOWN:
                       if(direction !='U')   direction ='D';
                       break;
                }

            }

        }

        

}

