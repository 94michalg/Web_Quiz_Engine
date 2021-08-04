package engine.repository;

import engine.entity.Question;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

//    Page<Question> getAllQuestions (Pageable pageable);

//    List<Question> findByNameContaining(String value);

//    @Query("SELECT p FROM Question p WHERE p.name LIKE %:value%")
//    List<Question> findByNameLike(@Param("value") String value);

}
