package com.ouisncf.inno.agora.propositionengine.poll;

import static com.ouisncf.inno.agora.propositionengine.poll.PollProperties.getDestinations;
import static com.ouisncf.inno.agora.propositionengine.poll.PollProperties.getNbrFridays;
import static com.ouisncf.inno.agora.propositionengine.poll.PollProperties.getPatternDateFriday;
import static com.ouisncf.inno.agora.propositionengine.poll.PollProperties.getPrices;
import static com.ouisncf.inno.agora.propositionengine.utils.FridayFactory.getNextFridays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PollCategories extends HashMap<String, Map<String, PollScore>> {

  public final static List<String> DESTINATIONS = getDestinations();
  public final static List<String> DEPARTURE_DATES = getNextFridays(getNbrFridays());
  public final static List<String> PRICES = getPrices();

  public static final String DESTINATION = "destination";
  public static final String DEPARTURE = "departure";
  public static final String PRICE = "price";

  private static Categories buildCategories(){
    return Categories.builder()
      .withCategory(DESTINATION, DESTINATIONS)
      .withCategory(DEPARTURE, DEPARTURE_DATES)
      .withCategory(PRICE, PRICES)
      .buildWithEqualWeight();
  }

  public static List<String> get(String name){
     return buildCategories().stream()
       .filter(category -> name.equals(category.getName()))
       .flatMap(category -> category.getElements().stream())
       .collect(Collectors.toList());
  }

  public PollCategories() {
    buildCategories().forEach(category -> {
      Map<String, PollScore> pollElement = new HashMap<>();
      category.getElements().forEach(element -> pollElement.put(element, new PollScore(category.getWeight())));
      this.put(category.getName(), pollElement);
    });
  }

}
