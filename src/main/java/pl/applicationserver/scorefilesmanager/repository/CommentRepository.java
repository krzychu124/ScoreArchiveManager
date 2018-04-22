package pl.applicationserver.scorefilesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.applicationserver.scorefilesmanager.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
