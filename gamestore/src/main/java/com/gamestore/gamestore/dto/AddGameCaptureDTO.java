package com.gamestore.gamestore.dto;
import java.util.List;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddGameCaptureDTO {

    @Min(value=1 , message = "Mã trò chơi không hợp lệ")
    private Integer gameID;

    private List<String> urls;
}
