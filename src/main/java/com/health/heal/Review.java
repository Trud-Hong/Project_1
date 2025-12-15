package com.health.heal;

import java.util.Date;

// 비밀번호 제거된 단순한 POJO 클래스
public class Review {

    private Long id;
    private String writer;
    private String content;
    private Date createdAt = new Date();

    // 기본 생성자
    public Review() {}

    // 생성자 (비밀번호 제거)
    public Review(String writer, String content) {
        this.writer = writer;
        this.content = content;
        this.createdAt = new Date();
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setter 메서드들
    public void setId(Long id) {
        this.id = id;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // 리뷰 수정 메서드 (비밀번호 체크 제거)
    public void updateReview(String content, String writer) {
        this.content = content;
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}