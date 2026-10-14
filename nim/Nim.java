package jeux.nim;

import java.util.ArrayList;
import java.util.List;

import jeux.Game;

public class Nim implements Game<List<Integer>, Integer, Integer> {

    private List<Integer> currentState;
    private Integer nbOfPlayer; // We ignore this parameter for the Nim game because it is played with only 2 players
    private Integer maxDepth;

    public Nim(int numStick, int nbOfPlayer) {
        this.currentState = new ArrayList<>();
        currentState.add(0);
        currentState.add(numStick);
        if (nbOfPlayer != 2) {
            throw new UnsupportedOperationException("ERREUR : Nombre de joueurs différent de 2.");
        }
        this.nbOfPlayer = nbOfPlayer;
        maxDepth = Integer.MAX_VALUE; // initial value
    }

    @Override
    public List<Integer> getInitialState() {
        return currentState;
    }

    @Override
    public Integer[] getPlayers() {
        if (nbOfPlayer != 2) {
            throw new UnsupportedOperationException("ERREUR : Nombre de joueurs différent de 2.");
        }
        return new Integer[]{0,1};
    }

    @Override
    public Integer getPlayer(List<Integer> state) {
        return state.get(0);
    }

    @Override
    public List<Integer> getActions(List<Integer> state) {
        List<Integer> actions = new ArrayList<>();

        int numStick = state.get(1);

        if (numStick >= 1) {actions.add(1);}
        if (numStick >= 2) {actions.add(2);}
        if (numStick >= 3) {actions.add(3);}

        return actions;
    }

    @Override
    public List<Integer> getResult(List<Integer> state, Integer action) {
        List<Integer> newState = new ArrayList<>();

        if (action < 1 || action > 3)
            throw new UnsupportedOperationException("Impossible action");

        newState.add(1 - state.get(0));

        if (state.get(1) - action < 0) {
            throw new UnsupportedOperationException("Impossible action");
        }
        else {
            newState.add(state.get(1) - action);
        }

        return newState;
    }

    @Override
    public boolean isTerminal(List<Integer> state) {
        return state.get(1) == 0;
    }

    @Override
    public double getUtility(List<Integer> state, Integer player) {
        if (state.get(0) == 1 - player) {
            if (state.get(1) == 1)
                return Double.POSITIVE_INFINITY;
            else if (((state.get(1) - 1) % 4) == 0)
                return 1;
            else
                return -1;
        } else {
            if (state.get(1) == 1)
                return Double.NEGATIVE_INFINITY;
            else if (((state.get(1) - 1) % 4) == 0)
                return -1;
            else
                return 1;
        }
    }

    @Override
    public boolean isMax(Integer player) {
        return player == 0;
    }

    public void setDepth(int depth) {
        if (depth < 1)
            throw new UnsupportedOperationException("Impossible action, depth < 1");

        maxDepth = depth;
    }

    @Override
    public int getDepth() {
        return maxDepth;
    }

    public int getNumStick(List<Integer> state) {
        return state.get(1);
    }

}
