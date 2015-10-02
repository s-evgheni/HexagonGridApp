# HexagonGridApp
Classical computer science exercise


# Read Me
Requirements: Java 7 (set PATH variables for JAVA_HOME)

To run source code:

1. Get source code of the project folder
2. Go to org.main.applicationLauncher package and run HexagonGridApp.java file


Excercise objective:
To create a simple swing based window put a grid inside of it and fill that grid with hexagons.
Each hexagon will have a random letter inside of it.
You will be able to interact with each Hexagon via keybourd or mouse.

To interact via mouse: click inside any hexagon. An event will be generated and displayed in the console. Clicked hexagon will become inactive

To interact via keyboard: press appropriate letter on the keyboard. This will trigger an event as well which will be displayed in the console. Selected hexagon will become inactive

The program will terminate itself once there will be no active hexagons left on grid.
You can control the size of the grid via main class HexagonGridApp.java:
ApplicationWindow window = new ApplicationWindow("Main Application Window", 4, 5);
Just replace numbers 4 and 5 with desired width and height.

Sample console output example:

Hexagon T has been selected via keyboard

Hexagon N has been selected via keyboard

Hexagon Z selected via mouse click

Hexagon O selected via mouse click

Hexagon D selected via mouse click

Hexagon G selected via mouse click

Hexagon G is the last selected hexagon in a grid
