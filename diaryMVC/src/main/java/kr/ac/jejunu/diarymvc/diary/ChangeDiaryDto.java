package kr.ac.jejunu.diarymvc.diary;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDiaryDto {

    private String content;

    @NotNull
    private LocalDate date;
    private int emotion;
    private String title;

}
