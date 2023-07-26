package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
  @Id //primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  @ManyToOne  //많은 답변과 하나의 질문
  private Question question;  //매칭
}
