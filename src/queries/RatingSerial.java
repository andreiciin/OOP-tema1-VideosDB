package queries;

import entertainment.Serial;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SortBySerial implements Comparator<Serial> {
  public int compare(final Serial a, final Serial b) {
    if (a.getGrade() != b.getGrade()) {
      return (int) (a.getGrade() - b.getGrade());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

public class RatingSerial {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Serial> sortedSerials = new ArrayList<>();

  public RatingSerial(final ActionInputData action, final List<Serial> serials) {

    int number = action.getNumber();
    String sortType = action.getSortType();

    // adaug serialele cu rating nenul, aplicand filtrele in lista de sortat
    for (Serial serial : serials) {

      double grade = 0; // calculez ratingul serialului in fct de sezoane
      for (int j = 0; j < serial.getSeasons().size(); j++) {
        grade += serial.getSeasons().get(j).getRatings();
      }
      grade /= serial.getSeasons().size();

      boolean filterOk = true;

      if (grade != 0) { // daca are rating
        serial.setGrade(grade);

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
    }
    // sortez vectorul
    sortedSerials.sort(new SortBySerial());

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
