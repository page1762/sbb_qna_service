package com.exam.sbb;

import com.exam.sbb.question.Question;
import com.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionRepositoryTests {

  @Autowired
  private QuestionRepository questionRepository;
  private static int lastSampleDataId;

  @BeforeEach
  void beforeEach() {
    clearData();
    createSampleData();
  }

  public static int createSampleData(QuestionRepository questionRepository) {
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    questionRepository.save(q1);

    Question q2 = new Question();
    q2.setSubject("스프링부트 모델 질문입니다.");
    q2.setContent("id는 자동으로 생성되나요?");
    q2.setCreateDate(LocalDateTime.now());
    questionRepository.save(q2);

    return q2.getId();
  }

  private void createSampleData() {
    lastSampleDataId = createSampleData(questionRepository);
  }

  public static void clearData(QuestionRepository questionRepository) {
    questionRepository.deleteAll();
    questionRepository.truncateTable();
  }

  private void clearData() {
    clearData(questionRepository);
  }

  @Test
  void 저장() {
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

    assertThat(q1.getId()).isEqualTo(lastSampleDataId + 1);
    assertThat(q2.getId()).isEqualTo(lastSampleDataId + 2);
  }

  @Test
  void 삭제() {
    assertThat(questionRepository.count()).isEqualTo(lastSampleDataId);
    Question q = questionRepository.findById(1).get();
    questionRepository.delete(q);

    assertThat(questionRepository.count()).isEqualTo(lastSampleDataId - 1);
  }

  @Test
  void 수정() {
    Question q = questionRepository.findById(1).get();
    q.setSubject("수정된 제목");
    questionRepository.save(q);

    q = questionRepository.findById(1).get();
    assertThat(q.getSubject()).isEqualTo("수정된 제목");
  }

  @Test
  void findAll() {
    List<Question> all = questionRepository.findAll();
    assertThat(all.size()).isEqualTo(lastSampleDataId);

    Question q = all.get(0);
    assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
  }

  @Test
  void findBySubject() {
    Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
    assertThat(q.getId()).isEqualTo(1);
  }

  @Test
  void findBySubjectAndContent() {
    Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
    assertThat(q.getId()).isEqualTo(1);
  }

  @Test
  void findBySubjectLike() {
    List<Question> qList = questionRepository.findBySubjectLike("sbb%");
    Question q = qList.get(0);

    assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
  }
}