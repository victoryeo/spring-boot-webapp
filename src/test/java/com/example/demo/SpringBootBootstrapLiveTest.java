package com.example.demo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.example.demo.persistence.model.Book;
import java.util.List;

public class SpringBootBootstrapLiveTest {

  private static final String API_ROOT
    = "http://localhost:8081/api/books";

  @Test
  public void whenGetAllBooks_thenOK() {
      final Response response = RestAssured.get(API_ROOT);
      assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void whenGetBooksByTitle_thenOK() {
      Book book = createRandomBook();
      createBookAsUri(book);
      Response response = RestAssured.get(
        API_ROOT + "/title/" + book.getTitle());
      
      assertEquals(HttpStatus.OK.value(), response.getStatusCode());
      assertTrue(response.as(List.class)
        .size() > 0);
  }

  private Book createRandomBook() {
      Book book = new Book();
      book.setTitle(randomAlphabetic(10));
      book.setAuthor(randomAlphabetic(15));
      return book;
  }

  private String createBookAsUri(Book book) {
      Response response = RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(book)
        .post(API_ROOT);
      return API_ROOT + "/" + response.jsonPath().get("id");
  }
}