package TDLBackend.tdl;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TdlApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TdlApplication.class)
        .web(WebApplicationType.NONE).run(args);
	}

}
