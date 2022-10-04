package actor;

import java.util.ArrayList;
import java.util.Map;

public final class Actor {
  /** actor name */
  private String name;
  /** description of the actor's career */
  private String careerDescription;
  /** videos starring actor */
  private ArrayList<String> filmography;
  /** awards won by the actor */
  private final Map<ActorsAwards, Integer> awards;

  private double ratingAvg = 0;

  private int numberRatings = 0;

  private int serialRatings = 0;

  private double finalRating;

  private int numberAwards = 0;

  public Actor(
      final String name,
      final String careerDescription,
      final ArrayList<String> filmography,
      final Map<ActorsAwards, Integer> awards) {
    this.name = name;
    this.careerDescription = careerDescription;
    this.filmography = filmography;
    this.awards = awards;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public ArrayList<String> getFilmography() {
    return filmography;
  }

  public void setFilmography(final ArrayList<String> filmography) {
    this.filmography = filmography;
  }

  public Map<ActorsAwards, Integer> getAwards() {
    return awards;
  }

  public double getRatingAvg() {
    return ratingAvg;
  }

  public void setRatingAvg(final double ratingAvg) {
    this.ratingAvg += ratingAvg;
  }

  public int getNumberRatings() {
    return numberRatings;
  }

  public void setNumberRatings() {
    this.numberRatings += 1;
  }

  public double getFinalRating() {
    return finalRating;
  }

  public void setFinalRating(final double finalRating) {
    this.finalRating = finalRating;
  }

  public int getNumberAwards() {
    return numberAwards;
  }

  public void setNumberAwards(final int numberAwards) {
    this.numberAwards = numberAwards;
  }

  public String getCareerDescription() {
    return careerDescription;
  }

  public int getSerialRatings() {
    return serialRatings;
  }

  public void setSerialRatings(int serialRatings) {
    this.serialRatings = serialRatings;
  }

  @Override
  public String toString() {
    return "Actor{"
        + "name='"
        + name
        + '\''
        + ", ratingAvg="
        + ratingAvg
        + ", numberRat="
        + serialRatings
        + ", finalRating="
        + finalRating
        + '}';
  }

  public void setCareerDescription(final String careerDescription) {
    this.careerDescription = careerDescription;
  }
}
