package com.ouisncf.inno.agora.propositionengine.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class FridayFactory {

  public static List<String> getNextFridays(int nbr, String patternDate){
    if (nbr==0){
       return new ArrayList<>();
    }

    List<String> fridays = new ArrayList<>();
    LocalDate friday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    fridays.add(friday.format(DateTimeFormatter.ofPattern(patternDate)));

    for (int i = 1; i < nbr; i++) {
        fridays.add(friday.plusDays(7*i).format(DateTimeFormatter.ofPattern(patternDate)));
    }

    return fridays;
  }
}
