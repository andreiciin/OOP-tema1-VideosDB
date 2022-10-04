package commands;

import entertainment.Movie;
import entertainment.Serial;
import user.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GiveRating {

  private String message = null;

  public final String getMessage() {
    return message;
  }

  public GiveRating(
      final String username,
      final String title,
      final double grade,
      final int season,
      final List<User> users,
      final List<Movie> movies,
      final List<Serial> serials) {

    // verific daca e film sau serial titlul
    boolean isMovie = false;
    boolean isSerial = false;
    for (int i = 0; i < movies.size(); i++) {
      if (movies.get(i).getTitle().equals(title)) {
        isMovie = true;
        break;
      }
    }
    for (int i = 0; i < serials.size(); i++) {
      if (serials.get(i).getTitle().equals(title)) {
        isSerial = true;
        break;
      }
    }

    for (User user : users) { // parcurg lista de utilizatori
      if (user.getUsername().equals(username)) { // gasesc utilizatorul
        // verific daca filmul a fost vizionat vreodata
        Iterator<Map.Entry<String, Integer>> iterator = user.getHistory().entrySet().iterator();

        boolean movieIsSeen = false;

        while (iterator.hasNext()) {

          Map.Entry<String, Integer> entry = iterator.next();
          if (entry.getKey().equals(title)) {
            movieIsSeen = true;
            // incrementez contorul pt cate ratinguri a dat un utilizator
            int contor = user.getNumberFavs();
            contor++;
            user.setNumberFavs(contor);
            break;
          }
        }
        // daca nu a fost vazut filmul avem eroare
        if (!movieIsSeen) {
          message = "error -> " + title + " is not seen";
        }
        // daca userul a mai votat odata filmul avem eroare
        if (movieIsSeen && isMovie) {
          boolean isRated = false;
          for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
              if (movie.getRatedUsers() != null) {
                for (int k = 0; k < movie.getRatedUsers().size(); k++) {
                  if (movie.getRatedUsers().get(k).equals(username)) {
                    isRated = true;
                    break;
                  }
                }
              }
            }
          }
          if (isRated) {
            message = "error -> " + title + " has been already rated";
          }
        }
        if (movieIsSeen && isSerial) {
          boolean isRated = false;
          for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
              if (serial.getSeasons().get(season - 1).getRatedUsers() != null) {
                for (int k = 0;
                    k < serial.getSeasons().get(season - 1).getRatedUsers().size();
                    k++) {
                  if (serial.getSeasons().get(season - 1).getRatedUsers().get(k).equals(username)) {
                    isRated = true;
                    break;
                  }
                }
              }
            }
          }
          if (isRated) {
            message = "error -> " + title + " has been already rated";
          }
        }
      }
    }

    if (message == null) {
      int userIndex = 0;
      for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getUsername().equals(username)) {
          userIndex = i;
          break;
        }
      }
      // verific daca titlul apartine unui film si in caz afirmativ ii ofer ratingul
      for (Movie movie : movies) {
        if (movie.getTitle().equals(title)) {
          List<String> aux = new ArrayList<>();
          if (movie.getRatedUsers() != null) {
            aux = movie.getRatedUsers();
          }
          aux.add(users.get(userIndex).getUsername());
          movie.setRatedUsers(aux);
          movie.setGrade(grade);
          movie.setNumberGrades();
          message = "success -> " + title + " was rated with " + grade + " by " + username;
          break;
        }
      }

      // verific daca titlul apartine unui serial, daca da, ofer rating pt sezonul dat
      for (Serial serial : serials) {
        if (serial.getTitle().equals(title)) {
          // lista in care salvez pt toate episoadele din sezon ratingul sezonului
          List<Double> ratings = new ArrayList<>();
          for (int j = 0; j < serial.getSeasons().size(); j++) {
            ratings.add(j, grade);
          }
          List<String> aux = new ArrayList<>();
          if (serial.getSeasons().get(season - 1).getRatedUsers() != null) {
            aux = serial.getSeasons().get(season - 1).getRatedUsers();
          }
          aux.add(users.get(userIndex).getUsername());
          serial.getSeasons().get(season - 1).setRatedUsers(aux);
          serial.getSeasons().get(season - 1).setRatings(grade);
          serial.getSeasons().get(season - 1).setNumberRatings();
          message = "success -> " + title + " was rated with " + grade + " by " + username;
          break;
        }
      }
    }
  }
}
