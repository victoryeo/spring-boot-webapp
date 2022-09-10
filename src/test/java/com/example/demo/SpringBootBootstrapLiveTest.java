package com.example.demo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;
import com.example.demo.persistence.model.Book;

public class SpringBootBootstrapLiveTest {

  private static final String API_ROOT
    = "http://localhost:8081/api/books";

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