package jeux.grundy;

import java.util.HashMap;
import java.util.Map;

public class GrundyState {
    private Integer player;
    private Map<Integer, Integer> board;

    public GrundyState(GrundyState other) {
        this.player = other.player;
        this.board = new HashMap<>(other.board); // Copy of the board
    }

    public GrundyState(Integer player, Map<Integer, Integer> board) {
        this.player = player;
        this.board = new HashMap<>(board);
    }

    public Integer getPlayer() {
        return player;
    }

    public void setNumber(Integer player) {
        this.player = player;
    }

    public Map<Integer, Integer> getBoard() {
        return new HashMap<>(board);
    }

    public void setMap(Map<Integer, Integer> board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "GrundyState{" +
                "player=" + player +
                ", board=" + board +
                '}';
    }
}
