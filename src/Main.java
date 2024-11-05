import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application
{
    Stage window;
    Scene scene;
    Game game;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        window.setTitle("Rock Paper Scissors!");

        game = new Game();

        scene = new Scene(game.getRoot());
        window.setScene(scene);
        window.show();

        scene.setOnMouseClicked(event -> {
            if(game.isGameInProgress())
            {
                double clickx = event.getX();
                double clicky = event.getY();

                for(Card card : game.getCards())
                {
                    if(card.getImageView().getBoundsInParent().contains(clickx, clicky))
                    {
                        String cardType = card.getType();
                        Boolean canPick = false;


                        if(card.getRemainingUses() > 0)
                        {
                            canPick = true;
                        }

                        if(canPick)
                        {
                            card.setPicked(true);
                        }
                        else
                        {
                            System.out.println("You have no " + cardType + " cards left!");
                        }
                        break;
                    }
                }
            }
        });

        AnimationTimer animation = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                //update
                game.update();

                //render
                game.render();
            }
        };

        animation.start();
    }
}