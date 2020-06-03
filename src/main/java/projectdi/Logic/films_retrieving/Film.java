package projectdi.Logic.films_retrieving;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Film {
    private String title;
    private String image;
    private int year;
    private Date releaseDateInUSA;
    private List<String> countries;
    private List<String> directors;
    private List<String> cast;
    private int durationInMinutes;
    private List<String> distributedBy;
    private List<String> languages;
    private List<String> musicAuthor;
    private float boxOffice;

    public Film(String title, String image, int year, Date releaseDateInUSA, List<String> countries,
                List<String> directors, List<String> cast, int durationInMinutes,
                List<String> distributedBy, List<String> languages, List<String> musicAuthor, float boxOffice) {
        this.title = title; // element
        this.image = image; // attribute
        this.year = year; // attribute
        this.releaseDateInUSA = releaseDateInUSA; // element
        this.countries = countries; //elements
        this.directors = directors; // elements
        this.cast = cast; // elements
        this.durationInMinutes = durationInMinutes; //element
        this.distributedBy = distributedBy; // element
        this.languages = languages; // elements
        this.musicAuthor = musicAuthor; // element
        this.boxOffice = boxOffice; // element
    }

    public Film(Film film) {
        this.title = film.title; // element
        this.image = film.image; // attribute
        this.year = film.year; // attribute
        this.releaseDateInUSA = film.releaseDateInUSA; // element
        this.countries = film.countries; //elements
        this.directors = film.directors; // elements
        this.cast = film.cast; // elements
        this.durationInMinutes = film.durationInMinutes; //element
        this.distributedBy = film.distributedBy; // element
        this.languages = film.languages; // elements
        this.musicAuthor = film.musicAuthor; // element
        this.boxOffice = film.boxOffice; // element
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getReleaseDateInUSA() {
        return releaseDateInUSA;
    }

    public void setReleaseDateInUSA(Date releaseDateInUSA) {
        this.releaseDateInUSA = releaseDateInUSA;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<String> getDistributedBy() {
        return distributedBy;
    }

    public void setDistributedBy(List<String> distributedBy) {
        this.distributedBy = distributedBy;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getMusicAuthor() {
        return musicAuthor;
    }

    public void setMusicAuthor(List<String> musicAuthor) {
        this.musicAuthor = musicAuthor;
    }

    public float getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(float boxOffice) {
        this.boxOffice = boxOffice;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", year=" + year +
                ", releaseDateInUSA=" + releaseDateInUSA +
                ", countries=" + countries +
                ", directors=" + directors +
                ", cast=" + cast +
                ", durationInMinutes=" + durationInMinutes +
                ", distributedBy=" + distributedBy +
                ", languages=" + languages +
                ", musicAuthor=" + musicAuthor +
                ", boxOffice=" + boxOffice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return year == film.year &&
                durationInMinutes == film.durationInMinutes &&
                Float.compare(film.boxOffice, boxOffice) == 0 &&
                Objects.equals(title, film.title) &&
                Objects.equals(image, film.image) &&
                Objects.equals(releaseDateInUSA, film.releaseDateInUSA) &&
                Objects.equals(countries, film.countries) &&
                Objects.equals(directors, film.directors) &&
                Objects.equals(cast, film.cast) &&
                Objects.equals(distributedBy, film.distributedBy) &&
                Objects.equals(languages, film.languages) &&
                Objects.equals(musicAuthor, film.musicAuthor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, image, year, releaseDateInUSA, countries, directors, cast, durationInMinutes, distributedBy, languages, musicAuthor, boxOffice);
    }
}
