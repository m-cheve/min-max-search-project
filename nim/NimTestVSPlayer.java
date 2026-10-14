package jeux.nim;

import jeux.AlphaBeta;
import jeux.AlphaBetaWithMaxDepth;
import jeux.MinMaxWithMaxDepth;
import jeux.MinimaxSearch;

import java.util.List;
import java.util.Scanner;

public class NimTestVSPlayer {

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
        Scanner scanner = new Scanner(System.in);

        while (!game.isTerminal(state)) {
            int action = -1;
            int currentPlayer = game.getPlayer(state);
            if (currentPlayer == 0) {
                System.out.println("Number of sticks remaining :" + game.getNumStick(state));
                System.out.println("Player, what is your action?");
                while (true) {
                    try {
                        System.out.print("Enter the number of sticks to pick (");
                        for (Integer ac : game.getActions(state)) {
                            switch (ac) {
                                case 1:
                                    System.out.print("1");
                                    break;
                                case 2:
                                    System.out.print(", 2");
                                    break;
                                default:
                                    System.out.print(" or 3");
                                    break;
                            }
                        }
                        System.out.print(") : ");
                        action = scanner.nextInt();

                        state = game.getResult(state, action);
                        break; // Exit the loop once everything is valid
                    } catch (UnsupportedOperationException e) {
                        System.out.println("Action impossible : " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Entrée invalide. Veuillez entrer un entier.");
                        scanner.nextLine(); // Clear the buffer to avoid an infinite loop
                    }
                }
            } else {
                action = minimaxSearch.makeDecision(state);
                System.out.println("Metrics for Minimax : " + minimaxSearch.getMetrics());
                System.out.println("The computer has chosen the action " + action);
                action = minimaxSearchWithMaxDepth.makeDecision(state);
                System.out.println("Metrics for Minimax with max depth : " + minimaxSearchWithMaxDepth.getMetrics());
                System.out.println("With max depth, the computer has chosen the action " + action);
                action = alphaBetaSearch.makeDecision(state);
                System.out.println("Metrics for AlphaBeta : " + alphaBetaSearch.getMetrics());
                System.out.println("With AlphaBeta, the computer has chosen the action " + action);
                action = alphaBetaWithMaxDepthSearch.makeDecision(state);
                System.out.println("Metrics for AlphaBeta with max depth : " + alphaBetaWithMaxDepthSearch.getMetrics());
                System.out.println("With max depth and AlphaBeta, the computer has chosen the action " + action);
                state = game.getResult(state, action);
            }
        }
        if (game.isMax(game.getPlayer(state))) {
            System.out.println("You Win !");
        } else {
            System.out.println("You lose...");
        }
        scanner.close();

   }
}
