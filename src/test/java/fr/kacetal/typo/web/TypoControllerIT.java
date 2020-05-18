package fr.kacetal.typo.web;

import fr.kacetal.typo.domain.Typo;
import fr.kacetal.typo.repository.TypoRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static fr.kacetal.typo.domain.TypoStatus.CANCELED;
import static fr.kacetal.typo.domain.TypoStatus.REPORTED;
import static fr.kacetal.typo.web.WebConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:data.sql")
class TypoControllerIT {

    @Autowired
    private TypoRepository typoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTypos() throws Exception {
        mockMvc.perform(get(TYPO_ROOT))
                .andExpect(status().isOk())
                .andExpect(view().name(TYPO_LIST_PAGE))
                .andExpect(model().attribute("typos", typoRepository.findAll()));
    }

    @Test
    public void getTypoById() throws Exception {
        final var typo = typoRepository.findAll().stream().findAny().orElseThrow();
        final var typoId = typo.getId();
        mockMvc.perform(get(TYPO_ROOT + "/{id}", typoId))
                .andExpect(status().isOk())
                .andExpect(view().name(TYPO_VIEW_PAGE))
                .andExpect(model().attribute("typo", typoRepository.findById(typoId).orElseThrow()))
                .andExpect(model().attribute("typo", hasProperty("id", is(typoId))))
                .andExpect(content().string(containsString("<div class=\"card-footer text-muted\">ID: " + htmlEscape(typoId.toString()) + "</div>")))
                .andExpect(model().attribute("typo", hasProperty("pageUrl", is(typo.getPageUrl()))))
                .andExpect(content().string(containsString("<div class=\"card-header\"><a href=\"" + htmlEscape(typo.getPageUrl()) + "\">Page Url</a></div>")))
                .andExpect(model().attribute("typo", hasProperty("reporterName", is(typo.getReporterName()))))
                .andExpect(content().string(containsString("<h5 class=\"card-title\">Reported by " + htmlEscape(typo.getReporterName()) + "</h5>")))
                .andExpect(model().attribute("typo", hasProperty("reporterRemark", is(typo.getReporterRemark()))))
                .andExpect(content().string(containsString("<p class=\"card-text\">" + htmlEscape(typo.getReporterRemark()) + "</p>")))
                .andExpect(model().attribute("typo", hasProperty("textBeforeTypo", is(typo.getTextBeforeTypo()))))
                .andExpect(content().string(containsString("<span>" + htmlEscape(typo.getTextBeforeTypo()) + "</span>")))
                .andExpect(model().attribute("typo", hasProperty("textTypo", is(typo.getTextTypo()))))
                .andExpect(content().string(containsString("<span class=\"text-danger\">" + htmlEscape(typo.getTextTypo()) + "</span>")))
                .andExpect(model().attribute("typo", hasProperty("textAfterTypo", is(typo.getTextAfterTypo()))))
                .andExpect(content().string(containsString("<span>" + htmlEscape(typo.getTextAfterTypo()) + "</span>")))
                .andExpect(model().attribute("typo", hasProperty("typoStatus", is(typo.getTypoStatus()))))
                .andExpect(content().string(containsString("<div class=\"card-header\">" + htmlEscape(typo.getTypoStatus().toString()) + "</div>")));
    }

    @Test
    public void getCreatePage() throws Exception {
        mockMvc.perform(get(TYPO_ROOT + "/create"))
                .andExpect(status().isOk())
                .andExpect(view().name(TYPO_CREATE_PAGE))
                .andExpect(model().attribute("typo", hasProperty("id", nullValue())))
                .andExpect(model().attribute("typo", hasProperty("pageUrl", nullValue())))
                .andExpect(model().attribute("typo", hasProperty("reporterName", nullValue())))
                .andExpect(model().attribute("typo", hasProperty("reporterRemark", nullValue())))
                .andExpect(model().attribute("typo", hasProperty("textBeforeTypo", nullValue())))
                .andExpect(model().attribute("typo", hasProperty("textTypo", nullValue())))
                .andExpect(model().attribute("typo", hasProperty("textAfterTypo", nullValue())))
                .andExpect(model().attribute("typo", hasProperty("typoStatus", is(REPORTED))));
    }

    @Test
    public void getUpdatePage() throws Exception {
        final var typo = typoRepository.findAll().stream().findAny().orElseThrow();
        final var typoId = typo.getId();
        mockMvc.perform(get(TYPO_ROOT + "/{id}/update", typoId))
                .andExpect(status().isOk())
                .andExpect(view().name(TYPO_UPDATE_PAGE))
                .andExpect(model().attribute("typo", hasProperty("id", is(typo.getId()))))
                .andExpect(model().attribute("typo", hasProperty("pageUrl", is(typo.getPageUrl()))))
                .andExpect(model().attribute("typo", hasProperty("reporterName", is(typo.getReporterName()))))
                .andExpect(model().attribute("typo", hasProperty("reporterRemark", is(typo.getReporterRemark()))))
                .andExpect(model().attribute("typo", hasProperty("textBeforeTypo", is(typo.getTextBeforeTypo()))))
                .andExpect(model().attribute("typo", hasProperty("textTypo", is(typo.getTextTypo()))))
                .andExpect(model().attribute("typo", hasProperty("textAfterTypo", is(typo.getTextAfterTypo()))))
                .andExpect(model().attribute("typo", hasProperty("typoStatus", is(typo.getTypoStatus()))));
    }

    @ParameterizedTest
    @MethodSource("fr.kacetal.typo.config.TestUtils#createTypo")
    public void createTypo(final Typo typo) throws Exception {
        final var location = mockMvc.perform(post(TYPO_ROOT)
                .param("pageUrl", typo.getPageUrl())
                .param("reporterName", typo.getReporterName())
                .param("reporterRemark", typo.getReporterRemark())
                .param("textBeforeTypo", typo.getTextBeforeTypo())
                .param("textTypo", typo.getTextTypo())
                .param("textAfterTypo", typo.getTextAfterTypo())
                .param("typoStatus", typo.getTypoStatus().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");
        assertThat(location).isNotNull();
        final var typoId = Long.valueOf(location.substring((TYPO_ROOT + "/").length()));
        assertThat(typoRepository.findById(typoId)).isPresent().get()
                .hasFieldOrPropertyWithValue("pageUrl", typo.getPageUrl())
                .hasFieldOrPropertyWithValue("reporterName", typo.getReporterName())
                .hasFieldOrPropertyWithValue("reporterRemark", typo.getReporterRemark())
                .hasFieldOrPropertyWithValue("textBeforeTypo", typo.getTextBeforeTypo())
                .hasFieldOrPropertyWithValue("textTypo", typo.getTextTypo())
                .hasFieldOrPropertyWithValue("textAfterTypo", typo.getTextAfterTypo())
                .hasFieldOrPropertyWithValue("typoStatus", typo.getTypoStatus());
    }

    @Test
    public void updateTypo() throws Exception {
        final var typo = typoRepository.findAll().stream().findAny().orElseThrow();
        final var typoId = typo.getId();
        final var updatedStr = RandomString.make(10);
        mockMvc.perform(put(TYPO_ROOT)
                .param("id", typoId.toString())
                .param("pageUrl", typo.getPageUrl() + updatedStr)
                .param("reporterName", typo.getReporterName() + updatedStr)
                .param("reporterRemark", typo.getReporterRemark() + updatedStr)
                .param("textBeforeTypo", typo.getTextBeforeTypo() + updatedStr)
                .param("textTypo", typo.getTextTypo() + updatedStr)
                .param("textAfterTypo", typo.getTextAfterTypo() + updatedStr)
                .param("typoStatus", CANCELED.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", TYPO_ROOT + "/" + typoId));
        final var mayBeTypo = typoRepository.findById(typoId);
        assertThat(mayBeTypo).isPresent();
        final var updatedTypo = mayBeTypo.get();
        assertThat(updatedTypo.getPageUrl()).endsWith(updatedStr);
        assertThat(updatedTypo.getReporterName()).endsWith(updatedStr);
        assertThat(updatedTypo.getReporterRemark()).endsWith(updatedStr);
        assertThat(updatedTypo.getTextBeforeTypo()).endsWith(updatedStr);
        assertThat(updatedTypo.getTextTypo()).endsWith(updatedStr);
        assertThat(updatedTypo.getTextAfterTypo()).endsWith(updatedStr);
        assertThat(updatedTypo.getTypoStatus()).isEqualTo(CANCELED);
    }

    @Test
    public void deleteTypoById() throws Exception {
        final var typoId = typoRepository.findAll().stream().findAny().orElseThrow().getId();
        mockMvc.perform(delete(TYPO_ROOT + "/{id}", typoId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_TO_TYPO_ROOT));
        assertThat(typoRepository.existsById(typoId)).isFalse();
    }
}
