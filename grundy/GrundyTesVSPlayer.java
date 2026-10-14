package jeux.grundy;

import jeux.AlphaBeta;
import jeux.AlphaBetaWithMaxDepth;
import jeux.MinMaxWithMaxDepth;
import jeux.MinimaxSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GrundyTesVSPlayer {

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

        Grundy game = new Grundy(10, 2 - startingPlayer);
        game.setDepth(5);
        MinimaxSearch<GrundyState, List<Integer>, Integer> minimaxSearch =
            MinimaxSearch.createFor(game);

        MinMaxWithMaxDepth<GrundyState, List<Integer>, Integer> minimaxSearchWithMaxDepth =
        MinMaxWithMaxDepth.createFor(game);

        AlphaBeta<GrundyState, List<Integer>, Integer> alphaBetaSearch =
        AlphaBeta.createFor(game);

        AlphaBetaWithMaxDepth<GrundyState, List<Integer>, Integer> alphaBetaWithMaxDepthSearch =
        AlphaBetaWithMaxDepth.createFor(game);

        GrundyState state = game.getInitialState();

        while (!game.isTerminal(state)) {
            int action = -1;
            int currentPlayer = game.getPlayer(state);
            if (currentPlayer == 1) {
                Grundy.printTheBoard(state);
                System.out.println("Player, what is your action?");
                while (true) {
                    while (true) {
                        try {
                            System.out.print("Choose the stack you want to split : ");
                            action = scanner.nextInt();
                            if (state.getBoard().containsKey(action)) {
                                break;
                            } // If 'action' is not an existing pile, this command triggers an error
                            System.out.println("There is no stack of " + action + " tokens."); // Exit the loop once everything is valid
                        } catch (UnsupportedOperationException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Invalid entry, please choose an integer.");
                            scanner.nextLine(); // Clear the buffer to avoid an infinite loop
                        }
                    }
                    try {
                        List<Integer> finalAction = new ArrayList<>();
                        finalAction.add(action); // Save the stack we want to split
                        System.out.print("Choose the number of token you want to take apart from the stack : ");
                        action = scanner.nextInt();
                        finalAction.add(action);
                        state = game.getResult(state, finalAction); // If 'action' is not an existing pile, this command triggers an error
                        break; // Exit the loop once everything is valid
                    } catch (UnsupportedOperationException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Invalid entry, please choose an integer.");
                        scanner.nextLine(); // Clear the buffer to avoid an infinite loop
                    }
                }
                if (currentPlayer == state.getPlayer()) {System.out.println("Erreur player"); break;};

            } else {
                System.out.println("---------------------------------------");

                List<Integer> finalAction = minimaxSearch.makeDecision(state);
                System.out.println("Metrics for Minimax : " + minimaxSearch.getMetrics());
                System.out.println("The computer has chosen the action : split " + finalAction.get(0)
                                                                        + " with " + finalAction.get(1));
                finalAction = minimaxSearchWithMaxDepth.makeDecision(state);
                System.out.println("Metrics for Minimax with max depth : " + minimaxSearchWithMaxDepth.getMetrics());
                System.out.println("With max depth, the computer has chosen the action : split " + finalAction.get(0)
                                                                        + " with " + finalAction.get(1));
                finalAction = alphaBetaSearch.makeDecision(state);
                System.out.println("Metrics for AlphaBeta : " + alphaBetaSearch.getMetrics());
                System.out.println("With AlphaBeta, the computer has chosen the action  : split " + finalAction.get(0)
                                                                        + " with " + finalAction.get(1));
                finalAction = alphaBetaWithMaxDepthSearch.makeDecision(state);
                System.out.println("Metrics for AlphaBeta with max depth : " + alphaBetaWithMaxDepthSearch.getMetrics());
                System.out.println("With max depth and AlphaBeta, the computer has chosen the action  : split " + finalAction.get(0)
                                                                        + " with " + finalAction.get(1));
                state = game.getResult(state, finalAction);
                if (currentPlayer == state.getPlayer()) {System.out.println("Erreur pc"); break;};
            }
        }
        Grundy.printTheBoard(state);
        if (game.getPlayer(state) == 1) {
            System.out.println("You lose...");
        } else {
            System.out.println("You Win !");
        }
        scanner.close();

   }
}
