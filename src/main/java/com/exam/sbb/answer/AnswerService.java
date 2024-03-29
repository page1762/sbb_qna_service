package com.exam.sbb.answer;

import com.exam.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service
public class AnswerService {
  private final AnswerRepository answerRepository;

  public void create(Question question, String content) {
    Answer answer = new Answer();
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    question.addAnswer(answer);
    answerRepository.save(answer);
  }
}
