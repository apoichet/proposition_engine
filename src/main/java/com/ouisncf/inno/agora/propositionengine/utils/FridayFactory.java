package com.ouisncf.inno.agora.propositionengine.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class FridayFactory {

  public static List<String> getNextFridays(int nbr){
    if (nbr==0){
      return new ArrayList<>();
    }

    List<String> fridays = new ArrayList<>();
    LocalDate friday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    fridays.add(getFridayStr(friday));

    for (int i = 1; i < nbr; i++) {
      final LocalDate plusDays = friday.plusDays(7 * i);

      fridays.add(getFridayStr(plusDays));
    }

    return fridays;
  }

  public static String getFridayStr(LocalDate friday){
    
    String day = String.valueOf(friday.getDayOfMonth());
    day = day.length() == 1 ? "0".concat(day) : day;

    String month = String.valueOf(friday.getMonthValue());
    month = month.length() == 1 ? "0".concat(month) : month;

    return day.concat("/").concat(month);
  }
}
