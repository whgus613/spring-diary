package kr.ac.jejunu.diarymvc.folder;

import kr.ac.jejunu.diarymvc.user.User;
import kr.ac.jejunu.diarymvc.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    public FolderService(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    public FolderResponseDto createFolder(Long userId, FolderDto folderDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        Folder folder = new Folder();
        folder.setName(folderDto.getName());
        folder.setUser(user);

        Folder createdFolder = folderRepository.save(folder);

        return new FolderResponseDto(createdFolder.getName(), createdFolder.getUser().getId());
    }

    public FolderResponseDto updateFolder(Long folderId, FolderDto updatedFolderDto) {
        Folder existingFolder = folderRepository.findById(folderId)
                .orElseThrow(() -> new NoSuchElementException("Folder not found with id: " + folderId));

        existingFolder.setName(updatedFolderDto.getName());

        Folder updatedFolder = folderRepository.save(existingFolder);

        return new FolderResponseDto(updatedFolder.getName(), updatedFolder.getUser().getId());
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }

    public List<FolderResponseDto> getFoldersByUserId(Long userId) {
        List<Folder> folders = folderRepository.findByUserId(userId);
        return folders.stream()
                .map(folder -> new FolderResponseDto(folder.getName(), folder.getUser().getId()))
                .collect(Collectors.toList());
    }
}
