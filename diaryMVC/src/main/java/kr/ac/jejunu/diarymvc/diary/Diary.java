package kr.ac.jejunu.diarymvc.diary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kr.ac.jejunu.diarymvc.folder.Folder;
import kr.ac.jejunu.diarymvc.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "diaries")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private int emotion;
    @CreatedDate
    @Column(name = "date", nullable = false, updatable = true)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonBackReference
    private Folder folder;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

}
