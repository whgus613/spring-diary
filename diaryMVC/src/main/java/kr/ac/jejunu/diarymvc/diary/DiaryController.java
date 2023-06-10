package kr.ac.jejunu.diarymvc.diary;

import kr.ac.jejunu.diarymvc.folder.Folder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Diary createDiary(@RequestBody Diary diary, @RequestParam Long folderId) {
        // Set the folder ID in the diary object
        Folder folder = new Folder();
        folder.setId(folderId);
        diary.setFolder(folder);

        return diaryService.createDiary(diary);
    }


    @PutMapping("/{diaryId}")
    public Diary updateDiary(@PathVariable Long diaryId, @RequestBody Diary updatedDiary) {
        return diaryService.updateDiary(diaryId, updatedDiary);
    }

    @DeleteMapping("/{diaryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
    }

    @GetMapping("/search/by-date")
    public List<Diary> searchDiariesByDate(@RequestParam String date) {
        return diaryService.searchDiariesByDate(date);
    }

    @GetMapping("/search/by-keyword")
    public List<Diary> searchDiariesByKeyword(@RequestParam String keyword) {
        return diaryService.searchDiariesByKeyword(keyword);
    }
}
