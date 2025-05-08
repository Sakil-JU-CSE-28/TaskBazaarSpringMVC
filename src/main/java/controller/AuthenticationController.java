package controller;

import dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapper.UserMapper;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.RoleService;
import service.UserService;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("availableRoles", roleService.getAll());
        model.addAttribute("user", new UserDto());
        return "register-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("Validating user for register: {}", userDto);
        if (bindingResult.hasErrors()) {
            log.info("Validation errors: {}", bindingResult.getAllErrors());
            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match");
            return "register-form";
        }
        User user = userMapper.toUser(userDto);
        userService.save(user, userDto.getRole());
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login with your credentials.");
        return "redirect:/login-form";
    }
}