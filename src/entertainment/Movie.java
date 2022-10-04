package entertainment;

import fileio.ShowInput;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends ShowInput {
  /** Duration in minutes of a season */
  private final int duration;
  /** grade */
  private double grade = 0;
  /** number of grades */
  private int numberGrades = 0;
  /** number of views */
  private int numberViews = 0;
  /** number of favorites */
  private int numberFavs = 0;

  List<String> ratedUsers;

  public Movie(
      final String title,
      final ArrayList<String> cast,
      final ArrayList<String> genres,
      final int year,
      final int duration) {
    super(title, year, cast, genres);
    this.duration = duration;
  }

  public int getDuration() {
    return duration;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(final double grade) {
    this.grade += grade;
  }

  public int getNumberGrades() {
    return numberGrades;
  }

  public void setNumberGrades() {
    this.numberGrades += 1;
  }

  public int getNumberViews() {
    return numberViews;
  }

  public void setNumberViews(final int numberViews) {
    this.numberViews += numberViews;
  }

  public int getNumberFavs() {
    return numberFavs;
  }

  public void setNumberFavs() {
    this.numberFavs = this.numberFavs + 1;
  }

  public void setRatedUsers(final List<String> ratedUsers) {
    this.ratedUsers = ratedUsers;
  }

  public List<String> getRatedUsers() {
    return ratedUsers;
  }

  @Override
  public String toString() {
    return "Movie{" + "title= " + this.getTitle() + " grade = " + this.getGrade();
  }
}
