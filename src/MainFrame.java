import Classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JTextField txtFile1;
    private JButton    btnFile1;
    private JTextField txtFile2;
    private JButton    btnFile2;
    private JComboBox<Languages> cmbType1;
    private JComboBox<Languages> cmbType2;
    private TextArea txtText1;
    private TextArea txtText2;
    private JButton  btnStart;

    private final JFileChooser dialog = new JFileChooser();

    // class of templates

    String[][] keywords = new String[][] {
            { // Java
                "abstract", "assert", "boolean", "break", "byte", "case", "catch",
                "char", "class", "const", "continue", "default", "do", "double",
                "else", "enum", "extends", "false", "final", "finally", "for", "goto",
                "if", "implements", "import", "instanceof", "int", "interface",
                "long", "native", "new", "null", "package", "private", "protected",
                "public", "return", "short", "static", "strictfp", "super", "switch",
                "synchronized", "this", "throw", "throws", "transient", "true", "try",
                "void", "volatile", "while"
            },
            { // Visual Basic for Applications
                "#If", "#Else", "#ElseIf", "#EndIf", "#End If", "And", "Append", "As",
                "Base", "Binary", "Boolean", "ByRef", "ByVal", "Call", "Case",
                "CBool", "CByte", "CCur", "CDate", "CInt", "CLng", "Close", "Compare",
                "Const", "CSng", "CStr", "CType", "Currency", "Database", "Date",
                "Declare", "DefBool", "DefByte", "DefDate", "DefDec", "DefDouble",
                "DefInt", "DefLng", "DefLngLng", "DefLngPtr", "DefObj", "DefStr",
                "Dim", "Do", "Double", "Each", "Else", "ElseIf", "Empty", "End",
                "EndIf", "Enum", "Eqv", "Error", "Event", "Exit", "Explicit", "False",
                "For", "Friend", "Function", "Get", "GoTo", "If", "IIf", "Imp",
                "Implements", "In", "Input", "Integer", "Is", "LBound", "Len", "Let",
                "Lib", "Like", "Lock", "Long", "LongLong", "Loop", "LSet", "Me",
                "Mid", "New", "Next", "Not", "Nothing", "Null", "Object", "On",
                "Open", "Option", "Optional", "Or", "Output", "ParamArray",
                "Preserve", "Print", "Private", "Property", "PrtSafe", "Public",
                "Randomize", "ReDim", "Resume", "Return", "RSet", "Seek", "Select",
                "Set", "Shared", "Single", "Static", "Step", "Stop", "String", "Sub",
                "Text", "Then", "Time", "To", "True", "Type", "TypeOf", "UBound",
                "Until", "Variant", "Wend", "While", "With", "WithEvents", "Xor"
            }
    };

    // this class

    public static void main(String[] args) {
        MainFrame app = new MainFrame();
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setVisible(true);
        //app.pack();
    }

    private MainFrame() {
        super("Remitter");

        Languages[] codeTypes = new Languages[] {
                Languages.Java,
                Languages.VBA
        };

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        int row = 0;
        int colW = 2;

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
        c.gridx = colW;
        c.gridy = row;
        add(txtFile2, c);

        btnFile2 = new JButton("...");
        btnFile2.setSize(5, btnFile2.getHeight());
        btnFile2.addActionListener(this::selectFile);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = colW + 1;
        c.gridy = row;
        add(btnFile2, c);

        row++;

        cmbType1 = new JComboBox<>(codeTypes);
        cmbType1.setSelectedIndex(0);
        cmbType1.addActionListener((ActionEvent e) -> {
            if (cmbType1.getSelectedItem() == Languages.Java)
                cmbType2.setSelectedItem(Languages.VBA);
            else
                cmbType2.setSelectedItem(Languages.Java);
        });
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridwidth = colW;
        c.gridx = 0;
        c.gridy = row;
        add(cmbType1, c);

        cmbType2 = new JComboBox<>(codeTypes);
        cmbType2.setSelectedIndex(1);
        cmbType2.addActionListener((ActionEvent e) -> {
            if (cmbType2.getSelectedItem() == Languages.Java)
                cmbType1.setSelectedItem(Languages.VBA);
            else
                cmbType1.setSelectedItem(Languages.Java);
        });
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridwidth = colW;
        c.gridx = colW;
        c.gridy = row;
        add(cmbType2, c);

        row++;

        txtText1 = new TextArea();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridwidth = colW;
        c.gridx = 0;
        c.gridy = row;
        add(txtText1, c);

        txtText2 = new TextArea();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.gridwidth = colW;
        c.gridx = colW;
        c.gridy = row;
        add(txtText2, c);

        row++;

        btnStart = new JButton("Translate source");
        btnStart.addActionListener(this::translate);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = colW * 2;
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
                txtFile1.setText(file.getAbsolutePath());
                txtText1.setText(readFile(file));
            }
            else if (e.getSource() == btnFile2) {
                txtFile2.setText(file.getAbsolutePath());
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
