import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


class Window extends JFrame {
    private JTextArea textField;
    private JScrollPane scrollPane;
    private JButton button;
    private JButton clearButton;
    private JLabel label;
    private JPanel panel;
    private GridBagConstraints constraints;
    private GridBagLayout layout;
    private ArrayList<Perceptron> perceptrons;
    private String text;

    public Window(int w, int h, String title, ArrayList<Perceptron> perceptrons) {
        super(title);
        this.perceptrons = perceptrons;
        constraints = new GridBagConstraints();
        layout = new GridBagLayout();
        setSize(w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(layout);

        constraints.gridheight = 4;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.8;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        textField = new JTextArea(15, 100);
        textField.setLineWrap(true);
        scrollPane = new JScrollPane(textField);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        clearButton = new JButton("Clear");
        button = new JButton("Submit");
        label = new JLabel("language");
        panel = new JPanel(layout);

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        clearButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

        button.addActionListener(this::buttonUpdate);
        clearButton.addActionListener(this::reset);

        panel.add(scrollPane, constraints);
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.2;
        constraints.weighty = 1;
        panel.add(button, constraints);
        constraints.gridy = 1;
        panel.add(clearButton, constraints);
        constraints.gridy = 2;
        panel.add(label, constraints);

        add(panel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void reset(ActionEvent e) {
        textField.setText("");
    }

    public void buttonUpdate(ActionEvent e) {
        DecimalFormat df = new DecimalFormat("00.00");
        text = textField.getText();
        Vector input = getASCIICount(text);
        String guess = getGuess(input);
        displayGuess(guess);
    }

    public String getGuess(Vector input) {
        HashMap<String, Double> perceptronOutputs = new HashMap<>();
        for (Perceptron p : perceptrons) {
            perceptronOutputs.put(p.getLanguage(), p.guessLINEAR(input));
        }
        return perceptronOutputs.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()))
                .get().getKey();
    }

    public void getText() {
        text = textField.getText();
    }

    public void displayGuess(String guess) {
        label.setText(guess);
    }

    public Vector getASCIICount(String text) {
        String ASCIIletters = "abcdefghijklmnopqrstuvwxyz";
        LinkedHashMap<String, Double> data = new LinkedHashMap<>();
        for (char c : ASCIIletters.toCharArray()) {
            data.put(String.valueOf(c), 0.0);
        }
        for (char c : text.toCharArray()) {
            if (data.containsKey(String.valueOf(c)))
                data.put(String.valueOf(c), data.get(String.valueOf(c)) + 1);
        }
        Vector vector = new Vector(data.values());
        vector.normalize();
        return vector;
    }

}