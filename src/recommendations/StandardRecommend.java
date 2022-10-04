package recommendations;

import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.List;
import java.util.Map;

public class StandardRecommend {

  private String message = "StandardRecommendation result: ";

  public final String getMessage() {
    return message;
  }

  public StandardRecommend(
      final String username,
      final List<User> users,
      final List<Movie> movies,
      final List<Serial> serials) {

    for (User user : users) { // parcurg lista de utilizatori
      if (user.getUsername().equals(username)) { // gasesc utilizatorul

        boolean videoIsFound = false;

        for (Movie movie : movies) { // parcurg filmele si afisez primul film nevazut
          boolean currentVideo = true;
          for (Map.Entry<String, Integer> entry
                  : user.getHistory().entrySet()) { // parcurg istoricul
            if (entry.getKey().equals(movie.getTitle())) {
              currentVideo = false;
              break;
            }
          }
          if (currentVideo) {
            videoIsFound = true;
            message += movie.getTitle();
            break;
          }
        }
        if (!videoIsFound) { // in cazul in care nu am gasit un film nevazut
          for (Serial serial : serials) { // parcurg serialele si afisez primul serial nevazut
            boolean currentVideo = true;
            for (Map.Entry<String, Integer> entry
                    : user.getHistory().entrySet()) { // [arcurg istoricul
              if (entry.getKey().equals(serial.getTitle())) {
                currentVideo = false;
                break;
              }
            }
            if (currentVideo) {
              message += serial.getTitle();
              break;
            }
          }
        }
      }
    }
    if (message.equals("StandardRecommendation result: ")) {
      message = "StandardRecommendation cannot be applied!";
    }
  }
}
