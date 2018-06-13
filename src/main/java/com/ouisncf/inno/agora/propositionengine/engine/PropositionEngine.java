package com.ouisncf.inno.agora.propositionengine.engine;

import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DEPARTURE;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DEPARTURE_DATES;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DESTINATION;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DESTINATIONS;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.PRICE;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.PRICES;

import com.ouisncf.inno.agora.propositionengine.poll.PollCategories;
import com.ouisncf.inno.agora.propositionengine.poll.PollScore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PropositionEngine {
  
  public List<Proposition> build(Collection<TravelerChoice> travelerChoices){

    //New poll categories
    PollCategories pollCategories = new PollCategories();

    //Update poll with travel choices
    updatePoll(travelerChoices, pollCategories);

    //Build propositions
    final List<Proposition> propositions = buildPropositions(pollCategories);

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
}
