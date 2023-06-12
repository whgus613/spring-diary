package kr.ac.jejunu.diarymvc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@EnableWebSecurity
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("userDto") UserDto userDto, RedirectAttributes redirectAttributes, Model model) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        boolean isAuthenticated = userService.authenticateUser(email, password);
        if (isAuthenticated) {
            Long userId = userService.getUserIdByEmail(email);

            return "redirect:/folders/list/" + userId;
        } else {
            model.addAttribute("errorMessage", "로그인에 실패하셨습니다.");
            return "login";
        }
    }



}
