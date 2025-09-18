package org.example.finalproject.controller;

import jakarta.validation.Valid;
import org.example.finalproject.entity.User;
import org.example.finalproject.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Views
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        return "user/list";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("user") User user, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("users", userRepository.findAll());
            return "user/list";
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }


    // REST (examples)
    @GetMapping("/api")
    @ResponseBody
    public List<User> apiAll() { return userRepository.findAll(); }

    @GetMapping("/api/{id}")
    public ResponseEntity<User> apiOne(@PathVariable Long id) {
        return userRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
