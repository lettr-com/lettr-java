# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.5.0] - 2026-08-14

Covers the reworked bulk contact import (TPL-2105) and the duplicate-create fix. Everything here is additive — code written against 1.4.0 keeps compiling and sends the exact same payloads.

### Added

- **Per-contact bulk create.** `BulkCreateAudienceContactsOptions` supports a second request shape where each contact carries its own properties, lists and topic subscriptions, alongside the original flat `emails` list. Exactly one of the two must be filled in

  ```java
  BulkCreateAudienceContactsOptions.builder()
      .contacts(List.of(
          BulkAudienceContactRow.builder()
              .email("cara@example.com")
              .properties(Map.of("plan", "pro"))
              .build(),
          BulkAudienceContactRow.builder()
              .email("dan@example.com")
              .topic(AudienceTopicSubscription.optOut("01h-promos"))
              .build()))
      .listIds(List.of("01h-everyone"))
      .updateExisting(true)
      .build();
  ```
- `BulkAudienceContactRow` (builder: `email`, `properties`, `listIds`, `topics`/`topic`) and `AudienceTopicSubscription` (with the `optIn(id)` / `optOut(id)` constructors), plus the `AudienceTopicSubscriptionState` enum (`OPT_IN`, `OPT_OUT`)
- `AudienceTopicSubscriptionState` says what a request should *do* with a topic and is deliberately separate from `AudienceTopicDefaultSubscription`, which describes how a topic behaves for a contact that says nothing. An `optOut` on a topic whose default is opt-out suppresses the auto-subscription in the same request instead of needing a second call
- **Batch-wide `listIds` and `topics`,** plus `updateExisting`, on `BulkCreateAudienceContactsOptions`. Batch-wide lists and topics are unioned into every row; a row-level property key or opt-out wins over the batch-wide value. `updateExisting(true)` merges properties (submitted keys overwrite, absent keys are preserved) and allows dropping a subscription. It is only serialized when `true`, so a legacy payload stays byte-identical
- **Bulk create now reports what happened per row.** `BulkCreateAudienceContactsResponse` gains `getUpdated()`, `getErrorCount()`, `getErrors()` (`BulkAudienceContactError` — `index`, `email`, `errorCode`, `error`) and `getContacts()` (`BulkAudienceContactRef` — `id`, `email`, `created`), plus `hasErrors()`, `getContactIds()` and `findIdFor(email)`. `getCreated()` and `getAlreadyExisted()` keep their exact meaning, and the collection getters never return `null`, so the response also reads a pre-TPL-2105 body

  A bulk create can **partially succeed**: rows that fail validation are skipped and returned in `getErrors()` while the rest of the batch commits, and the call still returns HTTP 201. Check `hasErrors()` — a call that returns without throwing does not mean every row landed

  Note that `getAlreadyExisted()` and `getUpdated()` overlap by design. They answer different questions ("was the address already in the audience?" vs "did this request change the contact?"), so they do not sum to the row count: a contact that already existed and got attached to a list is counted in both
- `BulkAudienceContactErrorCode` enum (`missing_email`, `invalid_email`, `invalid_property_value`, `unknown_property_key`, `unknown_list`, `unknown_topic`, `invalid_topic_subscription`) with `fromWire(String)`. `BulkAudienceContactError.getErrorCode()` stays a raw `String` so a code added server-side survives; `getCode()` gives the typed form and returns `null` for an unknown code
- **Bulk topic subscribe/unsubscribe** — 2 new methods on `audience().contacts()`, mirroring the existing `bulkAttachToLists` / `bulkDetachFromLists` pair:
  - `bulkSubscribeToTopics(BulkContactTopicsOptions)` — `POST /audience/contacts/topics/bulk`, returns `BulkSubscribeContactsResponse` (`subscribed`, `alreadySubscribed`, `totalPairs`)
  - `bulkUnsubscribeFromTopics(BulkContactTopicsOptions)` — `DELETE /audience/contacts/topics/bulk` with a request body, returns `BulkUnsubscribeContactsResponse` (`unsubscribed`, `totalPairs`). Pairs that do not exist are ignored

  Both process every `contactIds` × `topicIds` combination (up to 1000 × 50). Feed them `getContactIds()` from a bulk create — no id lookup needed
- `ContactAlreadyExistsException` — thrown by `audience().contacts().create()` when the email is already in the team's audience. It carries the colliding `getEmail()`. This is a client-correctable condition, **not** an outage: do not retry it; update the existing contact, or use `bulkCreate()` with `updateExisting(true)`

### Changed

- Creating a contact whose email already exists now throws `ContactAlreadyExistsException` (HTTP 409, `resource_already_exists`). The API previously let this escape as HTTP 500 with the misleading `send_error` code, which arrived as a plain `LettrApiException`. **If your retry policy retries 5xx, duplicate creates are no longer retried** — which was pointless anyway. Any error mapping or docs of yours that name `send_error` for this endpoint should be corrected. The exception extends `LettrApiException`, so existing `catch (LettrApiException)` / `catch (LettrException)` handlers catch it unchanged, and a 409 with any other error code stays a plain `LettrApiException`
- `BulkCreateAudienceContactsOptions.getEmails()` is annotated `@Nullable` instead of `@Nonnull`, since it is now absent when the `contacts` shape is used. Source- and binary-compatible; only a static analyzer's view of it changes

## [1.4.0] - 2026-05-28

### Added

- `CampaignDetail` — detailed view of a campaign returned by `campaigns().get(id)`. Extends `CampaignView` and adds `getHtmlContent()`. `CampaignDetail` IS-A `CampaignView`, so callers that assign the `get()` result to a `CampaignView` variable keep working

### Changed

- `Campaigns.get(String)` return type narrowed from `CampaignView` to `CampaignDetail`. The rendered HTML is exposed via `CampaignDetail.getHtmlContent()`; existing `get(...).getHtmlContent()` callers are source-compatible
- `CampaignView` no longer carries an `htmlContent` field. The API never populated it on list, send, schedule, or unschedule responses, so its presence on the base type was misleading — calls like `lettr.campaigns().send(id).getHtmlContent()` no longer compile (the branch was always dead anyway)

## [1.3.0] - 2026-05-28

### Added

- **Campaigns** service covering six endpoints, reached via `lettr.campaigns()`:
  - `list()` / `list(ListCampaignsParams)` — paginated list with optional `status` filter (`GET /campaigns`)
  - `get(id)` — single campaign with rendered `htmlContent` (`GET /campaigns/{id}`)
  - `listEvents(id)` / `listEvents(id, ListCampaignEventsParams)` — cursor-paginated engagement events (`GET /campaigns/{id}/events`)
  - `send(id)` — dispatch a draft now (`POST /campaigns/{id}/send`)
  - `schedule(id, ScheduleCampaignOptions)` — schedule or reschedule (`POST /campaigns/{id}/schedule`)
  - `unschedule(id)` — cancel a scheduled send (`POST /campaigns/{id}/unschedule`)
- `CampaignStatus` and `CampaignEventType` enums (with `getValue()` for query building), plus `CampaignView`, `CampaignStats`, `CampaignEvent`, and `CampaignPagination` models
- Unit tests covering campaign Gson deserialization, enum round-tripping, query-param building, and argument validation

### Changed

- `HttpClient.post(String path, Type responseType)` — new no-body overload, used by `Campaigns.send` / `unschedule` instead of passing a dummy empty map
- `HttpClient.encodePathSegment(String)` — percent-encodes path segments so callers can safely interpolate arbitrary identifiers; adopted by `Campaigns` (id-taking methods) and `Emails.get` / `getScheduled` / `cancelScheduled`
- `USER_AGENT` now reads the SDK version from a generated `com/lettr/version.properties` resource (templated by Gradle from `gradle.properties`) instead of a hardcoded string — single source of truth across releases
- `OffsetPagination` (`com.lettr.core.model`) — shared pagination shape for new code; `ListCampaignsResponse` uses it. `AudiencePagination` is unchanged and remains the return type of the audience list responses (both classes have identical shape)
- `PageParams` (`com.lettr.core.model`) — shared listing parameters for new code; `ListCampaignsParams` composes it. `com.lettr.services.audience.model.PageParams` is unchanged and continues to work for existing audience usage (both classes are behaviourally identical)
- `WireValues.of(Enum)` (`com.lettr.core.util`) reads `@SerializedName` reflectively for URL query building, so enums declare each wire value exactly once
- `Args.requireNonEmpty(name, value)` / `Args.requireNonNull(name, value)` (`com.lettr.core.util`) — shared validators; adopted by `Campaigns` and `ScheduleCampaignOptions`
- `getCampaigns()` / `getEvents()` (and the audience list responses) defensively return `Collections.emptyList()` when the API omits the list field, so `@Nonnull` getters never return null

### Notes

- `CampaignView.htmlContent` is only populated by `get(...)`; it is `null` on list, send, schedule, and unschedule responses
- Campaign events use cursor pagination — keep requesting with `getNextCursor()` until it is `null`; a filtered page may return an empty `events` list with a non-null cursor, meaning more pages remain

## [1.2.0] - 2026-05-25

### Added

- **Audience** namespace covering 33 endpoints across five sub-services, reached via `lettr.audience()`:
  - `lists()` — list, get, create, update, delete, bulk delete
  - `contacts()` — list, get, create (with optional double opt-in), bulk create, update, delete, attach/detach to lists (single + bulk), subscribe/unsubscribe to topics
  - `topics()` — list, get, create, update, delete
  - `properties()` — list, get, create, update, delete
  - `segments()` — list, get, create, update, delete (with full `SegmentOperator` / `SegmentCondition` / `SegmentConditionGroup` modelling)
- `HttpClient.patch(path, body, type)` — used by every audience update endpoint
- `HttpClient.delete(path, body, type)` — used by `/audience/lists/bulk` and `/audience/contacts/lists/bulk`
- `HttpClient.post(path, body)` — void overload for attach/subscribe endpoints that return only `{message}`
- `NullablePropertiesAdapter` — preserves `null` map values when serializing `UpdateAudienceContactOptions.properties`, so the spec's "set a property to null to delete it" semantics actually work
- `UpdateAudiencePropertyOptionsAdapter` — always emits `fallback_value`, so `UpdateAudiencePropertyOptions.withFallbackValue(null)` clears the fallback instead of being silently dropped
- 46 unit tests covering the new audience namespace (Gson deserialization + argument validation)

### Notes

- `UpdateAudienceContactOptions.status(...)` now rejects `BOUNCED`, `COMPLAINED`, and `UNVERIFIED` at builder time — the API only accepts `SUBSCRIBED` / `UNSUBSCRIBED` for updates; the other statuses are server-managed
- `/audience/confirm/{token}` is intentionally not covered (token-flow endpoint)

## [1.1.0] - 2026-04-22

### Added

- `UpdateWebhookOptions.url()` — matches the renamed API field on `PUT /webhooks/{id}`

### Changed

- Webhook event types are now namespaced (e.g. `message.delivery`, `engagement.click`, `generation.generation_failure`). The server emits and accepts the namespaced form on both `POST` and `PUT`. Update any hard-coded event strings in `CreateWebhookOptions.events` / `UpdateWebhookOptions.events`.

### Deprecated

- `UpdateWebhookOptions.target()` / `getTarget()` — use `url()` / `getUrl()`. The builder still accepts `target` for source compatibility and serializes it as `url`, so existing callers keep working.

## [1.0.0] - 2026-04-21

### Added

- JSR-305 `@Nonnull` / `@Nullable` annotations across every public type (params, return values, getters, setters)
- Consistent **(required)** / **(optional)** Javadoc prefix on every builder setter, with constraint info (max length, mutual-exclusion, etc.)
- README section explaining how to tell required vs optional fields
- `compileOnly` dependency on `com.google.code.findbugs:jsr305:3.0.2`

## [0.2.0] - 2026-04-15

Full sync with the Lettr OpenAPI spec. All 27 documented endpoints are now covered.

### Added

- **Emails**: `listEvents()`, `schedule()`, `getScheduled()`, `cancelScheduled()`
- **Domains**: `verify()` with detailed DKIM/CNAME/DMARC/SPF validation results
- **Webhooks**: `create()`, `update()`, `delete()`
- **Templates**: `get()`, `update()`, `delete()`, `getMergeTags()`, `getHtml()`
- **Projects** service with `list()`
- **System** service with `health()` and `authCheck()`
- `CreateEmailOptions`: `cc`, `bcc`, `replyTo`, `replyToName`, `ampHtml`, `tag`, `headers`
- `EmailOptions`: `inlineCss`, `performSubstitutions`
- `EmailEvent`: type-specific fields (`bounceClass`, `targetLinkUrl`, `geoIp`, `userAgentParsed`, etc.) and many missing common properties
- `Domain`: `dmarcStatus`, `spfStatus`, `isPrimaryDomain`, `dnsProvider`
- HTTP client: `PUT` support and `DELETE` with query params
- Test suite (98 tests across 8 files)

### Changed

- `ListEmailsResponse` restructured to match the API's nested `events.data` shape (breaking)
- `GetEmailResponse` restructured to expose `transmissionId`, `state`, `recipients`, `events` (breaking)
- `EmailEvent`: `clickTracking`, `openTracking`, `transactional` now nullable `Boolean` (was primitive `boolean`); `msgSize` now nullable `Integer`
- `CreateEmailOptions`: `subject` no longer required when using `templateSlug`
- `MergeTag` extracted from `CreateTemplateResponse` into its own class; now exposes `type` and `children`

### Fixed

- DKIM record now includes the `headers` field
- DKIM response now includes `signingDomain`

## [0.1.0] - 2026-01-15

Initial release.

### Added

- Initial SDK with `Emails`, `Domains`, `Webhooks`, and `Templates` services
- Basic send, list, and get operations
- Bearer token auth, Gson-based JSON serialization
- Structured exceptions: `LettrException`, `LettrApiException`, `LettrValidationException`

[1.5.0]: https://github.com/lettr/lettr-java/compare/v1.4.0...v1.5.0
[1.4.0]: https://github.com/lettr/lettr-java/compare/v1.3.0...v1.4.0
[1.1.0]: https://github.com/lettr/lettr-java/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/lettr/lettr-java/compare/v0.2.0...v1.0.0
[0.2.0]: https://github.com/lettr/lettr-java/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/lettr/lettr-java/releases/tag/v0.1.0
