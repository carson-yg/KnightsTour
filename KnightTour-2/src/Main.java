import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private int[][] board; // stores all the moves
    private int currRow;
    private int currCol;
    private int[] rowMove = {1, -1, 1, -1, 2, -2, 2, -2};
    private int[] colMove = {2, 2, -2, -2, 1, 1, -1, -1};
    private int count;
    private Image img;

    public static void main(String[] args) {
        Main kt = new Main();
        kt.runOnce();
        kt.runSims(10);
    }

    public Main() {
        board = new int[8][8];
        resetBoard();
        this.setStartingLocation(0, 0); // Set default starting location

        try {
            img = ImageIO.read(new File("/Users/carsonyang/AP-CSA/KnightTour-2/img/knight.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runOnce() {
        resetBoard(); // Reset board before starting a new tour
        int num = tour();
        System.out.println("Knight got trapped after " + num + " moves. Ending location was: (" + currRow + ", " + currCol + ")");
        printBoard();
    }

    public void runSims(int times) {
        Random r = new Random();
        int total = 0;
        for (int x = 0; x < times; x++) {
            int ranRow = r.nextInt(8);
            int ranCol = r.nextInt(8);
            this.setStartingLocation(ranRow, ranCol);
            resetBoard(); // Reset board before starting a new tour
            int num = tour();
            total += num;
            System.out.println("Knight got trapped after " + num + " moves. Ending location was: (" + currRow + ", " + currCol + ")");
            printBoard();
        }
        double average = (double) total / times;
        System.out.println("Average: " + average);
    }

    public void resetBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = -1;
            }
        }
        count = 0;
    }

    public void setStartingLocation(int row, int col) {
        currRow = row;
        currCol = col;
    }

    public void makeRandomMove() {
        boolean var1 = false;
        Random var2 = new Random();
  
        while(!var1) {
           int var3 = var2.nextInt(8);
           int var4 = this.currRow + this.rowMove[var3];
           int var5 = this.currCol + this.colMove[var3];
           if (var4 >= 0 && var4 < 8 && var5 >= 0 && var5 < 8 && this.board[var4][var5] == -1) {
              this.currRow = var4;
              this.currCol = var5;
              ++this.count;
              this.board[this.currRow][this.currCol] = this.count;
              var1 = true;
           }
        }
  
     }

    public int tour() {
        board[currRow][currCol] = count; // Start from the initial position with count 0
        while (!trapped()) {
            makeBestMove();
        }
        return count;
    }

    public void draw(Graphics g, int xShift, int yShift, int SQ) {
        g.setColor(new Color(100, 120, 70));
        g.fillRect(xShift, yShift, SQ * 8, SQ * 8);

        img = img.getScaledInstance(SQ / 2 + SQ / 5, SQ, Image.SCALE_SMOOTH);

        g.setColor(new Color(255, 255, 255));
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if ((row + col) % 2 == 0) {
                    g.fillRect(xShift + col * SQ, yShift + row * SQ, SQ, SQ);
                }
                if (board[row][col] != -1) {
                    g.setColor(new Color(0, 0, 255));
                    g.setFont(new Font("", Font.BOLD, 25));
                    g.drawString("" + board[row][col], xShift + col * SQ + SQ / 4, yShift + row * SQ + SQ / 2);
                    g.setColor(new Color(255, 255, 255));
                }
            }
        }
        g.drawImage(img, xShift + currCol * SQ + SQ / 8, yShift + currRow * SQ, null);
    }

    public void draw(Graphics g) {
        this.draw(g, 0, 0, 80);
    }

    public void printBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                System.out.print(board[x][y] + "  ");
            }
            System.out.println("\n");
        }
    }

      // Selects and moves the knight to an optimal location which makes it 
  // more likely a complete tour will be accomplished
  // Precondition:  The knight is not trapped when this method is called
  public void makeBetterMove(){
    int prevRow = currRow;
    int prevCol = currCol;
    int nextRow = 0;
    int nextCol = 0;
    int posMoveCounter = 0;
    int prevMoveCounter = 8;
    //System.out.println("currRow" +currRow + "  currCol" + currCol);
    for (int i = 0; i < 8; i++){
      currRow += rowMove[i];
      currCol += colMove[i];
      int tempRow = currRow;
      int tempCol = currCol;
      //System.out.println("currRow" +currRow + "  currCol" + currCol);
      if(!outOfBounds()){
        for(int x = 0; x < 8; x++){
          currRow += rowMove[x];
          currCol += colMove[x];
          
          if(!outOfBounds()){
            posMoveCounter++;
          }

          //System.out.println("currRow" +currRow + "  currCol" + currCol);
          currRow = tempRow;
          currCol = tempCol;
        }
        
        if (posMoveCounter < prevMoveCounter){
            prevMoveCounter = posMoveCounter;
            nextRow = prevRow + rowMove[i];
            nextCol = prevCol + colMove[i];
            //System.out.println("nextRow" +nextRow + "  nextCol" + nextCol + "   posMoveCounter" + posMoveCounter + "   rowMove" + rowMove[i] + "   colMove" + colMove[i] + "   prevMoveCounter" + prevMoveCounter);
        }
        posMoveCounter = 0;
      }

      currRow = prevRow;
      currCol = prevCol;
    }
    
    currRow = nextRow;
    currCol = nextCol;
    //System.out.println("currRow" +currRow + "  currCol" + currCol + "(changed)");
    count++;
    board[currRow][currCol] = count;

    //printBoard(board);
  }

   public void makeBestMove() {
        List<Move> bestMoves = new ArrayList<>();
        int minOnwardMoves = Integer.MAX_VALUE;

        for (int i = 0; i < 8; i++) {
            int nextRow = currRow + rowMove[i];
            int nextCol = currCol + colMove[i];
            if (isValidMove(nextRow, nextCol)) {
                int onwardMoves = countOnwardMoves(nextRow, nextCol);
                if (onwardMoves < minOnwardMoves) {
                    minOnwardMoves = onwardMoves;
                    bestMoves.clear();
                    bestMoves.add(new Move(nextRow, nextCol, onwardMoves));
                } else if (onwardMoves == minOnwardMoves) {
                    bestMoves.add(new Move(nextRow, nextCol, onwardMoves));
                }
            }
        }

        if (!bestMoves.isEmpty()) {
            Move chosenMove = breakTies(bestMoves);
            currRow = chosenMove.row;
            currCol = chosenMove.col;
            count++;
            board[currRow][currCol] = count;
        }
    }

    private Move breakTies(List<Move> moves) {
        if (moves.size() == 1) {
            return moves.get(0);
        }

        // Implement tie-breaking strategies
        Move bestMove = moves.get(0);
        int maxAccessibility = -1;

        for (Move move : moves) {
            int accessibility = calculateAccessibility(move.row, move.col);
            if (accessibility > maxAccessibility) {
                maxAccessibility = accessibility;
                bestMove = move;
            }
        }

        // If there are still ties, choose randomly
        List<Move> tiedMoves = new ArrayList<>();
        for (Move move : moves) {
            if (calculateAccessibility(move.row, move.col) == maxAccessibility) {
                tiedMoves.add(move);
            }
        }

        return tiedMoves.get(new Random().nextInt(tiedMoves.size()));
    }

    private int calculateAccessibility(int row, int col) {
        int accessibility = 0;
        for (int i = 0; i < 8; i++) {
            int newRow = row + rowMove[i];
            int newCol = col + colMove[i];
            if (isValidMove(newRow, newCol)) {
                accessibility++;
            }
        }
        return accessibility;
    }

    private static class Move {
        int row, col, onwardMoves;

        Move(int row, int col, int onwardMoves) {
            this.row = row;
            this.col = col;
            this.onwardMoves = onwardMoves;
        }
    }

    private int countOnwardMoves(int row, int col) {
        int moveCount = 0;
        for (int i = 0; i < 8; i++) {
            int newRow = row + rowMove[i];
            int newCol = col + colMove[i];
            if (isValidMove(newRow, newCol)) {
                moveCount++;
            }
        }
        return moveCount;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8 && board[row][col] == -1;
    }

    public boolean outOfBounds(){
        if ((currRow >= 0 && currRow < 8) && (currCol >= 0 && currCol < 8) && (board[currRow][currCol] == -1)){
          return false;
        }
        return true;
      }

    public boolean trapped() {
        for (int i = 0; i < 8; i++) {
            int nextRow = currRow + rowMove[i];
            int nextCol = currCol + colMove[i];
            if (isValidMove(nextRow, nextCol)) {
                return false;
            }
        }
        return true;
    }

    
}
