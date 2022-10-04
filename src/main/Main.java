package main;

import actor.Actor;
import checker.Checker;
import checker.Checkstyle;
import commands.AddFavoriteMovie;
import commands.GiveRating;
import commands.ViewVideo;
import common.Constants;
import entertainment.Movie;
import entertainment.Serial;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import queries.*;
import recommendations.*;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** The entry point to this homework. It runs the checker that tests your implentation. */
public final class Main {
  /** for coding style */
  private Main() { }

  /**
   * Call the main checker and the coding style checker
   *
   * @param args from command line
   * @throws IOException in case of exceptions to reading / writing
   */
  public static void main(final String[] args) throws IOException {
    File directory = new File(Constants.TESTS_PATH);
    Path path = Paths.get(Constants.RESULT_PATH);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }

    File outputDirectory = new File(Constants.RESULT_PATH);

    Checker checker = new Checker();
    checker.deleteFiles(outputDirectory.listFiles());

    for (File file : Objects.requireNonNull(directory.listFiles())) {

      String filepath = Constants.OUT_PATH + file.getName();
      File out = new File(filepath);
      boolean isCreated = out.createNewFile();
      if (isCreated) {
        action(file.getAbsolutePath(), filepath);
      }
    }

    checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
    Checkstyle test = new Checkstyle();
    test.testCheckstyle();
  }

  /**
   * @param filePath1 for input file
   * @param filePath2 for output file
   * @throws IOException in case of exceptions to reading / writing
   */
  public static void action(final String filePath1, final String filePath2) throws IOException {
    InputLoader inputLoader = new InputLoader(filePath1);
    Input input = inputLoader.readData();

    Writer fileWriter = new Writer(filePath2);
    JSONArray arrayResult = new JSONArray();

    // TODO add here the entry point to your implementation

    // creez lista de actori proprie pt a putea modifica in ea si parsez din input
    List<Actor> actors = new ArrayList<>();
    for (int i = 0; i < input.getActors().size(); i++) {
      Actor actor =
          new Actor(
              input.getActors().get(i).getName(),
              input.getActors().get(i).getCareerDescription(),
              input.getActors().get(i).getFilmography(),
              input.getActors().get(i).getAwards());
      actors.add(i, actor);
    }
    // creez lista de filme proprie pt a putea modifica in ea si parsez din input
    List<Movie> movies = new ArrayList<>();
    for (int i = 0; i < input.getMovies().size(); i++) {
      Movie movie =
          new Movie(
              input.getMovies().get(i).getTitle(),
              input.getMovies().get(i).getCast(),
              input.getMovies().get(i).getGenres(),
              input.getMovies().get(i).getYear(),
              input.getMovies().get(i).getDuration());
      movies.add(i, movie);
    }
    // creez lista de seriale proprie pt a putea modifica in ea si parsez din input
    List<Serial> serials = new ArrayList<>();
    for (int i = 0; i < input.getSerials().size(); i++) {
      Serial serial =
          new Serial(
              input.getSerials().get(i).getTitle(),
              input.getSerials().get(i).getCast(),
              input.getSerials().get(i).getGenres(),
              input.getSerials().get(i).getNumberSeason(),
              input.getSerials().get(i).getSeasons(),
              input.getSerials().get(i).getYear());
      serials.add(i, serial);
    }
    // creez lista de useri proprie pt a putea modifica in ea si parsez din input
    final List<User> users = new ArrayList<>();
    for (int i = 0; i < input.getUsers().size(); i++) {
      User user =
          new User(
              input.getUsers().get(i).getUsername(),
              input.getUsers().get(i).getSubscriptionType(),
              input.getUsers().get(i).getHistory(),
              input.getUsers().get(i).getFavoriteMovies());
      users.add(i, user);
    }

    // instruciunile pe care le voi urma
    List<ActionInputData> actions = input.getCommands();

    // parcurg lista de comenzi si le generez outputul aferent
    for (int i = 0; i < input.getCommands().size(); i++) {

      String message = null;

      // pentru comanda Favorite:
      if (actions.get(i).getActionType().equals("command")
          && actions.get(i).getType().equals("favorite")) {

        message =
            new AddFavoriteMovie(actions.get(i).getUsername(), actions.get(i).getTitle(), users)
                .getMessage();
      }

      // pentru comanda View:
      if (actions.get(i).getActionType().equals("command")
          && actions.get(i).getType().equals("view")) {

        message =
            new ViewVideo(actions.get(i).getUsername(), actions.get(i).getTitle(), users)
                .getMessage();
      }

      // pentru comanda Rating:
      if (actions.get(i).getActionType().equals("command")
          && actions.get(i).getType().equals("rating")) {

        message =
            new GiveRating(
                    actions.get(i).getUsername(),
                    actions.get(i).getTitle(),
                    actions.get(i).getGrade(),
                    actions.get(i).getSeasonNumber(),
                    users,
                    movies,
                    serials)
                .getMessage();
      }

      // pentru query cu utilizatori
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("users")) {

        message = new MostActiveUsers(actions.get(i), users).getMessage();
      }

      // pentru query cu actori - Average
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("actors")
          && actions.get(i).getCriteria().equals("average")) {

        message = new AverageActors(actions.get(i), actors, movies, serials).getMessage();
      }

      // pentru query cu actori - Awards
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("actors")
          && actions.get(i).getCriteria().equals("awards")) {

        message = new AwardsActors(actions.get(i), actors).getMessage();
      }

      // pentru query cu actori - Filter Description
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("actors")
          && actions.get(i).getCriteria().equals("filter_description")) {

        message = new FilterActors(actions.get(i), actors).getMessage();
      }

      // pentru query cu filme - Rating
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("movies")
          && actions.get(i).getCriteria().equals("ratings")) {

        message = new RatingMovie(actions.get(i), movies).getMessage();
      }

      // pentru query cu seriale - Rating
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("shows")
          && actions.get(i).getCriteria().equals("ratings")) {

        message = new RatingSerial(actions.get(i), serials).getMessage();
      }

      // pentru query cu filme - Longest
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("movies")
          && actions.get(i).getCriteria().equals("longest")) {

        message = new LongestMovie(actions.get(i), movies).getMessage();
      }

      // pentru query cu seriale - Longest
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("shows")
          && actions.get(i).getCriteria().equals("longest")) {

        message = new LongestSerial(actions.get(i), serials).getMessage();
      }

      // pentru query cu filme - Most Viewed
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("movies")
          && actions.get(i).getCriteria().equals("most_viewed")) {

        message = new MostViewedMovie(actions.get(i), movies, users).getMessage();
      }

      // pentru query cu seriale - Most Viewed
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("shows")
          && actions.get(i).getCriteria().equals("most_viewed")) {

        message = new MostViewedSerial(actions.get(i), serials, users).getMessage();
      }

      // pentru query cu filme - Favorite
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("movies")
          && actions.get(i).getCriteria().equals("favorite")) {

        message = new FavoriteMovie(actions.get(i), movies, users).getMessage();
      }

      // pentru query cu seriale - Favorite
      if (actions.get(i).getActionType().equals("query")
          && actions.get(i).getObjectType().equals("shows")
          && actions.get(i).getCriteria().equals("favorite")) {

        message = new FavoriteSerial(actions.get(i), serials, users).getMessage();
      }

      // pentru recomandari - Standard
      if (actions.get(i).getActionType().equals("recommendation")
          && actions.get(i).getType().equals("standard")) {

        message =
            new StandardRecommend(actions.get(i).getUsername(), users, movies, serials)
                .getMessage();
      }

      // pentru recomandari - Best unseen
      if (actions.get(i).getActionType().equals("recommendation")
          && actions.get(i).getType().equals("best_unseen")) {

        message =
            new BestUnseenRecommend(actions.get(i).getUsername(), users, movies, serials)
                .getMessage();
      }

      // pentru recomandari - premium Popular
      if (actions.get(i).getActionType().equals("recommendation")
          && actions.get(i).getType().equals("popular")) {

        message =
            new PopularRecommend(actions.get(i).getUsername(), users, movies, serials).getMessage();
      }

      // pentru recomandari - premium Favorite
      if (actions.get(i).getActionType().equals("recommendation")
          && actions.get(i).getType().equals("favorite")) {

        message =
            new FavoriteRecommend(actions.get(i).getUsername(), users, movies, serials)
                .getMessage();
      }

      // pentru recomandari - premium Search
      if (actions.get(i).getActionType().equals("recommendation")
          && actions.get(i).getType().equals("search")) {

        message =
            new SearchRecommend(
                    actions.get(i).getUsername(), actions.get(i).getGenre(), users, movies, serials)
                .getMessage();
      }

      JSONObject object =
          fileWriter.writeFile(actions.get(i).getActionId(), actions.get(i).getCriteria(), message);
      arrayResult.add(object);
    }

    fileWriter.closeJSON(arrayResult);
  }
}
