package com.lettr;

import com.lettr.services.domains.Domains;
import com.lettr.services.emails.Emails;
import com.lettr.services.projects.Projects;
import com.lettr.services.system.System;
import com.lettr.services.templates.Templates;
import com.lettr.services.webhooks.Webhooks;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LettrTest {

    @Test
    void constructorRequiresApiKey() {
        assertThrows(IllegalArgumentException.class, () -> new Lettr(null));
        assertThrows(IllegalArgumentException.class, () -> new Lettr(""));
    }

    @Test
    void constructorAcceptsValidApiKey() {
        Lettr lettr = new Lettr("test-api-key");
        assertNotNull(lettr);
    }

    @Test
    void emailsReturnsServiceInstance() {
        Lettr lettr = new Lettr("test-api-key");
        Emails emails = lettr.emails();
        assertNotNull(emails);
    }

    @Test
    void domainsReturnsServiceInstance() {
        Lettr lettr = new Lettr("test-api-key");
        Domains domains = lettr.domains();
        assertNotNull(domains);
    }

    @Test
    void webhooksReturnsServiceInstance() {
        Lettr lettr = new Lettr("test-api-key");
        Webhooks webhooks = lettr.webhooks();
        assertNotNull(webhooks);
    }

    @Test
    void templatesReturnsServiceInstance() {
        Lettr lettr = new Lettr("test-api-key");
        Templates templates = lettr.templates();
        assertNotNull(templates);
    }

    @Test
    void projectsReturnsServiceInstance() {
        Lettr lettr = new Lettr("test-api-key");
        Projects projects = lettr.projects();
        assertNotNull(projects);
    }

    @Test
    void systemReturnsServiceInstance() {
        Lettr lettr = new Lettr("test-api-key");
        System system = lettr.system();
        assertNotNull(system);
    }

    @Test
    void servicesAreNewInstancesEachCall() {
        Lettr lettr = new Lettr("test-api-key");
        assertNotSame(lettr.emails(), lettr.emails());
        assertNotSame(lettr.domains(), lettr.domains());
        assertNotSame(lettr.webhooks(), lettr.webhooks());
        assertNotSame(lettr.templates(), lettr.templates());
        assertNotSame(lettr.projects(), lettr.projects());
        assertNotSame(lettr.system(), lettr.system());
    }
}
