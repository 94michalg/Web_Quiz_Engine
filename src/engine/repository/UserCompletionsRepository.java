package engine.repository;

import engine.entity.UserCompletions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserCompletionsRepository extends PagingAndSortingRepository<UserCompletions, Long> {
    Page<UserCompletions> findByUser(String user, Pageable pageable);
}
