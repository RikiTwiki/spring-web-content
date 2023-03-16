package com.example.blog.servingwebcontent.controllers;

import com.example.blog.servingwebcontent.models.Post;
import com.example.blog.servingwebcontent.models.Status;
import com.example.blog.servingwebcontent.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }


    // Логика лайков и диз



    @PostMapping("/blog/{id}/like")
    public String blogPostLike(@PathVariable(value = "id") long id, Status status, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        if (status.getStatus() == false) {
            for (int i = 0; i < 1; i++) {
                int count = post.getViews();
                count = count + 1;
                post.setViews(count);
            }
//            status.setStatus(true);
            if (status.getStatus() == true) {
                return "redirect:/blog/{id}";
            }

            postRepository.save(post);
            return "redirect:/blog/{id}";
        } else {
            return "redirect:/blog";
        }

    }


    @PostMapping("/blog/{id}/dislike")
    public String blogPostDislike(@PathVariable(value = "id") long id, Status status, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        if (status.getStatus() == false) {
            int count = post.getViews();
            count = count - 1;
            post.setViews(count);
            status.setStatus(true);
            if (post.getViews() < 0) {
                return "redirect:/blog/{id}";
            }
        } else {
            return "redirect:/blog";
        }
        postRepository.save(post);

        return "redirect:/blog/{id}";
    }

}
