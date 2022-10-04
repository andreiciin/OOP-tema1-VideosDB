package user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class User {
  /** User's username */
  private final String username;
  /** Subscription Type */
  private final String subscriptionType;
  /** The history of the movies seen */
  private final Map<String, Integer> history;

  /** Movies added to favorites */
  private final ArrayList<String> favoriteMovies;

  private final ArrayList<Integer> isFavorite =
      new ArrayList<Integer>(Collections.nCopies(1000, 0));

  private int numberFavs = 0;

  public User(
      final String username,
      final String subscriptionType,
      final Map<String, Integer> history,
      final ArrayList<String> favoriteMovies) {
    this.username = username;
    this.subscriptionType = subscriptionType;
    this.favoriteMovies = favoriteMovies;
    this.history = history;
  }

  public final String getUsername() {
    return username;
  }

  public final Map<String, Integer> getHistory() {
    return history;
  }

  public final String getSubscriptionType() {
    return subscriptionType;
  }

  public final ArrayList<String> getFavoriteMovies() {
    return favoriteMovies;
  }

  public final int getNumberFavs() {
    return numberFavs;
  }

  public final void setNumberFavs(final int numberFavs) {
    this.numberFavs = numberFavs;
  }

  public final ArrayList<Integer> getIsFavorite() {
    return isFavorite;
  }

  public final void setIsFavorite(final int i) {
    this.isFavorite.add(i, 1);
  }
}
