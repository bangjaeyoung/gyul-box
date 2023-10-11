package jeju.oneroom.domain.houseInfo.controller;

import jeju.oneroom.domain.area.service.AreaService;
import jeju.oneroom.global.common.dto.ListResponseDto;
import jeju.oneroom.domain.houseInfo.service.HouseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequiredArgsConstructor
public class HouseInfoController {
    private final HouseInfoService houseInfoService;
    private final AreaService areaService;

    // 건물 정보 단일 조회
    @GetMapping("/houseInfos/{houseInfo-id}")
    public ResponseEntity<?> getHouseInfo(@PathVariable("houseInfo-id") @Positive long houseInfoId) {
        return new ResponseEntity<>(houseInfoService.findHouseInfo(houseInfoId), HttpStatus.OK);
    }

    // 지도에서 노드 클릭 시 제공되는 단순 건물 정보 조회
    @GetMapping("/houseInfos/{houseInfo-id}/simple")
    public ResponseEntity<?> getHouseInfoSimple(@PathVariable("houseInfo-id") @Positive long houseInfoId) {
        return new ResponseEntity<>(houseInfoService.findHouseInfoSimple(houseInfoId), HttpStatus.OK);
    }

    // 동, 면, 웁에 속해있는 건물 좌표와 리뷰 수. 지도의 노드에서 사용
    @GetMapping("/areas/{area-id}/houseInfos")
    public ResponseEntity<?> getAreaHouseInfos(@PathVariable("area-id") @Positive long areaCode,
                                               @RequestParam int level) {
        return new ResponseEntity<>(new ListResponseDto<>(houseInfoService.findHouseInfosByArea(areaCode, level)), HttpStatus.OK);
    }

    // 건물 주소를 통한 건물 정보 검색. Review create 시 사용
    @GetMapping("/houseInfos/search")
    public ResponseEntity<?> getAreaHouseInfos(@RequestParam String content) {
        return new ResponseEntity<>(new ListResponseDto<>(houseInfoService.findHouseInfosByContent(content)), HttpStatus.OK);
    }
}
