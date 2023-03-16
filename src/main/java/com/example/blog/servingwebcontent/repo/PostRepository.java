package com.example.blog.servingwebcontent.repo;

import com.example.blog.servingwebcontent.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
