package jeux.tictactoe;

import java.util.ArrayList;
import java.util.List;

import jeux.Game;

public class TicTacToe implements Game<List<Integer>, Integer, Integer>{

    List<Integer> initialState;
    Integer playerMax;
    int maxDepth;

    private static char getToken(Integer value) {
        switch (value) {
            case 0:
                return 'X';
            case 1:
                return 'O';
            default:
                break;
        }
        return ' ';
    }

    public static void printTheBoard(List<Integer> state)
    {
        System.out.println( " " + getToken(state.get(1)) + " | " +  getToken(state.get(2)) + " | " + getToken(state.get(3)));
        System.out.println( " - + - + - " );
        System.out.println( " " + getToken(state.get(4)) + " | " +  getToken(state.get(5)) + " | " + getToken(state.get(6)));
        System.out.println( " - + - + - " );
        System.out.println( " " + getToken(state.get(7)) + " | " +  getToken(state.get(8)) + " | " + getToken(state.get(9)));
    }

    public TicTacToe(Integer startingPlayer) {
        if (startingPlayer < 0 || startingPlayer > 1) {
            throw new UnsupportedOperationException("Le joueur doit être 0 ou 1.");
        }
        playerMax = 1 - startingPlayer;
        this.initialState = new ArrayList<>();
        maxDepth = Integer.MAX_VALUE;
        initialState.add(startingPlayer);
        for (int i = 0; i < 9; i++) {
            initialState.add(-1); // Fill the board with 9 empty cells
        }
    }

    @Override
    public List<Integer> getInitialState() {
        return initialState;
    }

    @Override
    public Integer[] getPlayers() {
        return new Integer[]{0,1};
    }

    @Override
    public Integer getPlayer(List<Integer> state) {
        return state.get(0);
    }

    @Override
    public List<Integer> getActions(List<Integer> state) {
        List<Integer> actions = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            if (state.get(i) == -1) actions.add(i);
        }
        return actions;
    }

    @Override
    public List<Integer> getResult(List<Integer> state, Integer action) {
        if (action < 1 || action > 9 || state.get(action) != -1) {
            throw new UnsupportedOperationException("L'action " + action + " est impossible !");
        }

        List<Integer> newState = new ArrayList<>();

        newState.add(1 - state.get(0));

        for (int i = 1; i < 10; i++) {
            newState.add(state.get(i));
        }

        newState.set(action, state.get(0));

        return newState;
    }

    @Override
    public boolean isTerminal(List<Integer> state) {
        for (int i = 0; i < 3; i++) {
            if (state.get(1 + 3*i) == state.get(2 + 3*i)
             && state.get(1 + 3*i) == state.get(3 + 3*i)
             && state.get(1 + 3*i) != -1)
                return true; // a winning row

            if (state.get(i + 1) == state.get(i + 4)
             && state.get(i + 1) == state.get(i + 7)
             && state.get(i + 1) != -1)
                return true; // a winning column
        }

        if (state.get(1) == state.get(5)
         && state.get(1) == state.get(9)
         && state.get(1) != -1)
            return true; // a winning diagonal

        if (state.get(3) == state.get(5)
         && state.get(3) == state.get(7)
         && state.get(3) != -1)
            return true; // a winning diagonal

        return !(state.contains(-1)); // If the list does not contain -1, the board is full
    }

    @Override
    public double getUtility(List<Integer> state, Integer player) {
        if (isTerminal(state) && state.get(0) == 1 - player && state.contains(-1)) return Double.POSITIVE_INFINITY;
        if (isTerminal(state) && state.get(0) == player && state.contains(-1)) return Double.NEGATIVE_INFINITY;
        if (isTerminal(state) && !state.contains(-1)) return 0;

        Integer X2 = 0, X1 = 0, O2 = 0, O1 = 0;

        Integer[][] winningLines = {
            {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, // Rows
            {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, // Columns
            {1, 5, 9}, {3, 5, 7}             // Diagonals
        };

        for (Integer[] line : winningLines) {
            Integer xCount = 0, oCount = 0, emptyCount = 0;

            for (Integer index : line) {
                Integer value = state.get(index);
                if (value == 0) xCount++;      // X token
                else if (value == 1) oCount++; // O token
                else emptyCount++;             // Empty cell
            }

            // Analyze the line
            if (xCount == 2 && emptyCount == 1) X2++;
            if (xCount == 1 && emptyCount == 2) X1++;
            if (oCount == 2 && emptyCount == 1) O2++;
            if (oCount == 1 && emptyCount == 2) O1++;
        }

        double res = 3 * X2 + X1 - (3 * O2 + O1);

        if (state.get(0) == 1 - player) return res;

        return -res;
    }

    @Override
    public boolean isMax(Integer player) {
        return player == playerMax;
    }

    @Override
    public int getDepth() {
        return maxDepth;
    }

    public void setDepth(Integer depth) {
        maxDepth = depth;
    }

}
