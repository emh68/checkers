## Overview
This project is a Checkers game implemented in Java with a clear separation between game logic and user interface. It focuses on building a rule-driven application that correctly manages game state, turn order, captures, and win conditions.

## Description
The software includes a complete checkers engine that runs as a console-based MVP and a JavaFX graphical interface built on the same core logic. The game supports forced captures, multi-jump sequences, king promotion, turn enforcement, and automatic win detection when a player has no remaining pieces or legal moves.

The purpose of this project was to strengthen my understanding of Java through object-oriented design, clear class responsibilities, and state management. I also wanted experience integrating a JavaFX graphical user interface.

[Software Demo Video](https://youtu.be/nuETDkw3IN0)

# Development Environment

**Tools Used**
- Visual Studio Code
- Java JDK 25 (Oracle)
- JavaFX SDK 25
- Git for version control
- Windows 64-bit environment

**Programming Language & Libraries**
- Java
- JavaFX
- Standard Java libraries

# Useful Websites

- [Java 25 JDK Download](https://www.oracle.com/java/technologies/downloads/#jdk25-windows)
- [W3 Schools Java Tutorial](https://www.w3schools.com/java/default.asp)
- [GeeksforGeeks Java Tutorial](https://www.geeksforgeeks.org/java/java/)
- [Java Full Course for Beginners - Programming with Mosh](https://www.youtube.com/watch?v=eIrMbAQSU34)
- [JavaFX Home Reference Documentation](https://openjfx.io/)
- [JavaFX Documentation Project](https://fxdocs.github.io/docs/html5/)

# Future Work

- Improve JavaFX visuals by replacing text-based pieces with graphical shapes or images
- Add visual indicators for valid moves and forced captures
- Implement animations and sound effects
- Add an optional AI opponent
- Add save/load functionality for game state
- Resolve remaining JavaFX classpath and IDE diagnostic issues

# AI Disclosure 
I used ChatGPT as a learning and reference tool to help reason through the gameplay logic, not to generate code. For example, I asked questions about how to track pieces and moves on the board to better understand the board structure from a "board-centric" perspective versus a "player-centric" perspective, since there are technically no real pieces in computer memory. It confirmed that using a board structure where empty spaces are represented as null is a clean and common approach.

I also used it to check movement logic. I asked whether moving to the right for black pieces should increase the column value and whether the same idea applies to red pieces when capturing. It confirmed the logic but also suggested a clearer way to think about movement: from the perspective of the board rather than the player. In that model, moving right always increases the column index and moving left always decreases it, regardless of piece color.

ChatGPT did not write or generate my code. I used it strictly to talk through logic and confirm that my understanding and approach made sense before implementing all the logic myself.