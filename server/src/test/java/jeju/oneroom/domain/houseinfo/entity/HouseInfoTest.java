package jeju.oneroom.domain.houseinfo.entity;

import jeju.oneroom.domain.area.entity.Area;
import jeju.oneroom.global.common.entity.Coordinate;
import jeju.oneroom.global.common.entity.Rate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HouseInfoTest {

    @DisplayName("평점이 업데이트되는 것을 확인한다.")
    @Test
    void updateRate() {
        // given
        HouseInfo houseInfo = HouseInfo.builder().build();

        Rate rate = Rate.builder().build();

        // when
        houseInfo.updateRate(rate);

        // then
        assertThat(houseInfo.getRate()).isEqualTo(rate);
    }

    @DisplayName("지역이 설정되는 것을 확인한다.")
    @Test
    void setArea() {
        // given
        HouseInfo houseInfo = HouseInfo.builder().build();

        Area area = Area.builder().build();

        // when
        houseInfo.setArea(area);

        // then
        assertThat(houseInfo.getArea()).isEqualTo(area);
    }

    @DisplayName("위도와 경도가 설정되는 것을 확인한다.")
    @Test
    void setCoordinate() {
        // given
        HouseInfo houseInfo = HouseInfo.builder().build();

        Coordinate coordinate = Coordinate.builder().build();

        // when
        houseInfo.setCoordinate(coordinate);

        // then
        assertThat(houseInfo.getCoordinate()).isEqualTo(coordinate);
    }
}
