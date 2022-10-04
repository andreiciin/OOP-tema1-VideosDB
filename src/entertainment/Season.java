package entertainment;

import java.util.List;

/**
 * Information about a season of a tv show
 *
 * <p>DO NOT MODIFY
 */
public final class Season {
  /** Number of current season */
  private final int currentSeason;
  /** Duration in minutes of a season */
  private int duration;
  /** List of ratings for each season */
  private int ratings = 0;

  private int numberRatings = 0;

  private List<String> ratedUsers;

  public Season(final int currentSeason, final int duration) {
    this.currentSeason = currentSeason;
    this.duration = duration;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(final int duration) {
    this.duration = duration;
  }

  public double getRatings() {
    return ratings;
  }

  public void setRatings(final double ratings) {
    this.ratings += ratings;
  }

  public int getNumberRatings() {
    return numberRatings;
  }

  public void setNumberRatings() {
    this.numberRatings += 1;
  }

  public List<String> getRatedUsers() {
    return ratedUsers;
  }

  public void setRatedUsers(final List<String> ratedUsers) {
    this.ratedUsers = ratedUsers;
  }

  @Override
  public String toString() {
    return "Episode{" + "number ratings=" + numberRatings + ", ratings=" + ratings + '}';
  }
}
