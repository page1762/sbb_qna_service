package com.exam.sbb.controller;


import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

  private int increaseA = 0;

  @RequestMapping("/sbb")
  @ResponseBody
  public String index() {
    return "안녕하세요.";
  }

  @GetMapping("/page1")
  @ResponseBody
  public String ShowGet() {
    return """
        <form method="POST" action="/page2"/>
        <input type = "number" name="age" placeholder="나이입력"/>
        <input type="submit" value="page2로 POST 방식으로 이동"/>
        </form>
         """;
  }

  @PostMapping("/page2")
  @ResponseBody
  public String ShowPage2Post(@RequestParam(defaultValue = "0") int age) {
    return """
        <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. POST 방식으로 오신걸 환영합니다.</h1>
        """.formatted(age);
  }


  @GetMapping("/page2")
  @ResponseBody
  public String ShowPost(@RequestParam(defaultValue = "0") int age) {
    return """
        <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. GET 방식으로 오신걸 환영합니다.</h1>
        """.formatted(age);
  }

  @GetMapping("/plus")
  @ResponseBody
  public String ShowAdd(int a, int b) {
    int sum = a + b;
    return """
        <h1>값 : %d</h1>
        """.formatted(sum);
  }

  @GetMapping("/minus")
  @ResponseBody
  public String ShowMinus(int a, int b) {
    int result = a - b;
    return """
        <h1>값 : %d</h1>
        """.formatted(result);
  }

  @GetMapping("/increase")
  @ResponseBody
  public int ShowIncrease() {
    increaseA++;
    return increaseA;
  }

  @GetMapping("/gugudan")
  @ResponseBody
  public String showGugudan(Integer dan, Integer limit) {
    if (dan == null) {
      dan = 9;
    }

    if (limit == null) {
      limit = 9;
    }

    final Integer finalDan = dan;
    return IntStream.rangeClosed(1, limit)
        .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
        .collect(Collectors.joining("<br>\n"));
  }

  @GetMapping("/mbti/{name}")
  @ResponseBody
  public String ShowMbti(@PathVariable String name) {
    return switch (name) {
      case "홍길동" -> "ENFP";
      case "홍길순" -> "ENFJ";
      case "임꺽정" -> "INFP";
      case "김경민" -> "ESFP";
      default -> "모름";
    };
  }

  @GetMapping("/saveSession/{name}/{value}")
  @ResponseBody
  public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
    HttpSession session = req.getSession();
    session.setAttribute(name, value);

    return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
  }

  @GetMapping("/getSession/{name}")
  @ResponseBody
  public String getSession(@PathVariable String name, HttpSession session) {

    String value = (String) session.getAttribute(name);

    return "세션변수 %s의 값은 %s입니다.".formatted(name, value);
  }

  private List<Article> articles = new ArrayList<>();

  @GetMapping("/addArticle")
  @ResponseBody
  public String addArticle(String title, String body) {

    Article article = new Article(title, body);
    articles.add(article);

    return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
  }

  @GetMapping("/article/{id}")
  @ResponseBody
  public Article getArticle(@PathVariable int id) {

    Article article = articles
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    return article;
  }

  @GetMapping("/modifyArticle/{id}")
  @ResponseBody
  public String modifyArticle(@PathVariable int id, String title, String body) {

    Article article = articles
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    if(article == null) {
      return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }

    article.setTitle(title);
    article.setBody(body);

    return "%d번 게시물을 수정하였습니다.".formatted(article.getId());
  }

  @GetMapping("/deleteArticle/{id}")
  @ResponseBody
  public String deleteArticle(@PathVariable int id) {

    Article article = articles
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    if(article == null) {
      return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }

    articles.remove(article);

    return "%d번 게시물을 삭제하였습니다.".formatted(article.getId());
  }

 // private List<Person> people = new ArrayList<>();
//  @GetMapping("/addPersonOldWay")
//  @ResponseBody
//  public String addPersonOldWay(int id, int age, String name) {
//
//    Person p = new Person(id, age, name);
//    people.add(p);
//
//    return "%d번 사용자가 생성되었습니다.".formatted(p.getId());
//  }

  @GetMapping("/addPerson/{id}")
  @ResponseBody
  public Person addPerson(Person p) {
    return p;
  }

  @AllArgsConstructor
  @Getter
  @Setter
  @NoArgsConstructor
  @Builder
  static class Person {
    private int id;
    private int age;
    private String name;

    public Person(int age, String name) {
      this.age = age;
      this.name = name;
    }
  }


  @AllArgsConstructor
  @Getter
  @Setter
  class Article {
    private static int lastId = 0;
    private int id;
    private String title;
    private String body;

    public Article(String title, String body) {
      this(++lastId, title, body);
    }
  }
}
