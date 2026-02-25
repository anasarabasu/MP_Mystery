/*

C = 1 ... 3
N = 0 ... 16

M = [1, 1] [1, 2] [1, 3]
    [2, 1] [2, 2] [2, 3]
    [3, 1] [3, 2] [3, 3]

V = 0, 1

*/

import java.util.*;

public class Program {

    boolean good = false;
    boolean go = true;
    boolean start = true;
    boolean found = false;

    boolean over;


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


    private void remove(int pos[]) {
        if(go) R.remove(pos);
        else B.remove(pos);
    
        S.remove(pos);
        T.remove(pos);
    }

    private void replace(int pos[]) {
        found = false;

        if(go) {
            if(B.contains(pos)) {
                B.remove(pos);
                found = true;
            }

            if(R.contains(pos)) found = true;
            else R.add(pos);
        }
        else {
            if(R.contains(pos)) {
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
                    expand(pos);
                }
            }
            else {
                S.add(pos);
                found = false;
            }

        }
    }

    private void expand(int pos[]) {
        int ab[] = pos;

        int u[] = {pos[0]-1, pos[1]};
        int d[] = {pos[0]+1, pos[1]};
        int k[] = {pos[0], pos[1]-1};
        int r[] = {pos[0], pos[1]+1};

        remove(pos);

        if(go) replace(u);
        else replace(d);

        replace(k);
        replace(r);
    }

    private void update(int pos[]) {
        good = false;

        if(!S.contains(pos)) {
            S.add(pos);
            good = !good;
        }
        else {
            if(!good && !T.contains(pos)) {
                T.add(pos);
                expand(pos);
            }
        }
    }

    private void nextPlayerMove(int pos[]) {
        if(!over) {
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
                    update(pos);
                    good = true;
                }
            }

            if(good) {
                good = !good;
                go = !go;
                val++;
            }
        }

        if(start && !R.isEmpty() && !B.isEmpty()) { //make the check to 1 uniqie val?
            start = false;
        }
    }


    // private void gameOver() {
    //     if(over) {

    //     }
    // }
    

    public static void main(String[] args) {
        Program program = new Program();
        
        
        
        ArrayList<int[]> remainingBoard = new ArrayList<int[]>(Arrays.asList(program.M));
        int remainingBoardElements = remainingBoard.size();
        
        int currentRound = program.val;
        boolean gameStarted = program.start;
        
        int playerR = program.R.size();
        int playerB = program.B.size();
        
        /*
        over ↔ (|F | = 3 ∨ val ≥ 20 ∨ ¬start ∧ (|R| > 0 ∧ |B| = 0 ∨ |R| = 0 ∧ |B| > 0))
        This is the condition for the while loop
        
        o ↔ c aka o→c ∧ c→o
        > over is always assumed false because programming languages tends to
        > therefore, 
            false→false(T) ∧ false→false(T) == while loop runs
        > only stops when c changes values because 
            false→true(T)  ∧ true→false(F)  == while loop ends
        
        so I can just use the c statement for the game loop condition, just flip it lol
        */
       boolean isRunning = !(remainingBoardElements == 3 || currentRound >= 20 || !gameStarted && ((playerR > 0 && playerB == 0) || (playerB > 0 && playerR == 0)));
       
        while(isRunning) {
            System.err.println("uwu");

            //     program.nextPlayerMove(test);


            // System Facts //

            // F = M − (R ∪ B)
            remainingBoard = new ArrayList<int[]>(Arrays.asList(program.M));
            remainingBoard.removeAll(program.R);
            remainingBoard.removeAll(program.B);
            remainingBoardElements = remainingBoard.size();
            
            // over ↔ (|F | = 3 ∨ val ≥ 20 ∨ ¬start ∧ (|R| > 0 ∧ |B| = 0 ∨ |R| = 0 ∧ |B| > 0))
            currentRound = program.val;
            gameStarted = program.start;

            playerR = program.R.size();
            playerB = program.B.size();

            isRunning = !(remainingBoardElements == 3 || currentRound >= 20 || !gameStarted && ((playerR > 0 && playerB == 0) || (playerB > 0 && playerR == 0)));
        }
    }
    
}