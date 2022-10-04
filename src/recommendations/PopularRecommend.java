package recommendations;

import entertainment.Genre;
import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PopularRecommend {

  private String message = "PopularRecommendation result: ";

  public final String getMessage() {
    return message;
  }

  private final List<String> genres =
      Stream.of(Genre.values()).map(Genre::name).collect(Collectors.toList());

  private final int length = 30;
  private final List<Integer> views = new ArrayList<>(Collections.nCopies(length, 0));
  private final List<Movie> unseenMovies = new ArrayList<>();
  private final List<Serial> unseenSerials = new ArrayList<>();

  public PopularRecommend(
      final String username,
      final List<User> users,
      final List<Movie> movies,
      final List<Serial> serials) {

    // calculez nr de vizionari ale filmelor
    for (User value : users) {
      for (Map.Entry<String, Integer> entry : value.getHistory().entrySet()) {
        for (Movie movie : movies) {
          if (entry.getKey().equals(movie.getTitle())) {
            int view = entry.getValue();
            movie.setNumberViews(view);
          }
        }
        for (Serial serial : serials) {
          if (entry.getKey().equals(serial.getTitle())) {
            int view = entry.getValue();
            serial.setNumberViews(view);
          }
        }
      }
    }

    // creez lista cu cele mai populare genuri
    for (Movie value : movies) { // parcurg lista de filme
      for (int j = 0; j < value.getGenres().size(); j++) { // parcurg toate genurile filmului
        for (int k = 0; k < genres.size(); k++) {
          if (value.getGenres().get(j).toLowerCase().equals(genres.get(k).toLowerCase())) {
            int sum = views.get(k);
            sum += value.getNumberViews();
            views.add(k, sum);
          }
        }
      }
    }
    for (Serial value : serials) {
      for (int j = 0; j < value.getGenres().size(); j++) {
        for (int k = 0; k < genres.size(); k++) {
          if (value.getGenres().get(j).toLowerCase().equals(genres.get(k).toLowerCase())) {
            int sum = views.get(k);
            sum += value.getNumberViews();
            views.add(k, sum);
          }
        }
      }
    }

    // sortez descrescator listele
    List<String> sortedGenres = new ArrayList<>();
    sortedGenres.addAll(genres);
    for (int i = 0; i < genres.size(); i++) {
      for (int j = i + 1; j < genres.size(); j++) {
        if (views.get(i) < views.get(j)) {
          int aux = views.get(j);
          views.set(j, views.get(i));
          views.set(i, aux);
          String auxS = sortedGenres.get(j);
          sortedGenres.set(j, sortedGenres.get(i));
          sortedGenres.set(i, auxS);
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
            if (currentVideo) {
              unseenMovies.add(movie);
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
            if (currentVideo) {
              unseenSerials.add(serial);
            }
          }
        } else {
          isPremium = false;
          message = "PopularRecommendation cannot be applied!";
          break;
        }
      }
    }

    if (isPremium) {
      boolean isFound = false;
      for (String sortedGenre : sortedGenres) {
        if (!isFound) {
          for (Movie unseenMovie : unseenMovies) {
            if (!isFound) {
              for (int k = 0; k < unseenMovie.getGenres().size(); k++) {
                if (sortedGenre
                    .toLowerCase()
                    .equals(unseenMovie.getGenres().get(k).toLowerCase())) {
                  isFound = true;
                  message += unseenMovie.getTitle();
                  break;
                }
              }
            }
          }
          for (Serial unseenSerial : unseenSerials) {
            if (!isFound) {
              for (int k = 0; k < unseenSerial.getGenres().size(); k++) {
                if (sortedGenre
                    .toLowerCase()
                    .equals(unseenSerial.getGenres().get(k).toLowerCase())) {
                  isFound = true;
                  message += unseenSerial.getTitle();
                  break;
                }
              }
            }
          }
        }
      }
      if (message.equals("PopularRecommendation result: ")) {
        message = "PopularRecommendation cannot be applied!";
      }
    }
  }
}
