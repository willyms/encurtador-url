package com.desafio.encurtadorurl.service;

import com.desafio.encurtadorurl.exception.*;
import com.desafio.encurtadorurl.model.*;
import com.desafio.encurtadorurl.model.dto.*;
import com.desafio.encurtadorurl.repository.*;
import com.desafio.encurtadorurl.util.*;
import lombok.*;
import org.modelmapper.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.servlet.support.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public UrlDTO save(String strUrl) {
       Url url = Url.builder().createdAt(LocalDateTime.of(2021, Month.APRIL, 8, 12, 30)).url(strUrl).shortUrl(verifyShortUrl(generatingRandomString(new Random()))).build();
       return Optional.of(urlRepository.save(url)).map(this::toDTO).get();
    }

    @Transactional(readOnly = true)
    public List<UrlDTO> findAll() {
        return urlRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UrlDTO findByNewUrl(String shortUrl) {
        return urlRepository.findOneByShortUrl(shortUrl)
                        .map(this::toDTO)
                        .map(Optional::of)
                    .orElseThrow(NotFoundException::new)
                    .filter(UrlDTO::verifyExpirationDate)
                    .orElseThrow(ExpirationDateException::new);
    }

    private UrlDTO toDTO(Url url) {
        UrlDTO dto = modelMapper.map(url, UrlDTO.class);
        dto.setShortUrl(ServletUriComponentsBuilder.fromCurrentRequest().path("/{shortUrl}").buildAndExpand(dto.getShortUrl()).toString());
        dto.setExpirationdt(url.getCreatedAt().plusDays(UrlUtils.ADD_DAY_VALIDITY));
        return dto;
    }

    private String verifyShortUrl(String shorUrl) {
        if(urlRepository.existsByShortUrl(shorUrl))
            verifyShortUrl(generatingRandomString(new Random()));
        return shorUrl;
    }

    private String generatingRandomString(Random random){
        return  random.ints(UrlUtils.LIMIT_MIN, UrlUtils.LIMIT_MAX + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(UrlUtils.SHORT_URL_LENGTH)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
    }
}
