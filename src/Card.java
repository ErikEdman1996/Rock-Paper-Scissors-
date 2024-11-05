import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Card
{
    private ImageView imageView;
    private String type;
    private int index;
    private boolean picked;
    private int remainingUses;

    public Card(String type, Image image, int index, int remainingUses)
    {
        this.type = type;
        this.picked = false;
        this.index = index;
        this.remainingUses = remainingUses;

        imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

    }

    public void reset()
    {
        picked = false;
        imageView.setVisible(true);
    }

    public void setPos(double x, double y)
    {
        imageView.setX(x);
        imageView.setY(y);
    }

    public String getType()
    {
        return type;
    }

    public int getIndex()
    {
        return index;
    }

    public ImageView getImageView()
    {
        return imageView;
    }


    public void setVisible(Boolean visible)
    {
        this.imageView.setVisible(visible);
    }

    public boolean isPicked()
    {
        return picked;
    }

    public void setPicked(Boolean picked)
    {
        this.picked = picked;
    }

    public int getRemainingUses()
    {
        return remainingUses;
    }

    public void setRemainingUses(int remainingUses)
    {
        this.remainingUses = remainingUses;
    }
}
