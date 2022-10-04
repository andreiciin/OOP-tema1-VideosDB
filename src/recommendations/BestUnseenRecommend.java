package recommendations;

import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class SortBestMovie implements Comparator<Movie> {
  public int compare(final Movie a, final Movie b) {
    if (a.getGrade() != b.getGrade()) {
      return (int) (a.getGrade() - b.getGrade());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

class SortBestSerial implements Comparator<Serial> {
  public int compare(final Serial a, final Serial b) {
    if (a.getGrade() != b.getGrade()) {
      return (int) (a.getGrade() - b.getGrade());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

public class BestUnseenRecommend {

  private String message = "BestRatedUnseenRecommendation result: ";

  public final String getMessage() {
    return message;
  }

  private final List<Movie> unseenMovies = new ArrayList<>();
  private final List<Serial> unseenSerials = new ArrayList<>();
  private final List<Movie> ratedMovies = new ArrayList<>();
  private final List<Serial> ratedSerials = new ArrayList<>();

  public BestUnseenRecommend(
      final String username,
      final List<User> users,
      final List<Movie> movies,
      final List<Serial> serials) {

    // creez o lista de seriale si filme nevazute, pe care o voi ordona descrescator
    for (User user : users) { // parcurg lista de utilizatori
      if (user.getUsername().equals(username)) { // gasesc utilizatorul

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
      }
    }

    // adaug filmele si serialele cu nota nevida in liste aferente pt a le sorta
    for (Movie unseenMovie : unseenMovies) {
      if (unseenMovie.getGrade() != 0) {
        ratedMovies.add(unseenMovie);
      }
    }
    for (Serial unseenSerial : unseenSerials) {
      if (unseenSerial.getGrade() != 0) {
        ratedSerials.add(unseenSerial);
      }
    }

    // sortez filmele si serialele
    ratedMovies.sort(new SortBestMovie());
    ratedSerials.sort(new SortBestSerial());

    // creez outputul dupa criteriile cerute
    boolean videoIsFound = false;
    if (ratedMovies.size() > 0) {
      message += ratedMovies.get(ratedMovies.size() - 1).getTitle();
      videoIsFound = true;
    }
    if ((!videoIsFound) && (ratedSerials.size() > 0)) {
      message += ratedSerials.get(ratedSerials.size() - 1).getTitle();
      videoIsFound = true;
    }
    if ((!videoIsFound) && (unseenMovies.size() > 0)) {
      message += unseenMovies.get(0).getTitle();
      videoIsFound = true;
    }
    if ((!videoIsFound) && (unseenSerials.size() > 0)) {
      message += unseenSerials.get(0).getTitle();
    }
    if (message.equals("BestRatedUnseenRecommendation result: ")) {
      message = "BestRatedUnseenRecommendation cannot be applied!";
    }
  }
}
