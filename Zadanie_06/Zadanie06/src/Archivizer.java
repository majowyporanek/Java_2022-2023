import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archivizer implements ArchivizerInterface {
    private String zippedFilePath;
    private void zipSingleFile(String filePath, ZipOutputStream zos) throws IOException
    {
        File fileToBeZipped = new File(filePath);
        FileInputStream fis = new FileInputStream(fileToBeZipped);

        ZipEntry zipEntry = new ZipEntry(filePath);

        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];

        while (fis.read(bytes) >= 0)
        {
            zos.write(bytes, 0, bytes.length);
        }

        zos.closeEntry();

        fis.close();
    }

    private void zipWholeDirectory(String filePath, ZipOutputStream zos) throws IOException
    {
        File fileToBeZipped = new File(filePath);

        if(fileToBeZipped.isDirectory())
        {
            File[] files = fileToBeZipped.listFiles();

            for (File file : files)
            {
                zipWholeDirectory(filePath+"\\"+file.getName(), zos);
            }
        }
        else
        {
            zipSingleFile(filePath, zos);
        }
    }

    private static int getFileSizeBytes(File file) {
        return (int) file.length();
    }

    @Override
    public int compress(String dir, String filename) {
        int sizeToReturn;
        String path = dir + "\\" + filename;
        zippedFilePath = path;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ZipOutputStream zos = new ZipOutputStream(fos);

            zipWholeDirectory(dir, zos);

            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File zippedFile = new File(path);
        sizeToReturn = getFileSizeBytes(zippedFile);
        System.out.println(sizeToReturn);
        return sizeToReturn;
    }

    @Override
    public void decompress(String filename, String dir) {

    }

    public static void main(String[] args) {
        Archivizer a = new Archivizer();
        String path = "C:\\Users\\klaud\\IdeaProjects\\Java-zaj-cia-2022\\Zadanie_06\\Zadanie06\\src\\exampleFile";
        a.compress(path, "compressedExample");
    }
}