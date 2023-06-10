package kr.ac.jejunu.diarymvc.folder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<FolderResponseDto> createFolder(@PathVariable Long userId, @RequestBody FolderDto folderDto) {
        FolderResponseDto createdFolder = folderService.createFolder(userId, folderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder);
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<FolderResponseDto> updateFolder(@PathVariable Long folderId, @RequestBody FolderDto folderDto) {
        FolderResponseDto updatedFolder = folderService.updateFolder(folderId, folderDto);
        return ResponseEntity.ok(updatedFolder);
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FolderResponseDto>> getFoldersByUserId(@PathVariable Long userId) {
        List<FolderResponseDto> folders = folderService.getFoldersByUserId(userId);
        return ResponseEntity.ok(folders);
    }
}
