import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * Class Rectangle for drawing rectangles in DrawingArea XD
 *
 * @author Marek Bauer
 */
public class Rectangle extends Figures {
    /**
     * Default constructor
     *
     * @param x - position X
     * @param y - position X
     * @param w - width
     * @param h - height
     * @param c - color
     */
    public Rectangle(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        color = c;
        resizeCorner = Corner.None;
    }

    /**
     * Constructs from string
     *
     * @param data - correct string for Rectangle
     * @throws Exception
     */
    public Rectangle(String data) throws Exception {
        String[] d = data.split(" ");
        if (!d[0].equals("rec")) throw new Exception("Wrong figure");
        x = Integer.parseInt(d[1]);
        y = Integer.parseInt(d[2]);
        width = Integer.parseInt(d[3]);
        height = Integer.parseInt(d[4]);
        color = new Color(Integer.parseInt(d[5]));
        resizeCorner = Corner.None;
    }

    @Override
    public boolean inArea(int x, int y) {
        return x > this.x && y > this.y && x < this.x + width && y < this.y + height;
    }

    public void getDragEvent(MouseEvent e) {
        if (resizeCorner != Corner.None) resize(e.getX(), e.getY());
        else {
            if (relX == -1) {
                relX = e.getX() - x;
                relY = e.getY() - y;
            }
            x = e.getX() - relX;
            y = e.getY() - relY;
        }
    }

    @Override
    public PopupMenu getPopupMenu() {
        PopupMenu popup = new PopupMenu();
        Menu menu = new Menu("Change color");
        MenuItem item = new MenuItem("Black");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.BLACK;
        });
        menu.add(item);
        item = new MenuItem("Red");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.RED;
        });
        menu.add(item);
        item = new MenuItem("Blue");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.BLUE;
        });
        menu.add(item);
        item = new MenuItem("Green");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.GREEN;
        });
        menu.add(item);
        item = new MenuItem("Yellow");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.YELLOW;
        });
        item = new MenuItem("Pink");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.PINK;
        });
        menu.add(item);
        item = new MenuItem("Orange");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.ORANGE;
        });
        menu.add(item);
        item = new MenuItem("Cyan");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.CYAN;
        });
        menu.add(item);
        item = new MenuItem("Magenta");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.MAGENTA;
        });
        menu.add(item);
        item = new MenuItem("White");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.WHITE;
        });
        menu.add(item);
        item = new MenuItem("Gray");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.GRAY;
        });
        menu.add(item);
        popup.add(menu);
        item = new MenuItem("Expand");
        item.addActionListener((ActionEvent a) -> {
            this.width += 50;
            this.height += 50;
        });
        popup.add(item);
        item = new MenuItem("Shrink");
        item.addActionListener((ActionEvent a) -> {
            this.width -= 50;
            this.height -= 50;
            super.normalisation();
        });
        popup.add(item);
        popup.addSeparator();
        return popup;
    }

    /**
     *
     * @param g - standard graphics object
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
    }

    @Override
    public void paintAsSelected(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.drawRect(x, y, width, height);
        super.paintAsSelected(g);
    }

    @Override
    public String toString() {
        return ("rec" + " " + x + " " + y + " " + width + " " + height + " " + color.hashCode());
    }
}
