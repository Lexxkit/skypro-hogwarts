package com.lexxkit.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexxkit.hogwarts.school.model.Avatar;
import com.lexxkit.hogwarts.school.repository.AvatarRepository;
import com.lexxkit.hogwarts.school.service.AvatarService;
import com.lexxkit.hogwarts.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AvatarController.class)
public class AvatarControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private AvatarController avatarController;

    @Test
    void getAllAvatarsWithPaging() throws Exception {
        Avatar avatar = new Avatar();
        avatar.setId(1L);
        avatar.setMediaType(MediaType.MULTIPART_FORM_DATA_VALUE);

        Page<Avatar> avatarPage = new PageImpl<>(List.of(avatar));

        when(avatarRepository.findAll(any(Pageable.class)))
                .thenReturn(avatarPage);

        mockMvc.perform(get("/avatars")
                        .queryParam("page", "1")
                        .queryParam("size", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(avatar))));
    }
}
