package com.iread.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public class AttachmentFileStore {
    private String tmpPath;
    private String dwhPath;

    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    public void setDwhPath(String dwhPath) {
        this.dwhPath = dwhPath;
    }

    private String getTmpPath() {
        return tmpPath != null ? tmpPath : System.getProperty("java.io.tmpdir");
    }

    private String getDwhPath() {
        return dwhPath;
    }

    public String saveTmpFile(MultipartFile file) throws IOException {
        if (file != null) {
            final String uid = UUID.randomUUID().toString();
            final File f = new File(getTmpPath(), uid);
            try (FileOutputStream fo = new FileOutputStream(f)) {
                fo.write(file.getBytes());
            }
            return uid;
        }
        return null;
    }

    public void saveAttachmentToStore(String uid) throws IOException {
        String dwhPath = getDwhPath();
        File destDir = new File(dwhPath);

        if (!destDir.exists()) {
            destDir.mkdir();
        }

        File destFile = new File(destDir, uid);

        if (!destFile.exists()) {
            File src = new File(getTmpPath(), uid);
            if (src.exists())
                FileUtils.copyFileToDirectory(src, destDir);
        }
    }

    private File getFileByUid(String uid) {
        File dwhFile = new File(getDwhPath(), uid);
        if (dwhFile.exists()) {
            return dwhFile;
        } else {
            File tmpFile = new File(getTmpPath(), uid);
            if (tmpFile.exists())
                return tmpFile;
        }
        return null;
    }

    public void copyFile(String uid, OutputStream out) throws IOException {
        File f = getFileByUid(uid);
        if (f == null)
            throw new FileNotFoundException(uid);

        FileCopyUtils.copy(new FileInputStream(f), out);
    }
}
