package org.example.finalproject.controller;

import jakarta.validation.Valid;
import org.example.finalproject.entity.Post;
import org.example.finalproject.entity.User;
import org.example.finalproject.repository.PostRepository;
import org.example.finalproject.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("post", new Post());
        return "post/list";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("post") Post post, BindingResult br,
                         @RequestParam("userId") Long userId, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("posts", postRepository.findAll());
            model.addAttribute("users", userRepository.findAll());
            return "post/list";
        }
        User u = userRepository.findById(userId).orElse(null);
        if (u != null) post.setUser(u);
        postRepository.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        postRepository.deleteById(id);
        return "redirect:/posts";
    }


    // REST (examples)
    @GetMapping("/api")
    @ResponseBody
    public List<Post> apiAll() { return postRepository.findAll(); }

    @GetMapping("/api/{id}")
    public ResponseEntity<Post> apiOne(@PathVariable Long id) {
        return postRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
