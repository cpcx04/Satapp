package triana.salesianos.edu.SataApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.query.sqm.tree.SqmNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import triana.salesianos.edu.SataApp.dto.inventory.GetInventoryDto;
import triana.salesianos.edu.SataApp.model.*;
import triana.salesianos.edu.SataApp.service.InventoryService;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        {SprinBootSecurityTestWebConfiguration.class})
@AutoConfigureMockMvc
public class InventoryControllerWithSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InventoryService inventoryService;

    @MockBean
    private GetInventoryDto getInventoryDto;

    InventoryItems inventoryItems;

    GetInventoryDto inventoryDto;

    @BeforeEach
    void initTest(){
        UserWorker u1 = UserWorker.builder()
                .id(UUID.fromString("9a98b20f-8f24-4647-af45-45b0bfe63a11"))
                .username("admin1")
                .role(Function.ADMIN)
                .validated(true)
                .build();

        Ticket ticket1= Ticket.builder()
                .id(UUID.fromString("5fb05a52-eb6d-4d34-9e8d-98e6d01472fc"))
                .assignedTo(u1)
                .createdBy(u1)
                .build();

        Location l1 = Location.builder()
                .id(UUID.fromString("b742b204-406f-4f65-8e6e-cbc52f8c494e"))
                .nombre("1DAM")
                .pasillo("3")
                .planta("3")
                .build();

        Type type1= Type.builder()
                .id(UUID.fromString("3f0190ac-ebef-4fc2-99c9-5d44016da63a"))
                .price(200)
                .cedido(false)
                .type(EquipmentType.valueOf("DESKTOP"))
                .build();

        GetInventoryDto i1= GetInventoryDto.of(InventoryItems.builder()
                .id(UUID.fromString("3f0190ac-ebef-4fc2-99c9-5d44016da63a"))
                .description("So cool")
                .type(type1)
                .additionalDetails("Nothing")
                .location(l1)
                .relatedTickets(List.of(ticket1))
                .build());

    }

    @Test
    @WithUserDetails(value="admin")
    @DisplayName("GET /inventariable")
    void whenValidRequestWithAdmin_thenReturn200() throws Exception{
        GetInventoryDto mainModel =  Mockito.mock(GetInventoryDto.class);
        List<GetInventoryDto>invetoryList= inventoryService.findAll();
        when(mainModel.getClass()).thenReturn(invetoryList);

            MvcResult result = mockMvc.perform(get("/inventariable").with(httpBasic("admin1","admin1")))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(inventoryDto)))
                    .andReturn();

            SqmNode.log.info(result.getResponse().getContentAsString());
    }
}
