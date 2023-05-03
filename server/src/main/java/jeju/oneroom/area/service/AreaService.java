package jeju.oneroom.area.service;

import jeju.oneroom.area.dto.AreaDto;
import jeju.oneroom.area.entity.Area;
import jeju.oneroom.area.mapper.AreaMapper;
import jeju.oneroom.area.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaService {
    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    public List<AreaDto.Response> findAreasByAreaName(String areaName){
        return areaRepository.findByAreaName(areaName).stream().map(areaMapper::areaToResponseDto).collect(Collectors.toList());
    }

    public Area findVerifiedAreaByAreaCode(long areaCode){
        return areaRepository.findById(areaCode).orElseThrow(() -> new RuntimeException("AREA_NOT_FOUND"));
    }
}
