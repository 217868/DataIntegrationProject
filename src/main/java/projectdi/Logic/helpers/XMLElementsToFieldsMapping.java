package helpers;

public enum XMLElementsToFieldsMapping {
    title("title"),
    image("image_link"),
    year("year"),
    releaseDateInUSA("release_date_in_USA"),
    countries("countries"),
    directors("directors"),
    cast("cast"),
    durationInMinutes("duration_in_minutes"),
    distributedBy("distributors"),
    languages("languages"),
    musicAuthor("music_authors"),
    boxOffice("box_office");

    private String value;

    XMLElementsToFieldsMapping(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }
}
