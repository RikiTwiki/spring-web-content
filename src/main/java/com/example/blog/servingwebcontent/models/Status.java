package com.example.blog.servingwebcontent.models;

import jakarta.persistence.*;

@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OneToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)

    private Post post;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    
    private boolean status = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Status() {
    }


    public Status(boolean status) {
        this.status = status;
    }
}
