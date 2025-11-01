package hu.codemosaic.taller;

import hu.codemosaic.taller.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class TallerApplicationTests {

	@MockitoBean
	JwtService jwtService;

	@Test
	void contextLoads() {
	}

}
