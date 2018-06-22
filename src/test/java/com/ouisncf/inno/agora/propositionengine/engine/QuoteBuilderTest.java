package com.ouisncf.inno.agora.propositionengine.engine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

public class QuoteBuilderTest {

  @Test
  public void testConnect(){
    Proposition p = new Proposition();
    p.setDepartureDate("06/07");
    p.setDestination("Lyon");
    p.setPrice("> 80 €");
    p.setScore(2);
    QuoteBuilder quoteBuilder = new QuoteBuilder(Collections.singletonList(p));
    final Collection<Quote> quotes = quoteBuilder.getQuotes();
    quotes.forEach(quote -> System.out.println(quote));
  }

  @Test
  public void testDate(){
    final String[] split = StringUtils.split("06/07", "/");
    String year = String.valueOf(LocalDate.now().getYear());
    System.out.println(year.concat(split[1]).concat(split[0]));
  }



}