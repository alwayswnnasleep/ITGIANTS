package org.example.tools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileSelector {
    String selectMp3File() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select mp3 file");
        fileChooser.setFileFilter( new FileNameExtensionFilter("mp3", "mp3"));
        fileChooser.showOpenDialog(null);
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }
}
