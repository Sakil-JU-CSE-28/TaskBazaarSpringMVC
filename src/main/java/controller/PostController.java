package controller;

import dto.PostDto;
import exception.DbException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.PostService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getAll(Model model, HttpServletRequest request) {
        List<PostDto> posts = postService.getAll();
        model.addAttribute("posts", posts);

        if (request.getSession().getAttribute("successMessage") != null) {
            model.addAttribute("successMessage", request.getSession().getAttribute("successMessage"));
            request.getSession().removeAttribute("successMessage");
        }

        if (request.getSession().getAttribute("errorMessage") != null) {
            model.addAttribute("errorMessage", request.getSession().getAttribute("errorMessage"));
            request.getSession().removeAttribute("errorMessage");
        }

        return "list-posts";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("post", new PostDto());
        return "add-post";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('BUYER')")
    public String save(@Valid @ModelAttribute("post") PostDto post, BindingResult bindingResult, Model model) throws DbException {

        if (bindingResult.hasErrors()) {
            log.info("Validation errors on create post: {}", bindingResult.getAllErrors());
            model.addAttribute("post", post);
            return "add-post";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        post.setAuthor(authentication.getName());
        postService.save(post);
        log.info("Saved post with id: {}", post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('BUYER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<PostDto> post = postService.getById(id);
        log.info("Editing post: {}", post);
        model.addAttribute("post", post);
        return "edit-post";
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('BUYER')")
    public String update(@Valid @ModelAttribute PostDto post, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("Validation errors in update post: {}", bindingResult.getAllErrors());
            model.addAttribute("post", post);
            return "edit-post";
        }
        postService.update(post);
        log.info("Updated post : {}", post);
        return "redirect:/posts";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteById(@PathVariable Long id, Model model) throws DbException {
        postService.deleteById(id);
        log.info("Deleted post with id: {}", id);
        return "redirect:/posts";
    }

    @GetMapping("get/{id}")
    public String getById(@PathVariable Long id, Model model) {
        Optional<PostDto> post = postService.getById(id);
        log.info("Getting post with id: {}", id);
        model.addAttribute("post", post);
        return "view-post";
    }
}