package queries;

import actor.Actor;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class SortByName implements Comparator<Actor> {
  public int compare(final Actor a, final Actor b) {
    if (a.getName().charAt(0) != b.getName().charAt(0)) {
      return (a.getName().charAt(0) - b.getName().charAt(0));
    } else {
      return a.getName().charAt(1) - b.getName().charAt(1);
    }
  }
}
/** filters the actors */
public class FilterActors {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Actor> sortedActors = new ArrayList<>();

  public FilterActors(final ActionInputData action, final List<Actor> actors) {

    String sortType = action.getSortType();

    // verific daca in descrierea actorului apar toate keywordurile
    for (Actor actor : actors) {

      boolean allFiltersOk = true;
      for (int j = 0; j < action.getFilters().get(2).size(); j++) {

        boolean found =
            Arrays.asList(actor.getCareerDescription().toLowerCase().split(" "))
                .contains(action.getFilters().get(2).get(j).toLowerCase());
        if (!found) {
          allFiltersOk = false;
          break;
        }
      }
      // daca descrierea contine toate keywordurile il adaug in lista de actori de sortat
      if (allFiltersOk) {
        sortedActors.add(actor);
      }
    }

    // sortarea actorilor
    sortedActors.sort(new SortByName());

    // creez outputul message din primii number actori sortati asc sau desc
    if (sortType.equals("asc")) {
      for (int i = 0; i < sortedActors.size(); i++) {
        if (i == sortedActors.size() - 1) {
          message += sortedActors.get(i).getName();
        } else {
          message += sortedActors.get(i).getName() + ", ";
        }
      }
      message += "]";
    }

    if (sortType.equals("desc")) {
      for (int i = sortedActors.size() - 1; i >= 0; i--) {
        if (i == 0) {
          message += sortedActors.get(i).getName();
        } else {
          message += sortedActors.get(i).getName() + ", ";
        }
      }
      message += "]";
    }
  }
}
