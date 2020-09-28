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
public class CallLogsTests {
    @Autowired
    private MockMvc mvc;

    @Value("classpath:calllogs/test1.json")
    Resource test1Res;

    @Value("classpath:calllogs/test2.json")
    Resource test2Res;

    @Test
    public void testBadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/callLogs/analyse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        assert true;
    }

    @Test
    public void testInvalidData() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/callLogs/analyse")
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
                .post("/api/callLogs/analyse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(test1Res.getFile().toPath())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.unishop._count").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kyowon._count").value("1"))
        ;
        assert true;
    }

    @Test
    public void testWithTestCase2() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/callLogs/analyse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(test2Res.getFile().toPath())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.unishop._count").value("8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kyowon._count").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commerce._count").value("4"))
        ;
        assert true;
    }
}
