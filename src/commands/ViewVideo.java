package commands;

import user.User;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ViewVideo {

  private String message = null;
  private int views;

  public final String getMessage() {
    return message;
  }

  public ViewVideo(final String username, final String title, final List<User> users) {

    for (User user : users) { // parcurg lista de utilizatori
      if (user.getUsername().equals(username)) { // gasesc utilizatorul

        // verific daca filmul a fost vizionat vreodata
        Iterator<Map.Entry<String, Integer>> iterator = user.getHistory().entrySet().iterator();

        boolean movieIsSeen = false;

        while (iterator.hasNext()) {

          Map.Entry<String, Integer> entry = iterator.next();
          if (entry.getKey().equals(title)) {
            movieIsSeen = true;
            views = entry.getValue();
            views++;
            entry.setValue(views);
            break;
          }
        }
        // daca filmul nu a mai fost vazut:
        if (!movieIsSeen) {
          views = 1;
          user.getHistory().put(title, views);
        }
        message = "success -> " + title + " was viewed with total views of " + views;
      }
    }
  }
}
