package com.desafio.encurtadorurl;

import com.desafio.encurtadorurl.controller.*;
import com.desafio.encurtadorurl.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;

import java.util.stream.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EncurtadorUrlApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /urls - Success")
	@Sql(
		 statements = {"insert into urls (createdat, shorturl, url, id) values ('2021-06-10', 'rFR1dibPVq', 'https://olhardigital.com.br/', 100)",
				      "insert into urls (createdat, shorturl, url, id) values ('2021-06-01', 'sOC0tTYUa2', 'https://www.amazon.com.br/', 101)"},
		 executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Sql(statements = "delete from urls where id in (100, 101)", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void httpGetListSuccess() throws Exception {
		mockMvc.perform(get("/urls")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*").isArray())
				.andExpect(jsonPath("$.*.url", hasItem("https://olhardigital.com.br/")))
				.andExpect(jsonPath("$.*.url", hasItem("https://www.amazon.com.br/")));
	}

	@DisplayName("POST /urls - Created")
	@ParameterizedTest(name = "{index} - {0}")
	@ValueSource(strings = {"https://www.baeldung.com/", "https://github.com/", "https://mkyong.com/"})
	void httpPostCreateUrl(String url) throws Exception {
		mockMvc.perform(post("/urls")
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.content(String.format("{\"url\": \"%s\"}", url))
						).andDo(print())
						 .andExpect(status().isCreated())
						 .andExpect(header().string(HttpHeaders.LOCATION, matchesPattern("http://localhost/urls/[a-zA-Z0-9]{10}+$")));
	}

	@MethodSource
	@DisplayName("GET /urls/{shortUrl} - Redirect")
	@ParameterizedTest(name = "{index} - /urls/{0}")
	@Sql(
			statements = {"insert into urls (createdat, shorturl, url, id) values ('2021-06-10', 'xdqzgQ5N9s', 'https://www.google.com/search?q=longest+url+length&rlz=1C1GCEA_enBR912BR912&oq=url+length+long&aqs=chrome.1.69i57j0i22i30l2.11939j1j7&sourceid=chrome&ie=UTF-8', 102)",
					     "insert into urls (createdat, shorturl, url, id) values ('2021-06-10', 'hQYdTXR2Tf', 'https://www.magazineluiza.com.br/?partner_id=974&gclid=Cj0KCQjwh_eFBhDZARIsALHjIKcGb33z9vPPP0B2JTy46gNLeL_ngi45nkHq_rC6nqh5f6zzBJ4n1EkaAjx9EALw_wcB', 103)"},
			executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
	)
	@Sql(statements = "delete from urls where id in (102, 103)", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void httpGetShortUrlThenRedirectToUrlOrigin(String shortUrl, String urlOrigin) throws Exception {
		mockMvc.perform(get("/urls/{shortUrl}", shortUrl))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string(HttpHeaders.LOCATION, is(urlOrigin)));

	}

	private static Stream<Arguments> httpGetShortUrlThenRedirectToUrlOrigin() {
		return Stream.of(
				Arguments.of("xdqzgQ5N9s", "https://www.google.com/search?q=longest+url+length&rlz=1C1GCEA_enBR912BR912&oq=url+length+long&aqs=chrome.1.69i57j0i22i30l2.11939j1j7&sourceid=chrome&ie=UTF-8"),
				Arguments.of("hQYdTXR2Tf", "https://www.magazineluiza.com.br/?partner_id=974&gclid=Cj0KCQjwh_eFBhDZARIsALHjIKcGb33z9vPPP0B2JTy46gNLeL_ngi45nkHq_rC6nqh5f6zzBJ4n1EkaAjx9EALw_wcB")
		);
	}

	@Test
	@DisplayName("GET /urls/{shortUrl} - Not Found")
	void httpGetUrlByShortUrlNotFound() throws Exception {
		mockMvc.perform(get("/urls/{shortUrl}", "aAaAaAaAaA"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is(UrlUtils.NO_RECORDS_FOUND)));
	}

	@Test
	@DisplayName("GET /urls/{shortUrl} - Date Expired")
	@Sql(statements = "insert into urls (createdat, shorturl, url, id) values ('2021-01-10', 'vfrc52k0Oh', 'https://reflectoring.io/', 104)",
		 executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete from urls where id = 104", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void httpGetUrlByShortUrlDateExpired() throws Exception {
		mockMvc.perform(get("/urls/{shortUrl}", "vfrc52k0Oh"))
				.andExpect(status().isPreconditionFailed())
				.andExpect(jsonPath("$.message", is(UrlUtils.DATE_EXPIRED)));
	}

	@Test
	@DisplayName("GET / - Redirect To Swagger")
	void accessRootThenRedirectToSwagger() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("/swagger-ui/**"));
	}

}
