package jeux.tictactoe;

import jeux.AlphaBeta;
import jeux.AlphaBetaWithMaxDepth;
import jeux.MinMaxWithMaxDepth;
import jeux.MinimaxSearch;

import java.util.List;
import java.util.Scanner;

public class TicTacToeTesVSPlayer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer startingPlayer = -1;
        while (startingPlayer != 1 && startingPlayer != 2) {
            try {
                System.out.println("Vous souhaitez jouer en premier (1) ou jouer en second (2) ?");
                startingPlayer = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Entrée invalide. Veuillez entrer un entier.");
                scanner.nextLine(); // Clear the buffer to avoid an infinite loop
            }
            if (startingPlayer != 1 && startingPlayer != 2)
                System.out.println("Entrée invalide. Veuillez entrer 1 ou 2.");
        }

        System.out.println();

        TicTacToe game = new TicTacToe(2 - startingPlayer);
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

        System.out.println( "Here are the indexes : " );
        System.out.println( " 1 | 2 | 3 " );
        System.out.println( " - + - + - " );
        System.out.println( " 4 | 5 | 6 " );
        System.out.println( " - + - + - " );
        System.out.println( " 7 | 8 | 9 " );

        System.out.println( "" );

        while (!game.isTerminal(state)) {
            int action = -1;
            int currentPlayer = game.getPlayer(state);
            if (currentPlayer == 1) {
                TicTacToe.printTheBoard(state);
                System.out.println("Player, what is your action?");
                while (true) {
                    try {
                        System.out.print("Enter the index of where to place your token (");
                        for (Integer ac : game.getActions(state)) {
                            System.out.print(" " + ac);
                        }
                        System.out.print(" ) : ");
                        action = scanner.nextInt();

                        state = game.getResult(state, action);
                        break; // Exit the loop once everything is valid
                    } catch (UnsupportedOperationException e) {
                        System.out.println(e.getMessage());
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
        TicTacToe.printTheBoard(state);
        if (game.getPlayer(state) == 1) {
            System.out.println("You lose...");
        } else {
            System.out.println("You Win !");
        }
        scanner.close();

   }
}
