#include <stdio.h>
#include <stdbool.h>

//Translations?

/* 
    C = 1, 2, 3

    N = 0, ..., 16

    M = 01 02 03 //2D array
        11 12 13
        21 22 23
        31 32 33

    V = 1, 0

    -------------------------------

    R, B, S, T = {} EMPTY ARRAY

    -------------------------------

    F = ???
    over <-> ???

    -------------------------------

*/

// void variables() {
//     bool good = false;
//     bool go = true;
//     bool start = true;
//     bool over;
//     bool found = false;

//     int val = 0;

//     int R = {};

// }

// void remove(int pos[]) {
//     if(go) {
//         // remove pos from R
//     }
//     else {
//         // remove pos from B
//     }

//     // remove pos from S
//     // remove pos from T
// }


// void expand(int pos[]) {
//     int a = pos[0];
//     int b = pos[1];

//     int u[2] = {a - 1, b};
//     int d[2] = {a + 1, b};
//     int k[2] = {a, b - 1};
//     int r[2] = {a, b  + 1};

//     remove(pos);

//     if(go) replace(u);
//     else replace(d);

//     replace(k);
//     replace(r);
// }

// void replace(int pos[]) {
//     bool found = false;

//     if(go) {
//         if(pos == B) {
//             // remove pos from B
//             found = true;
//         }

//         if(pos == R) found = true;
//         else {
//             // add pos to R
//             found = true;
//         }
//     }
//     else {
//         if(pos == R) {
//             // remove pos from R
//             found = true;
//         }

//         if(pos == B) found = true;
//         else // add poss to B
//     }

//     if(found) {
//         if(pos != S) {
//             // add pos to S
//             found = false;
//         }
//         else if(pos != T) {
//             // add pos to T
//             expand(pos);
//         }
//     }
// }

// void update() {
//     bool good = false;

//     if() { // S doesnt contain pos
//         // add pos to S
//         good = !good;
//     }

//     else if(!good &&) {// T doesnt contain pos
//         // add pos to T
//         expand(pos);
//     }
// }

// void nextPlayerMove() {
//     if(!over) {
//         if(start) {
//             if(go) {
//                 // add pos to R
//                 // add pos to S
//                 good = true;
//             }
//             else {
//                 // add pos to B
//                 // add pos to S
//                 good = true;
//             }
//         }
//         else {
//             if((go && /*R contains pos*/) || (!go && /*B contains pos*/)) {
//                 update(pos);
//                 good = true;
//             }
//         }

//         if(good) {
//             good = !good;
//             go = !go;
//             val += 1;
//         }
//     }

//     if(start && /*len of R == 1*/ && /*len of B == 1*/) {
//         start = false;
//     }
// }

void gameOver() {
    char * resultOptions = {
        "R wins",
        "B wins",
        "draw"
    };
    int result;

    if(over) {
        if(/*len of R > len of B*/) result = 0;
        if(/*len of R < len of B*/) result = 1
        if(/*len of R == len of B*/) result = 2
    }

    printf("%s", result);

}

int main() {
    int R[][2];
    int B[][2];

    gameOver(/*R & B*/);

    return 0;
}