import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testing6 extends JFrame {

    private JTextArea textArea;
    private StringBuilder conversionLog = new StringBuilder();

    public Testing6() {
        setTitle("Literary Device Analyzer");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem singleDeviceItem = new JMenuItem("Analyze a Single Figure of Speech");
        JMenuItem multipleDevicesItem = new JMenuItem("Analyze Multiple Figures of Speech");
        JMenuItem uploadFileItem = new JMenuItem("Upload a Text File");
        JMenuItem clearTextItem = new JMenuItem("Clear Text");

        menu.add(singleDeviceItem);
        menu.add(multipleDevicesItem);
        menu.add(uploadFileItem);
        menu.add(clearTextItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        textArea = new JTextArea(10, 40);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Text Input"));
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, createGridBagConstraints(0, 0, 2, 1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 15, 15));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton analyzeButton = createButton("Analyze");
        JButton exportButton = createButton("Export to Text File");
        JButton convertButton = createButton("Convert to Another Figure of Speech");
        JButton uploadButton = createButton("Upload Text File");
        JButton wordCountButton = createButton("Word Count");
        JButton clearTextButton = createButton("Clear Text");

        buttonPanel.add(analyzeButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(convertButton);
        buttonPanel.add(uploadButton);
        buttonPanel.add(wordCountButton);
        buttonPanel.add(clearTextButton);

        add(buttonPanel, createGridBagConstraints(0, 1, 2, 1));

        analyzeButton.addActionListener(e -> {
            String analysisResults = analyzeText();
            if (!analysisResults.isEmpty()) {
                // Optionally display the results in a dialog
                JOptionPane.showMessageDialog(null, analysisResults);
            }
        });
        
        exportButton.addActionListener(e -> exportToTextFile());

        convertButton.addActionListener(e -> selectConversion());
        uploadButton.addActionListener(e -> loadFile());
        wordCountButton.addActionListener(e -> showWordCount());
        clearTextButton.addActionListener(e -> clearTextArea());

        uploadFileItem.addActionListener(e -> loadFile());
        clearTextItem.addActionListener(e -> clearTextArea());
    }

    private void exportToTextFile() {
        String analysisResults = analyzeText(); // Get the analysis results
    
        if (analysisResults.isEmpty() && conversionLog.length() == 0) {
            return; // If no results, do not proceed with export
        }
    
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Analysis Results");
        int userSelection = fileChooser.showSaveDialog(this);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(analysisResults); // Write analysis results to the file
                writer.write("\n" + conversionLog.toString()); // Append conversion log
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }
    
    










    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setPreferredSize(new Dimension(250, 40));
        button.addActionListener(e -> button.setBackground(new Color(30, 144, 255)));
        return button;
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage());
            }
        }
    }

    private String analyzeText() {
        String inputText = textArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter text to analyze.");
            return ""; // Return empty if no input
        }
    
        StringBuilder results = new StringBuilder("Analysis Results:\n");
    
        // Detection logic for various figures of speech
        if (detectSimile(inputText)) results.append("Simile detected.\n");
        if (detectMetaphor(inputText)) results.append("Metaphor detected.\n");
        if (detectAlliteration(inputText)) results.append("Alliteration detected.\n");
        if (detectPersonification(inputText)) results.append("Personification detected.\n");
        if (detectAntithesis(inputText)) results.append("Antithesis detected.\n");
        if (detectApostrophe(inputText)) results.append("Apostrophe detected.\n");
        if (detectCircumlocution(inputText)) results.append("Circumlocution detected.\n");
        if (detectEpigram(inputText)) results.append("Epigram detected.\n");
        if (detectEuphemism(inputText)) results.append("Euphemism detected.\n");
        if (detectHyperbole(inputText)) results.append("Hyperbole detected.\n");
        if (detectIrony(inputText)) results.append("Irony detected.\n");
        if (detectLitotes(inputText)) results.append("Litotes detected.\n");
        if (detectMetonymy(inputText)) results.append("Metonymy detected.\n");
        if (detectOnomatopoeia(inputText)) results.append("Onomatopoeia detected.\n");
        if (detectOxymoron(inputText)) results.append("Oxymoron detected.\n");
        if (detectParadox(inputText)) results.append("Paradox detected.\n");
        if (detectPleonasm(inputText)) results.append("Pleonasm detected.\n");
        if (detectPun(inputText)) results.append("Pun detected.\n");
        if (detectSynecdoche(inputText)) results.append("Synecdoche detected.\n");
        if (detectUnderstatement(inputText)) results.append("Understatement detected.\n");
    
        return results.toString();
    }
    

    private boolean detectSimile(String text) {
        return text.contains("like") || text.contains("as");
    }

    private boolean detectMetaphor(String text) {
        return text.contains("is");
    }

    private boolean detectAlliteration(String text) {
        Pattern pattern = Pattern.compile("\\b(\\w)\\w*\\b(?:\\s+\\1\\w*){2,}");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    private boolean detectPersonification(String text) {
        return text.contains("whispers") || text.contains("dance");
    }

    private boolean detectAntithesis(String text) {
        return text.contains("opposite") || text.contains("contrast");
    }

    private boolean detectApostrophe(String text) {
        return text.contains("O ");
    }

    private boolean detectCircumlocution(String text) {
        return text.contains("in other words");
    }

    private boolean detectEpigram(String text) {
        return text.contains("short saying");
    }

    private boolean detectEuphemism(String text) {
        return text.contains("passed away");
    }

    private boolean detectHyperbole(String text) {
        return text.contains("never") || text.contains("always");
    }

    private boolean detectIrony(String text) {
        return text.contains("ironic");
    }

    private boolean detectLitotes(String text) {
        return text.contains("not bad");
    }

    private boolean detectMetonymy(String text) {
        return text.contains("crown");
    }

    private boolean detectOnomatopoeia(String text) {
        return text.contains("buzz") || text.contains("whisper");
    }

    private boolean detectOxymoron(String text) {
        return text.contains("jumbo shrimp");
    }

    private boolean detectParadox(String text) {
        return text.contains("paradox");
    }

    private boolean detectPleonasm(String text) {
        return text.contains("burning fire");
    }

    private boolean detectPun(String text) {
        return text.contains("pun");
    }

    private boolean detectSynecdoche(String text) {
        return text.contains("part of");
    }

    private boolean detectUnderstatement(String text) {
        return text.contains("not very much");
    }

    private void clearTextArea() {
        textArea.setText("");
    }

    private void selectConversion() {
        String[] figuresOfSpeech = {
            "Metaphor", "Simile", "Hyperbole", "Understatement",
            "Personification", "Irony", "Pun", "Euphemism", 
            "Oxymoron", "Alliteration", "Antithesis"
        };
    
        String from = (String) JOptionPane.showInputDialog(null, 
                "Select the figure of speech to convert from:", 
                "Convert Figure of Speech", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                figuresOfSpeech, 
                figuresOfSpeech[0]);
    
        String to = (String) JOptionPane.showInputDialog(null, 
                "Select the figure of speech to convert to:", 
                "Convert Figure of Speech", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                figuresOfSpeech, 
                figuresOfSpeech[0]);
    
        if (from != null && to != null && !from.equals(to)) {
            String inputText = textArea.getText();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter text to convert.");
                return;
            }
    
            String convertedText = convertFigureOfSpeech(inputText, from, to);
            if (convertedText.equals(inputText)) {
                JOptionPane.showMessageDialog(null, "Conversion not applicable for the selected figures of speech.");
            } else {
                textArea.setText(convertedText);
                conversionLog.append("Converted from " + from + " to " + to + ".\n");
                JOptionPane.showMessageDialog(null, "Conversion complete! Here is the converted text:\n" + convertedText);
            }
        }
    }
    

    private String convertFigureOfSpeech(String inputText, String from, String to) {
        if (from.equals("Metaphor") && to.equals("Simile")) {
            return convertMetaphorToSimile(inputText);
        } else if (from.equals("Simile") && to.equals("Metaphor")) {
            return convertSimileToMetaphor(inputText);
        } else if (from.equals("Hyperbole") && to.equals("Understatement")) {
            return convertHyperboleToUnderstatement(inputText);
        } else if (from.equals("Understatement") && to.equals("Hyperbole")) {
            return convertUnderstatementToHyperbole(inputText);
        } else if (from.equals("Personification") && to.equals("Literal")) {
            return convertPersonificationToLiteral(inputText);
        } else if (from.equals("Irony") && to.equals("Literal")) {
            return convertIronyToLiteral(inputText);
        } else if (from.equals("Pun") && to.equals("Literal")) {
            return convertPunToLiteral(inputText);
        } else if (from.equals("Euphemism") && to.equals("Direct")) {
            return convertEuphemismToDirect(inputText);
        } else {
            return inputText; // If no valid conversion is found, return the original text.
        }
    }

    private String convertMetaphorToSimile(String text) {
        return text.replaceAll("is a", "is like a");
    }

    private String convertSimileToMetaphor(String text) {
        return text.replaceAll("like a", "is a");
    }

    private String convertHyperboleToUnderstatement(String text) {
        return text.replaceAll("I have a million things to do", "I have a few things to do");
    }

    private String convertUnderstatementToHyperbole(String text) {
        return text.replaceAll("It's just a scratch", "It's a huge wound!");
    }

    private String convertPersonificationToLiteral(String text) {
        return text.replaceAll("the wind whispered", "the wind blew gently");
    }

    private String convertIronyToLiteral(String text) {
        return text.replaceAll("ironically", "coincidentally");
    }

    private String convertPunToLiteral(String text) {
        return text.replaceAll("pun", "play on words");
    }

    private String convertEuphemismToDirect(String text) {
        return text.replaceAll("passed away", "died");
    }

    // Other conversion methods can be added here

    private void showWordCount() {
        String inputText = textArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter text to count words.");
            return;
        }
        String[] words = inputText.trim().split("\\s+");
        int wordCount = words.length;
        conversionLog.append("Word count: " + wordCount + ".\n");
        JOptionPane.showMessageDialog(null, "Word count: " + wordCount);
    }
    

    private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Testing6 frame = new Testing6();
            frame.setVisible(true);
        });
    }
}
