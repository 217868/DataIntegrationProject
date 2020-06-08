package projectdi.Logic.films_retrieving;

import helpers.Const;
import imported.HttpRequestFunctions;
import projectdi.Logic.exceptions.MovieNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmURLHelper {

    public List<String> getUrlsFromTitles(String fileName) {
        Scanner s = null;

        try {
            s = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> urls = new ArrayList<>();

        while(s.hasNextLine()) {
            urls.add(searchFilmURL(s.nextLine()));
        }
        return urls;

    }

    public String searchFilmURL(String title) {
        Scanner s = null;


        try {
            HttpRequestFunctions.httpRequest1(Const.SEARCH_URL.getValue(), title, Const.OUTPUT_FILE_NAME.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        s = openFile(s);

        String testHref = Const.SEARCH_URL.getValue() + title;

        if (!checkIfPageIsMovie(s)) {
            System.out.println("Strona to nie film, szukam dalej");
            String href = checkIfDisambiguation(s);
            changePage(href);
            href = checkIfList(s, title);
            changePage(href);

            testHref = href;
        }

        if (!checkIfPageIsMovie(s)) return "";

        testHref = testHref.replace(' ', '_');
        return testHref;
    }

    private boolean checkIfPageIsMovie(Scanner s) {
        s = openFile(s);

        String checkIfMovieRegex1 = "<table class=\"infobox vevent\"";
        String checkIfMovieRegex2 = "Directed by";



        Pattern pattern1 = Pattern.compile(checkIfMovieRegex1);
        Pattern pattern2 = Pattern.compile(checkIfMovieRegex2);

        boolean firstFound = false;
        boolean secondFound = false;

        while(s.hasNextLine()) {
            String line = s.nextLine();
            Matcher matcher1 = pattern1.matcher(line);
            if(matcher1.find()){
                firstFound = true;
            }
            Matcher matcher2 = pattern2.matcher(line);
            if(matcher2.find()){
                secondFound = true;
            }
            if (firstFound && secondFound) return true;
        }

        return false;
    }

    private String checkIfDisambiguation(Scanner s) {
        s = openFile(s);

        String checkIfDisambiguation = "class=\".*?mw-disambig.*?\"";
        String findUrl = "(?<=href=\").*?(?=\")";
        String exclude = "<li>";


        Pattern pattern1 = Pattern.compile(checkIfDisambiguation);
        Pattern urlPattern = Pattern.compile(findUrl);
        Pattern excludePattern = Pattern.compile(exclude);

        while(s.hasNextLine()) {
            String line = s.nextLine();

            Matcher matcher1 = pattern1.matcher(line);
            if(matcher1.find()){
                Matcher matcher2 = urlPattern.matcher(line);
                if(matcher2.find()){
                    Matcher matcher3 = excludePattern.matcher(line);
                    if (matcher3.find()) continue;

                    return Const.SEARCH_URL_SHORT.getValue() + matcher2.group();
                }
            }
        }

        return "";
    }

    private String checkIfList(Scanner s, String title) {
        s = openFile(s);

        String findFromList1 = title;
        String findFromList2 = "film";

        String findUrl = "(?<=href=\").*?(?=\")";

        Pattern pattern1 = Pattern.compile(findFromList1);
        Pattern pattern2 = Pattern.compile(findFromList2);
        Pattern urlPattern = Pattern.compile(findUrl);

        while(s.hasNextLine()) {
            String line = s.nextLine();
            Matcher matcher1 = pattern1.matcher(line);
            if(matcher1.find()){
                Matcher matcher2 = pattern2.matcher(line);
                if(matcher2.find()){
                    Matcher matcher3 = urlPattern.matcher(line);
                    if (matcher3.find()) {
                        return Const.SEARCH_URL_SHORT.getValue() + matcher3.group();
                    }
                }
            }
        }
        return "";
    }

    private void changePage(String href) {
        if (!href.equals("")) {
            try {
                HttpRequestFunctions.httpRequest1(href, "", Const.OUTPUT_FILE_NAME.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Scanner openFile(Scanner s) {

        try {
            s = new Scanner(new File(Const.OUTPUT_FILE_NAME.getValue()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }


}
