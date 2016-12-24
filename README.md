"# ultimate tic-tac-toe" 
Welcome to the Ultimate Tic Tac Toe Readme. Ultimate Tic Tac Toe is a recursive version of regular tic-tac-toe ("TTT").
The game board takes the shape of a large TTT board, with each square containing its own regular TTT board. Players take
turns placing tokens on the board as in regular TTT - but with a twist. The location of the token you place within the
small board determines which small board the opponent can move in. The first player can move anywhere. For example,
let's say X goes first and chooses the top-middle small board, middle square:

 | | || | | || | |
-----||-----||-----
 | | || |X| || | |     Key:
-----||-----||-----      123
 | | || | | || | |       456
-------------------      789
-------------------
 | | ||O|O|O|| | |
-----||-----||-----    Overall Status Board
 | | ||O|O|O|| | |        | |
-----||-----||-----      -----
 | | ||O|O|O|| | |        |O|
-------------------      -----
-------------------       | |
 | | || | | || | |
-----||-----||-----
 | | || | | || | |
-----||-----||-----
 | | || | | || | |

O can now go anywhere in the middle board. If they choose the bottom left square, then X's next play would be in the
bottom left board. The objective of the game is still to get three in a row - three boards. The "Overall Status Board"
shows which squares have been won. If a player would be sent to a board that has already been won (or is full), they may
place their token anywhere. There is in-game help available with examples.

Origin/Source: I did NOT invent this game - this is just an implementation of the existing game. This is the description
I worked from: https://mathwithbaddrawings.com/2013/06/16/ultimate-tic-tac-toe/
I don't think the author claims to have invented the game either, so I don't know to whom to give credit. If you're
interested in reading about the game, I highly recommend that link. There is actually quite a bit of strategy to the
game.

Features:
* Text-based/CLI interface.
* Sanitized user inputs, in-game help.
* Play with 0, 1, or 2 human players (remaining spots filled by bots that move randomly)

Features In Development:
* Smart AI - one of the main extension goals of the project was to develop a heuristic based AI
* Wildcard - how does a tied square count? The obvious answer would be that it counts for neither player, but with the
"Wildcard" rule variant, any square that is tied (full and with no winner) counts for both players.

Features Possibly Eventually Maybe:
* Machine Learning - implementing positive/negative feedback and/or starting to map terminal game-states
* Non-text interface that would allow users to click on their desired square, and highlight possible moves.
* Save game history, as well as being able to save and load games in progress

Development Notes:
When I first started this project, some sort of AI jumped into my head as a natural extension of the project. I think
this game is a fairly good fit for developing a basic AI. It's not too complex (much less so than Chess, for example)
but provides enough complexity that a brute force tree search is highly impractical. I developed a simple symmetric
heuristic that evaluated the state of the game based on the number of boards controlled by each player, giving higher
value to the middle and corner boards (in a regular TTT game, there are 4 wins that involve the middle, 3 for corners,
and just 2 for the edges). I then built a tree structure representing the possible moves by both players. I am
attempting to navigate this tree with a "minimax" algorithm. Eventually I will try to implement an "Alpha Beta Pruning"
algorithm to make the tree search more efficient.

This project was originally developed before we covered unit testing in class. The testSmartBoardEvaluation is an
example of this (as is testWildCardDetection()). The Node/Tree structure was developed with unit tests/TDD
(see TreeTester).

Further reading/Extra files:
How many possibly starting moves are there in a regular game of Tic-Tac-Toe? Nine, right? But how many *distinct* moves
are there? The answer is three (corner, edge, or middle). All starting options are rotationally symmetrical in one of
those categories. As part of considering how to develop a smart AI, one goal is to determine if there are good starting
moves, as in chess, or if some squares are more valuable than others. This is trickier than it seems however; if you
control a bunch of middle squares in the boards, that will give you an advantage - but you will have given your opponent
control of the middle board (less than ideal). Supporting this, I have determined that there are 15 possible starting
moves in Ultimate (this number might be reducible, but it's a good start. These moves are mapped out in "moves.ods".

What does BigBoard sound like? Open BigBoard.txt for a clue.

Thanks for reading!
Paul Dennis