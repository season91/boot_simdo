package com.kh.simdo.movie;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_MOVIE")
public class Movie {

    @Id
    private String mvNo;
    private String mvTitle;
    private String mvTitleorg;
    private String director;
    private String genre;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(columnDefinition = "clob")
    private String plot;
    @Column(columnDefinition = "clob")
    private String script;

    private String nation;
    private String thumbnail;
    private String runtime;
    private String rating;
    private String poster;

    @Column(columnDefinition = "number default 0")
    private boolean isDel;

    @Override
    public String toString() {
        return "Movie{" +
                "mvNo='" + mvNo + '\'' +
                ", mvTitle='" + mvTitle + '\'' +
                ", mvTitleorg='" + mvTitleorg + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate=" + releaseDate +
                ", plot='" + plot + '\'' +
                ", script='" + script + '\'' +
                ", nation='" + nation + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", runtime='" + runtime + '\'' +
                ", rating='" + rating + '\'' +
                ", poster='" + poster + '\'' +
                ", isDel=" + isDel +
                '}';
    }
}
