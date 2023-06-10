package kr.ac.jejunu.diarymvc.folder;

import jakarta.persistence.*;
import kr.ac.jejunu.diarymvc.diary.Diary;
import kr.ac.jejunu.diarymvc.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "folders")
@NoArgsConstructor
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    private List<Diary> diaries;

}
