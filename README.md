# Graph Colouring Game

Graph Colouring game, which uses a backtracking algorithm in order to determine the chromatic number of an undirected graph. GUI was build using JavaFX. A Hint system is implemented as well for user's convenience.

The game has 3 modes:

## 1. Random
Generates a random graph. The amount of vertices and edges are specified by the user. The user could also load from a file. The edges should be colored in a particular sequence. Once a vertice's been colored, the color can't be changed. Adjacent vertices can't have the same color. A circular layout algorithm is implemented here in order to re-order the randomly generated graph, if needed.
## 2. Best Upper Bound
Color a graph with as little colors as possible for a certain amount of time. Adjacent vertices can't have the same color.
## 3. Until the bitter end
You can only finish this game mode if you find the chromatic number of the graph (meaning that you've used the minimum amount of colors possible to color the graph). Adjacent vertices can't have the same color.
