import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * It just close window when user wants to
 */
class exitWindowAdapter extends WindowAdapter {
    exitWindowAdapter() {
    }

    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}

/**
 * Main program class
 *
 * @author Marek Bauer
 */
public class Program extends Frame {
    MenuBar mb;
    Menu file;
    DrawingArea da;
    Path path;

    /**
     * Main is main function
     *
     * @param a - params
     */
    public static void main(String a[]) {
        Frame f = new Program();
    }

    /**
     * Default constructor
     */
    public Program() {
        super("Programik");
        path = null;
        da = new DrawingArea();
        mb = new MenuBar();
        file = new Menu("File");
        MenuItem item = new MenuItem("New");
        item.addActionListener((ActionEvent) -> {
            setTitle("Programik");
            path = null;
            da.clear();
            da.repaint(80);
        });
        file.add(item);
        item = new MenuItem("Load");
        item.addActionListener((ActionEvent) -> {
            FileDialog fd = new FileDialog(new Frame(), "Load", FileDialog.LOAD);
            fd.setLocation(50, 50);
            fd.show();
            if (fd.getFile() != null) {
                path = Paths.get(fd.getDirectory() + fd.getFile());
                setTitle(fd.getFile() + " - Programik");
                try {
                    String entireFileText = new Scanner(new File(path.toString()))
                            .useDelimiter("\\A").next();
                    da.loadFormString(entireFileText);
                    da.repaint(80);
                } catch (FileNotFoundException e) {
                    MessageBox msg = new MessageBox("Loading error: " + e.getMessage(), new String[]{"OK"});
                }
            }
        });
        file.add(item);
        item = new MenuItem("Save");
        item.addActionListener((ActionEvent) -> {
            saveToFile();
        });
        file.add(item);
        item = new MenuItem("Save as");
        item.addActionListener((ActionEvent) -> {
            path = null;
            saveToFile();
        });
        file.add(item);
        item = new MenuItem("Exit");
        item.addActionListener((ActionEvent) -> {
            System.exit(0);
        });
        file.add(item);
        setMenuBar(mb);
        mb.add(file);
        setLayout(new GridLayout());
        add(da);
        setSize(600, 400);
        setVisible(true);
        addWindowListener(new exitWindowAdapter());
    }

    private void saveToFile() {
        if (path == null) {
            boolean good = false;
            while (!good) {
                good = true;
                FileDialog fd = new FileDialog(new Frame(), "Save as", FileDialog.SAVE);
                fd.setLocation(50, 50);
                fd.show();
                if (fd.getFile() != null) {
                    path = Paths.get(fd.getDirectory() + fd.getFile());
                    setTitle(fd.getFile() + " - Programik");
                    try (PrintWriter out = new PrintWriter(path.toString())) {
                        out.println(da.saveToString());
                    } catch (FileNotFoundException e) {
                        MessageBox msg = new MessageBox("Saving error: " + e.getMessage(), new String[]{"OK"});
                        good = false;
                    }
                }
            }
        } else {
            try (PrintWriter out = new PrintWriter(path.toString())) {
                out.println(da.saveToString());
            } catch (FileNotFoundException e) {
                MessageBox msg = new MessageBox("Saving error: " + e.getMessage(), new String[]{"OK"});
                path = null;
                saveToFile();
            }
        }
    }
}
