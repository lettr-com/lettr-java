package com.lettr.services.domains;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lettr.services.domains.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainsTest {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .create();

    @Test
    void createDomainOptionsFactory() {
        CreateDomainOptions options = CreateDomainOptions.of("example.com");
        assertNotNull(options);
    }

    @Test
    void domainDeserializesAllFields() {
        String json = "{\"domain\":\"example.com\",\"status\":\"approved\",\"status_label\":\"Approved\"," +
                "\"can_send\":true,\"cname_status\":\"valid\",\"dkim_status\":\"valid\"," +
                "\"dmarc_status\":\"valid\",\"spf_status\":\"valid\",\"is_primary_domain\":false," +
                "\"tracking_domain\":\"tracking.example.com\"," +
                "\"dns\":{\"dkim\":{\"selector\":\"scph0123\",\"public\":\"MIGfMA0G\",\"headers\":\"from:to:subject\"}}," +
                "\"dns_provider\":{\"provider\":\"cloudflare\",\"provider_label\":\"Cloudflare\"," +
                "\"nameservers\":[\"ns1.cf.com\",\"ns2.cf.com\"],\"error\":null}," +
                "\"created_at\":\"2024-01-15T10:30:00+00:00\",\"updated_at\":\"2024-01-16T14:45:00+00:00\"}";

        Domain domain = gson.fromJson(json, Domain.class);
        assertEquals("example.com", domain.getDomain());
        assertEquals("approved", domain.getStatus());
        assertTrue(domain.isCanSend());
        assertEquals("valid", domain.getDkimStatus());
        assertEquals("valid", domain.getDmarcStatus());
        assertEquals("valid", domain.getSpfStatus());
        assertFalse(domain.getIsPrimaryDomain());
        assertEquals("tracking.example.com", domain.getTrackingDomain());
        assertNotNull(domain.getDns());
        assertEquals("scph0123", domain.getDns().getDkim().getSelector());
        assertEquals("MIGfMA0G", domain.getDns().getDkim().getPublicKey());
        assertEquals("from:to:subject", domain.getDns().getDkim().getHeaders());
        assertNotNull(domain.getDnsProvider());
        assertEquals("cloudflare", domain.getDnsProvider().getProvider());
        assertEquals("Cloudflare", domain.getDnsProvider().getProviderLabel());
        assertEquals(2, domain.getDnsProvider().getNameservers().size());
    }

    @Test
    void createDomainResponseDeserializes() {
        String json = "{\"domain\":\"example.com\",\"status\":\"pending\",\"status_label\":\"Pending\"," +
                "\"dkim\":{\"public\":\"MIGfMA0G\",\"selector\":\"scph0123\",\"headers\":\"from:to\"," +
                "\"signing_domain\":\"example.com\"}}";

        CreateDomainResponse response = gson.fromJson(json, CreateDomainResponse.class);
        assertEquals("example.com", response.getDomain());
        assertEquals("pending", response.getStatus());
        assertNotNull(response.getDkim());
        assertEquals("MIGfMA0G", response.getDkim().getPublicKey());
        assertEquals("scph0123", response.getDkim().getSelector());
        assertEquals("from:to", response.getDkim().getHeaders());
        assertEquals("example.com", response.getDkim().getSigningDomain());
    }

    @Test
    void listDomainsResponseDeserializes() {
        String json = "{\"domains\":[{\"domain\":\"example.com\",\"status\":\"approved\"," +
                "\"status_label\":\"Approved\",\"can_send\":true," +
                "\"created_at\":\"2024-01-15T10:30:00+00:00\",\"updated_at\":\"2024-01-16T14:45:00+00:00\"}]}";

        ListDomainsResponse response = gson.fromJson(json, ListDomainsResponse.class);
        assertEquals(1, response.getDomains().size());
        assertEquals("example.com", response.getDomains().get(0).getDomain());
    }

    @Test
    void verifyDomainResponseDeserializes() {
        String json = "{\"domain\":\"example.com\",\"dkim_status\":\"valid\",\"cname_status\":\"valid\"," +
                "\"dmarc_status\":\"valid\",\"spf_status\":\"valid\",\"is_primary_domain\":false," +
                "\"ownership_verified\":\"true\"," +
                "\"dns\":{\"dkim_record\":null,\"cname_record\":null,\"dkim_error\":\"DKIM record not found\"," +
                "\"cname_error\":null,\"dmarc_record\":null,\"dmarc_error\":null,\"spf_record\":null,\"spf_error\":null}," +
                "\"dmarc\":{\"is_valid\":true,\"status\":\"valid\",\"found_at_domain\":\"example.com\"," +
                "\"record\":\"v=DMARC1; p=reject\",\"policy\":\"reject\",\"subdomain_policy\":null," +
                "\"error\":null,\"covered_by_parent_policy\":false}," +
                "\"spf\":{\"is_valid\":true,\"status\":\"valid\",\"record\":\"v=spf1 include:_spf.sparkpostmail.com ~all\"," +
                "\"error\":null,\"includes_sparkpost\":true}}";

        VerifyDomainResponse response = gson.fromJson(json, VerifyDomainResponse.class);
        assertEquals("example.com", response.getDomain());
        assertEquals("valid", response.getDkimStatus());
        assertEquals("valid", response.getCnameStatus());
        assertEquals("valid", response.getDmarcStatus());
        assertEquals("valid", response.getSpfStatus());
        assertFalse(response.isPrimaryDomain());
        assertEquals("true", response.getOwnershipVerified());

        assertNotNull(response.getDns());
        assertEquals("DKIM record not found", response.getDns().getDkimError());

        assertNotNull(response.getDmarc());
        assertTrue(response.getDmarc().isValid());
        assertEquals("reject", response.getDmarc().getPolicy());

        assertNotNull(response.getSpf());
        assertTrue(response.getSpf().isValid());
        assertTrue(response.getSpf().isIncludesSparkpost());
    }

    // --- Service argument validation ---

    @Test
    void domainsGetRequiresDomain() {
        Domains domains = new Domains("test-key");
        assertThrows(IllegalArgumentException.class, () -> domains.get(null));
        assertThrows(IllegalArgumentException.class, () -> domains.get(""));
    }

    @Test
    void domainsDeleteRequiresDomain() {
        Domains domains = new Domains("test-key");
        assertThrows(IllegalArgumentException.class, () -> domains.delete(null));
        assertThrows(IllegalArgumentException.class, () -> domains.delete(""));
    }

    @Test
    void domainsVerifyRequiresDomain() {
        Domains domains = new Domains("test-key");
        assertThrows(IllegalArgumentException.class, () -> domains.verify(null));
        assertThrows(IllegalArgumentException.class, () -> domains.verify(""));
    }
}
