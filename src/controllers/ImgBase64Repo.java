package controllers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class ImgBase64Repo {


    public static String encode(Path pathToImage) throws IOException {
        byte[] fileContent = Files.readAllBytes(pathToImage);
        return Base64.getEncoder().encodeToString(fileContent);
    }


    public static void decode(String encodedImage, Path toWrite) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
        Files.write(toWrite, decodedBytes);
        //FileUtils.writeByteArrayToFile(toWrite, decodedBytes);
    }
}