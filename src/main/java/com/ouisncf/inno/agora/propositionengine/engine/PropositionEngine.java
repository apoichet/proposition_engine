package com.ouisncf.inno.agora.propositionengine.engine;

import com.ouisncf.inno.agora.propositionengine.category.Categories;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PropositionEngine {

  public final static List<String> DESTINATIONS = Arrays.asList("Lyon","Marseille","Bordeaux");
  public final static List<String> DEPARTURE_DATES = getDepartureDates();
  public final static List<String> PRICES = Arrays.asList("Moins de 50€", "Moins de 80€", "Plus de 80€");

  private static final String DESTINATION = "destination";
  private static final String DEPARTURE = "departure";
  private static final String PRICE = "price";
  public static final String PATTERN_DATE = "d/M";

  public List<Proposition> build(Collection<TravelerChoice> travelerChoices){

    //Build poll categories
    Map<String, Map<String, PollScore>> pollCategorie = buildEmptyPoll();

    //Update poll with travel choices
    updatePoll(travelerChoices, pollCategorie);

    //Build propositions
    final List<Proposition> propositions = buildPropositions(pollCategorie);

    //Limite propositions
    int nbrPropositionMax = calculNbrPropositions(travelerChoices);

    return propositions.stream()
      .sorted(Comparator.comparing(Proposition::getScore).reversed())
      .limit(nbrPropositionMax)
      .collect(Collectors.toList());

  }

  private int calculNbrPropositions(Collection<TravelerChoice> travelerChoices) {
    int nbrProposition;
    if (travelerChoices.size() <= 2){
      nbrProposition = 1;
    }
    else if (travelerChoices.size() <= 3){
      nbrProposition = 2;
    }
    else{
      nbrProposition = 3;
    }
    return nbrProposition;
  }

  private List<Proposition> buildPropositions(Map<String, Map<String, PollScore>> pollCategorie) {
    String[][][] matrixProposition = new String[DESTINATIONS.size()][DEPARTURE_DATES.size()][PRICES.size()];
    List<Proposition> propositions = new ArrayList<>();
    for (int i = 0; i < matrixProposition.length; i++) {
      for (int j = 0; j < matrixProposition[i].length; j++) {
        for (int k = 0; k < matrixProposition[i][j].length; k++) {
          float scoreDesti = pollCategorie.get(DESTINATION).get(DESTINATIONS.get(i)).calculScore();
          float scoreDate = pollCategorie.get(DEPARTURE).get(DEPARTURE_DATES.get(j)).calculScore();
          float scorePrice = pollCategorie.get(PRICE).get(PRICES.get(k)).calculScore();
          propositions.add(new Proposition(DESTINATIONS.get(i), DEPARTURE_DATES.get(j), PRICES.get(k), scoreDesti+scoreDate+scorePrice));
        }
      }
    }
    return propositions;
  }

  private void updatePoll(Collection<TravelerChoice> travelerChoices, Map<String, Map<String, PollScore>> pollCategorie) {
    travelerChoices.forEach(travelerChoice -> {

      //Destination
      Optional.ofNullable(pollCategorie.get(DESTINATION).get(travelerChoice.getDestination()))
        .ifPresent(pollScore -> pollScore.score++);

      //Date
      Optional.ofNullable(pollCategorie.get(DEPARTURE).get(travelerChoice.getDate()))
        .ifPresent(pollScore -> pollScore.score++);

      //Price
      Optional.ofNullable(pollCategorie.get(PRICE).get(travelerChoice.getPrice()))
        .ifPresent(pollScore -> pollScore.score++);

    });
  }

  private Map<String, Map<String, PollScore>> buildEmptyPoll() {
    Map<String, Map<String, PollScore>> pollCategorie = new HashMap<>();
    buildCategories().forEach(category -> {
      Map<String, PollScore> pollElement = new HashMap<>();
      category.getElements().forEach(element -> pollElement.put(element, new PollScore(category.getWeight())));
      pollCategorie.put(category.getName(), pollElement);
    });
    return pollCategorie;
  }


  private Categories buildCategories() {
    return Categories.builder()
      .withCategory(DESTINATION, DESTINATIONS)
      .withCategory(DEPARTURE, DEPARTURE_DATES)
      .withCategory(PRICE, PRICES)
      .buildWithEqualWeight();
  }

  private static List<String> getDepartureDates(){
    LocalDate friday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    LocalDate nextFriday = friday.plusDays(7);
    LocalDate afterNextFriday = nextFriday.plusDays(7);
    final DateTimeFormatter pattern = DateTimeFormatter.ofPattern(PATTERN_DATE);
    return Arrays.asList(friday.format(pattern), nextFriday.format(pattern), afterNextFriday.format(pattern));
  }
}
