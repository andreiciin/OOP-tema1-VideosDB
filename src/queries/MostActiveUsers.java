package queries;

import fileio.ActionInputData;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SortByFavs implements Comparator<User> {
  public int compare(final User a, final User b) {
    if (a.getNumberFavs() != b.getNumberFavs()) {
      return a.getNumberFavs() - b.getNumberFavs();
    } else {
      return a.getUsername().charAt(0) - b.getUsername().charAt(0);
    }
  }
}

public class MostActiveUsers {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<User> sortedUsers = new ArrayList<>();

  public MostActiveUsers(final ActionInputData action, final List<User> users) {

    int number = action.getNumber();
    String sortType = action.getSortType();

    // am sortat userii dupa numarul de ratinguri date crescator
    users.sort(new SortByFavs());

    // salvez doar userii activi
    for (User user : users) {
      if (user.getNumberFavs() != 0) {
        sortedUsers.add(user);
      }
    }

    // creez stringul mesaj pt output din primii number useri sortati asc sau desc
    if (sortType.equals("asc")) {
      for (int i = 0; i < sortedUsers.size(); i++) {
        if (number == 0) {
          break;
        }
        if ((i == sortedUsers.size() - 1) || (number == 1)) {
          message += sortedUsers.get(i).getUsername();
        } else {
          message += sortedUsers.get(i).getUsername() + ", ";
        }
        number--;
      }
      message += "]";
    }

    if (sortType.equals("desc")) {
      for (int i = sortedUsers.size() - 1; i >= 0; i--) {
        if (number == 0) {
          break;
        }
        if ((i == 0) || (number == 1)) {
          message += sortedUsers.get(i).getUsername();
        } else {
          message += sortedUsers.get(i).getUsername() + ", ";
        }
        number--;
      }
      message += "]";
    }
  }
}
