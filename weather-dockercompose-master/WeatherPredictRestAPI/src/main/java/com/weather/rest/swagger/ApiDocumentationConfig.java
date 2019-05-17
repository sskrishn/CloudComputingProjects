package com.weather.rest.swagger;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(info = @Info(description = "Created to submit as an assignment", version = "V1.0.0", title = "Weather Predict API", contact = @Contact(name = "Siva Sai Krishna Paladugu", email = "sskrishna.paladugu@gmail.com", url = "https://github.com/sskrishn")
/*
 * ,license = @License( name = "Apache 2.0", url =
 * "http://www.apache.org/licenses/LICENSE-2.0" )
 */
), consumes = { "application/json" }, produces = { "application/json" }, schemes = { SwaggerDefinition.Scheme.HTTP,
		SwaggerDefinition.Scheme.HTTPS }, externalDocs = @ExternalDocs(value = "Visit My Git for more...", url = "https://github.com/sskrishn"))
public interface ApiDocumentationConfig {

}
