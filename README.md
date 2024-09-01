<div align="center">

# ♞ Visualization of Knight's Tour ♞

<img src="https://github.com/carson-yg/KnightsTour/blob/main/KnightTour-2/SolutionGif.gif" alt="Knight's Tour Gif">

</div>

## Heuristic Solution 📈

This project implements a solution to the [Knight's Tour](https://en.wikipedia.org/wiki/Knight%27s_tour#) problem using [Warnsdorff's rule](https://en.wikipedia.org/wiki/Knight%27s_tour#Warnsdorf's_rule), a heuristic following these principles:

- The knight is moved to a square from which the knight will have the fewest onward moves. The idea is to always move the knight to a position where there are fewer possibilities for future moves, ensuring that no squares are missed.

## Optimized Heuristic Solution 🚀

In addition to the basic heuristic, there's optimized solution that includes a tie-breaking mechanism:

- **Accessibility**: For each potential move, we calculate the accessibility (the number of valid onward moves possible from that position). We then select the move with the highest accessibility, preserving more options for future moves.
  
- **Random Selection**: If multiple moves have the same level of accessibility, the algorithm randomly selects from these tied moves.

## Usage 🎮

- **Moving Your Knight**: Click on any square within the GUI to place your knight's starting position

- **Next Button**: Moves the knight to the next optimal position based on the selected heuristic, allowing you to step through the tour one move at a time.

- **Run Button**: Automatically visualizes the entire Knight's Tour, displaying each move sequentially. Each visited square will be numbered to indicate the order in which the knight made its stops.

- **Stop Button**: Stops the visualization
