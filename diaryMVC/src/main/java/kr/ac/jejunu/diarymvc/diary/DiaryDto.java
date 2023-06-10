package kr.ac.jejunu.diarymvc.diary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {

    private String title;
    private String content;
    private int emotion;

}
