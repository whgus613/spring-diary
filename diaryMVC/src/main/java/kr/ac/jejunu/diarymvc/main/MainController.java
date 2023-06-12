package kr.ac.jejunu.diarymvc.main;

import kr.ac.jejunu.diarymvc.diary.DiaryResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    @GetMapping()
    public String getDiary(Model model) {
        return "main";
    }

}
