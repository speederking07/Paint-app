import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Figures {

    public Ellipse(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        color = c;
        resizeCorner = Corner.None;
    }

    public Ellipse(String data) throws Exception {
        String[] d = data.split(" ");
        if (!d[0].equals("ell")) throw new Exception("Wrong figure");
        x = Integer.parseInt(d[1]);
        y = Integer.parseInt(d[2]);
        width = Integer.parseInt(d[3]);
        height = Integer.parseInt(d[4]);
        color = new Color(Integer.parseInt(d[5]));
        resizeCorner = Corner.None;
    }

    @Override
    boolean inArea(int x, int y) {
        double xx = this.x, yy = this.y, a = width * 0.5, b = height * 0.5;
        return ((x - (xx + a)) * (x - (xx + a)) / (a * a)) +
                ((y - (yy + b)) * (y - (yy + b))) / (b * b) <= 1.0;
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
        menu.add(item);
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

    @Override
    void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(color);
        g2d.fill(new Ellipse2D.Double(x, y, width, height));
    }

    @Override
    public void paintAsSelected(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.draw(new Ellipse2D.Double(x, y, width, height));
        super.paintAsSelected(g);
    }

    @Override
    public String toString() {
        return ("ell" + " " + x + " " + y + " " + width + " " + height + " " + color.hashCode());
    }
}
