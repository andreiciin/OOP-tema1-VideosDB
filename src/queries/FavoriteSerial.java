package queries;

import entertainment.Serial;
import fileio.ActionInputData;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SortFavsSerial implements Comparator<Serial> {
  public int compare(final Serial a, final Serial b) {
    if (a.getNumberFavs() != b.getNumberFavs()) {
      return (a.getNumberFavs() - b.getNumberFavs());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

public class FavoriteSerial {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Serial> sortedSerials = new ArrayList<>();

  public FavoriteSerial(
      final ActionInputData action, final List<Serial> serials, final List<User> users) {

    int number = action.getNumber();
    String sortType = action.getSortType();

    // extrag de cate ori a fost adaugat la favorite un serial
    for (Serial value : serials) {
      for (User user : users) {
        for (int k = 0; k < user.getFavoriteMovies().size(); k++) {
          if (value.getTitle().equals(user.getFavoriteMovies().get(k))) {
            value.setNumberFavs();
          }
        }
      }
    }

    // adaug serialele de sortat aplicand filtrele necesare
    for (Serial serial : serials) {
      boolean filterOk = true;
      if (action.getFilters().get(0).get(0) != null) { // filter year
        String year = String.valueOf(serial.getYear());
        if (!(year.equals(action.getFilters().get(0).get(0)))) {
          filterOk = false;
        }
      }
      if (action.getFilters().get(1).get(0) != null) { // filter genre
        boolean filterGenre = false;
        for (int j = 0; j < serial.getGenres().size(); j++) {
          if (serial
              .getGenres()
              .get(j)
              .toLowerCase()
              .contains(action.getFilters().get(1).get(0).toLowerCase())) {
            filterGenre = true;
            break;
          }
        }
        if (!filterGenre) {
          filterOk = false;
        }
      }
      if ((filterOk) && (serial.getNumberFavs() != 0)) {
        sortedSerials.add(serial);
      }
    }

    // sortez vectorul
    sortedSerials.sort(new SortFavsSerial());

    // creez mesajul pt output in functie de sortarea listei de filme asc sau desc
    if (sortedSerials.size() > 0) {

      if (sortType.equals("asc")) {
        for (int i = 0; i < sortedSerials.size(); i++) {
          if (number == 0) {
            break;
          }
          if ((i == sortedSerials.size() - 1) || (number == 1)) {
            message += sortedSerials.get(i).getTitle();
          } else {
            message += sortedSerials.get(i).getTitle() + ", ";
          }
          number--;
        }
      }
      if (sortType.equals("desc")) {
        for (int i = sortedSerials.size() - 1; i >= 0; i--) {
          if (number == 0) {
            break;
          }
          if ((i == 0) || (number == 1)) {
            message += sortedSerials.get(i).getTitle();
          } else {
            message += sortedSerials.get(i).getTitle() + ", ";
          }
          number--;
        }
      }
    }
    message += "]";
  }
}
