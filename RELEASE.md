# Release Guide

## Release Flow

1. **Update version** in `gradle.properties` → `pluginVersion = YYYY.M.X`
2. **Update CHANGELOG.md** → add changes under `[Unreleased]`
3. **Push to main** → CI runs build + verify, auto-creates a **draft release** on GitHub
4. **Go to [GitHub Releases](https://github.com/iml885203/intellij-git-merge-into/releases)** → find the draft, click **Publish**
5. **Release workflow triggers** → auto-publishes to JetBrains Marketplace + creates changelog PR

## Required GitHub Secrets

Release workflow needs these secrets in repo settings:

| Secret | Description |
|--------|-------------|
| `PUBLISH_TOKEN` | JetBrains Marketplace upload token ([generate here](https://plugins.jetbrains.com/author/me/tokens)) |
| `CERTIFICATE_CHAIN` | Plugin signing certificate chain ([docs](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html)) |
| `PRIVATE_KEY` | Plugin signing private key |
| `PRIVATE_KEY_PASSWORD` | Private key password |

## Version Convention

`YYYY.M.X` — e.g. `2026.2.0`

- `YYYY.M` follows the release date
- `X` is the patch number (start at 0)
