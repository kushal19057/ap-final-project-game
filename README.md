# Color Switch 
## Details

Created by:
- Kushal Juneja
- Rohan Hiranandani

This game is made for our AP major project.
It is clone of the famous Color Switch game.
The game is made using Java and JavaFX.
The objective of the game is to collect
as many stars as possible by manoeuvring the obstacles. If you hit any obstacle, you can continue
with your earned stars or the game ends and you have to start from the beginning. This is a high
score game. We have developed only the endless (infinity) game mode of the classic
gameplay.

## Features
- 4 Obstacles
- A jumping Ball
- Score
- Revive
- Save Game
- Restart Game
- Load Game
- Sound Effects

## Design Patters
- Iterator
- Factory
- Chain of responsibility
- Facade

## Screenshots from the game
![Main Menu](/screenshots/main-menu.png)
![Help option](/screenshots/help.png)
![gameplay](/screenshots/gameplay1.png)
![gameplay](/screenshots/gameplay2.png)
![load game](/screenshots/load.png)
![pause menu](/screenshots/pause.png)

## Configure eclipse IDE to run by adding the following VM arguments
```
--module-path "PATH_TO_FX" --add-modules javafx.fxml,javafx.controls,javafx.media
```
- Also ensure that the project recognises JavaFX jars as external user libraries
