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
import java.util.NoSuchElementException;

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

    @GetMapping("/update/{folderId}")
    public String showUpdateFolderPage(@PathVariable Long folderId, Model model) {
        Folder folder = folderService.getFolderById(folderId);
        if (folder == null) {
            throw new NoSuchElementException("Folder not found with id: " + folderId);
        }

        model.addAttribute("folder", folder);
        return "updateFolder";
    }

    @PostMapping("/update/{folderId}")
    public String updateFolder(@PathVariable Long folderId, @ModelAttribute("folder") FolderDto updatedFolderDto, HttpServletRequest request) {
        FolderResponseDto folderResponseDto = folderService.updateFolder(folderId, updatedFolderDto);

        Long userId = folderResponseDto.getUser_id();

        return "redirect:/folders/list/" + userId;
    }


}
