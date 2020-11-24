package com.example.diary;

/**对应diary表的实体类*/
public class Note {
    private Integer id;
    private String tittle;
    private String content;
    private String date;
    private String author;

    public Note() {
    }

    public Note(int id,String tittle, String content, String date, String author) {
        this.id=id;
        this.tittle = tittle;
        this.content = content;
        this.date = date;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getTittle() {
        return tittle;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
