package com.reedelk.runtime.openapi.model;

import com.reedelk.runtime.openapi.OpenApiJsons;
import org.junit.jupiter.api.Test;

class InfoObjectTest extends AbstractOpenApiSerializableTest {

    @Test
    void shouldCorrectlySerializeInfoWithAllProperties() {
        // Given
        ContactObject contact = new ContactObject();
        contact.setName("API Support");
        contact.setUrl("http://www.example.com/support");
        contact.setEmail("support@example.com");

        LicenseObject license = new LicenseObject();
        license.setName("Apache 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0.html");

        InfoObject info = new InfoObject();
        info.setVersion("1.0.1");
        info.setTitle("Sample Pet Store App");
        info.setTermsOfService("http://example.com/terms/");
        info.setDescription("This is a sample server for a pet store.");
        info.setContact(contact);
        info.setLicense(license);

        // Expect
        assertSerializedCorrectly(info, OpenApiJsons.InfoObject.WithAllProperties);
    }

    @Test
    void shouldCorrectlySerializeInfoWithRequiredValues() {
        // Given
        InfoObject info = new InfoObject();

        // Expect
        assertSerializedCorrectly(info, OpenApiJsons.InfoObject.WithDefaultProperties);
    }
}