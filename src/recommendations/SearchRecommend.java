package recommendations;

import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class SortSearchMovie implements Comparator<Movie> {
  public int compare(final Movie a, final Movie b) {
    if (a.getGrade() != b.getGrade()) {
      return (int) (a.getGrade() - b.getGrade());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

class SortSearchSerial implements Comparator<Serial> {
  public int compare(final Serial a, final Serial b) {
    if (a.getGrade() != b.getGrade()) {
      return (int) (a.getGrade() - b.getGrade());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

public class SearchRecommend {

  private String message = "SearchRecommendation result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Movie> sameMovies = new ArrayList<>();
  private final List<Serial> sameSerials = new ArrayList<>();
  private final List<Movie> unseenMovies = new ArrayList<>();
  private final List<Serial> unseenSerials = new ArrayList<>();

  public SearchRecommend(
      final String username,
      final String genre,
      final List<User> users,
      final List<Movie> movies,
      final List<Serial> serials) {

    // salvez filmele si serialele de acelasi genre si cu rating nenul
    for (Movie movie : movies) {
      for (int j = 0; j < movie.getGenres().size(); j++) {
        if (movie.getGenres().get(j).toLowerCase().equals(genre.toLowerCase())) {
          sameMovies.add(movie);
          break;
        }
      }
    }
    for (Serial serial : serials) {
      for (int j = 0; j < serial.getGenres().size(); j++) {
        if (serial.getGenres().get(j).toLowerCase().equals(genre.toLowerCase())) {
          sameSerials.add(serial);
          break;
        }
      }
    }

    // creez lista cu filme si seriale nevazute pt utilizator
    boolean isPremium = true;
    for (User user : users) { // parcurg lista de utilizatori
      if (user.getUsername().equals(username)) { // gasesc utilizatorul premium
        if (user.getSubscriptionType().equals("PREMIUM")) {

          for (Movie sameMovie : sameMovies) { // parcurg filmele si salvez filmele nevazute
            boolean currentVideo = true;
            for (Map.Entry<String, Integer> entry
                    : user.getHistory().entrySet()) { // parcurg istoricul
              if (entry.getKey().equals(sameMovie.getTitle())) {
                currentVideo = false;
                break;
              }
            }
            if (currentVideo) {
              unseenMovies.add(sameMovie);
            }
          }
          for (Serial sameSerial : sameSerials) { // parcurg serialele si salvez serialele nevazute
            boolean currentVideo = true;
            for (Map.Entry<String, Integer> entry
                    : user.getHistory().entrySet()) { // parcurg istoricul
              if (entry.getKey().equals(sameSerial.getTitle())) {
                currentVideo = false;
                break;
              }
            }
            if (currentVideo) {
              unseenSerials.add(sameSerial);
            }
          }
        } else {
          isPremium = false;
          message = "SearchRecommendation cannot be applied!";
          break;
        }
      }
    }

    // sortez listele cu videoclipuri si le afisez in output
    if (isPremium) {
      unseenMovies.sort(new SortSearchMovie());
      unseenSerials.sort(new SortSearchSerial());
      boolean noValues = false;
      if (unseenMovies.size() == 0 && unseenSerials.size() == 0) {
        message = "SearchRecommendation cannot be applied!";
        noValues = true;
      }
      if (!noValues) {
        if (unseenMovies.size() == 0) {
          for (int i = 0; i < unseenSerials.size(); i++) {
            if (i != unseenSerials.size() - 1) {
              message += unseenSerials.get(i).getTitle() + ", ";
            } else {
              message += unseenSerials.get(i).getTitle() + "]";
            }
          }
        }
        if (unseenSerials.size() == 0) {
          for (int i = 0; i < unseenMovies.size(); i++) {
            if (i != unseenMovies.size() - 1) {
              message += unseenMovies.get(i).getTitle() + ", ";
            } else {
              message += unseenMovies.get(i).getTitle() + "]";
            }
          }
        }
      }
    }
  }
}
