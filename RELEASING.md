# Releasing

This project publishes to [Maven Central](https://central.sonatype.com/) via the Sonatype Central Portal. The release is driven by GitHub Releases: creating a Release triggers `.github/workflows/publish.yml`, which stages, signs, and auto-finalises the deployment.

## Versioning

We follow [Semantic Versioning](https://semver.org/):

- **MAJOR** — incompatible public API changes
- **MINOR** — backwards-compatible additions (new services, new methods, new optional fields)
- **PATCH** — backwards-compatible bug fixes

The Git tag and GitHub Release name use the `v<version>` form (e.g. `v0.2.0`), but the **source of truth is `VERSION=` in `gradle.properties`** — that's what ends up in the published artifact. The tag is convention; the property is law. They must match.

While on `0.x`, breaking changes may ship in minor versions. Once we cut `1.0.0`, the full semver contract applies.

## Release Checklist

1. **Update the changelog**
   - Move items from `[Unreleased]` into a new version section in `CHANGELOG.md`
   - Use the headings `Added` / `Changed` / `Deprecated` / `Removed` / `Fixed` / `Security`
   - Update the compare links at the bottom

2. **Bump the version**
   - Edit `VERSION=` in `gradle.properties`
   - Edit the `User-Agent` constant in `src/main/java/com/lettr/core/net/HttpClient.java`
   - Edit the install snippets in `README.md`

3. **Verify locally**
   ```bash
   ./gradlew build
   ```

4. **Commit and push to `main`**
   ```bash
   git add gradle.properties src/main/java/com/lettr/core/net/HttpClient.java README.md CHANGELOG.md
   git commit -m "Release 0.2.0"
   git push origin main
   ```

5. **Create a GitHub Release**
   - Go to the repo → **Releases → Draft a new release**
   - **Choose a tag** → type `v0.2.0` → **Create new tag: v0.2.0 on publish**
   - **Release title**: `v0.2.0`
   - **Description**: paste the relevant section from `CHANGELOG.md`
   - Click **Publish release**

   Creating the Release creates the tag — no separate `git tag` / `git push --tags` needed.

6. **Watch the workflow**
   - The `Publish to Maven Central` workflow starts automatically
   - It runs `./gradlew publish`, then POSTs to the Central Portal to finalise the deployment
   - Maven Central indexes the artifact within ~15–30 minutes; [central.sonatype.com](https://central.sonatype.com/artifact/com.lettr/lettr-java) updates first

## One-Time Setup

Only needed when setting up the repo the first time.

1. **Register the `com.lettr` namespace** at [central.sonatype.com](https://central.sonatype.com/) (requires DNS TXT verification).
2. **Generate a Central Portal user token**: click your name → **View Account** → **Generate User Token**.
3. **Generate a GPG key** for signing artifacts:
   ```bash
   gpg --gen-key
   gpg --list-keys --keyid-format long
   gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
   ```
4. **Add 4 secrets** to the GitHub repo (`Settings → Secrets and variables → Actions`):

   | Secret | Value |
   |--------|-------|
   | `CENTRAL_PORTAL_TOKEN_USERNAME` | Token username from step 2 |
   | `CENTRAL_PORTAL_TOKEN_PASSWORD` | Token password from step 2 |
   | `SIGNING_KEY` | Output of `gpg --export-secret-keys --armor YOUR_KEY_ID` |
   | `SIGNING_PASSWORD` | The passphrase set during `gpg --gen-key` |

## Publishing Locally (optional)

Useful for dry-runs against the staging repository.

```bash
export CENTRAL_PORTAL_TOKEN_USERNAME=your-token-username
export CENTRAL_PORTAL_TOKEN_PASSWORD=your-token-password
export SIGNING_KEY="$(gpg --export-secret-keys --armor YOUR_KEY_ID)"
export SIGNING_PASSWORD=your-passphrase

./gradlew publish
```

This stages the artifact on the Central Portal but does not finalise it — log in to the portal to inspect or drop the staged deployment.

## Troubleshooting

- **Validation failed on the Central Portal** — missing POM metadata, missing Javadoc/sources JARs, or unsigned artifacts. Check the workflow log.
- **`401 Unauthorized` during publish** — the `CENTRAL_PORTAL_TOKEN_*` secrets are wrong or expired. Regenerate in the portal.
- **Finalisation step fails but publish succeeded** — the artifact is staged but not live. Log in to the Central Portal and publish manually, or re-run the workflow's finalise step.
- **Version already exists** — Maven Central is immutable. Bump the version and release again; you cannot overwrite.
