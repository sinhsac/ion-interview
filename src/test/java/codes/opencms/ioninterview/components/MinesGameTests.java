package codes.opencms.ioninterview.components;

import codes.opencms.ioninterview.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
public class MinesGameTests {
    @Autowired
    private MockMvc mvc;

    @Value("classpath:mines/test1.json")
    Resource test1Res;

    @Value("classpath:mines/test2.json")
    Resource test2Res;

    @Value("classpath:mines/test3.json")
    Resource test3Res;

    @Test
    public void testBadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/minesweeper/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        assert true;
    }

    @Test
    public void testInvalidData() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/minesweeper/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{}"))
        ;
        assert true;
    }

    @Test
    public void testWithTestCase1() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/minesweeper/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(test1Res.getFile().toPath())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value("1, 2, 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1]").value("2, 1, 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").value("1, 1, 1"))
        ;
        assert true;
    }

    @Test
    public void testWithTestCase2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/minesweeper/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(test2Res.getFile().toPath())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value("0, 0, 0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1]").value("0, 0, 0"))
        ;
        assert true;
    }

    @Test
    public void testWithTestCase3() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/minesweeper/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(test3Res.getFile().toPath())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value("1, 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1]").value("2, 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").value("2, 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[3]").value("1, 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[4]").value("0, 0"))
        ;
        assert true;
    }
}
