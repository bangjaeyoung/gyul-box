package jeju.oneroom.domain.houseinfo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jeju.oneroom.domain.houseinfo.entity.HouseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jeju.oneroom.domain.area.entity.QArea.area;
import static jeju.oneroom.domain.houseinfo.entity.QHouseInfo.houseInfo;

@Repository
@RequiredArgsConstructor
public class HouseInfoCustomRepositoryImpl implements HouseInfoCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HouseInfo> findByArea_AreaCode(long areaCode) {
        return jpaQueryFactory.selectFrom(houseInfo)
            .leftJoin(houseInfo.area, area).fetchJoin()
            .where(area.areaCode.eq(areaCode))
            .fetch();
    }

    @Override
    public List<HouseInfo> findTop20ByArea_AreaCodeOrderByReviewCount(long areaCode) {
        return jpaQueryFactory.selectFrom(houseInfo)
            .leftJoin(houseInfo.area, area).fetchJoin()
            .where(area.areaCode.eq(areaCode))
            .orderBy(houseInfo.reviewCount.desc())
            .limit(20)
            .fetch();
    }
}
