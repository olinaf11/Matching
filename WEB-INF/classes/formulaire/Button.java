package formulaire;

import java.awt.event.MouseListener;
import javax.swing.JButton;

public class Button extends JButton {
    int width, height;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) throws Exception {
        if (width < 0)
            throw new Exception("Width is négative");
        this.width = width;
    }
    
    public void setHeight(int height) throws Exception {
        if (height < 0)
            throw new Exception("Heigth is négative");
        this.height = height;
    }

    protected Button(String name, int width, int height) throws Exception {
        super(name);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Construtor for adding listeners
     * @param events
     * @param name
     * @param width
     * @param height
     */
    public Button(MouseListener[] events, String name) throws Exception {
        this(name, name.length() * 10 + 30, 50);
        for (MouseListener event : events)
            this.addMouseListener(event);
    }

    /**
     * Construtor for adding listener
     * @param events
     * @param name
     * @param width
     * @param height
     */
    public Button(MouseListener event, String name) throws Exception {
        this(name, name.length() * 10 + 50, 50);
        this.addMouseListener(event);
    }
}