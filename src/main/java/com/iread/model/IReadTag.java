package com.iread.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class IReadTag {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @SequenceGenerator(name = "PKIReadTags", sequenceName = "SEQ_TAGS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PKIReadTags")
    private Long id;

    @Column(name = "TAG_NAME", length = 100, nullable = false)
    private String tagName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
