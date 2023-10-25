package com.korea.testblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    PostRepository postRepository;
    @RequestMapping("/")
    public String main(Model model){
        List<Post> postList = postRepository.findAll();
        model.addAttribute("postList",postList);
        model.addAttribute("targetPost",postList.get(0));
        return "main";
    }

    @PostMapping("/write")
    public String write(String title, String content){
        Post post = new Post();
        post.setTitle("new title");
        post.setContent("");
        post.setCreateDate(LocalDateTime.now());
        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(Long id, String title, String content){
        Post post = postRepository.findById(id).get();
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return "redirect:/detail/"+id;
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Long id) {
        Post post = postRepository.findById(id).get();
        model.addAttribute("postList",postRepository.findAll());
        model.addAttribute("targetPost",post);
        return "main";
    }
}
