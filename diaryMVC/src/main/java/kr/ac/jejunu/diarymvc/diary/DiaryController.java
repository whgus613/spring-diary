package kr.ac.jejunu.diarymvc.diary;

import jakarta.validation.Valid;
import kr.ac.jejunu.diarymvc.folder.FolderDto;
import kr.ac.jejunu.diarymvc.user.User;
import kr.ac.jejunu.diarymvc.user.UserRepository;
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
    private final UserRepository userRepository;

    public DiaryController(DiaryService diaryService,
                           UserRepository userRepository) {
        this.diaryService = diaryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{diaryId}")
    public String getDiary(@PathVariable Long diaryId, Model model) {
        DiaryResponseDto diary = diaryService.getDiary(diaryId);
        model.addAttribute("diary", diary);
        return "diaryDetails";
    }

    @GetMapping("/user/{userId}/folder/{folderId}")
    public String getDiariesByFolderId(@PathVariable Long userId, @PathVariable Long folderId, Model model) {
        List<DiaryResponseDto> diaries = diaryService.getDiariesByFolderId(folderId);
        model.addAttribute("diaries", diaries);
        model.addAttribute("userId", userId);
        return "diaryList";
    }

    @GetMapping("/create/user/{userId}/folder/{folderId}")
    public String showCreateDiaryForm(@PathVariable Long userId, @PathVariable Long folderId, Model model) {
        model.addAttribute("diaryDto", new DiaryDto());
        model.addAttribute("userId", userId);
        model.addAttribute("folderId", folderId);
        return "createDiary";
    }

    @PostMapping("/create/user/{userId}/folder/{folderId}")
    public String createDiary(
            @PathVariable Long userId,
            @PathVariable Long folderId,
            @Valid @ModelAttribute("diaryDto") DiaryDto diaryDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createDiary";
        }
        diaryService.createDiary(userId, folderId, diaryDto);
        return "redirect:/diaries/user/" + userId + "/folder/" + folderId;

    }

    @GetMapping("/delete/{diaryId}")
    public String deleteDiary(@PathVariable Long diaryId) {
        DiaryResponseDto diary = diaryService.getDiary(diaryId);
        Long folderId = diary.getFolderId();
        Long userId = diary.getUserId();
        diaryService.deleteDiary(diaryId);
        return "redirect:/diaries/user/" + userId + "/folder/" + folderId;
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
        Long userId = diaryResponseDto.getUserId();

        return "redirect:/diaries/user/" + userId + "/folder/" + folderId;
    }

    @GetMapping("/user/{userId}/search-by-date")
    public String getDiariesByDate(@PathVariable Long userId, @RequestParam String date, Model model) {
        LocalDate parsedDate = LocalDate.parse(date);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        List<Diary> diaries = diaryService.findByUserAndDate(user, parsedDate);
        model.addAttribute("diaries", diaries);
        return "diaryByDate";
    }

    @GetMapping("/user/{userId}/search-by-word")
    public String searchDiariesByKeyword(@PathVariable Long userId, @RequestParam("keyword") String keyword, Model model) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        List<Diary> diaries = diaryService.searchByKeywordForUser(keyword, user);
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
