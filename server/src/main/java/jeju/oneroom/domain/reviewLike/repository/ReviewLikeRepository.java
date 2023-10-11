package jeju.oneroom.domain.reviewLike.repository;

import jeju.oneroom.domain.review.entity.Review;
import jeju.oneroom.domain.reviewLike.entity.ReviewLike;
import jeju.oneroom.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    // 해당 리뷰에 해당 유저의 좋아요 가져오기 (Optional)
    Optional<ReviewLike> findByReviewAndUser(Review review, User user);
}
