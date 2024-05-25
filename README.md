# Ice Maze Pathfinding

## Description
The Ice Maze Pathfinding project is a Java application that reads a maze from an input file and finds the shortest path from the start ('S') to the finish ('F') using the Breadth-First Search (BFS) algorithm. The maze consists of ice cells that can be slid over and rock cells that block the path. The solution includes displaying the maze and the path found, with graphical representation using ASCII characters.

## Features
- Reads a maze from an input file.
- Finds the shortest path from start to finish using BFS.
- Displays the maze and the path with ASCII characters.
- GUI component for file selection using `JFileChooser`.
- Handles file not found exceptions gracefully.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 1.8 or later.
- An IDE like IntelliJ IDEA, Eclipse, or NetBeans.

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/Ice-Maze-Pathfinding.git
    ```
2. Open the project in your IDE.

### Usage
1. Run the `Main` class.
2. Use the file chooser dialog to select the input file containing the maze.
3. The program will display the shortest path from the start ('S') to the finish ('F').

### Input File Format
The input file should be a text file representing the maze, where:
- 'S' denotes the start cell.
- 'F' denotes the finish cell.
- '0' denotes rock cells.
- '.' denotes ice cells.


