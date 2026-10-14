package jeux;

import jeux.core.Metrics;


public class MinMaxWithMaxDepth<STATE, ACTION, PLAYER> implements
		Search<STATE, ACTION> {

	private Game<STATE, ACTION, PLAYER> game;
	private int expandedNodes;

	/** Creates a new search object for a given game. */
	public static <STATE, ACTION, PLAYER> MinMaxWithMaxDepth<STATE, ACTION, PLAYER>
	    createFor(Game<STATE, ACTION, PLAYER> game) {
	return new MinMaxWithMaxDepth<STATE, ACTION, PLAYER>(game);
	}

	public MinMaxWithMaxDepth(Game<STATE, ACTION, PLAYER> game) {
		this.game = game;
	}

	@Override
	public ACTION makeDecision(STATE state) {
        int depth = game.getDepth();
		expandedNodes = 0;
		ACTION result = null ;
		double resultValue = Double.NEGATIVE_INFINITY;
		PLAYER player = game.getPlayer(state);
		for (ACTION action : game.getActions(state)) {
		 double value = minValue(game.getResult(state, action), player, depth - 1);
		 if (value > resultValue) {
		   result = action;
		   resultValue = value;
		 }
		}
		return result;
	}

	public double maxValue(STATE state, PLAYER player, int depth) {
        if (depth == 0) {
            return game.getUtility(state, player);
        }
	    // Calculates a utility value for a max node
		assert (game.isMax(player));
		expandedNodes++;
		if (game.isTerminal(state))
	     	return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		for (ACTION action : game.getActions(state))
		       value = Math.max(value,
			       minValue(game.getResult(state, action), player, depth - 1));
		return value;
	}

	public double minValue(STATE state, PLAYER player, int depth) {
        if (depth == 0) {
            return game.getUtility(state, player);
        }
	     // Calculates a utility value for a min node
	    assert (!(game.isMax(player)));
	        expandedNodes++;
		if (game.isTerminal(state)){
		    return game.getUtility(state, player);}
		double value = Double.POSITIVE_INFINITY;
		for (ACTION action : game.getActions(state))
			value = Math.min(value,
		       	maxValue(game.getResult(state, action), player, depth - 1));
		return value;
	}



	@Override
	public Metrics getMetrics() {
		Metrics result = new Metrics();
		result.set("expandedNodes", expandedNodes);
		return result;
	}
}
