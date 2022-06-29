package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void post_Film_correct_Test() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Film_1\",\"description\":\"Description Film 1\"," +
                                "\"releaseDate\":\"1985-10-30\",\"duration\":150}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Film_2\",\"description\":\"Description Film 2\"," +
                                "\"releaseDate\":\"1995-10-30\",\"duration\":100}"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void post_Film_Empty_Name_Test() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Description Film 1\"," +
                                "\"releaseDate\":\"1985-10-30\",\"duration\":150}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    void post_Film_wrong_description_length_Test() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Film_1\",\"description\":\"Description Film 1 : Description Film 1 _" +
                                " Description Film 1 _ Description Film 1 _ Description Film 1 _ Description Film 1 _" +
                                " Description Film 1 _ Description Film 1 _ Description Film 1 _ Description Film 1 _" +
                                " Description Film 1 _ Description Film 1 \"," +
                                "\"releaseDate\":\"1985-10-30\",\"duration\":150}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void post_Film_Negative_Duration_Test() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Film_1\",\"description\":\"Description Film 1\"," +
                                "\"releaseDate\":\"1985-10-30\",\"duration\":-10}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void post_Film_Zero_Duration_Test() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Film_1\",\"description\":\"Description Film 1\"," +
                                "\"releaseDate\":\"1985-10-30\",\"duration\":0}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void post_Film_wrong_Date_Test() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Film_1\",\"description\":\"Description Film 1\"," +
                                "\"releaseDate\":\"1895-12-27\",\"duration\":100}"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void post_Film_negative_id_Test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":-1,\"name\":\"Film_1\",\"description\":\"Description Film 1\"," +
                                "\"releaseDate\":\"1985-10-30\",\"duration\":100}"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

}