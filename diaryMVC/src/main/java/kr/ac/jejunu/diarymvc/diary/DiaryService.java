package kr.ac.jejunu.diarymvc.diary;

import kr.ac.jejunu.diarymvc.folder.Folder;
import kr.ac.jejunu.diarymvc.folder.FolderRepository;
import kr.ac.jejunu.diarymvc.user.User;
import kr.ac.jejunu.diarymvc.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    public DiaryService(DiaryRepository diaryRepository,
                        FolderRepository folderRepository,
                        UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    public DiaryResponseDto createDiary(Long userId, Long folderId, DiaryDto diaryDto) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new NoSuchElementException("Folder not found with id: " + folderId));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found with id: "+ userId));

        Diary diary = new Diary();
        diary.setTitle(diaryDto.getTitle());
        diary.setContent(diaryDto.getContent());
        diary.setEmotion(diaryDto.getEmotion());
        diary.setFolder(folder);
        diary.setUser(user);

        LocalDate currentDate = LocalDate.now();
        diary.setDate(currentDate);

        Diary createdDiary = diaryRepository.save(diary);

        return new DiaryResponseDto(
                createdDiary.getId(),
                createdDiary.getTitle(),
                createdDiary.getContent(),
                createdDiary.getEmotion(),
                createdDiary.getDate(),
                createdDiary.getFolder().getId(),
                createdDiary.getUser().getId()
        );
    }


    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }

    public DiaryResponseDto getDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("Diary not found with id: " + diaryId));

        return new DiaryResponseDto(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getEmotion(),
                diary.getDate(),
                diary.getFolder().getId(),
                diary.getUser().getId()
        );
    }

    public List<DiaryResponseDto> getDiariesByFolderId(Long folderId) {
        List<Diary> diaries = diaryRepository.findByFolderId(folderId);

        return diaries.stream()
                .map(diary -> new DiaryResponseDto(
                        diary.getId(),
                        diary.getTitle(),
                        diary.getContent(),
                        diary.getEmotion(),
                        diary.getDate(),
                        diary.getFolder().getId(),
                        diary.getUser().getId()
                ))
                .collect(Collectors.toList());
    }


    public List<Diary> findByUserAndDate(User user, LocalDate date) {
        return diaryRepository.findByUserAndDate(user, date);
    }

    public List<Diary> searchByKeywordForUser(String keyword, User user) {
        return diaryRepository.searchByKeywordForUser(keyword, user);
    }

    public DiaryResponseDto updateDiary(Long diaryId, ChangeDiaryDto changeDiaryDto) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found with id: " + diaryId));

        diary.setContent(changeDiaryDto.getContent());
        diary.setDate(changeDiaryDto.getDate());
        diary.setEmotion(changeDiaryDto.getEmotion());
        diary.setTitle(changeDiaryDto.getTitle());

        Diary updatedDiary = diaryRepository.save(diary);

        return new DiaryResponseDto(
                updatedDiary.getId(),
                updatedDiary.getTitle(),
                updatedDiary.getContent(),
                updatedDiary.getEmotion(),
                updatedDiary.getDate(),
                updatedDiary.getFolder().getId(),
                updatedDiary.getUser().getId()
        );

    }

    public Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found with id: " + diaryId));
    }

    public double calculateAverageEmotionScore(Long folderId) {
        List<Diary> diaries = diaryRepository.findByFolderId(folderId);
        int totalEmotionScore = diaries.stream()
                .mapToInt(Diary::getEmotion)
                .sum();
        return (double) totalEmotionScore / diaries.size();
    }

}
