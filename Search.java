package jeux;

import jeux.core.Metrics;

public interface Search<STATE, ACTION> {

        ACTION makeDecision(STATE state);
        Metrics getMetrics();
}
