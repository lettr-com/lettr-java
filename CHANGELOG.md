# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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

[1.1.0]: https://github.com/lettr/lettr-java/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/lettr/lettr-java/compare/v0.2.0...v1.0.0
[0.2.0]: https://github.com/lettr/lettr-java/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/lettr/lettr-java/releases/tag/v0.1.0
