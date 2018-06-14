package com.ouisncf.inno.agora.propositionengine;

import static com.ouisncf.inno.agora.propositionengine.engine.PropositionEngineTest.give_date_departure;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DEPARTURE;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.DESTINATION;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.PRICE;
import static com.ouisncf.inno.agora.propositionengine.poll.PollCategories.get;
import static org.assertj.core.api.Assertions.assertThat;

import com.ouisncf.inno.agora.propositionengine.engine.Proposition;
import com.ouisncf.inno.agora.propositionengine.engine.TravelerChoice;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropositionEngineControllerIT {

  @LocalServerPort
  private int port;

  private URI base;

  @Autowired
  private TestRestTemplate template;

  private static final String NEXT_FRIDAY = "next friday";

  @Test
  public void getDestinationsTest() throws Exception {
    //Given
    this.base = new URI("http://localhost:" + port + "/agora/proposition/destinations");
    //When
    ResponseEntity<String[]> response = template.getForEntity(this.base, String[].class);
    //Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
    assertThat(Arrays.asList(response.getBody())).isEqualTo(get(DESTINATION));
  }

  @Test
  public void getDateDeparturesTest() throws Exception {
    //Given
    this.base = new URI("http://localhost:" + port + "/agora/proposition/departures");
    //When
    ResponseEntity<String[]> response = template.getForEntity(this.base, String[].class);
    //Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
    assertThat(Arrays.asList(response.getBody())).isEqualTo(get(DEPARTURE));
  }

  @Test
  public void getPricesTest() throws Exception {
    //Given
    this.base = new URI("http://localhost:" + port + "/agora/proposition/prices");
    //When
    ResponseEntity<String[]> response = template.getForEntity(this.base, String[].class);
    //Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
    assertThat(Arrays.asList(response.getBody())).isEqualTo(get(PRICE));
  }

  @Test
  public void buildPropositionsTest() throws Exception {
    //Given
    this.base = new URI("http://localhost:" + port + "/agora/proposition/build");
    List<TravelerChoice> travelerChoices = new ArrayList<>();
    travelerChoices.add(new TravelerChoice("Lyon", give_date_departure(NEXT_FRIDAY), "Moins de 50€"));
    //When
    ResponseEntity<Proposition[]> response = template.postForEntity(this.base, travelerChoices, Proposition[].class);
    //Then
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
    assertThat(response.getBody()).hasSize(1);
    assertThat(Arrays.asList(response.getBody()).get(0).getDestination()).isEqualTo("Lyon");
    assertThat(Arrays.asList(response.getBody()).get(0).getDepartureDate()).isEqualTo(give_date_departure(NEXT_FRIDAY));
    assertThat(Arrays.asList(response.getBody()).get(0).getPrice()).isEqualTo("Moins de 50€");
  }


}