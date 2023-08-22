package jeju.oneroom.common.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponseDto<T> {

    private final List<T> data;

    public ListResponseDto(List<T> data) {
        this.data = data;
    }
}
