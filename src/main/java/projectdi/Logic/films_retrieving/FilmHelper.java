package projectdi.Logic.films_retrieving;

import helpers.Const;
import imported.HttpRequestFunctions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmHelper {
    private List<String> infoBox;

    public List<Film> createFilms(List<String> urls) {
        List<Film> list = new ArrayList<>();
        for (String s : urls) {
            list.add(createFilm(s));
        }
        return list;
    }

    public Film createFilm(String url){
        getInfoBox(url);
        return new Film(getTitle(),
                getImageLink(),
                getYearOfRelease(),
                getReleaseDate(),
                getCountries(),
                getDirectors(),
                getActors(),
                getDurationInMinutes(),
                getDistributedBy(),
                getLanguages(),
                getMusicAuthors(),
                getBoxOffice());
    }

    public String getContentSingleLine(String header){

        // pierwszy regex : (?<=Box office)(\n|.)*(?=td)
        // drugi regex : (?<=>)[^<]*(?=<)
        Pattern onlyHeaderPattern = Pattern.compile(header);
        String tag = "";
        boolean beginFound = false;
     //   boolean firstTime = false;
        for(String line : infoBox){
            Matcher headerMatcher = onlyHeaderPattern.matcher(line);
            if(line.contains("<th") && beginFound) {
                tag += line;
                break;
            }
            if(headerMatcher.find()) beginFound = true;
            if(beginFound) tag += line;
        }


        String headerRegex = "(?<=" + header + ")(\\n|.)*?(?=/td)";
        Pattern headerRegexPattern = Pattern.compile(headerRegex);
        String contenRegex = "(?<=>)[^<]*(?=<)";
        Pattern contentRegexPattern = Pattern.compile(contenRegex);
        String content = "";

        Matcher m1 = headerRegexPattern.matcher(tag);
        if(m1.find()) content = m1.group();

        Matcher m2 = contentRegexPattern.matcher(content);
        String matches = "";
        while(m2.find()) if (!m2.group().isEmpty() && !m2.group().contains("&#91") )matches += m2.group() + "\n";
        return matches;
    }

    public void getInfoBox(String url){
        Scanner s = null;
        List<String> resultBox = new ArrayList<>();

        try {
            HttpRequestFunctions.httpRequest1(url, "", Const.OUTPUT_FILE_NAME.getValue());
            s = new Scanner(new File(Const.OUTPUT_FILE_NAME.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }



        int lineCounter = 0;

        boolean openTagFound = false;
        boolean closingtagFound = false;
        String openTag = "<table class=\"infobox vevent\"";
        String closingTag = "</table>";
        Pattern openTagPattern = Pattern.compile(openTag);
        Pattern closingTagPattern = Pattern.compile(closingTag);
        while(s.hasNextLine()){
            String line = s.nextLine();
            Matcher m1 = openTagPattern.matcher(line);
            Matcher m2 = closingTagPattern.matcher(line);
            if(m1.find()) {
                openTagFound = true;
            }
            if(m2.find() && openTagFound) closingtagFound = true;
            if(openTagFound) resultBox.add(line);
            if(closingtagFound) {
                this.infoBox = resultBox;
                return;
            }
            lineCounter++;
        }
    }

    public String getTitle(){
        String titleRegex = "<th.*class=\"summary\".*<\\/th>";
        Pattern titleRegexPattern = Pattern.compile(titleRegex);
        String titleTag = "";

        for(String line : infoBox){
            Matcher m1 = titleRegexPattern.matcher(line);
            if(m1.find()) titleTag = m1.group();
        }

        String contentRegex =  "(?<=>)[^<]*(?=<)";
        Pattern contentPattern = Pattern.compile(contentRegex);
        Matcher m2 = contentPattern.matcher(titleTag);
        if(m2.find()) return m2.group();
        return "";
    }

    public String getImageLink(){
        String imageRegex = "<img alt=\".*?>";
        Pattern imageRegexPattern = Pattern.compile(imageRegex);
        String imageTag = "";

        for(String line : infoBox){
            Matcher m1 = imageRegexPattern.matcher(line);
            if(m1.find()) imageTag = m1.group();
        }

        String contentRegex = "(?<=src=\").*?(?=\")";
        Pattern contentPattern = Pattern.compile(contentRegex);
        Matcher m2 = contentPattern.matcher(imageTag);
        if(m2.find()) return "https:" + m2.group();
        return "";
    }



   // <img alt="The Hobbit- An Unexpected Journey.jpeg" src="//upload.wikimedia.org/wikipedia/en/b/b3/The_Hobbit-_An_Unexpected_Journey.jpeg" decoding="async" width="220" height="326" class="thumbborder" data-file-width="220" data-file-height="326">

    public Date getReleaseDate(){
        String year = getContentSingleLine("Release date");
        String dateRegex = "\\(.*\\s*[0-9]{4}-[0-9]{2}-[0-9]{2}\\s*\\)\\s.*(United States)";
        Pattern dateRegexPattern = Pattern.compile(dateRegex);
        String unitedStatesDate = "";
        Matcher m1 = dateRegexPattern.matcher(year);
        if(m1.find()) unitedStatesDate = m1.group();


        String onlyDateRegex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern onlyDatePattern = Pattern.compile(onlyDateRegex);
        Matcher m2 = onlyDatePattern.matcher(unitedStatesDate);
        String onlyDate = "";
        if(m2.find()) onlyDate = m2.group();
        else {
            m2 = onlyDatePattern.matcher(year);
            if (m2.find()) onlyDate = m2.group();
        }
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(onlyDate);
        } catch (ParseException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            String dateInString = "01/1/1900";
            try {
                return sdf.parse(dateInString);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return date;
    }

    public int getYearOfRelease(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(getReleaseDate());
        return cal.get(Calendar.YEAR);
    }

    public List<String> getCountries(){
        return stringToListOfStrings(getContentSingleLine("Country"));
    }

    public List<String> getDirectors(){
        return stringToListOfStrings(getContentSingleLine("Directed by"));
    }

    public List<String> getActors(){
        return stringToListOfStrings(getContentSingleLine("Starring"));
    }

    private int getDurationInMinutes(){
        String duration = getContentSingleLine("Running time");
        String durationRegex = "\\d*(?= minutes)";
        Pattern durationPattern = Pattern.compile(durationRegex);
        Matcher m1 = durationPattern.matcher(duration);
        if(m1.find()) return Integer.parseInt(m1.group());
        return 0;
    }

    private List<String> getDistributedBy(){
        return stringToListOfStrings(getContentSingleLine("Distributed by"));
    }

    private List<String> getLanguages(){
        return stringToListOfStrings(getContentSingleLine("Language"));
    }

    private List<String> getMusicAuthors(){
        return stringToListOfStrings(getContentSingleLine("Music by"));
    }

    private float getBoxOffice(){
        String boxOffice = getContentSingleLine("Box office");
        String boxOfficeRegex = "(?<=\\$).*(?= million)";
        Pattern boxOfficePattern = Pattern.compile(boxOfficeRegex);
        Matcher m1 = boxOfficePattern.matcher(boxOffice);
        if(m1.find()) return Float.parseFloat(m1.group());
        return 0;
    }

    private List<String> stringToListOfStrings(String s){
        return Arrays.asList(s.split("\n"));
    }




}
// (?<=<table class="infobox vevent")(\n|.)*?(?=<\/table>)
