import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game
{
    private Boolean gameInProgress;
    private Boolean playerClick, computerPick;

    private List<Card> playerCards;
    private List<Card> computerCards;

    private AnchorPane root;

    private Label playerRockLabel, playerPaperLabel, playerScissorLabel, playerLivesLabel;
    private Label computerRockLabel, computerPaperLabel, computerScissorLabel, computerLivesLabel;

    private int playerRockNr, playerPaperNr, playerScissorNr;
    private int computerRockNr, computerPaperNr, computerScissorNr;

    private int playerLives;
    private int computerLives;

    private List<String> playerMoveHistory;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    private static final int PLAYER_LABEL_X = 100;
    private static final int PLAYER_LABEL_Y = 480;
    private static final int COMPUTER_LABEL_X = 100;
    private static final int COMPUTER_LABEL_Y = 300;

    public Game() throws IOException
    {
        root = new AnchorPane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setPrefSize(WIDTH, HEIGHT);

        playerRockNr = playerPaperNr = playerScissorNr = 3;
        computerRockNr = computerPaperNr = computerScissorNr = 3;

        playerLives = 3;
        computerLives = 3;

        playerClick = false;
        gameInProgress = true;

        playerMoveHistory = new ArrayList<>();

        playerCards = createCards();
        computerCards = createCards();

        initPlayerLabels();
        initComputerLabels();

        root.getChildren().add(playerRockLabel);
        root.getChildren().add(playerPaperLabel);
        root.getChildren().add(playerScissorLabel);
        root.getChildren().add(computerRockLabel);
        root.getChildren().add(computerPaperLabel);
        root.getChildren().add(computerScissorLabel);
        root.getChildren().add(playerLivesLabel);
        root.getChildren().add(computerLivesLabel);

        initCards();

    }

    private void initPlayerLabels()
    {
        playerRockLabel = new Label();
        playerRockLabel.setText(playerRockNr + "");
        playerPaperLabel = new Label();
        playerPaperLabel.setText(playerPaperNr + "");
        playerScissorLabel = new Label();
        playerScissorLabel.setText(playerScissorNr + "");

        playerRockLabel.setLayoutX(PLAYER_LABEL_X + playerRockLabel.getLayoutBounds().getMinX());
        playerRockLabel.setLayoutY(PLAYER_LABEL_Y);
        playerRockLabel.setTextFill(Color.WHITE);
        playerPaperLabel.setLayoutX((PLAYER_LABEL_X * 3 + 10) + playerPaperLabel.getLayoutBounds().getMinX());
        playerPaperLabel.setLayoutY(PLAYER_LABEL_Y);
        playerPaperLabel.setTextFill(Color.WHITE);
        playerScissorLabel.setLayoutX((PLAYER_LABEL_X* 5 + 20) + playerScissorLabel.getLayoutBounds().getMinX());
        playerScissorLabel.setLayoutY(PLAYER_LABEL_Y);
        playerScissorLabel.setTextFill(Color.WHITE);

        playerLivesLabel = new Label();
        playerLivesLabel.setText("Player: "+playerLives);
        playerLivesLabel.setTextFill(Color.WHITE);

        playerLivesLabel.setLayoutX(200 - playerLivesLabel.getLayoutBounds().getWidth() / 2);
        playerLivesLabel.setLayoutY(400);

    }

    private void initComputerLabels()
    {
        computerRockLabel = new Label();
        computerRockLabel.setText(computerRockNr + "");
        computerPaperLabel = new Label();
        computerPaperLabel.setText(computerPaperNr + "");
        computerScissorLabel = new Label();
        computerScissorLabel.setText(computerScissorNr + "");

        computerRockLabel.setLayoutX(COMPUTER_LABEL_X + computerRockLabel.getLayoutBounds().getMinX());
        computerRockLabel.setLayoutY(COMPUTER_LABEL_Y);
        computerRockLabel.setTextFill(Color.WHITE);
        computerPaperLabel.setLayoutX((COMPUTER_LABEL_X * 3 + 10) + computerPaperLabel.getLayoutBounds().getMinX());
        computerPaperLabel.setLayoutY(COMPUTER_LABEL_Y);
        computerPaperLabel.setTextFill(Color.WHITE);
        computerScissorLabel.setLayoutX((COMPUTER_LABEL_X * 5 + 20) + computerScissorLabel.getLayoutBounds().getMinX());
        computerScissorLabel.setLayoutY(COMPUTER_LABEL_Y);
        computerScissorLabel.setTextFill(Color.WHITE);

        computerLivesLabel = new Label();
        computerLivesLabel.setText("Computer: "+computerLives);
        computerLivesLabel.setTextFill(Color.WHITE);

        computerLivesLabel.setLayoutX(400 - computerLivesLabel.getLayoutBounds().getWidth() / 2);
        computerLivesLabel.setLayoutY(400);
    }

    private ArrayList<Card> createCards() throws IOException
    {
        ArrayList<Card> cards = new ArrayList<>();

        cards.add(new Card("Rock", loadImage("images/Rock.jpg"), 0));
        cards.add(new Card("Paper", loadImage("images/Paper.jpg"), 1));
        cards.add(new Card("Scissor", loadImage("images/Scissor.jpg"), 2));

        return cards;
    }

    private Image loadImage(String path) throws IOException
    {
        InputStream stream = Files.newInputStream(Paths.get(path));
        return new Image(stream);
    }

    private void reset()
    {
        playerClick = false;
        computerPick = false;

        for(Card card : playerCards)
        {
            card.reset();
        }

        for(Card card : computerCards)
        {
            card.reset();
        }

        for(Card card: playerCards)
        {
            String type = card.getType();

            switch(type)
            {
                case "Rock":
                    if(playerRockNr <= 0)
                    {
                        card.setVisible(false);
                    }
                    break;
                case "Paper":
                    if(playerPaperNr <= 0)
                    {
                        card.setVisible(false);
                    }
                    break;
                case "Scissor":
                    if(playerScissorNr <= 0)
                    {
                        card.setVisible(false);
                    }
            }
        }

        for(Card card: computerCards)
        {
            String type = card.getType();

            switch(type)
            {
                case "Rock":
                    if(computerRockNr <= 0)
                    {
                        card.setVisible(false);
                    }
                    break;
                case "Paper":
                    if(computerPaperNr <= 0)
                    {
                        card.setVisible(false);
                    }
                    break;
                case "Scissor":
                    if(computerScissorNr <= 0)
                    {
                        card.setVisible(false);
                    }
            }
        }

        if(playerLives > 0 && computerLives > 0)
        {
            gameInProgress = true;
        }
    }

    public void update()
    {
        if(!gameInProgress)
        {
            return;
        }

        int playerCardIndex = -1;
        int computerCardIndex = -1;

        for(Card card : playerCards)
        {
            if(card.isPicked())
            {
                playerClick = true;
                playerCardIndex = card.getIndex();
                break;
            }
        }

        if(playerClick)
        {
            String computerMove = getWeightedMove();

            for(Card card : computerCards)
            {
                if(card.getType().equals(computerMove))
                {
                    switch(computerMove)
                    {
                        case "Rock":
                            if(computerRockNr > 0)
                            {
                                computerCardIndex = card.getIndex();
                            }
                            break;
                        case "Paper":
                            if(computerPaperNr > 0)
                            {
                                computerCardIndex = card.getIndex();
                            }
                            break;
                        case "Scissor":
                            if(computerScissorNr > 0)
                            {
                                computerCardIndex = card.getIndex();
                            }
                            break;
                    }
                }
            }

            if(computerCardIndex != -1)
            {
                computerPick = true;
                computerCards.get(computerCardIndex).setPicked(true);
                resolveRound(playerCardIndex, computerCardIndex);
            }
            else
            {
                System.out.println("No legal card available for the AI move: " + computerMove);
            }
        }
    }

    public void render()
    {
        if(playerClick && computerPick)
        {
            //System.out.println("Rendering... Player clicked. Hiding non-picked cards.");

            //Step 1. Hide unpicked cards.
            hideUnpickedCards();

            //Step 2. Set a delay before resetting the game and hiding cards with 0 left
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {

                //Step 3. Check if any cards should be hidden
                if(playerRockNr <= 0)
                {
                    for(Card card : playerCards)
                    {
                        if(card.getType().equals("Rock"))
                        {
                            card.setVisible(false); // Hide the card when player has 0 Rock cards left
                        }
                    }
                }

                if(playerPaperNr <= 0)
                {
                    for(Card card : playerCards)
                    {
                        if(card.getType().equals("Paper"))
                        {
                            card.setVisible(false); // Hide the card when player has 0 Paper cards left
                        }
                    }
                }

                if(playerScissorNr <= 0)
                {
                    for(Card card : playerCards)
                    {
                        if(card.getType().equals("Scissor"))
                        {
                            card.setVisible(false); // Hide the card when player has 0 Scissor cards left
                        }
                    }
                }

                if(computerRockNr <= 0)
                {
                    for(Card card : computerCards)
                    {
                        if(card.getType().equals("Rock"))
                        {
                            card.setVisible(false); // Hide the card when computer has 0 Rock cards left
                        }
                    }
                }

                if(computerPaperNr <= 0)
                {
                    for(Card card : computerCards)
                    {
                        if(card.getType().equals("Paper"))
                        {
                            card.setVisible(false); // Hide the card when computer has 0 Paper cards left
                        }
                    }
                }

                if(computerScissorNr <= 0)
                {
                    for(Card card : computerCards)
                    {
                        if(card.getType().equals("Scissor"))
                        {
                            card.setVisible(false); // Hide the card when computer has 0 Scissor cards left
                        }
                    }
                }
                //Step 4. Reset the game state after the delay
                reset();
            });

            //Start the pause
            pause.play();

            gameInProgress = false;
        }

        playerRockLabel.setText(playerRockNr + "");
        playerPaperLabel.setText(playerPaperNr + "");
        playerScissorLabel.setText(playerScissorNr + "");

        computerRockLabel.setText(computerRockNr + "");
        computerPaperLabel.setText(computerPaperNr + "");
        computerScissorLabel.setText(computerScissorNr + "");

        playerLivesLabel.setText("Player: "+playerLives);
        computerLivesLabel.setText("Computer: "+computerLives);
    }

    private void hideUnpickedCards()
    {

        for(Card card : playerCards)
        {
            //System.out.println("Card Type: " + card.getType() + " | Picked: " + card.isPicked());

            if(!card.isPicked())
            {
                card.setVisible(false);
                //System.out.println("Hiding card: " + card.getType());
            }
        }

        for(Card card : computerCards)
        {
            //System.out.println("Card Type: " + card.getType() + " | Picked: " + card.isPicked());

            if(!card.isPicked())
            {
                card.setVisible(false);
                //System.out.println("Hiding card: " + card.getType());
            }
        }
    }

    private void resolveRound(int playerCardIndex, int computerCardIndex)
    {
        String playerType = playerCards.get(playerCardIndex).getType();
        String computerType = computerCards.get(computerCardIndex).getType();
        playerMoveHistory.add(playerCards.get(playerCardIndex).getType());

        switch(playerType)
        {
            case "Rock":
                playerRockNr--;
                if(computerType.equals("Scissor"))
                {
                    computerScissorNr--;
                    computerLives--;
                }
                else if(computerType.equals("Paper"))
                {
                    computerPaperNr--;
                    playerLives--;
                }
                else
                {
                    computerRockNr--;
                }
                break;
            case "Paper":
                playerPaperNr--;
                if(computerType.equals("Rock"))
                {
                    computerRockNr--;
                    computerLives--;
                }
                else if(computerType.equals("Scissor"))
                {
                    computerScissorNr--;
                    playerLives--;
                }
                else
                {
                    computerPaperNr--;
                }
                break;
            case "Scissor":
                playerScissorNr--;
                if(computerType.equals("Paper"))
                {
                    computerPaperNr--;
                    computerLives--;
                }
                else if(computerType.equals("Rock"))
                {
                    computerRockNr--;
                    playerLives--;
                }
                else
                {
                    computerScissorNr--;
                }
                break;
        }

        if (playerLives == 0)
        {
            System.out.println("Game Over! You have lost all your lives.");
            gameInProgress = false;
        }
        else if (computerLives == 0)
        {
            System.out.println("Congratulations! You won, the computer is out of lives.");
            gameInProgress = false;
        }
    }

    public List<Card> getCards()
    {
        return playerCards;
    }

    public Boolean isGameInProgress()
    {
        return gameInProgress;
    }

    private void initCards() throws IOException
    {

        setUpPlayerCards();
        setUpComputerCards();
    }

    public AnchorPane getRoot()
    {
        return root;
    }

    private void setUpComputerCards()
    {
        computerCards.get(0).setPos(0, 0);
        computerCards.get(1).setPos(210, 0);
        computerCards.get(2).setPos(420, 0);

        for(Card card: computerCards)
        {
            root.getChildren().add(card.getImageView());
        }
    }

    private void setUpPlayerCards()
    {
        playerCards.get(0).setPos(0, 500);
        playerCards.get(1).setPos(210, 500);
        playerCards.get(2).setPos(420, 500);

        for(Card card: playerCards)
        {
            root.getChildren().add(card.getImageView());
        }
    }

    private String getWeightedMove()
    {
        int rockWeight = 33;
        int paperWeight = 33;
        int scissorWeight = 33;

        for(String move: playerMoveHistory)
        {
            switch(move)
            {
                case "Rock":
                    paperWeight += 10;
                    break;
                case "Paper":
                    scissorWeight += 10;
                    break;
                case "Scissor":
                    rockWeight += 10;
                    break;
            }
        }

        System.out.println("Rock weight: "+rockWeight);
        System.out.println("Paper weight: "+paperWeight);
        System.out.println("Scissor weight: "+scissorWeight);

        int totalWeight = rockWeight + paperWeight + scissorWeight;
        int randomNum = new Random().nextInt(totalWeight);

        if(randomNum < rockWeight)
        {
            return "Rock";
        }
        else if(randomNum < rockWeight + paperWeight)
        {
            return "Paper";
        }
        else
        {
            return "Scissor";
        }
    }

    public int getPlayerRockNr() {
        return playerRockNr;
    }

    public int getPlayerPaperNr() {
        return playerPaperNr;
    }

    public int getPlayerScissorNr() {
        return playerScissorNr;
    }
}




