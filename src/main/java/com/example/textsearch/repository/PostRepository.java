package com.example.textsearch.repository;

import com.example.textsearch.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String authorKeyword, String titleKeyword, String contentKeyword, Pageable pageable);

    Post findByAuthorAndTitleAndContent(String author, String title, String content);
}
