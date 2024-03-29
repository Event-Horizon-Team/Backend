package com.EventHorizon.EventHorizon.Controllers.UserControllers;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.EventHorizon.EventHorizon.DTOs.UserDto.InformationDTO;
import com.EventHorizon.EventHorizon.Entities.UserEntities.Information;
import com.EventHorizon.EventHorizon.Entities.enums.Gender;
import com.EventHorizon.EventHorizon.Entities.enums.Role;
import com.EventHorizon.EventHorizon.Services.UserServices.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private AdminService adminService;

    @Test
    void testAddModerator() throws Exception {
        // Arrange
        Information information = new Information();
        information.setActive(1);
        information.setEmail("jane.doe@example.org");
        information.setEnable(1);
        information.setFirstName("Jane");
        information.setGender(Gender.MALE);
        information.setId(1);
        information.setLastName("Doe");
        information.setPassword("iloveyou");
        information.setPayPalAccount("3");
        information.setRole(Role.CLIENT);
        information.setSignInWithEmail(1);
        information.setUserName("janedoe");
        when(adminService.addModerator(Mockito.<InformationDTO>any())).thenReturn(information);

        InformationDTO informationDTO = new InformationDTO();
        informationDTO.setEmail("jane.doe@example.org");
        informationDTO.setFirstName("Jane");
        informationDTO.setGender("Gender");
        informationDTO.setId(1);
        informationDTO.setLastName("Doe");
        informationDTO.setPassword("iloveyou");
        informationDTO.setPayPalAccount("3");
        informationDTO.setRole("Role");
        informationDTO.setSignInWithEmail(1);
        informationDTO.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(informationDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/addModerator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteModerator() throws Exception {
        // Arrange
        doNothing().when(adminService).deleteModerator(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/admin/deleteModerator");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("idOfModerator", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
