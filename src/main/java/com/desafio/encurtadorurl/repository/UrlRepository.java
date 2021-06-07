package com.desafio.encurtadorurl.repository;

import com.desafio.encurtadorurl.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findOneByShortUrl(String ShortUrl);
    boolean existsByShortUrl(String shorUrl);
}
