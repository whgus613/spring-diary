package kr.ac.jejunu.diarymvc.diary;

import jakarta.validation.Valid;
import kr.ac.jejunu.diarymvc.folder.FolderDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @GetMapping("/{diaryId}")
    public String getDiary(@PathVariable Long diaryId, Model model) {
        DiaryResponseDto diary = diaryService.getDiary(diaryId);
        model.addAttribute("diary", diary);
        return "diaryDetails";
    }

    @GetMapping("/folder/{folderId}")
    public String getDiariesByFolderId(@PathVariable Long folderId, Model model) {
        List<DiaryResponseDto> diaries = diaryService.getDiariesByFolderId(folderId);
        model.addAttribute("diaries", diaries);
        return "diaryList";
    }

    @GetMapping("/create")
    public String showCreateDiaryForm(Model model) {
        model.addAttribute("diaryDto", new DiaryDto());
        return "createDiary";
    }

    @PostMapping("/create/{folderId}")
    public String createDiary(
            @PathVariable Long folderId,
            @Valid @ModelAttribute("diaryDto") DiaryDto diaryDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createDiary";
        }
        diaryService.createDiary(folderId, diaryDto);
        return "redirect:/diaries/folder/" + folderId;

    }

    @GetMapping("/create/{folderId}")
    public String createDiaryForm(@PathVariable Long folderId, Model model) {
        model.addAttribute("diaryDto", new DiaryDto());
        model.addAttribute("folderId", folderId);
        return "createDiary";
    }

    @GetMapping("/delete/{diaryId}")
    public String deleteDiary(@PathVariable Long diaryId) {
        DiaryResponseDto diary = diaryService.getDiary(diaryId);
        Long folderId = diary.getFolderId();
        diaryService.deleteDiary(diaryId);
        return "redirect:/diaries/folder/" + folderId;
    }

    @GetMapping("/update/{diaryId}")
    public String showUpdateForm(@PathVariable Long diaryId, Model model) {
        Diary diary = diaryService.findDiaryById(diaryId);
        if (diary == null) {
            throw new NoSuchElementException("Diary not found with id: " + diaryId);
        }

        model.addAttribute("diary", diary);
        model.addAttribute("changeDiaryDto", new ChangeDiaryDto());
        return "updateDiary";
    }

    @PostMapping("/update/{diaryId}")
    public String updateDiary(
            @PathVariable Long diaryId,
            @ModelAttribute("diary") @Valid ChangeDiaryDto changeDiaryDto
    ) {
        DiaryResponseDto diaryResponseDto = diaryService.updateDiary(diaryId, changeDiaryDto);

        Long folderId = diaryResponseDto.getFolderId();

        return "redirect:/diaries/folder/" + folderId;
    }

    @GetMapping("/search-by-date")
    public String getDiariesByDate(@RequestParam("date")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate date, Model model) {
        List<Diary> diaries = diaryService.getDiariesByDate(date);
        model.addAttribute("diaries", diaries);
        return "diaryByDate";
    }

    @GetMapping("/search-by-word")
    public String searchDiariesByKeyword(@RequestParam("keyword") String keyword, Model model) {
        List<Diary> diaries = diaryService.searchDiariesByKeyword(keyword);
        model.addAttribute("diaries", diaries);
        return "diaryByWord";
    }

    @GetMapping("/folders/{folderId}/emotion-avg")
    public String showAverageEmotionScore(@PathVariable Long folderId, Model model) {
        double averageEmotionScore = diaryService.calculateAverageEmotionScore(folderId);
        model.addAttribute("averageEmotionScore", averageEmotionScore);
        return "emotionAvg";
    }

}
