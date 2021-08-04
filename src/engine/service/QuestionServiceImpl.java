package engine.service;

import engine.repository.QuestionRepository;
import engine.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    /**
     * Autowiring the Planet Repository
     */
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Get All Planets.
     * @return List of all planets.
     */
    @Override
    public Page<Question> getAllQuestions(Pageable pageable) {

            return questionRepository.findAll(pageable);

//        return (List<Question>) questionRepository.findAll();
    }

    /**
     * Get Planet By Id.
     * @param id Id
     * @return Question
     */
    @Override
    public Question getQuestionById(final Long id) {
        return questionRepository.findById(id).get();
    }

    /**
     * Save Planet.
     * @param question Planet to save
     * @return Saved Planet
     */
    @Override
    public Question saveQuestion(final Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(Long id) {
            questionRepository.deleteById(id);
    }


}
