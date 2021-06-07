package com.desafio.encurtadorurl.model;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "urls")
public class Url {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "url", length = 512, nullable = false)
    private String url;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "shorturl", nullable = false)
    private String shortUrl;
}
