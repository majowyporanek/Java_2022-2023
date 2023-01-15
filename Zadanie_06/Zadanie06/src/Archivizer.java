import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archivizer implements ArchivizerInterface {
    File compressedFileStore;

    public int compress(String dir, String filename) {
        int filesize;
        File director = new File(dir);
        File compressedFile = new File(filename);
//        System.out.println("FileSize Before: " + getFileSizeBytes(director));
        compressedFileStore = compressedFile;

        try {
            FileOutputStream fileoutputstream = new FileOutputStream(compressedFile);
            ZipOutputStream zipoutputstream = new ZipOutputStream(fileoutputstream);
            compressDirectory(director, zipoutputstream);
            filesize = getFileSizeBytes(compressedFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filesize;
    }

    private static int getFileSizeBytes(File file) {
        return (int) file.length();
    }

    public static void compressDirectory(File directory, ZipOutputStream zipOut) throws IOException {

        byte[] buffer = new byte[1024];
        File[] files = directory.listFiles();
        boolean addedEmpty = false;
        for (File file : files) {
            if (file.isDirectory()) {
                if(file.listFiles().length == 0){
                    String path = file.getParent();
                    String name = file.getName();
                   // System.out.println(path);
                    String nameinsert = name + "/";
                    zipOut.putNextEntry(new ZipEntry(path + "/"+nameinsert));
                    addedEmpty = true;
                    zipOut.closeEntry();
                }else {
                    compressDirectory(file, zipOut);
                }
            } else {
                String filepath;
                if(addedEmpty){
                    filepath = file.getParent();
                    addedEmpty = false;
                }else {
                    filepath = file.getPath();
                }
                FileInputStream fis = new FileInputStream(file);
                zipOut.putNextEntry(new ZipEntry(filepath));
                int len;
                while ((len = fis.read(buffer)) >= 0) {
                    zipOut.write(buffer, 0, len);
                }
                zipOut.closeEntry();
                fis.close();
            }
        }
    }

    public static void unzip(String zipFile, String destDirectory) {
        File directory = new File(destDirectory);

        if(!directory.exists()) {
            directory.mkdirs();
        }
        byte[] buffer = new byte[1024];

        try (FileInputStream fis = new FileInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry ze = zis.getNextEntry();

            while(ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDirectory + "/" + fileName);

                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                ze = zis.getNextEntry();
            }

            zis.closeEntry();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void decompress(String filename, String dir) {
        unzip(filename, dir);
    }
}