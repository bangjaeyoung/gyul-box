package jeju.oneroom.domain.houseinfo.repository;

import jeju.oneroom.domain.houseinfo.entity.HouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseInfoRepository extends JpaRepository<HouseInfo, Long>, HouseInfoCustomRepository {
    // 주소를 통한 건물 정보 리스트로 반환
    List<HouseInfo> findByPlatPlcContains(String content);
}
