package jeux.grundy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jeux.Game;

public class Grundy implements Game<GrundyState, List<Integer>, Integer>{

    GrundyState initialState;
    Integer playerMax;
    int maxDepth;

    public static void printTheBoard(GrundyState state)
    {
        Map<Integer, Integer> board = state.getBoard();
        System.out.println("Here are the differents stacks : ");
        board.forEach((stack, nbOfStacks) -> {
            System.out.println( stack + " : " + nbOfStacks);
        });
    }

    public Grundy(Integer initialPile, Integer startingPlayer) {
        if (startingPlayer < 0 || startingPlayer > 1) {
            throw new UnsupportedOperationException("The player must be 0 or 1.");
        }
        playerMax = 1 - startingPlayer;
        Map<Integer, Integer> board = new HashMap<>();
        board.put(initialPile, 1);
        this.initialState = new GrundyState(startingPlayer, board);
        maxDepth = Integer.MAX_VALUE;
    }

    @Override
    public GrundyState getInitialState() {
        return initialState;
    }

    @Override
    public Integer[] getPlayers() {
        return new Integer[]{0,1};
    }

    @Override
    public Integer getPlayer(GrundyState state) {
        return state.getPlayer();
    }

    @Override
    public List<List<Integer>> getActions(GrundyState state) {
        List<List<Integer>> actions = new ArrayList<>();
        Map<Integer, Integer> board = state.getBoard();
        board.forEach((stack, nbOfStacks) ->{
            if (stack == 3) { // If the pile size is 3, the following loop does not work
                List<Integer> action = new ArrayList<>();
                action.add(stack);
                action.add(1);
                actions.add(action);
            } else {
                for (int i = 1; i < stack/2; i++) {
                    if (i != stack - i) {
                        List<Integer> action = new ArrayList<>();
                        action.add(stack);
                        action.add(i);
                        actions.add(action);
                    }
                }
            }
        });
        return actions;
    }

    @Override
    public GrundyState getResult(GrundyState state, List<Integer> action) {
        Map<Integer, Integer> board = state.getBoard();
        Integer stack = action.get(0);
        Integer split = action.get(1);

        if (!board.containsKey(stack)) {
            throw new UnsupportedOperationException("There is no stack of " + stack + " tokens.");
        }

        if (split < 1 || split >= stack || split == stack - split) {
            throw new UnsupportedOperationException("It is not possible to split " + stack + " this way.");
        }

        board.remove(stack, 1); // If there is only one pile of `stack` tokens, remove it from the game
        if (board.containsKey(stack)) {
            Integer nbOfStack = board.get(stack);
            assert nbOfStack > 1; // There are at least 2 piles of `stack` tokens
            board.put(stack, nbOfStack - 1);
        }

        Integer otherSplit = stack - split;

        if (board.containsKey(split)) {
            Integer nbOfStack = board.get(split);
            assert nbOfStack > 1;
            board.put(split, nbOfStack + 1);
        } else {
            board.put(split, 1);
        }
        if (board.containsKey(otherSplit)) {
            Integer nbOfStack = board.get(otherSplit);
            assert nbOfStack > 1;
            board.put(otherSplit, nbOfStack + 1);
        } else {
            board.put(otherSplit, 1);
        }

        GrundyState newState = new GrundyState(1 - state.getPlayer(), board);

        return newState;
    }

    @Override
    public boolean isTerminal(GrundyState state) {
        Set<Integer> keys = state.getBoard().keySet();
        // A state is terminal if and only if it contains only piles of 1 and/or 2 (we cannot have no tokens in the game)
        return keys.equals(Set.of(1, 2)) || keys.equals(Set.of(1)) || keys.equals(Set.of(2));
    }

    @Override
    public double getUtility(GrundyState state, Integer player) {
        // We use MAX_VALUE and MIN_VALUE because there are cases where, no matter what the computer plays,
        // it will only lose, which prevents the MinMax algorithm from choosing an action (because all moves are losing)
        // e.g., if the initial number of tokens is 4, the only possible move is to split 4 = 1 + 3
        // and the opponent simply has to choose 3 = 1 + 2 (which is the only possible move)
        if (isTerminal(state) && state.getPlayer() == 1 - player) return Integer.MAX_VALUE;
        if (isTerminal(state) && state.getPlayer() == player) return Integer.MIN_VALUE;

        Map<Integer, Integer> board = state.getBoard();

        Integer[] res = {0}; // Using an array to store the result despite the loop

        board.forEach((stack, nbOfStack) -> {
            if (stack > 2) {
                for (int i = 0; i < nbOfStack; i++) {
                    res[0] = res[0] ^ stack;
                }
            }
        });

        if (state.getPlayer() == 1 - player && res[0] != 0) return -1;
        if (state.getPlayer() == 1 - player && res[0] == 0) return 1;
        if (state.getPlayer() == player && res[0] == 0) return -1;
        return 1;
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
