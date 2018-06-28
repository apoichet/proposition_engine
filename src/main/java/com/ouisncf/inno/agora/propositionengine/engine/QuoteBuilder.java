package com.ouisncf.inno.agora.propositionengine.engine;

import static com.ouisncf.inno.agora.propositionengine.engine.Quote.getCityCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QuoteBuilder {

  private Collection<Proposition> propositions;

  public QuoteBuilder(Collection<Proposition> propositions) {
    this.propositions = propositions;
  }

  public Collection<Quote> getQuotes(){
    Collection<Quote> quotes = new ArrayList<>();

    for (Proposition proposition : propositions) {
      try {
        URL url = new URL(getUrlStr(proposition));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = bufferedReader.readLine()) != null){
          response.append(line);
        }
        bufferedReader.close();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.toString());
        JSONArray array = (JSONArray) obj;

        int countQuote = 0;
        for (int i = 0; i < array.size(); i++) {
          JSONObject jsonQuote = (JSONObject) array.get(i);
          Integer totalPrice = Integer.parseInt(jsonQuote.get("totalPrice").toString())/100;
          LocalDateTime departureDate = LocalDateTime.parse(jsonQuote.get("departureDate").toString(), DateTimeFormatter.ISO_DATE_TIME);
          boolean isHourOk = departureDate.getHour() > 18;
          boolean isFirstPrice = proposition.getPrice().equals("< 50 €") && totalPrice <= 50;
          boolean isSecondPrice = proposition.getPrice().equals("< 80 €") && totalPrice <= 80;
          boolean isThirdPrice = proposition.getPrice().equals("> 80 €") && totalPrice > 80;

          JSONArray segments = (JSONArray) jsonQuote.get("segments");

          if(isHourOk && segments.size() == 1 && (isFirstPrice || isSecondPrice || isThirdPrice)){
            countQuote++;
            Quote quote = new Quote(countQuote);
            quote.setDepartureDate(departureDate.format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
            quote.setArrivalDate(LocalDateTime.parse(jsonQuote.get("arrivalDate").toString(), DateTimeFormatter.ISO_DATE_TIME).format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
            quote.setNbrPassenger(proposition.getNbrPassenger());

            JSONObject segment = (JSONObject) segments.get(0);
            JSONObject origin = (JSONObject) segment.get("origin");
            quote.setOutwardOrigin(origin.get("cityLabel").toString());
            JSONObject destination = (JSONObject) segment.get("destination");
            quote.setOutwardDestination(destination.get("cityLabel").toString());
            quote.setPrice(totalPrice);
            quote.setUrlImage(Quote.getUrlCity(proposition.getDestination()));
            JSONObject quotations = (JSONObject) segment.get("quotations");
            JSONObject typoPax = (JSONObject) quotations.get("26-NO_CARD");
            JSONObject fareCondition = (JSONObject) typoPax.get("fareCondition");
            JSONArray conditions = (JSONArray) fareCondition.get("conditions");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < conditions.size(); j++) {
                sb.append((String) conditions.get(j));
                sb.append("\n");
            }
            quote.setFareCondition(StringUtils.replace((String) conditions.get(0), "&euro", "€"));
            quotes.add(quote);
            if (quotes.size() > 2){
              return quotes;
            }
          }
        }

      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    }

    return quotes;

  }

  private String getUrlStr(Proposition proposition) {

    final String[] split = StringUtils.split(proposition.getDepartureDate(), "/");
    String year = String.valueOf(LocalDate.now().getYear());
    final StringBuilder request = new StringBuilder()
      .append("http://nucleus.eu-west-1.elasticbeanstalk.com/proposals/v1/")
      .append("FRPAR").append("/")
      .append(getCityCode(proposition.getDestination()))
      .append("/")
      .append(year.concat(split[1]).concat(split[0]))
      .append("/")
      .append("2")
      .append("/");

    List<String> passengers = new ArrayList<>();

    for (int i = 0; i < proposition.getNbrPassenger(); i++) {
      passengers.add("26-NO_CARD");
    }

    request.append(passengers.stream().collect(Collectors.joining(",")));

    return request.toString();
  }




}
