package entertainment;

import fileio.ShowInput;

import java.util.ArrayList;

public final class Serial extends ShowInput {
  /** Number of seasons */
  private final int numberOfSeasons;
  /** Season list */
  private final ArrayList<Season> seasons;

  private double grade;

  private int totalDuration;

  private int numberViews = 0;

  private int numberFavs = 0;

  public Serial(
      final String title,
      final ArrayList<String> cast,
      final ArrayList<String> genres,
      final int numberOfSeasons,
      final ArrayList<Season> seasons,
      final int year) {
    super(title, year, cast, genres);
    this.numberOfSeasons = numberOfSeasons;
    this.seasons = seasons;
  }

  public int getNumberSeason() {
    return numberOfSeasons;
  }

  public ArrayList<Season> getSeasons() {
    return seasons;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(final double grade) {
    this.grade = grade;
  }

  public int getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(final int totalDuration) {
    this.totalDuration = totalDuration;
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
    this.numberFavs += 1;
  }

  @Override
  public String toString() {
    return "Serial{" + " title= " + super.getTitle() + " grade: " + this.getGrade();
  }
}
