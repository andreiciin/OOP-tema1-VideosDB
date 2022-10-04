package queries;

import actor.Actor;
import actor.ActorsAwards;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class SortByNumberAwards implements Comparator<Actor> {
  public int compare(final Actor a, final Actor b) {
    if (a.getNumberAwards() != b.getNumberAwards()) {
      return (a.getNumberAwards() - b.getNumberAwards());
    } else {
      return a.getName().charAt(0) - b.getName().charAt(0);
    }
  }
}

public class AwardsActors {

  private String message = "Query result: [";

  public final String getMessage() {
    return message;
  }

  private final List<Actor> sortedActors = new ArrayList<>();

  public AwardsActors(final ActionInputData action, final List<Actor> actors) {

    String sortType = action.getSortType();
    final int level = 3;
    for (Actor actor : actors) {
      if (actor.getAwards().size() >= action.getFilters().get(level).size()) {
        // verific daca a castigat toate premiile din input, daca da salvez numarul total de
        // premieri
        int contor = 0;
        int numAwards = 0;
        for (int j = 0; j < action.getFilters().get(level).size(); j++) {

          for (Map.Entry<ActorsAwards, Integer> entry : actor.getAwards().entrySet()) {
            if (entry
                .getKey()
                .toString()
                .toLowerCase()
                .equals(action.getFilters().get(level).get(j).toLowerCase())) {
              contor++;
              numAwards += entry.getValue();
            } else {
              numAwards += entry.getValue();
            }
          }
        }
        if (contor == action.getFilters().get(level).size()) {
          actor.setNumberAwards(numAwards);
          sortedActors.add(actor);
        }
      }
    }

    // sortarea actorilor
    sortedActors.sort(new SortByNumberAwards());

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
