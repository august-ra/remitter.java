import javax.swing.*;

public class MainFrame extends JFrame {

    // this class

    public static void main(String[] args) {
        MainFrame app = new MainFrame();
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setVisible(true);
        //app.pack();
    }

    private MainFrame() {
        super("Remitter" );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
