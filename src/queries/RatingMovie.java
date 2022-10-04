package queries;

import entertainment.Movie;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SortByMovies implements Comparator<Movie> {
  public int compare(final Movie a, final Movie b) {
    if (a.getGrade() != b.getGrade()) {
      return (int) (a.getGrade() - b.getGrade());
    } else {
      return a.getTitle().charAt(0) - b.getTitle().charAt(0);
    }
  }
}

public class RatingMovie {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Movie> sortedMovies = new ArrayList<>();

  public RatingMovie(final ActionInputData action, final List<Movie> movies) {

    int number = action.getNumber();
    String sortType = action.getSortType();

    // adaug filmele cu rating nenul, aplicand filtrele in lista de sortat
    for (Movie movie : movies) {
      boolean filterOk = true; // verific daca filmul indeplineste filtrele
      if (movie.getGrade() != 0) { // daca filmul are ratingul nenul

        if (action.getFilters().get(0).get(0) != null) { // year
          String year = String.valueOf(movie.getYear());
          if (!(year.equals(action.getFilters().get(0).get(0)))) {
            filterOk = false;
          }
        }
        if (action.getFilters().get(1).get(0) != null) { // genre
          boolean filterGenre = false;
          for (int j = 0; j < movie.getGenres().size(); j++) {
            if (movie
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
          sortedMovies.add(movie);
        }
      }
    }

    // sortez vectorul
    sortedMovies.sort(new SortByMovies());

    // creez mesajul pt output in functie de sortarea listei de filme asc sau desc
    if (sortedMovies.size() > 0) {

      if (sortType.equals("asc")) {
        for (int i = 0; i < sortedMovies.size(); i++) {
          if (number == 0) {
            break;
          }
          if ((i == sortedMovies.size() - 1) || (number == 1)) {
            message += sortedMovies.get(i).getTitle();
          } else {
            message += sortedMovies.get(i).getTitle() + ", ";
          }
          number--;
        }
      }

      if (sortType.equals("desc")) {
        for (int i = sortedMovies.size() - 1; i >= 0; i--) {
          if (number == 0) {
            break;
          }
          if ((i == 0) || (number == 1)) {
            message += sortedMovies.get(i).getTitle();
          } else {
            message += sortedMovies.get(i).getTitle() + ", ";
          }
          number--;
        }
      }
    }
    message += "]";
  }
}
