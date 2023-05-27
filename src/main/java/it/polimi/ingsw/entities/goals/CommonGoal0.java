package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import java.util.HashMap;

/**
 * The always present game common goal (the one on the board)
 */
public class CommonGoal0 implements Goal{

    //region CONSTRUCTOR
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        HashMap<Integer, Integer> res = searchGroups(bookshelf.getBookshelfColors());
        int points = 0;

        for(Integer t: res.keySet()){
            if (res.get(t) == 3)points = points+2;
            if(res.get(t)==4)points=points+3;
            if(res.get(t)==5)points=points+5;
            if(res.get(t)>5)points=points+8;
        }
        return points;
    }
    //endregion

    //region METHODS
    /**
     * Searches for the biggest group of tiles of the same type
     * @param matrix type abstracted bookshelf
     * @return the biggest group size
     */
    private HashMap<Integer, Integer> searchGroups(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        HashMap<Integer, Integer> num = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j] && matrix[i][j] != UNAVAILABLE) {
                    int value = matrix[i][j];
                    num.put(value, findAdjacent(matrix, visited, i, j, value));
                }
            }
        }
        return num;
    }

    /**
     * Counts the size of the group of cards of the same type
     * @param matrix type abstracted bookshelf
     * @param visited matrix of already visited cards
     * @param row to check
     * @param col to check
     * @param value contained in tile
     * @return number of cards of the same type in the group
     */
    private int findAdjacent(int[][] matrix, boolean[][] visited, int row, int col, int value) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || visited[row][col] || matrix[row][col] != value) {
            return 0;
        }

        visited[row][col] = true;

        int count = 1;
        count += findAdjacent(matrix, visited, row - 1, col, value); //Up
        count += findAdjacent(matrix, visited, row + 1, col, value); //Down
        count += findAdjacent(matrix, visited, row, col - 1, value); //Left
        count += findAdjacent(matrix, visited, row, col + 1, value); //Right

        return count;
    }
    //endregion

}