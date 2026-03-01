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
    
    int M[][] = {
        {1, 1}, {1, 2}, {1, 3},
        {2, 1}, {2, 2}, {2, 3},
        {3, 1}, {3, 2}, {3, 3}
    };

    ArrayList<int[]> R = new ArrayList<int[]>();
    ArrayList<int[]> B = new ArrayList<int[]>();
    ArrayList<int[]> S = new ArrayList<int[]>();
    ArrayList<int[]> T = new ArrayList<int[]>();
    // ArrayList<int[]> F = new ArrayList<int[]>(Arrays.asList(M));

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
            
            if(R.contains(pos)) {
                System.out.println(go);

                found = true;
            }
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
            if(S.contains(pos)) {
                if(!T.contains(pos)) {
                    T.add(pos);
                    expand(raw);
                }
            }
            else {
                S.add(pos);
                found = false;
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
        
        // removed the if(go) condition because it doesnt seem right?
        // ensures that satisfies only this condition (pos ∈ M) 
        // if(pos[0])
        int u_raw[] = {pos[0]-1, pos[1]};
        int d_raw[] = {pos[0]+1, pos[1]};
        int k_raw[] = {pos[0], pos[1]-1};
        int r_raw[] = {pos[0], pos[1]+1};

        if(u_raw[0] > 0) replace(u, raw);
        if(d_raw[0] < 4) replace(d, raw);
        if(k_raw[1] > 0) replace(k, raw);
        if(r_raw[1] < 4) replace(r, raw);
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
        // if(!over) {} removed because over is always false anyways
        
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
        */

        
       // F = M − (R ∪ B)
        ArrayList<int[]> remainingBoard = new ArrayList<int[]>(Arrays.asList(M)); 
        remainingBoard.removeAll(R);
        remainingBoard.removeAll(B);

        int remainingBoardElements = remainingBoard.size();
        
        // over ↔ (|F| = 3 ∨ val ≥ 20 ∨ ¬start ∧ (|R| > 0 ∧ |B| = 0 ∨ |R| = 0 ∧ |B| > 0))
        int playerR = R.size();
        int playerB = B.size();

        return !(remainingBoardElements == 3 
            || !start 
            && ((playerR > 0 && playerB == 0) || (playerB > 0 && playerR == 0))
        );
    }

    private void gameOver() {
        String result = "uwu";

        if(R.size() > B.size()) result = "R wins";
        if(R.size() < B.size()) result = "B wins";
        if(R.size() == B.size()) result = "Draw";

        System.out.println(result);
    }
    
    //-------------------------------------------------------------------

    private void updateCell() {
        int pos = 0;

        
        for(int n = 0; n != 9; n++) {
            cells[n].setBackground(Color.WHITE);
            cells[n].setText("");

            if(!R.isEmpty()) {
                for(int[] element : R) {
                    pos = ((element[0]-1) *3) + (element[1]-1); // translates coordinate format to raw num
                    if(pos >= 0 && pos <= 8) cells[pos].setBackground(Color.RED);
                }
            }

            if(!B.isEmpty()) {
                for(int[] element : B) {
                    pos = ((element[0]-1) *3) + (element[1]-1); // translates coordinate format to raw num
                    if(pos >= 0 && pos <= 8) cells[pos].setBackground(Color.BLACK);
                }
            }
            
            if(!S.isEmpty()) {
                for(int[] element : S) {
                    pos = ((element[0]-1) *3) + (element[1]-1); // translates coordinate format to raw num
                    if(pos >= 0 && pos <= 8) cells[pos].setText("S");
                }
            }
        }
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

    public static void main(String[] args) {
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

        boolean isRunning = program.gameLogic();
        while (isRunning) {
            isRunning = program.gameLogic();
        }

        if(!isRunning) {
            program.gameOver();
            // frame.dispose();
        }
    }
    
}