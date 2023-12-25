package jeju.oneroom.domain.houseinfo.mapper;

import jeju.oneroom.domain.houseinfo.dto.HouseInfoDto;
import jeju.oneroom.domain.houseinfo.entity.HouseInfo;
import jeju.oneroom.global.common.entity.Coordinate;
import jeju.oneroom.global.common.entity.Rate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class HouseInfoMapperTest {
    
    @Autowired
    private HouseInfoMapper houseInfoMapper;
    
    @DisplayName("HouseInfo Entity가 HouseInfo ResponseDto로 정상적으로 매핑되는지 확인한다.")
    @Test
    void houseInfoToResponseDto() {
        // given
        Coordinate coordinate = Coordinate.builder()
                .latitude(11.1111)
                .longitude(22.2222)
                .build();
        
        Rate rate = Rate.builder()
                .interiorRate(11.1111)
                .buildingRate(11.1111)
                .trafficRate(11.1111)
                .securityRate(11.1111)
                .locationRate(11.1111)
                .build();
        
        HouseInfo houseInfo = HouseInfo.builder()
                .id(1L)
                .buildUes("사용1")
                .buildingStructure("구조1")
                .houseHold(1)
                .useAprDay("1년")
                .grndFloor(1)
                .ugrndFloor(1)
                .elevator(1)
                .platPlc("집1")
                .coordinate(coordinate)
                .rate(rate)
                .build();
        
        // when
        HouseInfoDto.Response houseInfoResponseDto = houseInfoMapper.houseInfoToResponseDto(houseInfo);
        
        // then
        assertThat(houseInfoResponseDto)
                .extracting(
                        "id",
                        "houseName",
                        "buildUes",
                        "buildingStructure",
                        "houseHold",
                        "useAprDay",
                        "floor",
                        "elevator",
                        "platPlc",
                        "coordinate.latitude",
                        "coordinate.longitude",
                        "rate.interiorRate",
                        "rate.buildingRate",
                        "rate.trafficRate",
                        "rate.securityRate",
                        "rate.locationRate"
                )
                .containsExactlyInAnyOrder(
                        1L,
                        "집1",
                        "사용1",
                        "구조1",
                        1,
                        "1년",
                        "지상 1층 (지하 1층)",
                        1,
                        "집1",
                        11.1111,
                        22.2222,
                        11.1111,
                        11.1111,
                        11.1111,
                        11.1111,
                        11.1111
                );
    }
}
