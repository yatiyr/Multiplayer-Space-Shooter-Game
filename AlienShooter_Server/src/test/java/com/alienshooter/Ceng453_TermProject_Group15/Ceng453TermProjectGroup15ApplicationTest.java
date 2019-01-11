package com.alienshooter.Ceng453_TermProject_Group15;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class) This created a problem which I did not understand
@SpringBootTest
@AutoConfigureMockMvc
public class Ceng453TermProjectGroup15ApplicationTest {

    @Autowired
    private MockMvc mockMvc;



    String user1_register="{\"name\": \"Koksal_Baba\", \"password\": \"yakamibirak\"}";
    String user2_register="{\"name\": \"Obayana\", \"password\": \"obayana123\"}";
    String user3_register="{\"name\": \"Cayci_Kenan\", \"password\": \"cayci_kenan123\"}";
    String user4_register="{\"name\": \"Osman_Aga\", \"password\": \"osman123\"}";
    String user5_register="{\"name\": \"Husnu_Coban\", \"password\": \"yemektenevarsuat\"}";
    String user6_register="{\"name\": \"Gulubik\", \"password\": \"gidelimsinancim\"}";
    String user7_register="{\"name\": \"Ali_Kefal\", \"password\": \"tabinuripapacium\"}";
    String user8_register="{\"name\": \"Light_Selami\", \"password\": \"tamamabicim\"}";
    String user9_register="{\"name\": \"Zero_Tuna\", \"password\": \"zerotuna123\"}";
    String user10_register="{\"name\": \"Tasfirin_Haluk\", \"password\": \"bababababa\"}";
    String user11_register="{\"name\": \"Buyuk_Hilmi\", \"password\": \"bideboyledusun\"}";
    String user12_register="{\"name\": \"Eleni_Hanim\", \"password\": \"nazlimu\"}";
    String user13_register="{\"name\": \"Gahraman_Abi\", \"password\": \"banabisiyoliyheyriye\"}";
    String user14_register="{\"name\": \"Efe_Karahanli\", \"password\": \"efekarahanli\"}";
    String user15_register="{\"name\": \"Iskender_Buyuk\", \"password\": \"isitmiyormusunbeni!\"}";
    String user16_register="{\"name\": \"Hayriye_Hanim\", \"password\": \"tadimizkacmasinalirizabey\"}";
    String user17_register="{\"name\": \"Kemal_Boratav\", \"password\": \"GuzelIsimdirKemal\"}";
    String user18_register="{\"name\": \"Tiriniri_Sinan\", \"password\": \"kaygara\"}";

    String signinsuccess = "{\"name\": \"Tiriniri_Sinan\", \"password\": \"kaygara\"}";
    String signinfail = "{\"name\": \"Tiriniri\", \"password\": \"kaygara\"}";
    String signinfail2 = "{\"name\": \"Tiriniri_Sinan\", \"password\": \"kaygara__\"}";

    String shortpasswordregister = "{ \"name\": \"Viper_Nuri\", \"password\": \"123\" }";

    String update_score1 = "{\"user_id\": \"1\", \"score\": \"100\", \"date\": \"2018-08-14\" , \"username\": \"Koksal_Baba\" }";
    String update_score2 = "{\"user_id\": \"2\", \"score\": \"200\", \"date\": \"2018-09-14\" , \"username\": \"Obayana\" }";
    String update_score3 = "{\"user_id\": \"3\", \"score\": \"300\", \"date\": \"2018-10-14\" , \"username\": \"Cayci_Kenan\" }";
    String update_score4 = "{\"user_id\": \"4\", \"score\": \"400\", \"date\": \"2018-11-14\" , \"username\": \"Osman_Aga\" }";
    String update_score5 = "{\"user_id\": \"5\", \"score\": \"500\", \"date\": \"2018-11-15\" , \"username\": \"Husnu_Coban\"}";


    String successfuldelete = "{\"name\": \"Eleni_Hanim\", \"password\": \"nazlimu\"}";
    String trytodeletenotinthelist ="{\"name\": \"Tiriniri\", \"password\": \"kaygara!\"}";
    String trytodeletewrongpassword = "{\"name\": \"Iskender_Buyuk\", \"password\": \"benimadimiskenderbuyuk\"}";

    String updatenamewrongpassword = "{\"id\": \"10\",\"name\": \"Havuckafa\", \"password\": \"efekarahanli\"}";
    String updatenamesuccessful = "{\"id\": \"14\",\"name\": \"Polat_Alemdar\", \"password\": \"efekarahanli\"}";
    String updatenamewrongname = "{\"id\": \"15\",\"name\": \"Tiriniri_Sinan\", \"password\": \"isitmiyormusunbeni!\"}";


    String updatepasswordsuccessful = "{\"id\": \"17\",\"name\": \"Kemal_Boratav\", \"password\": \"I'M_HERE_FOR_THE_RECKONING!!!!\"}";
    String updatepasswordwrongname = "{\"id\": \"15\",\"name\": \"Kutsal_Damacana\", \"password\": \"isitmiyormusunbeni!\"}";
    //String updatepasswordwrongpassword = "{\"id\": \"15\",\"name\": \"Kutsal_Damacana\", \"password\": \"isitmiyormusunbeni!\"}"; This ability is removed from the project.I decided to check password in client side.

    @Test
    void Test_Complete() throws Exception
    {
        /*
        Test registration
         */

        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user1_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user2_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user3_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user4_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user5_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user6_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user7_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user8_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user9_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user10_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user11_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user12_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user13_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user14_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user15_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user16_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user17_register))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user18_register))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(shortpasswordregister))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Your password needs to be at least 6 characters length"));

        mockMvc.perform(MockMvcRequestBuilders.post("/registration").contentType(MediaType.APPLICATION_JSON).content(user1_register))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("There is already a user with that name"));


        /*
        Test Sign_In
         */

        mockMvc.perform(MockMvcRequestBuilders.post("/sign_in").contentType(MediaType.APPLICATION_JSON).content(signinsuccess))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/sign_in").contentType(MediaType.APPLICATION_JSON).content(signinfail))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/sign_in").contentType(MediaType.APPLICATION_JSON).content(signinfail2))
                .andExpect(status().isBadRequest());

        /*
        Test Update_Score
         */

        mockMvc.perform(MockMvcRequestBuilders.post("/update_score").contentType(MediaType.APPLICATION_JSON).content(update_score1))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/update_score").contentType(MediaType.APPLICATION_JSON).content(update_score2))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/update_score").contentType(MediaType.APPLICATION_JSON).content(update_score3))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/update_score").contentType(MediaType.APPLICATION_JSON).content(update_score4))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/update_score").contentType(MediaType.APPLICATION_JSON).content(update_score5))
                .andExpect(status().isOk());

        /*
        Test Delete_User
         */

        mockMvc.perform(MockMvcRequestBuilders.post("/delete_user").contentType(MediaType.APPLICATION_JSON).content(successfuldelete))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/delete_user").contentType(MediaType.APPLICATION_JSON).content(trytodeletewrongpassword))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("wrong password"));
        mockMvc.perform(MockMvcRequestBuilders.post("/delete_user").contentType(MediaType.APPLICATION_JSON).content(trytodeletenotinthelist))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("this user already doesn't exist"));

        /*
        Test Update_Name
         */

        mockMvc.perform(MockMvcRequestBuilders.post("/update_name").contentType(MediaType.APPLICATION_JSON).content(updatenamesuccessful))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/update_name").contentType(MediaType.APPLICATION_JSON).content(updatenamewrongpassword))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("wrong password"));
        mockMvc.perform(MockMvcRequestBuilders.post("/update_name").contentType(MediaType.APPLICATION_JSON).content(updatenamewrongname))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("this name already exists"));

        /*
        Test Update_Password
         */

        mockMvc.perform(MockMvcRequestBuilders.post("/update_password").contentType(MediaType.APPLICATION_JSON).content(updatepasswordsuccessful))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/update_password").contentType(MediaType.APPLICATION_JSON).content(updatepasswordwrongname))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("this user doesn't even exist"));
    }
}