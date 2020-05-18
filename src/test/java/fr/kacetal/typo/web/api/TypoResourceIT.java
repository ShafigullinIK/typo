package fr.kacetal.typo.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kacetal.typo.domain.Typo;
import fr.kacetal.typo.repository.TypoRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static fr.kacetal.typo.domain.TypoStatus.REPORTED;
import static fr.kacetal.typo.web.WebConstants.TYPO_API_ROOT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TypoResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TypoRepository typoRepository;

    @Transactional
    @ParameterizedTest
    @MethodSource("fr.kacetal.typo.config.TestUtils#createTypo")
    public void createTypo(final Typo typo) throws Exception {
        final var contentAsString = mockMvc.perform(post(TYPO_API_ROOT)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(typo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.pageUrl", is(typo.getPageUrl())))
                .andExpect(jsonPath("$.reporterName", is(typo.getReporterName())))
                .andExpect(jsonPath("$.reporterRemark", is(typo.getReporterRemark())))
                .andExpect(jsonPath("$.textBeforeTypo", is(typo.getTextBeforeTypo())))
                .andExpect(jsonPath("$.textTypo", is(typo.getTextTypo())))
                .andExpect(jsonPath("$.textAfterTypo", is(typo.getTextAfterTypo())))
                .andExpect(jsonPath("$.typoStatus", is(REPORTED.toString())))
                .andReturn().getResponse().getContentAsString();
        final var savedTypo = objectMapper.readValue(contentAsString, Typo.class);
        assertThat(typoRepository.existsById(savedTypo.getId())).isTrue();
    }
}
