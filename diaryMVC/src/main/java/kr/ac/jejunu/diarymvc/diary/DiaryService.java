package kr.ac.jejunu.diarymvc.diary;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Diary createDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    public Diary updateDiary(Long diaryId, Diary updatedDiary) {
        Diary existingDiary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("Diary not found with id: " + diaryId));

        existingDiary.setTitle(updatedDiary.getTitle());
        existingDiary.setContent(updatedDiary.getContent());
        existingDiary.setEmotion(updatedDiary.getEmotion());
        existingDiary.setDate(updatedDiary.getDate());

        return diaryRepository.save(existingDiary);
    }

    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }

    public List<Diary> searchDiariesByDate(String date) {
        return diaryRepository.findByDate(date);
    }

    public List<Diary> searchDiariesByKeyword(String keyword) {
        return diaryRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

}
