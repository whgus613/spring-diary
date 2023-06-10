package kr.ac.jejunu.diarymvc.diary;

import jakarta.persistence.*;
import kr.ac.jejunu.diarymvc.folder.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import kr.ac.jejunu.diarymvc.user.User;

@Getter
@Setter
@Entity
@Table(name = "diaries")
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private int emotion;
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

}
