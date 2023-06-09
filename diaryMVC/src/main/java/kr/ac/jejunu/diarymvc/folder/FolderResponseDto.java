package kr.ac.jejunu.diarymvc.folder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponseDto {

    private long id;
    private String name;
    private long user_id;

}
