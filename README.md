# Lettr Java SDK

The official Java SDK for the [Lettr](https://lettr.com) Email API. Send transactional emails with tracking, attachments, templates, and personalization.

## Installation

### Gradle

```groovy
implementation 'com.lettr:lettr-java:1.0.0'
```

### Maven

```xml
<dependency>
    <groupId>com.lettr</groupId>
    <artifactId>lettr-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Requirements

- Java 11+

## Setup

Get your API key from the [Lettr Dashboard](https://app.lettr.com).

```java
import com.lettr.Lettr;

Lettr lettr = new Lettr("your-api-key");
```

## Required vs Optional Fields

Three signals tell you what's required:

1. **IDE inspections** — every builder setter and getter is annotated with `@Nonnull` or `@Nullable` (JSR-305). IntelliJ, Eclipse, and SpotBugs surface nullability inline as you type.
2. **Builder Javadoc** — each setter is prefixed with **(required)**, **(optional)**, or **(required if X)**. Constraints (max length, ranges, mutual exclusion) follow on a second line.
3. **Builder `build()` validation** — missing required fields throw `IllegalArgumentException` with a descriptive message at construction time.

Source of truth: the [OpenAPI spec](https://app.lettr.com/openapi.json).

## Emails

### Send an Email

```java
import com.lettr.services.emails.model.*;

CreateEmailOptions params = CreateEmailOptions.builder()
    .from("sender@example.com")
    .to("recipient@example.com")
    .subject("Hello from Lettr!")
    .html("<p>Hello, world!</p>")
    .build();

CreateEmailResponse response = lettr.emails().send(params);
System.out.println("Email sent! Request ID: " + response.getRequestId());
```

### Send with All Options

```java
CreateEmailOptions params = CreateEmailOptions.builder()
    .from("sender@example.com")
    .fromName("Sender Name")
    .to("recipient@example.com")
    .cc("cc@example.com")
    .bcc("bcc@example.com")
    .replyTo("reply@example.com")
    .replyToName("Reply Name")
    .subject("Welcome!")
    .html("<h1>Welcome</h1><p>Thanks for signing up.</p>")
    .text("Welcome\n\nThanks for signing up.")
    .tag("welcome-series")
    .headers(Map.of("X-Custom-ID", "abc-123"))
    .metadata(Map.of("user_id", "12345"))
    .options(EmailOptions.builder()
        .clickTracking(true)
        .openTracking(true)
        .transactional(true)
        .inlineCss(true)
        .performSubstitutions(true)
        .build())
    .build();

CreateEmailResponse response = lettr.emails().send(params);
```

### Send with Attachments

```java
Attachment invoice = Attachment.builder()
    .name("invoice.pdf")
    .type("application/pdf")
    .data("JVBERi0xLjQK...")  // base64-encoded content
    .build();

CreateEmailOptions params = CreateEmailOptions.builder()
    .from("billing@example.com")
    .to("customer@example.com")
    .subject("Your Invoice")
    .html("<p>Please find your invoice attached.</p>")
    .attachments(invoice)
    .build();

CreateEmailResponse response = lettr.emails().send(params);
```

### Send with Template

```java
CreateEmailOptions params = CreateEmailOptions.builder()
    .from("hello@example.com")
    .to("john@example.com")
    .templateSlug("welcome-email")
    .templateVersion(2)
    .projectId(5)
    .substitutionData(Map.of(
        "first_name", "John",
        "company", "Acme Inc"
    ))
    .build();

CreateEmailResponse response = lettr.emails().send(params);
```

### Schedule an Email

```java
ScheduleEmailOptions params = ScheduleEmailOptions.builder()
    .from("sender@example.com")
    .to("recipient@example.com")
    .subject("Scheduled Newsletter")
    .html("<p>This will arrive later!</p>")
    .scheduledAt("2024-01-16T10:00:00Z")  // must be 5min–3days in the future
    .build();

CreateEmailResponse response = lettr.emails().schedule(params);
```

### Get a Scheduled Email

```java
ScheduledEmail scheduled = lettr.emails().getScheduled("transmission-id");
System.out.println("State: " + scheduled.getState());       // submitted, scheduled, delivered, etc.
System.out.println("Scheduled at: " + scheduled.getScheduledAt());
```

### Cancel a Scheduled Email

```java
lettr.emails().cancelScheduled("transmission-id");
```

### List Sent Emails

```java
// List with default pagination
ListEmailsResponse emails = lettr.emails().list();

// List with filters
ListEmailsResponse filtered = lettr.emails().list(
    ListEmailsParams.builder()
        .perPage(50)
        .recipients("user@example.com")
        .from("2024-01-01")
        .to("2024-12-31")
        .build()
);

for (EmailEvent event : filtered.getEvents().getData()) {
    System.out.println(event.getSubject() + " -> " + event.getRcptTo());
}

// Pagination
String nextCursor = filtered.getEvents().getPagination().getNextCursor();
```

### List Email Events

```java
ListEmailEventsResponse events = lettr.emails().listEvents(
    ListEmailEventsParams.builder()
        .events(List.of("delivery", "bounce", "open"))
        .recipients(List.of("user@example.com"))
        .from("2024-01-01")
        .to("2024-12-31")
        .perPage(25)
        .build()
);

for (EmailEvent event : events.getEvents().getData()) {
    System.out.println(event.getType() + " at " + event.getTimestamp());
    if ("bounce".equals(event.getType())) {
        System.out.println("  Bounce class: " + event.getBounceClass());
        System.out.println("  Reason: " + event.getReason());
    }
    if ("click".equals(event.getType())) {
        System.out.println("  Link: " + event.getTargetLinkUrl());
        System.out.println("  Country: " + event.getGeoIp().getCountry());
    }
}
```

### Get Email Details

```java
GetEmailResponse email = lettr.emails().get("request-id-from-send");

System.out.println("State: " + email.getState());           // scheduled, delivered, bounced, failed
System.out.println("Recipients: " + email.getRecipients());

for (EmailEvent event : email.getEvents()) {
    System.out.println(event.getType() + " at " + event.getTimestamp());
}
```

## Domains

### List Domains

```java
import com.lettr.services.domains.model.*;

ListDomainsResponse domains = lettr.domains().list();
for (Domain d : domains.getDomains()) {
    System.out.println(d.getDomain() + " - " + d.getStatus() + " (can send: " + d.isCanSend() + ")");
}
```

### Create a Domain

```java
CreateDomainResponse newDomain = lettr.domains().create(
    CreateDomainOptions.of("example.com")
);
System.out.println("DKIM selector: " + newDomain.getDkim().getSelector());
System.out.println("DKIM public key: " + newDomain.getDkim().getPublicKey());
```

### Get Domain Details

```java
Domain domain = lettr.domains().get("example.com");
System.out.println("DKIM status: " + domain.getDkimStatus());
System.out.println("DMARC status: " + domain.getDmarcStatus());
System.out.println("SPF status: " + domain.getSpfStatus());
System.out.println("Primary domain: " + domain.getIsPrimaryDomain());
```

### Verify Domain DNS

```java
VerifyDomainResponse result = lettr.domains().verify("example.com");
System.out.println("DKIM: " + result.getDkimStatus());
System.out.println("CNAME: " + result.getCnameStatus());
System.out.println("DMARC: " + result.getDmarcStatus());
System.out.println("SPF: " + result.getSpfStatus());

if (result.getDns().getDkimError() != null) {
    System.out.println("DKIM error: " + result.getDns().getDkimError());
}
```

### Delete a Domain

```java
lettr.domains().delete("example.com");
```

## Webhooks

### List Webhooks

```java
import com.lettr.services.webhooks.model.*;

ListWebhooksResponse webhooks = lettr.webhooks().list();
for (Webhook w : webhooks.getWebhooks()) {
    System.out.println(w.getName() + " -> " + w.getUrl() + " (enabled: " + w.isEnabled() + ")");
}
```

### Create a Webhook

```java
Webhook webhook = lettr.webhooks().create(
    CreateWebhookOptions.builder()
        .name("Order Notifications")
        .url("https://example.com/webhook")
        .authType("basic")
        .authUsername("user")
        .authPassword("secret")
        .eventsMode("selected")
        .events(List.of("delivery", "bounce"))
        .build()
);
System.out.println("Webhook ID: " + webhook.getId());
```

### Get a Webhook

```java
Webhook webhook = lettr.webhooks().get("webhook-abc123");
```

### Update a Webhook

```java
Webhook updated = lettr.webhooks().update("webhook-abc123",
    UpdateWebhookOptions.builder()
        .name("Updated Webhook")
        .target("https://new.example.com/webhook")
        .active(false)
        .build()
);
```

### Delete a Webhook

```java
lettr.webhooks().delete("webhook-abc123");
```

## Templates

### List Templates

```java
import com.lettr.services.templates.model.*;

ListTemplatesResponse templates = lettr.templates().list();

// With pagination
ListTemplatesResponse page = lettr.templates().list(
    ListTemplatesParams.builder()
        .projectId(5)
        .perPage(10)
        .page(2)
        .build()
);
```

### Get a Template

```java
TemplateDetail template = lettr.templates().get("welcome-email");
System.out.println("Name: " + template.getName());
System.out.println("Active version: " + template.getActiveVersion());
System.out.println("HTML: " + template.getHtml());

// With a specific project
TemplateDetail template = lettr.templates().get("welcome-email", 5);
```

### Create a Template

```java
CreateTemplateResponse response = lettr.templates().create(
    CreateTemplateOptions.builder()
        .name("Welcome Email")
        .html("<p>Hello {{FIRST_NAME}}!</p>")
        .projectId(5)
        .folderId(10)
        .build()
);
System.out.println("Slug: " + response.getSlug());
System.out.println("Merge tags: " + response.getMergeTags());
```

### Update a Template

```java
UpdateTemplateResponse response = lettr.templates().update("welcome-email",
    UpdateTemplateOptions.builder()
        .name("Updated Welcome Email")
        .html("<p>Hello {{FIRST_NAME}}, welcome aboard!</p>")
        .build()
);
```

### Delete a Template

```java
lettr.templates().delete("welcome-email");

// With a specific project
lettr.templates().delete("welcome-email", 5);
```

### Get Merge Tags

```java
GetMergeTagsResponse tags = lettr.templates().getMergeTags("welcome-email");
for (MergeTag tag : tags.getMergeTags()) {
    System.out.println(tag.getKey() + " (required: " + tag.isRequired() + ")");
    if (tag.getChildren() != null) {
        for (MergeTagChild child : tag.getChildren()) {
            System.out.println("  " + child.getKey() + " (" + child.getType() + ")");
        }
    }
}

// With version and project
GetMergeTagsResponse tags = lettr.templates().getMergeTags("welcome-email",
    GetMergeTagsParams.builder().projectId(5).version(2).build()
);
```

### Get Template HTML

```java
GetTemplateHtmlResponse html = lettr.templates().getHtml(
    GetTemplateHtmlParams.builder()
        .projectId(5)
        .slug("welcome-email")
        .build()
);
System.out.println("Subject: " + html.getSubject());
System.out.println("HTML: " + html.getHtml());
```

## System

### Health Check

```java
import com.lettr.services.system.model.HealthResponse;

HealthResponse health = lettr.system().health();
System.out.println("Status: " + health.getStatus());
```

### Validate API Key

```java
import com.lettr.services.system.model.AuthCheckResponse;

AuthCheckResponse auth = lettr.system().authCheck();
System.out.println("Team ID: " + auth.getTeamId());
```

## Error Handling

The SDK provides structured exception types:

```java
import com.lettr.core.exception.LettrException;
import com.lettr.core.exception.LettrApiException;
import com.lettr.core.exception.LettrValidationException;

try {
    lettr.emails().send(params);
} catch (LettrValidationException e) {
    // 422 - Validation errors with field-level details
    System.err.println("Validation failed: " + e.getMessage());
    e.getErrors().forEach((field, messages) -> {
        System.err.println("  " + field + ": " + messages);
    });
} catch (LettrApiException e) {
    // Other API errors (400, 401, 404, 409, 429, 500, 502)
    System.err.println("API error: " + e.getMessage());
    System.err.println("Status: " + e.getStatusCode());
    System.err.println("Error code: " + e.getErrorCode());  // e.g. "invalid_domain", "quota_exceeded"
} catch (LettrException e) {
    // Network or parsing errors
    System.err.println("Error: " + e.getMessage());
}
```

### Error Codes

| Code | Description |
|------|-------------|
| `validation_error` | Request validation failed (422) |
| `invalid_domain` | Sender domain could not be determined (400) |
| `unconfigured_domain` | Sender domain not configured or approved (400) |
| `send_error` | General send error (400/500) |
| `transmission_failed` | Upstream provider failure (502) |
| `resource_already_exists` | Resource already exists (409) |
| `template_not_found` | Template/project/version not found (404) |
| `quota_exceeded` | Monthly sending quota exceeded (429) |
| `daily_quota_exceeded` | Daily sending quota exceeded (429) |

## License

MIT License - see [LICENSE](LICENSE) for details.
