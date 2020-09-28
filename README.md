# Texas Hold'em Poker Simulator

This exercise is based on the [Texas Hold'em](https://en.wikipedia.org/wiki/Texas_hold_%27em) variant of the Poker card game.

Please read up on the rules of this game beforehand to become familiar with them, or watch this 
[video](https://www.youtube.com/watch?v=GAoR9ji8D6A).

You should understand the following concepts before continuing:

- Identifying the highest hands of the players.
  - e.g If a player has One Pair and a Three of a Kind, the game should be able to identify that the player has a 
  Full House. 
- Identifying the player with the winning hand.

To provide a quick summary:

- At the start of the game, each player will be dealt two cards.
- Pressing space or clicking `Next Action` will put community cards on the table.
  - The first action will put three community cards on the table.
  - The second and third actions will put one community card each on the table.
- Once all five community cards slots are filled, the winner will be declared.
- Pressing space or clicking `New Game` will start a new game.

![simulator in action](texas-holdem-poker-simulator.gif)

## About the project:

The project simulates a game of poker by dealing cards out to each player, and then dealing the five community cards. 

It's made with Spring Boot, Thymeleaf provides the web interface.

Project works out of the box, and does not require any downloads except for Java 8 sdk.

Before you start applying changes to the code, it is recommended that you run the application first. To see if it's working properly.

### How to get running:
- Need at least Java 8 or higher installed
- IntelliJ IDEA is recommended. The Community Edition will do just fine.
  Otherwise, you can open the project in your editor of choice.
- To run the application, go to the project directory and `./gradlew bootRun`. 
- Check `http://localhost:8080` to see if it's working.
- To run the tests, run `./gradlew test` in the root directory of the project.

**Note on Spring and Dependency Injection:** Spring allows us to use Dependency Injection to inject the Game instance 
into our Web Controller. While knowledge of Dependency Injection and Spring is not required to answer the exam, 
familiarity of the two will help in understanding how the application works.

**Basic example of how Spring Boot with Thymeleaf:** https://spring.io/guides/gs/serving-web-content/


**Note on Thymeleaf:** While the Web UI code is complete and does not need to be modified for the exam, knowledge on how 
Thymeleaf works will help in understanding how the HTML is connected to the Java code.

## About the exam:
a
You've been given the start of a Texas Hold'em Poker simulator project, but the functionality is incomplete.

The Web UI code has been written and is complete. There's no need to modify the HTML code.
If you run the PokerApplication class it will start up a web server that you can access at http://localhost:8080/

This exam covers identifying the players' hands; i.e. if a player has a Flush, Straight, Full House, etc., and 
[kicker/s](https://en.wikipedia.org/wiki/Texas_hold_%27em#Kickers_and_ties) if any.  
This exam also covers identifying winner. The winner is the player with the highest hand value.

### General Instructions
- Only the Java code will be modified. There's no need to modify the HTML.
- Public methods are defined but do not have implementations. It's up to you to provide the implementation and figure out 
where they fit in the code. Feel free to add methods or create classes if you find them necessary.
- Provide unit tests for your implementations. This covers the classes provided but without implementations and 
new classes you might create.
- Refer to the attached GIF for reference on how the application works.

### Tasks
- The first is to identify the players' highest hands and display in the web UI, as seen in the attached GIF.
  - Every time community cards are placed on the board, the players' hands are identified.
  - In the application, the text displayed beside `Current Hand:` is the return of the `toString()` method of the hand 
  types. Java docs and unit tests are provided to show how the text should be formatted.
- The second task it to identify the winner at the end of the game.
  - In the event of a [tie](https://en.wikipedia.org/wiki/Texas_hold_%27em#Kickers_and_ties), declare tied players as 
  the winners.
- (Optional essay) What is the major flaw in this web application if we were to host it on a server for other people to use?
# poker-springboot
