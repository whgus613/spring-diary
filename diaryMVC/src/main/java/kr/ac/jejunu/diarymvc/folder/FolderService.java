package kr.ac.jejunu.diarymvc.folder;

import kr.ac.jejunu.diarymvc.diary.Diary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FolderService {
    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public Folder createFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    public Folder updateFolder(Long folderId, Folder updatedFolder) {
        Folder existingFolder = folderRepository.findById(folderId)
                .orElseThrow(() -> new NoSuchElementException("Folder not found with id: " + folderId));

        existingFolder.setName(updatedFolder.getName());

        return folderRepository.save(existingFolder);
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }

    public List<Diary> getFolderDiaries(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new NoSuchElementException("Folder not found with id: " + folderId));

        return folder.getDiaries();
    }

}
