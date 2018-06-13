package com.ouisncf.inno.agora.propositionengine;

import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DEPARTURE;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DESTINATION;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.PRICE;

import com.ouisncf.inno.agora.propositionengine.engine.Proposition;
import com.ouisncf.inno.agora.propositionengine.engine.PropositionEngine;
import com.ouisncf.inno.agora.propositionengine.engine.TravelerChoice;
import com.ouisncf.inno.agora.propositionengine.poll.PollCategories;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agora/proposition")
public class PropositionEngineController {

  /**
   * Permet de renvoyer les propositions de destinations
   * @return destinations
   */
  @GetMapping("/destinations")
  public Collection<String> getDestinations() {
    return PollCategories.get(DESTINATION);
  }

  /**
   * Permet de renvoyer les propositions de dates de départ
   * @return departure
   */
  @GetMapping("/departures")
  public Collection<String> getDepartureDates() { return PollCategories.get(DEPARTURE);}

  /**
   * Permet de renvoyer les propositions de prix
   * @return departure
   */
  @GetMapping("/prices")
  public Collection<String> getPrices() {
    return PollCategories.get(PRICE);
  }

  /**
   * Permet de renvoyer les propositions
   * @return departure
   */
  @PostMapping(path = "/build")
  public Collection<Proposition> buildPropositions(@RequestBody Collection<TravelerChoice> travelerChoices) {
    return new PropositionEngine().build(travelerChoices);
  }

}
