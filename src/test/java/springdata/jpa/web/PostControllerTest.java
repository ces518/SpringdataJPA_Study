package springdata.jpa.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springdata.jpa.repository.PostRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    PostRepository posts;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getPosts() throws Exception {
        this.mockMvc.perform(get("/posts")
                            .param("page","0")
                            .param("size","10")
                            .param("sort","created,desc")
                            .param("sort","title"))
                    .andDo(print())
                    .andExpect(status().isOk());
    }
}