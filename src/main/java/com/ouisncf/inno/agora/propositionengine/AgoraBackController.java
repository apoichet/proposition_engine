package com.ouisncf.inno.agora.propositionengine;

import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DEPARTURE;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DESTINATION;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.PRICE;

import com.ouisncf.inno.agora.propositionengine.engine.Proposition;
import com.ouisncf.inno.agora.propositionengine.engine.PropositionEngine;
import com.ouisncf.inno.agora.propositionengine.engine.Quote;
import com.ouisncf.inno.agora.propositionengine.engine.QuoteBuilder;
import com.ouisncf.inno.agora.propositionengine.engine.TravelerChoice;
import com.ouisncf.inno.agora.propositionengine.exception.AgoraBackException;
import com.ouisncf.inno.agora.propositionengine.exception.PollEqualityException;
import com.ouisncf.inno.agora.propositionengine.poll.PollCategories;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agoraback")
public class AgoraBackController {

  /**
   * Permet de renvoyer les propositions de destinations
   * @return destinations
   */
  @GetMapping("proposition/destinations")
  public Collection<String> getDestinations() {
    return PollCategories.get(DESTINATION);
  }

  /**
   * Permet de renvoyer les propositions de dates de départ
   * @return departure
   */
  @GetMapping("proposition/departures")
  public Collection<String> getDepartureDates() { return PollCategories.get(DEPARTURE);}

  /**
   * Permet de renvoyer les propositions de prix
   * @return departure
   */
  @GetMapping("proposition/prices")
  public Collection<String> getPrices() {
    return PollCategories.get(PRICE);
  }

  /**
   * Permet de renvoyer les propositions
   * @return propositions
   */
  @PostMapping(path = "proposition/build")
  public ResponseEntity<?> buildPropositions(@RequestBody Collection<TravelerChoice> travelerChoices) {
    try{
      final Collection<Proposition> proposals = new PropositionEngine().build(travelerChoices);
      return new ResponseEntity<>(proposals, HttpStatus.OK);
    }
    catch (AgoraBackException exception){
      if (exception instanceof PollEqualityException) {
        return new ResponseEntity<>(HttpStatus.valueOf(exception.getCodeException()));
      }
    }
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }

  /**
   * Permet de renvoyer les quotations
   * @return quotations
   */
  @PostMapping(path = "quotation/build")
  public Collection<Quote> buildQuotations(@RequestBody Collection<Proposition> propositions) {
    return new QuoteBuilder(propositions).getQuotes();
  }
  

}
