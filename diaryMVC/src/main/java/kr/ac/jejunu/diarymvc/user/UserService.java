package kr.ac.jejunu.diarymvc.user;

import kr.ac.jejunu.diarymvc.folder.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

    public UserService(UserRepository userRepository, FolderRepository folderRepository) {
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
    }

    public UserResponseDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User createdUser = userRepository.save(user);

        return new UserResponseDto(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail(), createdUser.getPassword());
    }


    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
