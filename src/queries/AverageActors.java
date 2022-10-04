package queries;

import actor.Actor;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SortByGrade implements Comparator<Actor> {
  public int compare(final Actor a, final Actor b) {
    if (a.getFinalRating() != b.getFinalRating()) {
      if (a.getFinalRating() < b.getFinalRating()) {
        return -1;
      } else {
        return 1;
      }
    } else {

      return a.getName().compareTo(b.getName());
    }
  }
}

public class AverageActors {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Actor> sortedActors = new ArrayList<>();

  public AverageActors(
      final ActionInputData action,
      final List<Actor> actors,
      final List<Movie> movies,
      final List<Serial> serials) {

    int number = action.getNumber();
    String sortType = action.getSortType();

    for (Actor actor : actors) { // parcurg  lista de actori
      // parcurg filmele in care acestia au jucat
      for (int j = 0; j < actor.getFilmography().size(); j++) {

        // parcurg filmele si serialele date, iar din cele in care au jucat actorii extrag ratingul
        // pt acestia
        for (Movie movie : movies) {
          if (actor.getFilmography().get(j).equals(movie.getTitle())) {
            if (movie.getGrade() != 0) {
              actor.setNumberRatings();
              double aux = movie.getGrade() / (double) movie.getNumberGrades();
              actor.setRatingAvg(aux);
            }
          }
        }
        for (Serial serial : serials) {
          if (actor.getFilmography().get(j).equals(serial.getTitle())) {
            for (int l = 0; l < serial.getSeasons().size(); l++) {
              actor.setNumberRatings();
              actor.setSerialRatings(serial.getSeasons().size());
              if (serial.getSeasons().get(l).getRatings() != 0) {
                double aux =
                    serial.getSeasons().get(l).getRatings()
                        / (double) serial.getSeasons().get(l).getNumberRatings();
                actor.setRatingAvg(aux);
              }
            }
          }
        }
      }
    }

    // salvezi in sorted actors actorii care au primit rating
    for (Actor actor : actors) {
      if (actor.getRatingAvg() != 0) {
        sortedActors.add(actor);
      }
    }
    // precalculez ratingul final al acestora
    for (Actor sortedActor : sortedActors) {
      sortedActor.setFinalRating(
          sortedActor.getRatingAvg() / (double) sortedActor.getNumberRatings());
    }

    // sortarea actorilor
    sortedActors.sort(new SortByGrade());

    // creez outputul message din primii number actori sortati asc sau desc
    if (sortType.equals("asc")) {
      for (int i = 0; i < sortedActors.size(); i++) {
        if (number == 0) {
          break;
        }
        if ((i == sortedActors.size() - 1) || (number == 1)) {
          message += sortedActors.get(i).getName();
        } else {
          message += sortedActors.get(i).getName() + ", ";
        }
        number--;
      }
      message += "]";
    }

    if (sortType.equals("desc")) {
      for (int i = sortedActors.size() - 1; i >= 0; i--) {
        if (number == 0) {
          break;
        }
        if ((i == 0) || (number == 1)) {
          message += sortedActors.get(i).getName();
        } else {
          message += sortedActors.get(i).getName() + ", ";
        }
        number--;
      }
      message += "]";
    }
  }
}
