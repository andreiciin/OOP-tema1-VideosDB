package recommendations;

import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class SortRecFavMovie implements Comparator<Movie> {
  public int compare(final Movie a, final Movie b) {
    if (a.getNumberFavs() != b.getNumberFavs()) {
      return (a.getNumberFavs() - b.getNumberFavs());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

class SortRecFavSerial implements Comparator<Serial> {
  public int compare(final Serial a, final Serial b) {
    if (a.getNumberFavs() != b.getNumberFavs()) {
      return (a.getNumberFavs() - b.getNumberFavs());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

public class FavoriteRecommend {

  private String message = "FavoriteRecommendation result: ";

  public final String getMessage() {
    return message;
  }

  private final List<Movie> favMovies = new ArrayList<>();
  private final List<Serial> favSerials = new ArrayList<>();

  public FavoriteRecommend(
      final String username,
      final List<User> users,
      final List<Movie> movies,
      final List<Serial> serials) {

    // extrag de cate ori a fost adaugat la favorite un film
    for (Movie value : movies) {
      for (User user : users) {
        for (int k = 0; k < user.getFavoriteMovies().size(); k++) {
          if (value.getTitle().equals(user.getFavoriteMovies().get(k))) {
            value.setNumberFavs();
          }
        }
      }
    }
    // extrag de cate ori a fost adaugat la favorite un serial
    for (Serial serial : serials) {
      for (User user : users) {
        for (int k = 0; k < user.getFavoriteMovies().size(); k++) {
          if (serial.getTitle().equals(user.getFavoriteMovies().get(k))) {
            serial.setNumberFavs();
          }
        }
      }
    }
    // creez lista cu filme si seriale nevazute pt utilizator
    boolean isPremium = true;
    for (User user : users) { // parcurg lista de utilizatori
      if (user.getUsername().equals(username)) { // gasesc utilizatorul premium
        if (user.getSubscriptionType().equals("PREMIUM")) {

          for (Movie movie : movies) { // parcurg filmele si salvez filmele nevazute
            boolean currentVideo = true;
            for (Map.Entry<String, Integer> entry
                    : user.getHistory().entrySet()) { // parcurg istoricul
              if (entry.getKey().equals(movie.getTitle())) {
                currentVideo = false;
                break;
              }
            }
            if ((currentVideo) && (movie.getNumberFavs() != 0)) {
              favMovies.add(movie);
            }
          }
          for (Serial serial : serials) { // parcurg serialele si salvez serialele nevazute
            boolean currentVideo = true;
            for (Map.Entry<String, Integer> entry
                    : user.getHistory().entrySet()) { // parcurg istoricul
              if (entry.getKey().equals(serial.getTitle())) {
                currentVideo = false;
                break;
              }
            }
            if (currentVideo && (serial.getNumberFavs() != 0)) {
              favSerials.add(serial);
            }
          }
        } else {
          isPremium = false;
          message = "FavoriteRecommendation cannot be applied!";
          break;
        }
      }
    }
    if (isPremium) {
      // sortarea listelor dupa numarul de adaugari la favorite a video-urilor
      favMovies.sort(new SortRecFavMovie());
      favSerials.sort(new SortRecFavSerial());

      boolean favOk = true;
      if (favMovies.size() == 0 && favSerials.size() == 0) {
        message = "FavoriteRecommendation cannot be applied!";
        favOk = false;
      }
      if (favOk) {
        // caut in liste primul film si il afisez
        boolean isFound = false;
        if (favMovies.size() > 0) {
          message += favMovies.get(favMovies.size() - 1).getTitle();
          isFound = true;
        }
        if (!isFound) {
          if (favSerials.size() > 0) {
            message += favSerials.get(favSerials.size() - 1).getTitle();
          }
        }
      }
    }
  }
}
