package com.exam.sbb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
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
}
