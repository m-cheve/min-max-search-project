# Minimax and Alpha-Beta Search Project

This repository contains a small academic project focused on adversarial search algorithms in game-playing AI. The project was developed as part of an artificial intelligence course and explores how classic decision-making methods behave on simple deterministic games.

The goal is to compare several search strategies and to observe how they differ in terms of performance, complexity, and decision quality when applied to games with alternating turns.

## Project context

This project is designed as an educational exercise in:

- adversarial search
- minimax reasoning
- alpha-beta pruning
- depth-limited search
- utility evaluation in zero-sum games
- state-space exploration for turn-based games

The implementation is intentionally simple and transparent so that the underlying algorithms remain easy to understand and compare.

## Algorithms implemented

The project includes the following strategies:

- Minimax
- Minimax with depth limit
- Alpha-Beta pruning
- Alpha-Beta pruning with depth limit

Each strategy is implemented in a generic way through a shared `Game` interface, allowing the same search logic to be reused across several games.

## Games included

The repository contains implementations for several classic toy games:

- Nim
- Tic-Tac-Toe
- Grundy game (token pile splitting)

These games are useful for demonstrating search behavior because they have well-defined rules, small state spaces, and clear terminal conditions.

## Repository structure

```text
.
├── AlphaBeta.java
├── AlphaBetaWithMaxDepth.java
├── Game.java
├── MinMaxWithMaxDepth.java
├── MinimaxSearch.java
├── Search.java
├── core/
│   └── Metrics.java
├── grundy/
│   ├── Grundy.java
│   ├── GrundyState.java
│   └── GrundyTesVSPlayer.java
├── nim/
│   ├── Nim.java
│   ├── NimTest.java
│   └── NimTestVSPlayer.java
├── tictactoe/
│   ├── TicTacToe.java
│   └── TicTacToeTesVSPlayer.java
└── README.md
```

## How the project works

Each game implements the same interface:

- initial state
- available players
- current player
- legal actions
- transitions between states
- terminal-state detection
- utility evaluation
- depth setting

The search classes then evaluate possible moves and choose the action considered best according to the selected algorithm.

## Example usage

The project is Java-based. From the project root, you can compile and run the provided demos:

```bash
javac $(find . -name "*.java")
java jeux.nim.NimTest
```

Other examples are also available in the game-specific packages, such as:

```bash
java jeux.tictactoe.TicTacToeTesVSPlayer
java jeux.grundy.GrundyTesVSPlayer
```

## Educational value

This project was especially useful for learning:

- how search trees are generated from game states
- why minimax is necessary in adversarial settings
- how alpha-beta pruning reduces unnecessary exploration
- how utility functions influence decision quality
- how depth limits trade performance for completeness

It was also a good illustration of how algorithmic choices affect execution time and search efficiency, even on relatively small problems.

## Potential limitations

As a school project, this repository intentionally focuses on clarity and didactic value rather than production-grade optimization. Some limitations are worth noting:

- The project is designed for small games and does not scale well to larger state spaces.
- Search performance can degrade quickly as the branching factor and depth increase.
- Alpha-beta pruning helps, but it still depends heavily on move ordering and state structure.
- Utility functions are handcrafted and may not always reflect the best strategic evaluation in complex positions.
- Some game implementations rely on manual console input and are not built as a polished graphical application.

## Conclusion

This project is a clean and accessible demonstration of classic adversarial search in the context of game AI. It is a good example of how a few fundamental algorithms can be used to model decision-making under competition and how their effectiveness changes depending on the problem structure.

It is best understood as an academic implementation and a learning tool rather than a full-featured game engine or competitive AI framework.
