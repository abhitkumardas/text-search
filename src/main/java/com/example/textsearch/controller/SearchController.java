package com.example.textsearch.controller;

import com.example.textsearch.model.PostReq;
import com.example.textsearch.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("/posts")
public class SearchController {

    @Autowired
    private PostService postService;

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(postService.search(keyword, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody PostReq postReq) {
        return ResponseEntity.ok(postService.create(postReq));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostReq postReq) {
        return ResponseEntity.ok(postService.update(id, postReq));
    }

}
