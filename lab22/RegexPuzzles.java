import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPuzzles {
    public static List<String> urlRegex(String[] urls) {
        // Create a String pattern to fill return array
        List<String> toReturn = new ArrayList<>();
        Pattern urlCapturer = Pattern.compile
                ("^\\(.*?https?://(\\w+\\.)+[a-z]{2,3}/\\w+\\.html.*?\\)$");
        for (String url : urls) {
            Matcher urlMatcher = urlCapturer.matcher(url);
            if (urlMatcher.matches()) {
                toReturn.add(urlMatcher.group());
            }
        }
        return toReturn;
    }

    public static List<String> findStartupName(String[] names) {
        // Create a String pattern to fill return array
        List<String> toReturn = new ArrayList<>();
        Pattern nameCapturer = Pattern.compile
                ("^(Data|App|my|on|un)([A-Za-hi-z0-9]+)(ly|sy|ify|\\.io|\\.fm|\\.tv)$");
        for (String name : names) {
            Matcher nameMatcher = nameCapturer.matcher(name);
            if (nameMatcher.matches()) {
                toReturn.add(nameMatcher.group());
            }
        }
        return toReturn;
    }

    public static BufferedImage imageRegex(String filename, int width, int height) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file found: " + filename);
        }

        // Initialize both Patterns and 3-d array
        try {
            String line;
            while ((line = br.readLine()) != null) {
                // Initialize both Matchers and find() for each

                // Parse each group as an Integer

                // Store in array
            }
        } catch (IOException e) {
            System.err.printf("Input error: %s%n", e.getMessage());
            System.exit(1);
        }
        // Return the BufferedImage of the array
        return null;
    }

    public static BufferedImage arrayToBufferedImage(int[][][] arr) {
        BufferedImage img = new BufferedImage(arr.length,
                arr[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int pixel = 0;
                for (int k = 0; k < 3; k++) {
                    pixel += arr[i][j][k] << (16 - 8 * k);
                }
                img.setRGB(i, j, pixel);
            }
        }

        return img;
    }

    public static void main(String[] args) {
        /* For testing image regex */
        BufferedImage img = imageRegex("mystery.txt", 400, 400);

        File outputfile = new File("output_img.jpg");
        try {
            ImageIO.write(img, "jpg", outputfile);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
