import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame() {

        // GamePanel gp =  new GamePanel(); 
        // add(gp);
        // instead of this we can also do 

           this.add(new GamePanel());
           this.setTitle("Snake");
           this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           this.setResizable(false);
           this.pack();   
           this.setVisible(true);
           this.setLocationRelativeTo(null);

        
        
    }
    
}
