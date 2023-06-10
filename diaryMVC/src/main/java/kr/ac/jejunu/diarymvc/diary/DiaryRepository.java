package kr.ac.jejunu.diarymvc.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByDate(String date);

    List<Diary> findByTitleContainingOrContentContaining(String keyword, String keyword1);
}