#!/usr/bin/env bash
set -e

echo "🚀 Generating Angular structure for GestionEventLot1Front..."

# CORE MODULE
ng g m core --module app.module.ts

# CORE MODELS FOLDER
mkdir -p src/app/core/models

# CORE SERVICES
ng g s core/services/donation --skip-tests
ng g s core/services/association --skip-tests
ng g s core/services/event --skip-tests

# PAGES MODULES
ng g m pages/donations --routing --module app.module.ts
ng g m pages/associations --routing --module app.module.ts
ng g m pages/events --routing --module app.module.ts

# DONATIONS PAGES
ng g c pages/donations/donation-list --skip-tests
ng g c pages/donations/donation-detail --skip-tests

# ASSOCIATIONS PAGES
ng g c pages/associations/association-list --skip-tests
ng g c pages/associations/association-detail --skip-tests

# EVENTS PAGES (timeline for a donation)
ng g c pages/events/event-timeline --skip-tests

echo "✅ Structure generated successfully."
