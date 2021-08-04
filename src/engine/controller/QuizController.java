package engine.controller;

import engine.entity.*;
import engine.repository.UserCompletionsRepository;
import engine.service.QuestionService;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class QuizController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCompletionsRepository userCompletionsRepository;

    @PostMapping(value = "/api/register")
    public User createUser(@RequestBody User user) {
        if (user.getPassword().length() >= 5 &&
                user.getEmail().contains(".") &&
                user.getEmail().contains("@")) {
            return userService.registerNewUser(user);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Wrong email or password"
            );
        }
    }

    @GetMapping("api/quizzes/{id}")
    public ResponseEntity<Question> getQuestionById(
            @PathVariable("id") final Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @GetMapping("api/quizzes")
    public ResponseEntity<Page<Question>> getAllQuestions(@RequestParam Optional<Integer> page) {

            Page<Question> questionPage = questionService.getAllQuestions(PageRequest.of(
                    page.orElse(0), 10, Sort.Direction.ASC, ("id")));

            return new ResponseEntity<Page<Question>>(questionPage, HttpStatus.OK);
    }
    
    @GetMapping("api/quizzes/completed")
    public ResponseEntity<Page<UserCompletions>> getCompletedQuestions(
            Authentication authentication,
            @RequestParam Optional<Integer> page) {
        String author = authentication.getName();
        Page<UserCompletions> pageOfUserCompletions = userCompletionsRepository.findByUser(author, PageRequest.of(
                page.orElse(0), 10, Sort.Direction.DESC, "completedAt"
        ));
        return new ResponseEntity<Page<UserCompletions>>(pageOfUserCompletions, HttpStatus.OK);
    }

    @PostMapping("api/quizzes")
    public ResponseEntity<Question> createQuiz(Authentication authentication,
                                               @RequestBody Question question) {
        if (question.getTitle() == null || question.getText() == null ||
            question.getTitle().equals("") || question.getText().equals("")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Title and Text are required"
            );
        } else if(question.getOptions() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Options size at least 2"
            );
        } else if(question.getOptions().length < 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Options size at least 2"
            );
        } else {
            String author = authentication.getName();
            question.setAuthor(author);
            Question savedQuestion = questionService.saveQuestion(question);
            return new ResponseEntity<>(savedQuestion, HttpStatus.OK);
        }
    }

    @PostMapping("api/quizzes/{id}/solve")
    public ResponseEntity<Response> solveQuiz(Authentication authentication,
                                              @PathVariable Long id,
                                              @RequestBody AnswersToQuizArray answersToQuizArray) {
        Question questionToSolve = questionService.getQuestionById(id);
        if (questionToSolve != null) {
            if (questionToSolve.isCorrect(answersToQuizArray.getAnswer())) {
                String author = authentication.getName();
                UserCompletions userCompletions = new UserCompletions();
                userCompletions.setCompletedAt(LocalDateTime.now());
                userCompletions.setId(id);
                userCompletions.setUser(author);
                userCompletionsRepository.save(userCompletions);
                return new ResponseEntity<>(Response.CORRECT_ANSWER, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Response.WRONG_ANSWER, HttpStatus.OK);
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @DeleteMapping("api/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id, Authentication authentication) {
        try {
            Question question = questionService.getQuestionById(id);
            String username = authentication.getName();
            if (question.getAuthor().equals(username)) {
                questionService.deleteQuestionById(id);
                return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "You are not an author"
                );
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}
