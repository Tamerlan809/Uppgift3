import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameOf15 extends JFrame implements ActionListener{

    //Här skapas nytt spel knappen arrayen med alla knappar.
    JButton newGameButton = new JButton();
    ArrayList<String> oneToFifteenBricks =
            new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","","15"));

    ArrayList<JButton> buttonList = new ArrayList<>();



    public GameOf15(){

        //GUI av spelet med nytt spel och GamePanel.
        newGameButton.addActionListener(this);
        newGameButton.setText("Nytt Spel");
        newGameButton.setPreferredSize(new Dimension(400,100));
        newGameButton.setBackground(Color.white);
        newGameButton.setForeground(Color.red);
        newGameButton.setFont(new Font("Comic Sans",Font.BOLD,50));
        newGameButton.setFocusable(false);

        setLayout(new BorderLayout());
        add(createGamePanel(), BorderLayout.CENTER);
        add(newGameButton, BorderLayout.SOUTH);

        setSize(400, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Metoden för att blanda Arrayen.
    public void shuffleOneToFifteenBricks(){
        Collections.shuffle(oneToFifteenBricks);
    }

    //Metoden för att skapa Spel Panel med gridlayout och knapparna skapas samt GUI av knapparna.
    private JPanel createGamePanel(){
        JPanel gamePanel = new JPanel(new GridLayout(4,4));


        for (int i = 0; i < 16; i++) {
            JButton button = new JButton(oneToFifteenBricks.get(i));
            buttonList.add(button);
            button.addActionListener(this);
            gamePanel.add(button);
            button.setBackground(Color.white);
            button.setForeground(Color.black);
            button.setFont(new Font("Comic Sans",Font.BOLD,50));
            button.setFocusable(false);
        }
        return gamePanel;
    }

    //Logicen för vad händer när man trycker på knappen nytt spel och knapparna på GamePanel.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton){
            shuffleOneToFifteenBricks();
            updateGamePanel();
        }
        else {
            JButton clickedButton = (JButton) e.getSource();
            if (besideEmptySlot(clickedButton)){
                changeBetweenEmptyCLickedButton(clickedButton);
                updateGamePanel();

                if (gameOver()) {
                    JOptionPane.showMessageDialog(null, "!!!Grattis Du Vann!!!");
                }
            }
        }
    }

    //Uppdaterar GamePanel varje gång man trycker på knapparna.
    public void updateGamePanel(){
        JPanel newGamePanel = createGamePanel();

        this.getContentPane().removeAll();
        this.add(newGamePanel, BorderLayout.CENTER);
        this.add(newGameButton, BorderLayout.SOUTH);

        buttonList.clear();

        for (int i = 0; i < 16; i++) {
            JButton button2 = (JButton) newGamePanel.getComponent(i);
            button2.addActionListener(this);
            buttonList.add(button2);
        }

        this.revalidate();
        this.repaint();
    }

    //Kollar om man tryckta knappen är på vänster, höger, ovan eller under tomma platsen.
    private boolean besideEmptySlot(JButton button){
        int indexOfEmptyIndex = oneToFifteenBricks.indexOf("");
        int indexOfClickedButton = buttonList.indexOf(button);

        return (indexOfEmptyIndex - 1 == indexOfClickedButton && indexOfEmptyIndex % 4 != 0) || //Vänster
                (indexOfEmptyIndex + 1 == indexOfClickedButton && indexOfEmptyIndex % 4 != 3) || //Höger
                (indexOfEmptyIndex - 4 == indexOfClickedButton) || //Ovan
                (indexOfEmptyIndex + 4 == indexOfClickedButton); //Under
    }

    //BYter plats mellan tomma platsen och tryckta knappen.
    private void changeBetweenEmptyCLickedButton(JButton clickedButton){
        int indexOfEmptyIndex = oneToFifteenBricks.indexOf("");
        int indexOfClickedButton = buttonList.indexOf(clickedButton);

        Collections.swap(oneToFifteenBricks, indexOfEmptyIndex, indexOfClickedButton);

        for (int i = 0; i < 16; i++) {
            buttonList.get(i).setText(oneToFifteenBricks.get(i));
        }
    }

    //Metoden för win contions.
    private boolean gameOver(){
        ArrayList<String> conditionsForGameOver =
                new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",""));

        return oneToFifteenBricks.equals(conditionsForGameOver);
    }

    //main metod.
    public static void main(String[] args){
        GameOf15 go15 = new GameOf15();
    }
}
