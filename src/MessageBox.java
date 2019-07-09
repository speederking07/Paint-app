import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * MessageBox with cancer
 *
 * @author Unfortunatelly Marek Bauer
 */
public class MessageBox extends Frame {
    private Button buttons[];

    /**
     * @param msg     - text to display
     * @param buttons - array of stings for text in buttons
     */
    MessageBox(String msg, String[] buttons) {
        super();
        setLayout(new FlowLayout());
        add(new Label(msg));
        this.buttons = new Button[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            this.buttons[i] = new Button(buttons[i]);
            add(this.buttons[i]);
            this.buttons[i].addActionListener((ActionEvent e) -> this.dispose());
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        pack();
        setVisible(true);
    }

    /**
     * Function to assign function to button action event
     *
     * @param n - number of button (From 0)
     * @param l - ActionListener to activate when button is clicked
     */
    public void publicAddButtonListner(int n, ActionListener l) {
        buttons[n].addActionListener((ActionEvent e) -> {
            l.actionPerformed(e);
            this.dispose();
        });

    }
}
