package com.kh.simdo.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    private String mvNo;

    private String mvTitle;
    private String mvTitleorg;
    private String mvScore;
    private String director;
    private String genre;
    private Date releaseDate;
    private String plot;
    private String nation;
    private String thumbnail;
    private String runtime;
    private String rating;
    private String poster;
    private int isDel;
}
