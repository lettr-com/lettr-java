# Lettr Java SDK

The official Java SDK for the [Lettr](https://lettr.com) Email API. Send transactional emails with tracking, attachments, templates, and personalization.

## Installation

### Gradle

```groovy
implementation 'com.lettr:lettr-java:0.1.0'
```

### Maven

```xml
<dependency>
    <groupId>com.lettr</groupId>
    <artifactId>lettr-java</artifactId>
    <version>0.1.0</version>
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

## Usage

### Send an Email

```java
import com.lettr.Lettr;
import com.lettr.services.emails.model.CreateEmailOptions;
import com.lettr.services.emails.model.CreateEmailResponse;

Lettr lettr = new Lettr("your-api-key");

CreateEmailOptions params = CreateEmailOptions.builder()
    .from("sender@example.com")
    .to("recipient@example.com")
    .subject("Hello from Lettr!")
    .html("<p>Hello, world!</p>")
    .build();

try {
    CreateEmailResponse response = lettr.emails().send(params);
    System.out.println("Email sent! Request ID: " + response.getRequestId());
} catch (LettrException e) {
    e.printStackTrace();
}
```

### Send with HTML and Plain Text

```java
CreateEmailOptions params = CreateEmailOptions.builder()
    .from("sender@example.com")
    .fromName("Sender Name")
    .to("recipient@example.com")
    .subject("Welcome!")
    .html("<h1>Welcome</h1><p>Thanks for signing up.</p>")
    .text("Welcome\n\nThanks for signing up.")
    .options(EmailOptions.builder()
        .clickTracking(true)
        .openTracking(true)
        .build())
    .build();

CreateEmailResponse response = lettr.emails().send(params);
```

### Send with Attachments

```java
import com.lettr.services.emails.model.Attachment;

Attachment invoice = Attachment.builder()
    .name("invoice.pdf")
    .type("application/pdf")
    .data("JVBERi0xLjQK...")  // base64-encoded content
    .build();

CreateEmailOptions params = CreateEmailOptions.builder()
    .from("billing@example.com")
    .fromName("Billing Department")
    .to("customer@example.com")
    .subject("Your Invoice")
    .html("<p>Please find your invoice attached.</p>")
    .attachments(invoice)
    .build();

CreateEmailResponse response = lettr.emails().send(params);
```

### Send with Template

```java
import java.util.Map;

CreateEmailOptions params = CreateEmailOptions.builder()
    .from("hello@example.com")
    .to("john@example.com")
    .subject("Welcome, {{first_name}}!")
    .templateSlug("welcome-email")
    .substitutionData(Map.of(
        "first_name", "John",
        "company", "Acme Inc"
    ))
    .build();

CreateEmailResponse response = lettr.emails().send(params);
```

### List Sent Emails

```java
import com.lettr.services.emails.model.ListEmailsParams;
import com.lettr.services.emails.model.ListEmailsResponse;

// List with default pagination
ListEmailsResponse emails = lettr.emails().list();

// List with filters
ListEmailsResponse filtered = lettr.emails().list(
    ListEmailsParams.builder()
        .perPage(50)
        .recipients("user@example.com")
        .from("2024-01-01")
        .build()
);

for (EmailEvent event : filtered.getResults()) {
    System.out.println(event.getSubject() + " -> " + event.getRcptTo());
}
```

### Get Email Details

```java
import com.lettr.services.emails.model.GetEmailResponse;

GetEmailResponse email = lettr.emails().get("request-id-from-send");

for (EmailEvent event : email.getResults()) {
    System.out.println(event.getType() + " at " + event.getTimestamp());
}
```

### Manage Domains

```java
import com.lettr.services.domains.model.*;

// List all domains
ListDomainsResponse domains = lettr.domains().list();

// Create a new domain
CreateDomainResponse newDomain = lettr.domains().create(
    CreateDomainOptions.of("example.com")
);
System.out.println("DKIM selector: " + newDomain.getDkim().getSelector());

// Get domain details
Domain domain = lettr.domains().get("example.com");
System.out.println("Can send: " + domain.isCanSend());

// Delete a domain
lettr.domains().delete("example.com");
```

### Manage Webhooks

```java
import com.lettr.services.webhooks.model.*;

// List all webhooks
ListWebhooksResponse webhooks = lettr.webhooks().list();

// Get a specific webhook
Webhook webhook = lettr.webhooks().get("webhook-abc123");
System.out.println("Webhook URL: " + webhook.getUrl());
```

### Manage Templates

```java
import com.lettr.services.templates.model.*;

// List all templates
ListTemplatesResponse templates = lettr.templates().list();

// List with pagination
ListTemplatesResponse page2 = lettr.templates().list(
    ListTemplatesParams.builder()
        .perPage(10)
        .page(2)
        .build()
);

// Create a new template
CreateTemplateResponse newTemplate = lettr.templates().create(
    CreateTemplateOptions.builder()
        .name("Welcome Email")
        .html("<p>Hello {{FIRST_NAME}}!</p>")
        .build()
);
System.out.println("Template slug: " + newTemplate.getSlug());
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
    // 422 - Validation errors
    System.err.println("Validation failed: " + e.getMessage());
    e.getErrors().forEach((field, messages) -> {
        System.err.println("  " + field + ": " + messages);
    });
} catch (LettrApiException e) {
    // Other API errors (401, 404, 500, etc.)
    System.err.println("API error: " + e.getMessage());
    System.err.println("Status: " + e.getStatusCode());
    System.err.println("Error code: " + e.getErrorCode());
} catch (LettrException e) {
    // Network or parsing errors
    System.err.println("Error: " + e.getMessage());
}
```

## CI/CD

This project includes two GitHub Actions workflows:

- **CI** (`.github/workflows/ci.yml`) -- runs on every push/PR to `main`, builds and tests against Java 17 and 21.
- **Publish** (`.github/workflows/publish.yml`) -- automatically publishes to Maven Central when you create a GitHub Release.

## Publishing to Maven Central

### One-Time Setup

1. **Register with Sonatype**: Create an account at [central.sonatype.com](https://central.sonatype.com/) and claim the `com.lettr` namespace (they verify domain ownership via DNS TXT record).

2. **Generate a Central Portal user token**: Go to [central.sonatype.com](https://central.sonatype.com) → click your name (top right) → **View Account** → **Generate User Token**. This gives you a token username and token password (both random strings).

3. **Generate a GPG key** for signing artifacts:

```bash
gpg --gen-key
gpg --list-keys --keyid-format long   # find your key ID
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

4. **Add 4 secrets** to your GitHub repo (`Settings > Secrets and variables > Actions`):

| Secret | Value |
|--------|-------|
| `CENTRAL_PORTAL_TOKEN_USERNAME` | Token username (from step 2) |
| `CENTRAL_PORTAL_TOKEN_PASSWORD` | Token password (from step 2) |
| `SIGNING_KEY` | Output of `gpg --export-secret-keys --armor YOUR_KEY_ID` |
| `SIGNING_PASSWORD` | The passphrase you set during `gpg --gen-key` |

### Publishing a New Version

Every time you want to release:

1. Update the version in `gradle.properties`
2. Commit, push, and create a **GitHub Release** (e.g. tag `v0.2.0`)
3. The workflow publishes automatically with `publishing_type=automatic` -- if validation passes, it goes straight to Maven Central (~30 min)

### Publishing Locally (optional)

```bash
export CENTRAL_PORTAL_TOKEN_USERNAME=your-token-username
export CENTRAL_PORTAL_TOKEN_PASSWORD=your-token-password
export SIGNING_KEY="$(gpg --export-secret-keys --armor YOUR_KEY_ID)"
export SIGNING_PASSWORD=your-passphrase

./gradlew publish
```

## License

MIT License - see [LICENSE](LICENSE) for details.
