# intellij-git-merge-into

![Build](https://github.com/iml885203/intellij-git-merge-into/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/24969-git-merge-into.svg)](https://plugins.jetbrains.com/plugin/24969-git-merge-into)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/24969-git-merge-into.svg)](https://plugins.jetbrains.com/plugin/24969-git-merge-into)

## Description

<!-- Plugin description -->

Git Merge Into is a plugin that helps you quickly merge the current branch into another branch — without leaving your IDE.

### How to Use

1. **Keyboard shortcut**: Press <kbd>Alt</kbd>+<kbd>G</kbd>, then <kbd>I</kbd> to trigger the merge action.
2. **Action search**: Press <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> (or <kbd>⌘</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> on macOS), then type **"Merge Into"**.
3. **Git Open menu**: If you have the [Git Open](https://plugins.jetbrains.com/plugin/24695-git-open) plugin installed, **Merge Into** will automatically appear in the Git Open action group.

   ![Git Open Menu](https://raw.githubusercontent.com/iml885203/intellij-git-merge-into/main/readme/git-open-menu.png)

Once triggered, you'll see the merge process in action:

![Merge Action](https://raw.githubusercontent.com/iml885203/intellij-git-merge-into/main/readme/action.png)

### What It Does

- Checks out the target branch
- Fetches and resets to the latest remote state
- Merges your current branch into it
- Optionally pushes the result
- Checks out back to your original branch

### Settings

Go to <kbd>Settings</kbd> > <kbd>Tools</kbd> > <kbd>Git Merge Into</kbd> to configure:

- **Target branch** — the branch to merge into (default: `develop`)
- **Push after merge** — automatically push after a successful merge
- **Run in background** — run the merge process in the background
- **Abort merge when conflicts** — automatically abort and return to your branch on conflicts

You can also override the target branch per project under the **Git Merge Into (Project)** settings.

<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Git Merge Into"</kbd> >
  <kbd>Install</kbd>

- Manually:

  Download the [latest release](https://github.com/iml885203/intellij-git-merge-into/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---

Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
