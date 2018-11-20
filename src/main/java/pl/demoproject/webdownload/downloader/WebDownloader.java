package pl.demoproject.webdownload.downloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.demoproject.webdownload.Repository.WebRepository;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.sql.*;

@Service
@Transactional
public class WebDownloader {

    @Autowired
    WebRepository webRepository;

    @Async
    public void downloadAndSavePage(String webpage, Long htmlId) throws IOException, SQLException {
        saveFileToDb(htmlId, getFilePath(webpage));
    }

    private void saveFileToDb(Long htmlId, String filePath) throws IOException {

        byte[] websitesBytes = readBytesFromFile(filePath);
        webRepository.getOne(htmlId).setHtmlFile(websitesBytes);

    }

    private static byte[] readBytesFromFile(String filePath) throws IOException {
        File inputFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(inputFile);

        byte[] fileBytes = new byte[(int) inputFile.length()];
        inputStream.read(fileBytes);
        inputStream.close();

        return fileBytes;
    }

    private String getFilePath(String webpage){

        String fileName="";

        try {
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            fileName = timestamp.toString();

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(fileName));

            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }
            readr.close();
            writer.close();

        } catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        } catch (IOException ie) {
            System.out.println("IOException raised");
        }
        return fileName;
    }

}
