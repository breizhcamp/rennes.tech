package org.breizhcamp.rennes.tech.config

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.converter.AnnotatedType
import io.swagger.v3.core.converter.ModelConverterContext
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.core.util.RefUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Discriminator

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI().info(Info()
            .title("Rennes Tech API")
            .version("1.0")
            .description("API for Rennes Tech")
        )


    /** In order to define correctly the json subtypes implementations */
    @Bean
    fun myCustomModelResolver(objectMapper: ObjectMapper?): ModelResolver =
        object : ModelResolver(objectMapper) {
            override fun resolveDiscriminator(type: JavaType?, context: ModelConverterContext?): Discriminator? {
                val ctx = context ?: return null
                val javaType = type ?: return null
                val discriminator = super.resolveDiscriminator(javaType, ctx) ?: return null

                if (discriminator.propertyName != null &&
                    (discriminator.mapping == null || discriminator.mapping.isEmpty()) // don't override anything
                ) {
                    val jsonSubTypes = javaType.rawClass.getDeclaredAnnotation(JsonSubTypes::class.java)
                    jsonSubTypes?.value?.forEach { subtype: JsonSubTypes.Type ->
                        val ref = RefUtils.constructRef(ctx.resolve(AnnotatedType(subtype.value.java)).name)
                        discriminator.mapping(subtype.name, ref) // add mapping
                    }
                }
                return discriminator
            }
        }
}
