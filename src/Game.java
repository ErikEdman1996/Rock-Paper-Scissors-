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
    private boolean gameInProgress;
    private boolean playerClick, computerPick;

    private List<Card> playerCards;
    private List<Card> computerCards;

    private AnchorPane root;

    private Label playerRockLabel, playerPaperLabel, playerScissorLabel, playerLivesLabel;
    private Label computerRockLabel, computerPaperLabel, computerScissorLabel, computerLivesLabel;

    private int playerLives;
    private int computerLives;

    private List<String> playerMoveHistory;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    private static final int PLAYER_LABEL_X = 100;
    private static final int PLAYER_LABEL_Y = 480;
    private static final int COMPUTER_LABEL_X = 100;
    private static final int COMPUTER_LABEL_Y = 300;

    private static final int ROCK_INDEX = 0;
    private static final int PAPER_INDEX = 1;
    private static final int SCISSOR_INDEX = 2;

    public Game() throws IOException
    {
        root = new AnchorPane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setPrefSize(WIDTH, HEIGHT);

        playerLives = 3;
        computerLives = 3;

        playerClick = false;
        gameInProgress = true;

        playerMoveHistory = new ArrayList<>();

        playerCards = createCards();
        computerCards = createCards();

        initCards();
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



    }

    private void initPlayerLabels()
    {
        playerRockLabel = new Label();
        playerRockLabel.setText(String.valueOf(playerCards.get(ROCK_INDEX).getRemainingUses()));
        playerPaperLabel = new Label();
        playerPaperLabel.setText(String.valueOf(playerCards.get(PAPER_INDEX).getRemainingUses()));
        playerScissorLabel = new Label();
        playerScissorLabel.setText(String.valueOf(playerCards.get(SCISSOR_INDEX).getRemainingUses()));

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
        computerRockLabel.setText(String.valueOf(computerCards.get(ROCK_INDEX).getRemainingUses()));
        computerPaperLabel = new Label();
        computerPaperLabel.setText(String.valueOf(computerCards.get(PAPER_INDEX).getRemainingUses()));
        computerScissorLabel = new Label();
        computerScissorLabel.setText(String.valueOf(computerCards.get(SCISSOR_INDEX).getRemainingUses()));

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

        cards.add(new Card("Rock", loadImage("images/Rock.jpg"), ROCK_INDEX, 3));
        cards.add(new Card("Paper", loadImage("images/Paper.jpg"), PAPER_INDEX, 3));
        cards.add(new Card("Scissor", loadImage("images/Scissor.jpg"), SCISSOR_INDEX, 3));

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
            if(card.getRemainingUses() <= 0)
            {
                card.setVisible(false);
            }
        }

        for(Card card: computerCards)
        {
            if(card.getRemainingUses() <= 0)
            {
                card.setVisible(false);
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
                card.setRemainingUses(card.getRemainingUses() - 1);
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
                    if(card.getRemainingUses() > 0)
                    {
                        computerCardIndex = card.getIndex();
                        card.setRemainingUses(card.getRemainingUses() - 1);
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
                for(Card card : playerCards)
                {
                    if(card.getRemainingUses() <= 0)
                    {
                        card.setVisible(false);
                    }
                }

                for(Card card : computerCards)
                {
                    if(card.getRemainingUses() <= 0)
                    {
                        card.setVisible(false);
                    }
                }

                //Step 4. Reset the game state after the delay
                reset();
            });

            //Start the pause
            pause.play();

            gameInProgress = false;
        }

        playerRockLabel.setText(String.valueOf(playerCards.get(ROCK_INDEX).getRemainingUses()));
        playerPaperLabel.setText(String.valueOf(playerCards.get(PAPER_INDEX).getRemainingUses()));
        playerScissorLabel.setText(String.valueOf(playerCards.get(SCISSOR_INDEX).getRemainingUses()));

        computerRockLabel.setText(String.valueOf(computerCards.get(ROCK_INDEX).getRemainingUses()));
        computerPaperLabel.setText(String.valueOf(computerCards.get(PAPER_INDEX).getRemainingUses()));
        computerScissorLabel.setText(String.valueOf(computerCards.get(SCISSOR_INDEX).getRemainingUses()));

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
                if(computerType.equals("Scissor"))
                {
                    computerLives--;
                }
                else if(computerType.equals("Paper"))
                {
                    playerLives--;
                }
                break;
            case "Paper":
                if(computerType.equals("Rock"))
                {
                    computerLives--;
                }
                else if(computerType.equals("Scissor"))
                {
                    playerLives--;
                }
                break;
            case "Scissor":
                if(computerType.equals("Paper"))
                {
                    computerLives--;
                }
                else if(computerType.equals("Rock"))
                {
                    playerLives--;
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
}




