package kr.ac.jejunu.diarymvc.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.ac.jejunu.diarymvc.diary.Diary;
import kr.ac.jejunu.diarymvc.folder.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Diary> diaries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Folder> folders;

}
