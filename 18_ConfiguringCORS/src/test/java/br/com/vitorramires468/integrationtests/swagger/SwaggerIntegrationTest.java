package br.com.vitorramires468.integrationtests.swagger;

import br.com.vitorramires468.config.TestConfigs;
import br.com.vitorramires468.integrationtests.testcontainers.AbstractIntegrationTest;
import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	void shouldDisplaySwaggerUIPage() {
		var content = given()
				.basePath("/swagger-ui/index.html")
					.port(TestConfigs.SERVER_PORT)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();

		assertTrue(content.contains("Swagger UI"));
	}

}
