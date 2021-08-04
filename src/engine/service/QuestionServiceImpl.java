package engine.service;

import engine.entity.Question;
import engine.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Page<Question> getAllQuestions(Pageable pageable) {

            return questionRepository.findAll(pageable);
    }

    @Override
    public Question getQuestionById(final Long id) {
        return questionRepository.findById(id).get();
    }

    @Override
    public Question saveQuestion(final Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(Long id) {
            questionRepository.deleteById(id);
    }


}
