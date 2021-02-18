import lombok.Data;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class IrregularVerbs {
    //путь где лежит документ с неправильными глаголами
    private String path = "E:\\Project\\Bot\\src\\main\\resources\\words.html";

    //метод который считает файл в строку
    public String readFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(getPath())))) {
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public List<String[]> readIrregularVerbs() {
        String str = readFile();
        List<String[]> irregularVerbsList = new ArrayList<>();

        // Pattern pattern = Pattern.compile("<td>(?<first>\\w.*)</td>");
        //Pattern pattern = Pattern.compile(";</span>(?<first>\\w.+?)<span class");
        Pattern pattern = Pattern.compile("td&gt;</span>(?<first>\\w.+?)<span class=\"html-tag\">&lt;.*?" +
                "</span>(?<second>\\w.+?)<span class=\"html-tag\">&lt;.*?" +
                "</span>(?<third>\\w.+?)<span class=\"html-tag\">&lt;.*?" +
                "</span>(?<fourth>[а-яА-я]+)<span class=\"html-tag\">&lt;");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String[] strings = new String[4];
            strings[0] = matcher.group("first");
            strings[1] = matcher.group("second");
            strings[2] = matcher.group("third");
            strings[3] = matcher.group("fourth");
            irregularVerbsList.add(strings);

        }

        return irregularVerbsList;
    }

    public List<ModelVerbs> readIrrVerbs() {
        String str = readFile();
        List<ModelVerbs> irregularVerbsList = new ArrayList<>();

        Pattern pattern = Pattern.compile("td&gt;</span>(?<first>\\w.+?)<span class=\"html-tag\">&lt;.*?" +
                "</span>(?<second>\\w.+?)<span class=\"html-tag\">&lt;.*?" +
                "</span>(?<third>\\w.+?)<span class=\"html-tag\">&lt;.*?" +
                "</span>(?<fourth>[а-яА-я]+)<span class=\"html-tag\">&lt;");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            ModelVerbs modelVerbs = new ModelVerbs(matcher.group("first"),
                    matcher.group("second"),
                    matcher.group("third"),
                    matcher.group("fourth"));
            irregularVerbsList.add(modelVerbs);
        }
        return irregularVerbsList;
    }
}
