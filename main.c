#include <stdio.h>
#include <stdbool.h>


/*-----------------------------

C = 1, 2, 3

N = 0, ..., 16

M = | 1, 1 | 1, 2 | 1, 3 |
    --------------------
    | 2, 1 | 2, 2 | 2, 3 |
    --------------------        
    | 3, 1 | 3, 2 | 3, 3 |

V = 1, 0
    
-------------------------------------------

Anasa's Ramblings    

- 3x3 map
- Two players: R & B

The game seems to be the player trying to find a specific coordinate first?
With the found var....



-----------------------------*/


typedef int set[9][2];

// set M = {
//     {1, 1}, {1, 2}, {1, 3},
//     {2, 1}, {2, 2}, {2, 3},
//     {3, 1}, {3, 2}, {3, 3}
// };

bool good, go, start, over, found;
int val;
set R;
// , B, S, T, F;


// void Facts() {
// F = M - (R ∪ B)
// Set of elements excluding the union of R & B

void addToSet(set SET, int[2] ELEMENT) {
    int curLength = sizeof()
}

void nexttPlayerMove(int pos[2]) {
    // if(start) {

        // start = false;
    // }

    if(!over) {
        if(start) {
            if(go) { // player R's turn
                addToSet(R, pos);
                addToSet(S, pos);
                
                good = true;
            }
            else { // player B's turn
                addToSet(B, pos);
                addToSet(S, pos);

                good = true;
            }
        }
        else {

        }

        if(good) { // turn switcher & counter
            good = !good;
            go = !gol
            val++;
        }
    }
}

int main() {
    // good = false;
    // go = true;
    // found = false;
    
    val = 0;
    
    start = true;
    while(start) {

        
    }

    // printf("%s", gameOver(/*R & B*/));

    return 0;
}