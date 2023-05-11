package com.example.textsearch.service;

import com.example.textsearch.model.Post;
import com.example.textsearch.model.PostReq;
import com.example.textsearch.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Please provide valid ID"));
    }

    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Cacheable(value = "posts", key = "#keyword")
    public Page<Post> search(String keyword, Pageable pageable) {
        return postRepository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, keyword, pageable);
    }
    @CacheEvict(value="posts", allEntries=true)
    public Post create(PostReq postReq) {
        Post post = new Post();

        if (Objects.isNull(postReq.getAuthor()) || postReq.getAuthor().isEmpty()) {
            throw new RuntimeException("Please provide valid Author Info");
        }
        if (Objects.isNull(postReq.getTitle()) || postReq.getTitle().isEmpty()) {
            throw new RuntimeException("Please provide valid Title");
        }
        if (Objects.isNull(postReq.getContent()) || postReq.getContent().isEmpty()) {
            throw new RuntimeException("Post Content can't be empty");
        }

        Post existingPost = postRepository.findByAuthorAndTitleAndContent(postReq.getAuthor(), postReq.getTitle(), postReq.getContent());
        if (!Objects.isNull(existingPost)) {
            throw new RuntimeException("Post Already Saved");
        }

        post.setAuthor(postReq.getAuthor());
        post.setTitle(postReq.getTitle());
        post.setContent(postReq.getContent());
        post.setCreatedAt(new Date());

        return postRepository.save(post);
    }

    @CacheEvict(value="posts", allEntries=true)
    public Post update(Long id, PostReq postReq) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Please provide valid ID"));

        if (!Objects.isNull(postReq.getAuthor()) && !postReq.getAuthor().isEmpty()) {
            post.setAuthor(postReq.getAuthor());
        }
        if (!Objects.isNull(postReq.getTitle()) && !postReq.getTitle().isEmpty()) {
            post.setTitle(postReq.getTitle());
        }
        if (!Objects.isNull(postReq.getContent()) && !postReq.getContent().isEmpty()) {
            post.setContent(postReq.getContent());
        }

        Post existingPost = postRepository.findByAuthorAndTitleAndContent(post.getAuthor(), post.getTitle(), post.getContent());
        if (!Objects.isNull(existingPost)) {
            throw new RuntimeException("Post Already exists with provided details, Please try with new details");
        }

        return postRepository.save(post);
    }


}
