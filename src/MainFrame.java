import Classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JTextField          txtFile1;
    private JButton             btnFile1;
    private JTextField          txtFile2;
    private JButton             btnFile2;
    private JComboBox<Language> cmbType1;
    private JComboBox<Language> cmbType2;
    private TextArea            txtText1;
    private TextArea            txtText2;
    private JButton             btnStart;

    private final JFileChooser dialog = new JFileChooser();

    // class of templates

    Token[][] tokens;
    int tRow, tNum;

    private String[][] keywords = new String[][] {
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

    private String[][] commentSimple = {
            { // Java
                "//"
            },
            { // Visual Basic for Applications
                "'"
            }
    };

    private String[][] commentMultiline = {
            { // Java
                "/*", "*/"
            },
            { // Visual Basic for Applications
            }
    };

    private String[] charsets = new String[] {
            "",
            " \n\t\r", // spaces
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_", // names
            "0123456789", // digits
            "=+-*/\\%><!&|^", // operators
            "()[]{}", // brackets
            ";:,.", // delimiters
            "'", // quote
            "\"", // double quote
            ""
    };

    // state machine

    private State state = State.Nothing;

    // this class

    public static void main(String[] args) {
        MainFrame app = new MainFrame();
        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.setVisible(true);
        //app.pack();
    }

    private MainFrame() {
        super("Remitter");

        Language[] codeTypes = new Language[] {
                Language.Java,
                Language.VBA
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
            if (cmbType1.getSelectedItem() == Language.Java)
                cmbType2.setSelectedItem(Language.VBA);
            else
                cmbType2.setSelectedItem(Language.Java);
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
            if (cmbType2.getSelectedItem() == Language.Java)
                cmbType1.setSelectedItem(Language.VBA);
            else
                cmbType1.setSelectedItem(Language.Java);
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

        String[] lines = txtText1.getText().split("\n");

        int l = lines.length;

        Language l1 = (Language)cmbType1.getSelectedItem();
        Language l2 = (Language)cmbType2.getSelectedItem();

        if (l1 == null || l2 == null)
            return;

        tokens = new Token[l][];
        state  = State.Nothing;

        StringBuilder builder = new StringBuilder();

        int index = l2.ordinal();

        boolean isSimpleComment    = (commentSimple[index].length > 0);
        boolean isMultilineComment = (commentMultiline[index].length > 0);

        for (tRow = 0; tRow < lines.length; tRow++) {
            tokens[tRow] = getElems(lines[tRow], l1);

            if (tRow > 0)
                builder.append("\n");

            for (int i = 0; i < tokens[tRow].length; i++) {
                Token t = tokens[tRow][i];

                // comments

                if (t.type == State.Comment) {
                    if (isSimpleComment) {
                        builder.append(commentSimple[index][0]);
                        builder.append(t.text);
                    }
                    else if (isMultilineComment) {
                        builder.append(commentMultiline[index][0]);
                        builder.append(t.text);
                        builder.append(commentMultiline[index][1]);
                    }
                }
                else if (t.type == State.Comment_Start) {
                    if (isMultilineComment) {
                        boolean isFirst = true;

                        lookingForTokens:
                        for (int p1 = tRow - 1; p1 >= 0; p1--) {
                            Token[] array = tokens[p1];

                            for (int p2 = array.length - 1; p2 >= 0; p2--) {
                                Token tmp = array[p2];

                                isFirst = (tmp.type != State.Comment_Start);
                                break lookingForTokens;
                            }
                        }

                        if (isFirst)
                            builder.append(commentMultiline[index][0]);
                        builder.append(t.text);
                    }
                    else if (isSimpleComment) {
                        builder.append(commentSimple[index][0]);
                        builder.append(t.text);
                    }
                }
                else if (t.type == State.Comment_End) {
                    if (isMultilineComment) {
                        builder.append(t.text);
                        builder.append(commentMultiline[index][1]);
                    }
                    else if (isSimpleComment) {
                        builder.append(commentSimple[index][0]);
                        builder.append(t.text);
                    }
                }
            }
        }

        txtText2.setText(builder.toString());
    }

    private Token[] getElems(String str, Language lang) {
        ArrayList<Token> list = new ArrayList<>();

        // TODO: calculating level

        int index = lang.ordinal();

        lookingForString:
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            // comments

            int j2 = -1;

            if (state == State.Nothing) {
                for (int j = 0; j < commentSimple[index].length; j++) {
                    String cSimple = commentSimple[index][j];

                    boolean equal = true;
                    int     len   = Math.min(str.length() - i, cSimple.length());

                    for (int p = 0; p < len; p++) {
                        ch = str.charAt(i + p);

                        if (ch != cSimple.charAt(p))
                            equal = false;
                    }

                    if (equal) {
                        String s = str.substring(i + cSimple.length(), str.length());
                        list.add(new Token(State.Comment, j, 0, s));

                        state = State.Nothing;

                        i = str.length();
                        continue lookingForString;
                    }
                }

                for (int j = 0; j < commentMultiline[index].length; j += 2) {
                    String cMultiline1 = commentMultiline[index][j];

                    if (state == State.Nothing) {
                        boolean equal = true;
                        int     len   = Math.min(str.length() - i, cMultiline1.length());

                        for (int p = 0; p < len; p++) {
                            ch = str.charAt(i + p);

                            if (ch != cMultiline1.charAt(p))
                                equal = false;
                        }

                        if (equal) {
                            state = State.Comment_Start;

                            str = str.substring(i + cMultiline1.length(), str.length());

                            if (str.isEmpty()) {
                                list.add(new Token(State.Comment_Start, j, 0, str));

                                continue lookingForString;
                            }
                            else {
                                j2 = j;

                                break;
                            }
                        }
                    }
                }
            }

            if (state == State.Comment_Start) {
                int j = j2;

                if (j == -1) {
                    lookingForTokens:
                    for (int p1 = tRow - 1; p1 >= 0; p1--) {
                        Token[] array = tokens[p1];

                        for (int p2 = array.length - 1; p2 >= 0; p2--) {
                            Token tmp = array[p2];

                            j = tmp.index;
                            break lookingForTokens;
                        }
                    }

                    if (j == -1)
                        throw new Error("Unknown symbols in " + ((Integer) i).toString());
                }

                String cMultiline2 = commentMultiline[index][j + 1];

                int p = str.indexOf(cMultiline2);

                if (p == -1) {
                    list.add(new Token(State.Comment_Start, j, 0, str));

                    i = str.length();
                    continue lookingForString;
                }
                else {
                    String s = str.substring(0, p);

                    list.add(new Token(State.Comment_End, j, 0, s));

                    i = p + cMultiline2.length() - 1; // this -1 is because 'for'
                }
            }
        }

        return list.toArray(new Token[0]);
    }
}
