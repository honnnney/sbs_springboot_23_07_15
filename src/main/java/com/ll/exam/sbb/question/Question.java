package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.Answer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity //아래 Question 클래스는 Entity 클래스이다 라고 알려줌.
//아래 클래스와 1:1로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
public class Question {
  @Id //primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
  private Integer id;

  @Column(length = 200) //varchar(200)
  private String subject;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;

  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) //
  private List<Answer> answerList;  //Question 하나(One)에 Answer 여러개(Many), answerList
}


