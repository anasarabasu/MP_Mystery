/*

C = 1 ... 3
N = 0 ... 16

M = [1, 1] [1, 2] [1, 3]
    [2, 1] [2, 2] [2, 3]
    [3, 1] [3, 2] [3, 3]

V = 0, 1

*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Program {

    boolean good = false;
    boolean go = true;
    boolean start = true;
    boolean found = false;
    // boolean over;

    int val = 0;
    
    ArrayList<int[]> R = new ArrayList<int[]>();
    ArrayList<int[]> B = new ArrayList<int[]>();
    ArrayList<int[]> S = new ArrayList<int[]>();
    ArrayList<int[]> T = new ArrayList<int[]>();
    
    int M[][] = {
        {1, 1}, {1, 2}, {1, 3},
        {2, 1}, {2, 2}, {2, 3},
        {3, 1}, {3, 2}, {3, 3}
    };
    ArrayList<int[]> F = new ArrayList<int[]>(Arrays.asList(M));

    //-------------------------------------------------------------------

    private void remove(int pos[]) {
        if(go) R.remove(pos);
        else B.remove(pos);
    
        S.remove(pos);
        T.remove(pos);
    }

    private void replace(int[] pos,  int raw) {
        found = false;
        
        if(go) {
            if(B.contains(pos)) {
                System.out.println("Captured B!");

                B.remove(pos);
                found = true;
            }
            
            if(R.contains(pos)) found = true;
            else R.add(pos);
        }
        else {
            if(R.contains(pos)) {
                System.out.println("Captured R!");

                R.remove(pos);
                found = true;
            }

            if(B.contains(pos)) found = true;
            else B.add(pos);
        }

        if(found) {
            if(!S.contains(pos)) {
                S.add(pos);
                found = false;
            }
            else {
                if(!T.contains(pos)) {
                    T.add(pos);
                    expand(raw);
                }
                else
                    System.out.println("T");
            }
        }

    }

    private void expand(int raw) {
        int[] pos = M[raw];

        int u[] = new int[2];
        int d[] = new int[2];
        int k[] = new int[2];
        int r[] = new int[2];
        
        if(raw-3 >= 0) u = M[raw-3];
        if(raw+3 <= 8) d = M[raw+3];
        if(raw-1 >= 0) k = M[raw-1];
        if(raw+1 <= 8) r = M[raw+1];
        
        remove(pos);
        
        // ensures that satisfies only this condition (pos ∈ M) 
        if(pos[0]-1 > 0) replace(u, raw);
        if(pos[0]+1 < 4) replace(d, raw);
        if(pos[1]-1 > 0) replace(k, raw);
        if(pos[1]+1 < 4) replace(r, raw);
    }

    private void update(int raw) {
        int[] pos = M[raw];

        good = false;

        if(!S.contains(pos)) {
            S.add(pos);
            good = !good;
        }
        else {
            if(!good && !T.contains(pos)) {
                T.add(pos);
                expand(raw);
            }
        }
    }

    private void nextPlayerMove(int raw) {
        int[] pos = M[raw];

        if(start) {
            if(go) {
                R.add(pos);
                S.add(pos);

                good = true; 
            }
            else {
                B.add(pos);
                S.add(pos);

                good = true;                
            }
        }
        else {
            if((go && R.contains(pos)) || (!go && B.contains(pos))) {
                update(raw);
                good = true;
            }
        }

        if(start && R.size() == 1 && B.size() == 1) start = false;

        if(good) {
            good = !good;
            go = !go;
            val++;
        }

        updateCell();
    }

    private void gameOver() {
        String result = "uwu";
        
        if(R.size() > B.size()) result = "R wins";
        if(R.size() < B.size()) result = "B wins";
        if(R.size() == B.size()) result = "Draw";
        
        System.out.println(result);
    }
    
    private boolean gameLogic() {
        /*
        over ↔ (|F| = 3 ∨ val ≥ 20 ∨ ¬start ∧ (|R| > 0 ∧ |B| = 0 ∨ |R| = 0 ∧ |B| > 0))
        This is the condition for the while loop
        
        o ↔ c aka o→c ∧ c→o
        > over is always assumed false because programming languages tends to
        > therefore, 
            false→false(T) ∧ false→false(T) == while loop runs
        > only stops when c changes values because 
            false→true(T)  ∧ true→false(F)  == while loop ends
        
        so I can just use the c statement for the game loop condition, just flip it lol

        ----

        val ≥ 20 doesnt make sense tho...? since val is only restricted to 0 - 16
        well the important vars are F and start anyways
        red herring maybe, imma remove it

        uhhh so i also removed the !start
        */

        
       // F = M − (R ∪ B)
        ArrayList<int[]> remainingBoard = new ArrayList<int[]>(Arrays.asList(M)); 
        remainingBoard.removeAll(R);
        remainingBoard.removeAll(B);
        F = remainingBoard;

        int remainingBoardElements = remainingBoard.size();
        
        // over ↔ (|F| = 3 ∨ val ≥ 20 ∨ ¬start ∧ (|R| > 0 ∧ |B| = 0 ∨ |R| = 0 ∧ |B| > 0))
        int playerR = R.size();
        int playerB = B.size();

        return !(remainingBoardElements <= 3 && ((playerR > 0 && playerB == 0) || (playerB > 0 && playerR == 0))
        );
    }

    //-------------------------------------------------------------------

    private void updateCell() {
        for (int[] element : F) {
            // translates coordinate format to raw num
            int pos = ((element[0]-1) *3) + (element[1]-1); 
            
            if(pos >= 0 && pos <= 8) {
                cells[pos].setBackground(Color.WHITE);
                cells[pos].setText("");

                if(!start) cells[pos].setEnabled(false);
            }
        }

        Timer timer = new Timer(75, null);
        ActionListener update = new ActionListener() {
            int x = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int pos;

                if(x < R.size()) {
                    pos = ((R.get(x)[0]-1) *3) + (R.get(x)[1]-1); 
                    if(pos >= 0 && pos <= 8) cells[pos].setBackground(Color.RED);

                    if(go) cells[pos].setEnabled(true);
                    else cells[pos].setEnabled(false);
                }
                
                if(x < B.size()) {
                    pos = ((B.get(x)[0]-1) *3) + (B.get(x)[1]-1);
                    if(pos >= 0 && pos <= 8) cells[pos].setBackground(Color.BLACK);

                    if(!go) cells[pos].setEnabled(true);
                    else cells[pos].setEnabled(false);
                }

                if(!S.isEmpty()) {
                    if(x < S.size()) {
                        pos = ((S.get(x)[0]-1) *3) + (S.get(x)[1]-1);
                        if((pos >= 0 && pos <= 8) && !(T.contains(S.get(x)))) 
                            cells[pos].setText("Second");
                    }
                }

                if(!T.isEmpty()) {
                    if(x < T.size()) {
                        pos = ((T.get(x)[0]-1) *3) + (T.get(x)[1]-1); 
                        if(pos >= 0 && pos <= 8) cells[pos].setText("Third");
                    }
                }
                
                x++;
            }
        };

        timer.addActionListener(update);
        timer.start();
    }

    //-------------------------------------------------------------------


    JPanel grid = new JPanel(new GridLayout(3, 3, 6, 6));
    JButton cells[] = new JButton[9];

    private void loadGrid() {
        grid.setOpaque(false); // removes bg colour
        
        int gridSize = 444;
        grid.setPreferredSize(new Dimension(gridSize, gridSize));

        // Buttons
        for(int n = 0; n != 9; n++) {
            cells[n] = new JButton();
            cells[n].setBackground(Color.WHITE);
            cells[n].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    for(int n = 0; n != 9; n++) 
                        if(e.getSource() == cells[n]) nextPlayerMove(n);
                }

            });
            
            grid.add(cells[n]);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String title = "Mystery Game";
        JFrame frame = new JFrame(title);

        // Window
        int winWidth = 800;
        int winLength = 400;
        frame.setSize(new Dimension(winWidth, winLength));

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        
        Program program = new Program();
        
        //Combining it all
        panel.add(program.grid);
        frame.add(panel);
        
        program.loadGrid();

        boolean isRunning = true;
        while (isRunning = program.gameLogic());

        if(!isRunning) {
            program.gameOver();

            for (JButton button : program.cells) {
                button.setEnabled(false);
                Thread.sleep(10000);
                frame.dispose();
            }
        }
    }
    
}