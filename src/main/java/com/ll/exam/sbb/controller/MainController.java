package com.ll.exam.sbb.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
  private int increaseNum = -1;
  @RequestMapping("/sbb")
  @ResponseBody
  public String index(){
    return "안녕하세요.";
  }

  @GetMapping("/page1")
  @ResponseBody
  public String showGet(){
    return """
          <form method="POST" action="/page2"/>
            <input type="number" name="age" placeholder="나이 입력" />
            <input type="submit" value="page2로 POST 방식으로 이동" />
          </form>
          """;
  }

  @PostMapping("/page2")
  @ResponseBody
  public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
    return """
           <h1>입력된 나이 : %d</h1>
           <h1>안녕하세요. POST 방식으로 오신걸 환영합니다.</h1>
           """.formatted(age);
  }

  @GetMapping("/page2")
  @ResponseBody
  public String showPost(@RequestParam(defaultValue = "0") int age) {
    return """
           <h1>입력된 나이 : %d</h1>
           <h1>안녕하세요. GET 방식으로 오신걸 환영합니다.</h1>           
           """.formatted(age);
  }

  @GetMapping("/plus")
  @ResponseBody
  public int plus(int a, int b){
    return a + b;
  }

  @GetMapping("/minus")
  @ResponseBody
  public int minus(int a, int b){
    return a - b;
  }

  @GetMapping("/increase")
  @ResponseBody
  public int increase(){
    increaseNum++;
    return increaseNum;
  }

  @GetMapping("/gugudan")
  @ResponseBody
  public String showGugudan(int dan, int limit){
    String rs = "";

    for(int i=1;i<=limit;i++){
      rs += "%d * %d = %d<br>\n".formatted(dan, i, dan * i);  //br
    }

    return rs;
  }

  @GetMapping("/gugudan2")
  @ResponseBody
  public String showGugudan2(Integer dan, Integer limit){
    if(dan==null){
      dan=9;
    }
    if(limit==null){
      limit=9;
    }

    Integer finalDan = dan;
    return IntStream.rangeClosed(1,limit)
        .mapToObj((i -> "%d * %d = %d".formatted(finalDan, i, finalDan*i)))
        .collect(Collectors.joining("<br>"));
  }

  @GetMapping("/mbti")
  @ResponseBody
  public String showMbti(String name){
    String mbti = "";

    if(name.equals("홍길동")){
      mbti = "INFP";
    }
    else if (name.equals("홍길순")){
      mbti = "ENFP";
    }
    else if (name.equals("김세헌")){
      mbti = "ISTP";
    }
    return mbti;
  }

  @GetMapping("/saveSession/{age}/{name}")
  @ResponseBody
  public String saveSession(@PathVariable String age, @PathVariable String name, HttpServletRequest request){
    HttpSession session = request.getSession();
    session.setAttribute(age, name);

    return "이름: %s, 나이: %s 저장완료".formatted(name, age);
  }

//  @GetMapping("/getSession/{name}")
//  @ResponseBody
//  public String getSession(@PathVariable String name, HttpSession session){
//    String age = (String) session.getAttribute(name);
//
//    return "%s의 나이는 %s입니다.".formatted(name, age);
//  }

  @GetMapping("/getSession/{age}")
  @ResponseBody
  public String getSession(@PathVariable String age, HttpSession session){
    String name = (String) session.getAttribute(age);

    return "%s의 나이는 %s입니다.".formatted(name, age);
  }

  private List<Article> articles = new ArrayList<>();

  @GetMapping("/addArticle/{title}/{body}")
  @ResponseBody
  public String addArticle (@PathVariable String title, @PathVariable String body){

    Article article = new Article(title, body);
    articles.add(article);

    return "%d번 글이 등록되었습니다.".formatted(article.getId());
  }

  @GetMapping("/article/{id}")
  @ResponseBody
  public Article article (@PathVariable int id){
    Article article = articles.stream().filter(article1 -> article1.getId() == id).findFirst().get();

    return article;
  }

  @GetMapping("/modifyArticle/{id}/{title}/{body}")
  @ResponseBody
  public String modifyarticle(@PathVariable int id, @PathVariable String title, @PathVariable String body){

    Article article = articles.stream().filter(article1 -> article1.getId() == id).findFirst().get();
    article.title = title;
    article.body = body;

    return "%d번 게시물을 수정하였습니다.".formatted(article.getId());
  }

  @GetMapping("/deleteArticle/{id}")
  @ResponseBody
  public void deleteArticle(@PathVariable int id){
    Article article = articles.stream().filter(article1 -> article1.getId() == id).findFirst().get();

    articles.remove(id);
  }



  @AllArgsConstructor
  @Getter
  class Article{
    private static int lastid = 0;

    private int  id;
    private String title;
    private String body;

    public Article(String title, String body){
      this(++lastid, title, body);
    }
  }

}
