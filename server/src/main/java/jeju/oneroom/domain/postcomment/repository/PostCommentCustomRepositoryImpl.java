package jeju.oneroom.domain.postcomment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jeju.oneroom.domain.post.entity.Post;
import jeju.oneroom.domain.postcomment.entity.PostComment;
import jeju.oneroom.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jeju.oneroom.domain.post.entity.QPost.post;
import static jeju.oneroom.domain.postcomment.entity.QPostComment.postComment;
import static jeju.oneroom.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PostCommentCustomRepositoryImpl implements PostCommentCustomRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public List<PostComment> findPostCommentsByPost(Post post) {
        return jpaQueryFactory
                .selectFrom(postComment)
                .leftJoin(postComment.user, user).fetchJoin()
                .where(postComment.post.id.eq(post.getId()))
                .orderBy(postComment.createdAt.asc())
                .fetch();
    }
    
    @Override
    public Page<PostComment> findPostCommentsByUser(User user, Pageable pageable) {
        List<PostComment> postComments = getPostCommentsByUser(user, pageable);
        
        Long count = jpaQueryFactory.select(postComment.count())
                .from(postComment)
                .where(postComment.user.eq(user))
                .fetchOne();
        
        return new PageImpl<>(postComments, pageable, count);
    }
    
    private List<PostComment> getPostCommentsByUser(User user, Pageable pageable) {
        return jpaQueryFactory.selectFrom(postComment)
                .leftJoin(postComment.post, post).fetchJoin()
                .where(postComment.user.eq(user))
                .orderBy(postComment.modifiedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
