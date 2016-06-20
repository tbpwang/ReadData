import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Created by Joe Wang, tbpwang@gmail.com
 * 2016/6/16.
 */
public class ReadData {
    //1923-1968
    private static final String FROM_URL = "http://worldwindserver.net/webworldwind/data/Earth/LandSat/9/1944";
    private static final String FILE_SAVE_PATH = "D:\\data\\";

    public static void main(String[] args) {
        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null;
        FileOutputStream out;
        BufferedImage image;
        URL url;
        URL urlPGN;
        URLConnection urlConnection;
        URLConnection urlPGNConnection;
        String line;
        String png;
        try {
            url = new URL(FROM_URL);
            urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            inputStreamReader = new InputStreamReader(url.openStream());
            reader = new BufferedReader(inputStreamReader);

            Long startTime = System.currentTimeMillis();
            Date date = new Date(startTime);
            System.out.println("Starting time is: " + date.toString());
            System.out.println("Please Wait!");

            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("<a href=")) {
                    png = line.substring(15, 27);
                    System.out.println(png);
//                    if (png.equals("1939_846.png")) {
//                        flag = true;
//                    }
//                    if (flag) {

                    urlPGN = new URL(FROM_URL + "/" + png);
                    urlPGNConnection = urlPGN.openConnection();
                    urlPGNConnection.setDoOutput(true);
                    urlPGNConnection.connect();

                    image = ImageIO.read(urlPGNConnection.getInputStream());
                    out = new FileOutputStream(FILE_SAVE_PATH + png);
                    ImageIO.write(image, "png", out);
                    out.flush();
                    out.close();
//                    }
                }
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("It costs: " + (endTime - startTime) / 1000 / 60 + " minutes.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
