[Back to Home](https://teanlouise.github.io)

![pacman](https://user-images.githubusercontent.com/19520346/69231371-af492480-0bd4-11ea-8575-e47e0ef59aa9.PNG)

This version of the traditional 'Pacman' game was implemented over the course of three assignments for CSSE2002 (Programming in the Large) at the University of Queensland. The overall goal of the project was to gain confidence with writing and testing code in Java that expands multiple classes. The marking criteria was according to correct functionality (55%), code review (25%) and successful tests (20%). Each assignment received a high distinction.

Throughout this project, I expertly handled invariants and exceptions. I demonstrated strong understanding of different access modifiers (private, public), non-access modifiers (abstract, static, final), data types (primitive, reference) and collections (maps, arrays, lists) As well as highlighting my ability to logically use subclasses (inheritance, super, casting) and parse variables.

Each assignment required the full implementation of precisely the set of classes and interfaces described in the Javadoc (though private members or classes was at own discretion) that would be used in the following assignment to complete the implementation. All of the classes would be tested extensively against, an unknown, suite of tests. Additionally, I was to write JUnit tests that would be used to test, unknown, faulty implementations of the methods in:
- PacmanBoard (1)
- ScoreBoard (1)
- GameReader (2)
- GameWriter (2)
- BoardViewModel (3)
- ScoreViewModel (3)

All other tests were used for my own testing purposes.

_The javadoc, specifications and code in ScoreModel, BoardModel and MainModel are the property of the respective authors at the University of Queensland_


### Overview of the javadoc:

**Pacman**

Launcher | Class | Main entry point for the CSSE2002/7023 PacMan Game.

**Display**

BoardView | View representation of the Games playable area.

BoardViewModel | BoardViewModel is the intermediary between BoardView and the PacmanGame

MainView | Main entry point for the Pacman View.

MainViewModel | Used as an intermediary between the game and the MainView

ScoreView | Represents the view for the score display in the application.

ScoreViewModel | ScoreViewModel is an intermediary between ScoreView and the PacmanGame. Used for displaying the player's score in the GUI.
 
**Board**

PacmanBoard | class | Represents the Pac Man game board. The board can be any size, it is set out as a grid with each space containing only one BoardItem. Game boards are by default surrounded by a BoardItem.WALL with every other space being BoardItem.NONE. 

BoardItem | enum | This enum defines different items that are placed on the board. Items may have a pickup score if the item can be picked up. Items must define whether they are path-able.
 
**Game**

Moveable | interface | An object that can move with a current position and a direction.

Entity | class | A entity is the animated objects in the game that can traverse the game board and interact with other entities.

GameReader | class | GameReader Reads in a saved games state and returns a game instance.

GameWriter | class | Writes the PacmanGame to a standard format.

PacmanGame | class |  PacmanGame stores the game's state and acts as the model for the entire game.
 
**Ghost**

GhostType | enum | GhostType contains all the valid ghosts; the current list of ghosts are: "BLINKY", "CLYDE", "INKY", "PINKY".

Phase | enum | Phase Defines the different phases a ghost can be in.

Ghost | Class | An Abstract Ghost which is a game entity.

Blinky | Class | Blinky is a ghost that behaves in a very aggressive manner towards a hunter.

Clyde | Class | Clyde is a ghost that behaves in a very scared manner when close to a hunter.

Inky | Class | Inky is a ghost that likes to tail close behind the hunter.

Pinky | Class | Pinky is a cunning ghost that tries to ambush the hunter.
 
**Hunter**

HunterType | enum | Definition of HunterType's available in the game.

Hunter | class | Hunters are entities which are controlled by the player to clear the board and win the game

Hungry | class | A Hungry hunter that has a special ability that allows the hunter to eat ghosts temporarily without them being in a Frightened state.

Phasey | class | A Phasey hunter with a special ability that allows the hunter to travel through ghosts temporarily without dieing.

Phil | class |  A Phil hunter that has no special ability.

Speedy | class | A Speedy hunter that has a special ability that allows the hunter to travel twice as fast.
 
**Score**

ScoreBoard | class | ScoreBoard contains previous scores and the current score of the PacmanGame
 
**util**

Position | class | Similar to a Point Class but instead called Position.

Direction | enum | Direction represents directions in a 2D plane.
UnpackableException | Exception thrown when a game file cannot be unpacked.
