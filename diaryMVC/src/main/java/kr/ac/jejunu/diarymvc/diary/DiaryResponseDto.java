package kr.ac.jejunu.diarymvc.diary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryResponseDto {

    private long id;
    private String title;
    private String content;
    private int emotion;
    private LocalDate date;

    private long folderId;

}
