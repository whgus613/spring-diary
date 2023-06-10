package kr.ac.jejunu.diarymvc.folder;

import kr.ac.jejunu.diarymvc.diary.Diary;
import kr.ac.jejunu.diarymvc.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Folder createFolder(@RequestBody Folder folder, @RequestParam Long userId) {
        User user = new User();
        user.setId(userId);
        folder.setUser(user);

        return folderService.createFolder(folder);
    }


    @PutMapping("/{folderId}")
    public Folder updateFolder(@PathVariable Long folderId, @RequestBody Folder updatedFolder) {
        return folderService.updateFolder(folderId, updatedFolder);
    }

    @DeleteMapping("/{folderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
    }

    @GetMapping("/{folderId}/diaries")
    public List<Diary> getFolderDiaries(@PathVariable Long folderId) {
        return folderService.getFolderDiaries(folderId);
    }

}

