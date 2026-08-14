# Lettr Java SDK

The official Java SDK for the [Lettr](https://lettr.com) Email API. A typed, builder-based client for emails, templates, domains, webhooks, audience, and campaigns — methods throw checked exceptions on failure.

## Installation

### Gradle

```groovy
implementation 'com.lettr:lettr-java:1.5.0'
```

### Maven

```xml
<dependency>
    <groupId>com.lettr</groupId>
    <artifactId>lettr-java</artifactId>
    <version>1.5.0</version>
</dependency>
```

## Requirements

- Java 11+

## Quick Start

```java
import com.lettr.Lettr;
import com.lettr.services.emails.model.*;

Lettr lettr = new Lettr("your-api-key");

CreateEmailOptions params = CreateEmailOptions.builder()
    .from("sender@example.com")
    .to("recipient@example.com")
    .subject("Hello from Lettr!")
    .html("<p>Hello, world!</p>")
    .build();

CreateEmailResponse response = lettr.emails().send(params);
System.out.println("Request ID: " + response.getRequestId());
```

Every builder validates required fields in `build()`, and setters are annotated with `@Nonnull`/`@Nullable` so your IDE surfaces what's required as you type.

## Error Handling

Methods throw `LettrException` subclasses — catch the specific type you care about:

```java
try {
    lettr.emails().send(params);
} catch (LettrValidationException e) {     // 422 — field-level details in e.getErrors()
    System.err.println(e.getMessage());
} catch (LettrApiException e) {            // other API errors — e.getStatusCode(), e.getErrorCode()
    System.err.println(e.getErrorCode());
} catch (LettrException e) {               // network / parsing errors
    System.err.println(e.getMessage());
}
```

See [Error Handling](https://docs.lettr.com/quickstart/java/advanced#error-handling) for the full exception hierarchy and error codes.

## Documentation

Full guides for every service, with complete request/response details, live in the docs:

📚 **[docs.lettr.com/quickstart/java](https://docs.lettr.com/quickstart/java/quickstart)**

| Topic | Guide |
|-|-|
| Install, setup, sending | [Quickstart](https://docs.lettr.com/quickstart/java/quickstart) |
| Batch sending, Spring Boot, error handling | [Advanced](https://docs.lettr.com/quickstart/java/advanced) |
| Manage Lettr templates & merge tags | [Templates](https://docs.lettr.com/quickstart/java/templates) |
| Add, verify, and manage sending domains | [Domains](https://docs.lettr.com/quickstart/java/domains) |
| Webhook endpoints for delivery & engagement events | [Webhooks](https://docs.lettr.com/quickstart/java/webhooks) |
| Lists, contacts, topics, properties, segments | [Audience](https://docs.lettr.com/quickstart/java/audience) |
| List, send, and schedule campaigns | [Campaigns](https://docs.lettr.com/quickstart/java/campaigns) |
| Endpoint reference (params & schemas) | [API Reference](https://docs.lettr.com/api-reference/introduction) |

## License

MIT
