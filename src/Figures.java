import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Abstract class of figures for DrawingArea
 *
 * @author Marek Bauer
 */
public abstract class Figures {
    int x, y, width, height;
    Color color;
    Corner resizeCorner;
    int relX, relY; //relative mouse position to top left corner (-1 means figure is not dragging)

    /**
     * Function generates figures from string
     *
     * @param data - input string
     * @return - figure that fits specification
     * @throws Exception - if there is no figure fits specification
     */
    static Figures getFigure(String data) throws Exception {
        String[] f = data.split(" ", 2);
        if (f[0].equals("rec")) return new Rectangle(data);
        else if (f[0].equals("ell")) return new Ellipse(data);
        else if (f[0].equals("pol")) return new fPolygon(data);
        else throw new Exception("Figure doesn't exists");
    }

    /**
     * Function print object
     *
     * @param g - standard graphics object
     */
    void paint(Graphics g) {
    }

    /**
     * Function checks if point is in figure
     *
     * @param x - x position
     * @param y - y position
     * @return true if point is in figure
     */
    abstract boolean inArea(int x, int y);

    /**
     * Function checks if point is in figure's special zones to e.g. resizing
     *
     * @param x - x position
     * @param y - y position
     * @return true if point is in figure's special zones to e.g. resizing
     */
    public boolean inAreaAsSelected(int x, int y) {
        return getCorner(x, y, this.x, this.y, width, height) != Corner.None;
    }

    /**
     * Function paints e.g. resizing zones
     *
     * @param g standard graphics object
     */
    void paintAsSelected(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(x - 3, y - 3, 7, 7);
        g2d.fillRect(x - 3 + width, y - 3 + height, 7, 7);
        g2d.fillRect(x - 3 + width, y - 3, 7, 7);
        g2d.fillRect(x - 3, y - 3 + height, 7, 7);
        g2d.setColor(Color.GREEN);
        g2d.drawRect(x - 3, y - 3, 7, 7);
        g2d.drawRect(x - 3 + width, y - 3 + height, 7, 7);
        g2d.drawRect(x - 3 + width, y - 3, 7, 7);
        g2d.drawRect(x - 3, y - 3 + height, 7, 7);
    }

    PopupMenu getPopupMenu() {
        return new PopupMenu();
    }

    /**
     * Get mouse pressed event
     *
     * @param e - standard mouse event
     */
    public void getPressedEvent(MouseEvent e) {
        resizeCorner = getCorner(e.getX(), e.getY(), this.x, this.y, width, height);
        relX = e.getX() - x;
        relY = e.getY() - y;
    }

    /**
     * Get mouse moved event
     *
     * @param e - standard mouse event
     */
    public void getMouseMoveEvent(MouseEvent e) {
    }

    /**
     * Get mouse drag event
     *
     * @param e - standard mouse event
     */
    void getDragEvent(MouseEvent e) {
    }

    /**
     * Get mouse released event
     *
     * @param e - standard mouse event
     */
    public void getReleasedEvent(MouseEvent e) {
        resizeCorner = Corner.None;
        relX = -1;
        relY = -1;
    }

    /**
     * Get focus on figure event
     */
    void getFocusEvent() {
    }

    /**
     * Get unfocused on figure event
     */
    public void getFocusoutEvent() {
        resizeCorner = Corner.None;
        relX = -1;
        relY = -1;
    }

    private Corner getCorner(int x, int y, int posX, int posY, int width, int height) {
        if (x >= posX - 3 && y >= posY - 3 && x <= posX + 3 && y <= posY + 3) return Corner.TopLeft;
        if (x >= posX + width - 3 && y >= posY + height - 3 && x <= posX + width + 3 && y <= posY + height + 3)
            return Corner.BottomRight;
        if (x >= posX + width - 3 && y >= posY - 3 && x <= posX + width + 3 && y <= posY + 3) return Corner.TopRight;
        if (x >= posX - 3 && y >= posY + height - 3 && x <= posX + 3 && y <= posY + height + 3)
            return Corner.BottomLeft;
        return Corner.None;
    }

    /**
     * Creates string representing figure (to overwrite)
     *
     * @return - string representing figure
     */
    public String toString() {
        return "";
    }

    /**
     * Adjust position of cornet to new values
     *
     * @param newX - new x position of corner
     * @param newY - new y position of corner
     */
    void resize(int newX, int newY) {
        if (resizeCorner == Corner.TopLeft) {
            width += x - newX;
            height += y - newY;
            if (width > 0) x = newX;
            else x = width + newX - 1;
            if (height > 0) y = newY;
            else y = height + newY - 1;
        } else if (resizeCorner == Corner.TopRight) {
            width = newX - x;
            height += y - newY;
            if (height > 1) y = newY;
            else y = height + newY - 1;
        } else if (resizeCorner == Corner.BottomRight) {
            width = newX - x;
            height = newY - y;

        } else if (resizeCorner == Corner.BottomLeft) {
            width += x - newX;
            height = newY - y;
            if (width > 1) x = newX;
            else x = width + newX - 1;
        }
        normalisation();
    }

    /**
     * Prevents wrong size
     */
    void normalisation() {
        if (x < 0) {
            width += x;
            x = 0;
        }
        if (y < 0) {
            height += y;
            y = 0;
        }
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
    }

    /**
     * Function shows if figure is in creation mode
     *
     * @return - true if figure is in creation mode
     */
    boolean inCreation() {
        return false;
    }
}
