package com.exam.sbb;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbRepositoryTests {

  @Autowired
  private QuestionRepository questionRepository;

  @Test
  void testJpa0() {
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    questionRepository.save(q1);

    System.out.println(q1.getId());

    Question q2 = new Question();
    q2.setSubject("스프링부트 모델 질문입니다.");
    q2.setContent("id는 자동으로 생성되나요?");
    q2.setCreateDate(LocalDateTime.now());
    questionRepository.save(q2);

    System.out.println(q2.getId());

    questionRepository.disableForeignKeyChecks();
    questionRepository.truncate();
    questionRepository.enableForeignKeyChecks();
  }

  @Test
  void testJpa2(){
    Question q1 = new Question();
    q1.setSubject("질문 1");
    q1.setContent("내용 1");
    questionRepository.save(q1);

  }
}