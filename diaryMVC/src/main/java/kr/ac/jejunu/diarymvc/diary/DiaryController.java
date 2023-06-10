package kr.ac.jejunu.diarymvc.diary;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/{folderId}")
    public ResponseEntity<DiaryResponseDto> createDiary(
            @PathVariable Long folderId,
            @RequestBody DiaryDto diaryDto) {
        DiaryResponseDto createdDiary = diaryService.createDiary(folderId, diaryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiary);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDto> getDiary(@PathVariable Long diaryId) {
        DiaryResponseDto diary = diaryService.getDiary(diaryId);
        return ResponseEntity.ok(diary);
    }

    @GetMapping("/folder/{folderId}")
    public ResponseEntity<List<DiaryResponseDto>> getDiariesByFolderId(@PathVariable Long folderId) {
        List<DiaryResponseDto> diaries = diaryService.getDiariesByFolderId(folderId);
        return ResponseEntity.ok(diaries);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Diary>> getDiariesByDate(@PathVariable("date") String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<Diary> diaries = diaryService.getDiariesByDate(date);
        return ResponseEntity.ok(diaries);
    }



    @GetMapping("/search")
    public ResponseEntity<List<Diary>> searchDiariesByKeyword(@RequestParam String keyword) {
        List<Diary> diaries = diaryService.searchDiariesByKeyword(keyword);
        return ResponseEntity.ok(diaries);
    }

    @PutMapping("/{diaryId}/content")
    public DiaryResponseDto changeContent(@PathVariable Long diaryId, @RequestBody ChangeContentDto changeContentDto) {
        String content = changeContentDto.getContent();
        return diaryService.changeContent(diaryId, content);
    }

    @PutMapping("/{diaryId}/emotion")
    public DiaryResponseDto changeEmotion(@PathVariable Long diaryId, @RequestBody ChangeEmotionDto changeEmotionDto) {
        int emotion = changeEmotionDto.getEmotion();
        return diaryService.changeEmotion(diaryId, emotion);
    }

    @PutMapping("/{diaryId}/title")
    public DiaryResponseDto changeTitle(@PathVariable Long diaryId, @RequestBody ChangeTitleDto changeTitleDto) {
        String title = changeTitleDto.getTitle();
        return diaryService.changeTitle(diaryId, title);
    }

    @PutMapping("/{diaryId}/change-date")
    public ResponseEntity<DiaryResponseDto> changeDiaryDate(
            @PathVariable Long diaryId,
            @RequestBody ChangeDateDto changeDateDto
    ) {
        DiaryResponseDto updatedDiary = diaryService.changeDate(diaryId, changeDateDto);
        return ResponseEntity.ok(updatedDiary);
    }

}
