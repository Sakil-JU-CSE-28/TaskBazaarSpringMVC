package exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public Object handleValidationException(ValidationException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.error("Validation error: {}", ex.getMessage(), ex);
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        redirectAttributes.addFlashAttribute("errors", ex.getErrorDetails());
        if (request.getAttribute("post") != null) {
            redirectAttributes.addFlashAttribute("post", request.getAttribute("post"));
        }
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/posts/save")) {
            return new RedirectView("/posts/add", true);
        } else if (requestURI.contains("/posts/update")) {
            String postId = request.getParameter("id");
            return new RedirectView("/posts/edit/" + postId, true);
        }
        return new RedirectView("/posts", true);
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeExceptions(RuntimeException ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        log.error("Runtime error: {}", ex.getMessage(), ex);
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/register")) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/register";
        }
        request.setAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(DbException.class)
    public String handleDbException(DbException ex, Model model) {
        log.error("Database error: {}", ex.getMessage(), ex);
        model.addAttribute("message", "Internal Server Error");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        model.addAttribute("message", "An unexpected error occurred");
        return "error";
    }
}
