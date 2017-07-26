package com.pauloamorim.apifile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.regex.Matcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApiFilesApplicationTests {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getFile() throws Exception {
    	 mockMvc.perform(get("/file"))
                 .andExpect(status().isOk());
    }
    @Test
    public void uploadFile() throws Exception {
    	MockMultipartFile firstFile = new MockMultipartFile("data", "testUpload.txt", "text/plain", "Test Upload File".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file/upload")
                        .file(firstFile)
                        .param("identification", "Paulo Amorim"))
                    .andExpect(status().is(200));
    }

}
