package com.example.ftps;

// this class is an object for listing files in the FileBrowser
// it includes the file name and the file size

public class FileBrowserListItem {

    private int imageResource;
    private String fileName;
    private String fileSize;
    private boolean isDirectory;

    public FileBrowserListItem(int ir, String fn, String fs, boolean id) {
        imageResource = ir;
        fileName = fn;
        fileSize = fs;
        isDirectory = id;
    }

    public int getImageRecource() {
        return imageResource;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public boolean getIsDirectory() {
        return isDirectory;
    }
}
