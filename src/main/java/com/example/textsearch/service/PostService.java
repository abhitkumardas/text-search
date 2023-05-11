package com.example.textsearch.service;

import com.example.textsearch.model.Post;
import com.example.textsearch.model.PostReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    public Page<Post> search(String keyword, Pageable pageable);
    public Post create(PostReq postReq);
    public Post update(Long id, PostReq postReq);
}
