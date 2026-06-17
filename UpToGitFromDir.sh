#!/usr/bin/env bash
# upload_repo.sh — lädt alle Dateien/Ordner im selben Verzeichnis ins angegebene Repo hoch (force push)
set -e

REPO="https://github.com/einsundnull/Dual-N-Back.git"

DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR" || exit 1

# Git init falls nötig
if [ ! -d .git ]; then
  echo "Kein Git-Repo gefunden — initialisiere."
  git init
fi

# Remote setzen (überschreibt vorhandene origin)
git remote remove origin 2>/dev/null || true
git remote add origin "$REPO"

# Alle Änderungen hinzufügen
git add -A

# Nur committen, wenn es Änderungen gibt
if [ -n "$(git status --porcelain)" ]; then
  git commit -m "Auto-commit: $(date -u +"%Y-%m-%dT%H:%M:%SZ")"
else
  echo "Keine Änderungen zum Committen."
fi

# Push mit force
if ! git push -u origin HEAD --force; then
  echo "Push HEAD fehlgeschlagen, versuche main..."
  git branch -M main 2>/dev/null || true
  if ! git push -u origin main --force; then
    echo "Push main fehlgeschlagen, versuche master..."
    git branch -M master 2>/dev/null || true
    git push -u origin master --force
  fi
fi

read -p "Fertig. Enter drücken zum Schließen..."
