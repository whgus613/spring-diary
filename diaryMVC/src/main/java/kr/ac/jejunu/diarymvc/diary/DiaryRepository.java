package kr.ac.jejunu.diarymvc.diary;

import kr.ac.jejunu.diarymvc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserAndDate(User user, LocalDate date);

    List<Diary> findByFolderId(Long folderId);

    @Query("SELECT d FROM Diary d WHERE d.user = :user AND (d.title LIKE %:keyword% OR d.content LIKE %:keyword%)")
    List<Diary> searchByKeywordForUser(@Param("keyword") String keyword, @Param("user") User user);

}