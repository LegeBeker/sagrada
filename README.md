# Sagrada

## Build Instructions

To build the project you need to have maven installed on your computer. You can download maven [here](https://maven.apache.org/download.cgi). After installing maven, you can build the project by running the following command in the root of the project

```bash
mvn package spring-boot:repackage
```

## Code Instructions

1. Clone the repository
2. Create a .env file in the root of the project
3. Fill in the .env file with the correct database credentials
4. Create a branch based on a issue or feature
5. Commit your changes to the branch
6. Create a pull request to merge your branch with the main branch
7. Assign yourself to the pull request
8. Make sure the github actions succeed
9. Assign 2 reviewers to review your pull request
10. After the pull request has been approved by 1 reviewer, merge the branch with the main branch
11. Delete the branch

## Env file

The env file should contain the following template

```env
DB_HOST=
DB_NAME=
DB_USERNAME=
DB_PASSWORD=
```

By using the .env file we prevent the database credentials to be pushed to a repository

## Sagrada

Sagrada is a board game for 1-4 players. The goal of the game is to get the most points by placing dice on the window frame of your player board. Each player has a private objective card and a public objective card. The private objective card is kept secret from the other players and gives points at the end of the game. The public objective cards are visible to all players and give points at the end of the game. Each player also has a collection of 5 dice. At the start of the game, each player rolls their dice and places them on the window frame of their player board. The dice can be placed anywhere on the window frame as long as the value of the die matches the color or number on the window frame. The players take turns placing their dice. On their turn, a player rolls the dice in their pool and places them on the window frame of their player board. The dice can be placed anywhere on the window frame as long as the value of the die matches the color or number on the window frame. If a player is unable to place a die, they must pass their turn. The game ends when all of the public objective cards have been completed or when a player is unable to place any more dice. At the end of the game, players count the points on their player board and add it to the points they received from their private and public objective cards. The player with the most points wins.

## Avans

Avans is a Dutch university of applied sciences located in the south of the Netherlands. The university offers a wide range of study programmes in the fields of ICT, Business, Engineering, Healthcare, Media and Design.
