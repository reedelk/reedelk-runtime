package com.reedelk.runtime.openapi.v3.model;

import com.reedelk.runtime.openapi.v3.OpenApiJsons;
import org.junit.jupiter.api.Test;

class LicenseObjectTest extends AbstractOpenApiSerializableTest {

    @Test
    void shouldCorrectlySerializeLicenseWithAllProperties() {
        // Given
        LicenseObject license = new LicenseObject();
        license.setName("Apache 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0.html");

        // Expect
        assertSerializeJSON(license, OpenApiJsons.LicenseObject.WithAllPropertiesJson);
        assertSerializeYAML(license, OpenApiJsons.LicenseObject.WithAllPropertiesYaml);
    }

    @Test
    void shouldCorrectlySerializeLicenseWithRequiredValues() {
        // Given
        LicenseObject license = new LicenseObject();

        // Expect
        assertSerializeJSON(license, OpenApiJsons.LicenseObject.WithDefaultPropertiesJson);
        assertSerializeYAML(license, OpenApiJsons.LicenseObject.WithDefaultPropertiesYaml);
    }
}
