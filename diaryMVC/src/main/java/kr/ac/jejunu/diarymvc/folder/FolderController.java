package kr.ac.jejunu.diarymvc.folder;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("/create")
    public String showCreateFolderForm(Model model) {
        model.addAttribute("folderDto", new FolderDto());
        return "folderForm";
    }


    @PostMapping("/create/{userId}")
    public String createFolder(
            @PathVariable Long userId,
            @Valid @ModelAttribute("folderDto") FolderDto folderDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "folderForm";
        }
        folderService.createFolder(userId, folderDto);
        return "redirect:/folders/list/" + userId;
    }


    @GetMapping("/create/{userId}")
    public String createFolderForm(@PathVariable Long userId, Model model) {
        model.addAttribute("folderDto", new FolderDto());
        model.addAttribute("userId", userId);
        return "folderForm";
    }


    @GetMapping("/list/{userId}")
    public String getFoldersByUserId(@PathVariable Long userId, Model model) {
        List<FolderResponseDto> folders = folderService.getFoldersByUserId(userId);
        model.addAttribute("folders", folders);
        return "folderList";
    }

    @GetMapping("/delete/{folderId}")
    public String deleteFolder(@PathVariable("folderId") Long folderId, HttpServletRequest request) {
        folderService.deleteFolder(folderId);

        return "redirect:" + request.getHeader("Referer");
    }



}
