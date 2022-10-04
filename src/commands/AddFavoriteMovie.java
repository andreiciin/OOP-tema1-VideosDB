package commands;

import user.User;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddFavoriteMovie {

  private String message = null;

  public final String getMessage() {
    return message;
  }

  public AddFavoriteMovie(final String username, final String title, final List<User> users) {

    for (User user : users) { // parcurg lista de utilizatori
      if (user.getUsername().equals(username)) { // gasesc utilizatorul

        // verific daca filmul a fost vizionat vreodata
        Iterator<Map.Entry<String, Integer>> iterator = user.getHistory().entrySet().iterator();

        boolean movieIsSeen = false;

        while (iterator.hasNext()) {

          Map.Entry<String, Integer> entry = iterator.next();
          if (entry.getKey().equals(title)) {
            movieIsSeen = true;
            break;
          }
        }
        if (!movieIsSeen) {
          message = "error -> " + title + " is not seen";
        }

        // verific daca filmul e deja la favorite
        boolean favIsDuplicate = false;
        if (movieIsSeen) {
          for (int i = 0; i < user.getFavoriteMovies().size(); i++) {
            if (user.getFavoriteMovies().get(i).equals(title)) {
              favIsDuplicate = true;
              break;
            }
          }
        }
        if (favIsDuplicate) {
          message = "error -> " + title + " is already in favourite list";
        }

        // adaug filmul la favorite daca trec de conditiile de mai sus
        if (movieIsSeen && !favIsDuplicate) {
          user.getFavoriteMovies().add(title);
          message = "success -> " + title + " was added as favourite";
        }
      }
    }
  }
}
