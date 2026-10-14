package jeux.nim;

import jeux.AlphaBeta;
import jeux.AlphaBetaWithMaxDepth;
import jeux.MinMaxWithMaxDepth;
import jeux.MinimaxSearch;

import java.util.List;

public class NimTest {

    public static void main(String[] args) {
        Nim game = new Nim(15, 2);
        game.setDepth(5);
        MinimaxSearch<List<Integer>, Integer, Integer> minimaxSearch =
            MinimaxSearch.createFor(game);

        MinMaxWithMaxDepth<List<Integer>, Integer, Integer> minimaxSearchWithMaxDepth =
        MinMaxWithMaxDepth.createFor(game);

        AlphaBeta<List<Integer>, Integer, Integer> alphaBetaSearch =
        AlphaBeta.createFor(game);

        AlphaBetaWithMaxDepth<List<Integer>, Integer, Integer> alphaBetaWithMaxDepthSearch =
        AlphaBetaWithMaxDepth.createFor(game);

        List<Integer> state = game.getInitialState();
	int action = -1;
	System.out.println("Machine player, what is your action?");
	//
        action = minimaxSearch.makeDecision(state);
        System.out.println("Metrics for Minimax without max depth : " + minimaxSearch.getMetrics());
	System.out.println("Chosen action without max depth is " + action);
        action = minimaxSearchWithMaxDepth.makeDecision(state);
        System.out.println("Metrics for Minimax with max depth : " + minimaxSearchWithMaxDepth.getMetrics());
	System.out.println("Chosen action with max depth is " + action);
        action = alphaBetaSearch.makeDecision(state);
        System.out.println("Metrics for AlphaBeta : " + alphaBetaSearch.getMetrics());
	System.out.println("Chosen action with AlphaBeta is " + action);
        action = alphaBetaWithMaxDepthSearch.makeDecision(state);
        System.out.println("Metrics for AlphaBeta with max depth : " + alphaBetaWithMaxDepthSearch.getMetrics());
	System.out.println("Chosen action with AlphaBeta and max depth is " + action);
   }
}
