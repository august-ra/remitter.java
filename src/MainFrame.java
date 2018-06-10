import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class MainFrame extends JFrame {
    private JTextField txtFile1;
    private JButton    btnFile1;
    private JTextField txtFile2;
    private JButton    btnFile2;
    private JComboBox<String> cmbType1;
    private JComboBox<String> cmbType2;
    private TextArea txtText1;
    private TextArea txtText2;
    private JButton  btnStart;

    private final JFileChooser dialog = new JFileChooser();

    // this class

    public static void main(String[] args) {
        MainFrame app = new MainFrame();
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setVisible(true);
        //app.pack();
    }

    private MainFrame() {
        super("Remitter");

        String[] codeTypes = new String[] {
                "Java",
                "Visual Basic for Applications"
        };

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        int row = 0;

        txtFile1 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = row;
        add(txtFile1, c);

        btnFile1 = new JButton("...");
        btnFile1.setSize(5, btnFile1.getHeight());
        btnFile1.addActionListener(this::selectFile);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 1;
        c.gridy = row;
        add(btnFile1, c);

        txtFile2 = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = row;
        add(txtFile2, c);

        btnFile2 = new JButton("...");
        btnFile2.setSize(5, btnFile2.getHeight());
        btnFile2.addActionListener(this::selectFile);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 3;
        c.gridy = row;
        add(btnFile2, c);

        row++;

        cmbType1 = new JComboBox<>(codeTypes);
        cmbType1.setSelectedIndex(0);
        cmbType1.addActionListener((ActionEvent e) -> {
            if (cmbType1.getSelectedIndex() == 0)
                cmbType2.setSelectedIndex(1);
            else
                cmbType2.setSelectedIndex(0);
        });
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = row;
        add(cmbType1, c);

        cmbType2 = new JComboBox<>(codeTypes);
        cmbType2.setSelectedIndex(1);
        cmbType2.addActionListener((ActionEvent e) -> {
            if (cmbType2.getSelectedIndex() == 0)
                cmbType1.setSelectedIndex(1);
            else
                cmbType1.setSelectedIndex(0);
        });
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = row;
        add(cmbType2, c);

        row++;

        txtText1 = new TextArea();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = row;
        add(txtText1, c);

        txtText2 = new TextArea();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = row;
        add(txtText2, c);

        row++;

        btnStart = new JButton("Translate source");
        btnStart.addActionListener(this::translate);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = row;
        add(btnStart, c);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void selectFile(ActionEvent e) {
        int returnVal = dialog.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = dialog.getSelectedFile();

            if (!file.exists())
                return;

            if (e.getSource() == btnFile1) {
                txtFile1.setText(file.getName());
                txtText1.setText(readFile(file));
            }
            else if (e.getSource() == btnFile2) {
                txtFile2.setText(file.getName());
                txtText2.setText(readFile(file));
            }
        }
    }

    private String readFile(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            BufferedReader  reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String        line;

            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append('\n');
                }
            }
            catch (IOException e) {
                return "";
            }

            return builder.toString();
        }
        catch (FileNotFoundException e) {
            return "";
        }
    }

    private void translate(ActionEvent e) {
        if (txtText1.getText().length() == 0)
            JOptionPane.showMessageDialog(txtText1, "Nothing to do!", "Error", JOptionPane.ERROR_MESSAGE);

        // TODO: translate
    }
}
