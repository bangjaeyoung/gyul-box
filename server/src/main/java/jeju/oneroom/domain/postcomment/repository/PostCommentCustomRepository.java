package jeju.oneroom.domain.postcomment.repository;

import jeju.oneroom.domain.post.entity.Post;
import jeju.oneroom.domain.postcomment.entity.PostComment;
import jeju.oneroom.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCommentCustomRepository {
    
    List<PostComment> findPostCommentsByPost(Post post);
    
    // 특정 유저가 작성한 모든 게시글 댓글 조회
    Page<PostComment> findPostCommentsByUser(User user, Pageable pageable);
}
