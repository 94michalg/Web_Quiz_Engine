package engine.service;

import engine.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

    /**
     * Get All Planets.
     * @return List of all questions.
     */
    Page<Question> getAllQuestions(Pageable pageable);

    /**
     * Get Planet By Id.
     * @param id Id
     * @return Planet
     */
    Question getQuestionById(Long id);

    /**
     * Save Planet.
     * @param question Planet to save
     * @return Saved Planet
     */
    Question saveQuestion(Question question);

    void deleteQuestionById(Long id);
}
