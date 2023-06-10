package kr.ac.jejunu.diarymvc.folder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderDao extends JpaRepository<Folder, Long> {
}