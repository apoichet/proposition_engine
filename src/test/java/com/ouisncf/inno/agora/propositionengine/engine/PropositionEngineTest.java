package com.ouisncf.inno.agora.propositionengine.engine;

import static com.ouisncf.inno.agora.propositionengine.poll.PollProperties.getPatternDateFriday;
import static org.assertj.core.api.Java6Assertions.assertThat;

import com.ouisncf.inno.agora.propositionengine.utils.FridayFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PropositionEngineTest {

  private static final String FRIDAY = "friday";
  private static final String NEXT_FRIDAY = "next friday";
  private static final String NEXT_AFTER_FRIDAY = "next after friday";

  private PropositionEngine propositionEngine;
  private List<TravelerChoice> travelerChoices;

  @Before
  public void setUp(){
    propositionEngine = new PropositionEngine();
    travelerChoices = new ArrayList<>();
  }
  

  @Test
  public void should_give_best_three_propositions(){
    //Given
    travelerChoices.add(new TravelerChoice("Lyon", give_date_departure(NEXT_FRIDAY), "< 50€"));
    travelerChoices.add(new TravelerChoice("Lyon", give_date_departure(FRIDAY), "< 50€"));
    travelerChoices.add(new TravelerChoice("Marseille", give_date_departure(NEXT_AFTER_FRIDAY), "< 80€"));
    travelerChoices.add(new TravelerChoice("Bordeaux", give_date_departure(FRIDAY), "< 50€"));
    travelerChoices.add(new TravelerChoice("Lyon", give_date_departure(FRIDAY), "< 50€"));

    //When
    List<Proposition> propositions = propositionEngine.build(travelerChoices);

    //Then
    assertThat(propositions).isNotEmpty();
    assertThat(propositions).hasSize(3);
    
    Proposition p1 = propositions.get(0);
    assertThat(p1.getDestination()).isEqualTo("Lyon");
    assertThat(p1.getDepartureDate()).isEqualTo(give_date_departure(FRIDAY));
    assertThat(p1.getPrice()).isEqualTo("< 50€");

    Proposition p2 = propositions.get(1);
    assertThat(p2.getDestination()).isEqualTo("Lyon");
    assertThat(p2.getDepartureDate()).isEqualTo(give_date_departure(NEXT_FRIDAY));
    assertThat(p2.getPrice()).isEqualTo("< 50€");

    Proposition p3 = propositions.get(2);
    assertThat(p3.getDestination()).isEqualTo("Lyon");
    assertThat(p3.getDepartureDate()).isEqualTo(give_date_departure(NEXT_AFTER_FRIDAY));
    assertThat(p3.getPrice()).isEqualTo("< 50€");
  }

  @Test
  public void should_detect_equalities_score_proposition(){
    //Given
    travelerChoices.add(new TravelerChoice("Lyon", give_date_departure(NEXT_FRIDAY), "< 50€"));
    travelerChoices.add(new TravelerChoice("Marseille", give_date_departure(FRIDAY), "< 80€"));
    travelerChoices.add(new TravelerChoice("Bordeaux", give_date_departure(NEXT_AFTER_FRIDAY), "> 50€"));

    //When
    List<Proposition> propositions = propositionEngine.build(travelerChoices);

    //Then
    assertThat(propositions).isNotEmpty();
    assertThat(propositions).hasSize(2);

    Proposition p1 = propositions.get(0);
    Proposition p2 = propositions.get(1);

    assertThat(p1.getScore()).isEqualTo(p2.getScore());

  }

  public static String give_date_departure(String whatFriday){
    LocalDate friday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    switch (whatFriday){
      case NEXT_FRIDAY: friday = friday.plusDays(7);break;
      case NEXT_AFTER_FRIDAY: friday = friday.plusDays(14);
    }
    return FridayFactory.getFridayStr(friday);
  }


}