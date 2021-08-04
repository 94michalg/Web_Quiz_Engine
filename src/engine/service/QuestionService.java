package engine.service;

import engine.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

    Page<Question> getAllQuestions(Pageable pageable);

    Question getQuestionById(Long id);

    Question saveQuestion(Question question);

    void deleteQuestionById(Long id);
}
