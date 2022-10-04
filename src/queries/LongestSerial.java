package queries;

import entertainment.Serial;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SortByDuration implements Comparator<Serial> {
  public int compare(final Serial a, final Serial b) {
    if (a.getTotalDuration() != b.getTotalDuration()) {
      return (a.getTotalDuration() - b.getTotalDuration());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

public class LongestSerial {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Serial> sortedSerials = new ArrayList<>();

  public LongestSerial(final ActionInputData action, final List<Serial> serials) {

    int number = action.getNumber();
    String sortType = action.getSortType();

    for (Serial serial : serials) {

      int time = 0; // calculez timpul total al serialului in fct de sezoane
      for (int j = 0; j < serial.getSeasons().size(); j++) {
        time += serial.getSeasons().get(j).getDuration();
      }
      serial.setTotalDuration(time);

      boolean filterOk = true; // adaug serialele de sortat aplicand filtrele necesare
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
      if (filterOk) {
        sortedSerials.add(serial);
      }
    }

    // sortez vectorul
    sortedSerials.sort(new SortByDuration());

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
