package kr.ac.jejunu.diarymvc.diary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryDao extends JpaRepository<Diary, Long> {
}