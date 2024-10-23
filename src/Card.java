import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Card
{
    private Image image;
    private ImageView imageView;
    private String type;
    private int index;
    private Boolean picked;
    private Boolean visible;
    private Boolean used;

    public Card(String type, Image image, int index)
    {
        this.type = type;
        this.image = image;
        this.picked = false;
        this.visible = true;
        this.used = false;
        this.index = index;
        imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

    }

    public void reset()
    {
        picked = false;
        visible = true;
        used = false;
        imageView.setVisible(true);
    }

    public Boolean isUsed()
    {
        return used;
    }

    public void setUsed(Boolean used)
    {
        this.used = used;
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

    public Boolean isVisible()
    {
        return visible;
    }

    public void setVisible(Boolean visible)
    {
        this.visible = visible;
        this.imageView.setVisible(visible);
    }

    public Boolean isPicked()
    {
        return picked;
    }

    public void setPicked(Boolean picked)
    {
        this.picked = picked;
    }
}
