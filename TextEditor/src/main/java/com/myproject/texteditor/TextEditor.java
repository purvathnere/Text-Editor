
package com.myproject.texteditor;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame frame;

    public TextEditor() {
        frame = new JFrame("Text Editor");

      

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14)); 
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(238, 130, 238)); 

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");
        JMenu formatMenu = new JMenu("Format");
        JMenu helpMenu = new JMenu("Help");

        // File menu items
        JMenuItem newItem = createMenuItem("New", "icons/new.png");
        JMenuItem openItem = createMenuItem("Open", "icons/open.png");
        JMenuItem saveItem = createMenuItem("Save", "icons/save.png");
        JMenuItem exitItem = createMenuItem("Exit", "icons/exit.png");

        // Edit menu items
        JMenuItem cutItem = createMenuItem("Cut", "icons/cut.png");
        JMenuItem copyItem = createMenuItem("Copy", "icons/copy.png");
        JMenuItem pasteItem = createMenuItem("Paste", "icons/paste.png");

        // View menu items
        JMenuItem zoomInItem = createMenuItem("Zoom In", "icons/zoom_in.png");
        JMenuItem zoomOutItem = createMenuItem("Zoom Out", "icons/zoom_out.png");

        // Format menu items
        JMenuItem boldItem = createMenuItem("Bold", "icons/bold.png");
        JMenuItem italicItem = createMenuItem("Italic", "icons/italic.png");

        // Help menu items
        JMenuItem aboutItem = createMenuItem("About", "icons/about.png");

        // Add action listeners for existing menu items
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);

        // Add action listeners for new menu items
        zoomInItem.addActionListener(this);
        zoomOutItem.addActionListener(this);
        boldItem.addActionListener(this);
        italicItem.addActionListener(this);
        aboutItem.addActionListener(this);

        // Add items to file menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Add items to edit menu
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        // Add items to view menu
        viewMenu.add(zoomInItem);
        viewMenu.add(zoomOutItem);

        // Add items to format menu
        formatMenu.add(boldItem);
        formatMenu.add(italicItem);

        // Add items to help menu
        helpMenu.add(aboutItem);

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                textArea.setText("");
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Cut":
                textArea.cut();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Paste":
                textArea.paste();
                break;
            case "Zoom In":
                zoomIn();
                break;
            case "Zoom Out":
                zoomOut();
                break;
            case "Bold":
                toggleBold();
                break;
            case "Italic":
                toggleItalic();
                break;
            case "About":
                showAboutDialog();
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                textArea.read(new FileReader(selectedFile), null);
            } catch (IOException ex) {
                showErrorDialog("Error opening file!");
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(file);
                textArea.write(writer);
                writer.close();
            } catch (IOException ex) {
                showErrorDialog("Error saving file!");
            }
        }
    }

    private JMenuItem createMenuItem(String text, String iconPath) {
        JMenuItem menuItem = new JMenuItem(text, new ImageIcon(iconPath));
        return menuItem;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showAboutDialog() {
        String aboutMessage = "Text Editor\nVersion 1.0\nCreated by PURVA";
        JOptionPane.showMessageDialog(frame, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void zoomIn() {
        Font currentFont = textArea.getFont();
        int newSize = currentFont.getSize() + 1;
        textArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), newSize));
    }

    private void zoomOut() {
        Font currentFont = textArea.getFont();
        int newSize = Math.max(8, currentFont.getSize() - 1);
        textArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), newSize));
    }

    private void toggleBold() {
        Font currentFont = textArea.getFont();
        int style = currentFont.getStyle() ^ Font.BOLD;
        textArea.setFont(new Font(currentFont.getFontName(), style, currentFont.getSize()));
    }

    private void toggleItalic() {
        Font currentFont = textArea.getFont();
        int style = currentFont.getStyle() ^ Font.ITALIC;
        textArea.setFont(new Font(currentFont.getFontName(), style, currentFont.getSize()));
    }
}
