<div align="center">

# Visualization of Knight's Tour

<img src="https://github.com/carson-yg/KnightsTour/blob/main/KnightTour-2/SolutionGif.gif" alt="Knight's Tour Gif">

</div>

## Heuristic Solution

This project implements a solution to the Knight's Tour problem using Warnsdorff's rule, a well-known heuristic that automatically solves the problem by following these principles:

- **Warnsdorff's Rule**: The knight is moved to a square from which the knight will have the fewest onward moves. The idea is to always move the knight to a position where there are fewer possibilities for future moves, ensuring that no squares are missed.

## Optimized Heuristic Solution

In addition to the basic heuristic, an optimized solution is provided that includes a tie-breaking mechanism to enhance the effectiveness of the algorithm:

- **Accessibility**: For each potential move, we calculate the accessibility (the number of valid onward moves possible from that position). We then select the move with the highest accessibility, thereby preserving more options for future moves.
  
- **Random Selection**: If multiple moves have the same level of accessibility, the algorithm randomly selects from these tied moves. This introduces variability, which can be beneficial in discovering different possible solutions to the tour.
