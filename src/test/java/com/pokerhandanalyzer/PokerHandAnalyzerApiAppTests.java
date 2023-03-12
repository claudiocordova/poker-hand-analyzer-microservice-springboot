package com.pokerhandanalyzer;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest
@AutoConfigureMockMvc
class PokerHandAnalyzerApiAppTests {

	

	@Autowired
	private MockMvc mockMvc;
	
	/*@DisplayName("Not enough cards")
	@Test
	public void submitPockerHandNotEnoughCards() throws Exception {
		String json = "[ {\"suit\":\"DIAMONDS\", \"value\" : \"2\"} ]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("INVALID_HAND"))
				.andExpect(jsonPath("$.message").value("Hand needs to have 7 cards!"));

	}
	@DisplayName("Duplicate cards")
	@Test
	public void submitPockerHand() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("INVALID_HAND"))
				.andExpect(jsonPath("$.message").value("Duplicate card 2 DIAMONDS"));

	}
	
	@DisplayName("Invalid Suit")
	@Test
	public void submitPockerHandInvalidSuit() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"3\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"4\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"5\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"6\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"7\"},"
				+ " {\"suit\":\"DIAMOND\", \"value\" : \"8\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("INVALID_HAND"))
				.andExpect(jsonPath("$.message").value("Invalid Suit DIAMOND"));

	}
	
	
	@DisplayName("Invalid Value")
	@Test
	public void submitPockerHandInvalidValue() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"3\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"4\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"5\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"6\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"7\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"88\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("INVALID_HAND"))
				.andExpect(jsonPath("$.message").value("Invalid Value 88"));

	}
	
	
	@DisplayName("Straight hand")
	@Test
	public void submitPockerHandStraightHand() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"3\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"4\"},"
				+ " {\"suit\":\"HEARTS\", \"value\" : \"5\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"6\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"7\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"8\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("VALID_HAND"))
				.andExpect(jsonPath("$.message").value("It is a straight!"));

	}
	
	@DisplayName("Straight hand with ace at beguining")
	@Test
	public void submitPockerHandStraightHandWithAce() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"ACE\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"2\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"3\"},"
				+ " {\"suit\":\"HEARTS\", \"value\" : \"4\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"5\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"JACK\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"QUEEN\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("VALID_HAND"))
				.andExpect(jsonPath("$.message").value("It is a straight!"));

	}
	
	@DisplayName("Straight hand with ace at end")
	@Test
	public void submitPockerHandStraightHandWithAceAtEnd() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"ACE\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"2\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"3\"},"
				+ " {\"suit\":\"HEARTS\", \"value\" : \"10\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"KING\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"JACK\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"QUEEN\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("VALID_HAND"))
				.andExpect(jsonPath("$.message").value("It is a straight!"));

	}
	
	
	@DisplayName("Not a Straight hand with ace at end and 2")
	@Test
	public void submitPockerHandNotStraightHandWithAceAtEndAndTwo() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"ACE\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"2\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"3\"},"
				+ " {\"suit\":\"HEARTS\", \"value\" : \"7\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"KING\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"JACK\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"QUEEN\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("VALID_HAND"))
				.andExpect(jsonPath("$.message").value("It is NOT a straight!"));

	}
	
	
	@DisplayName("Straight hand with duplicate value")
	@Test
	public void submitPockerHandStraightHandWithAceAtEndAndTwo() throws Exception {
		String json = "[ "
			    + " {\"suit\":\"DIAMONDS\", \"value\" : \"2\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"3\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"4\"},"
				+ " {\"suit\":\"HEARTS\", \"value\" : \"4\"},"
				+ " {\"suit\":\"DIAMONDS\", \"value\" : \"5\"},"
				+ " {\"suit\":\"SPADES\", \"value\" : \"6\"},"
				+ " {\"suit\":\"CLUBS\", \"value\" : \"QUEEN\"}"
				+ "]";
		
		mockMvc.perform(post("/isstraight")
				.contentType("application/json")
				.content(json)
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.status").value("VALID_HAND"))
				.andExpect(jsonPath("$.message").value("It is a straight!"));

	}
	*/
	
	
	
}
