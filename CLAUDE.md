# CLAUDE.md - N-Back Training Java Desktop Application

This file provides guidance to Claude Code when working with the **Java desktop version** of the N-Back cognitive training application.

---

## ⚠️ CRITICAL REFERENCE DOCUMENTS

**BEFORE making ANY changes to scoring logic, READ:**
- **SCORING_LOGIC.md** - Complete documentation of accuracy calculation and N-Back level adjustment
  - This is the REFERENCE IMPLEMENTATION that the JavaScript version must match
  - The `-1` penalty for wrong answers is CORRECT and MUST NOT be changed

These documents explain critical algorithms that MUST NOT be modified without understanding the impact.

---

## Project Overview

**N-Back Training** is a JavaFX-based desktop application for cognitive training using the N-Back working memory task.

**Technology Stack:**
- **JavaFX** - UI framework
- **Java 8+** - Programming language
- **Maven/Gradle** - Build system (check pom.xml or build.gradle)
- **Media API** - Audio playback for stimuli

**Project Structure:**
```
BT/
├── src/
│   ├── arrays/         - Utility array classes
│   ├── enums/          - Enums (Modes, TypeOfWindow, etc.)
│   ├── files/          - File I/O, results storage
│   ├── language/       - Internationalization
│   ├── lists/          - Data structures for training
│   ├── logics/         - Core logic (Results, DurationTraining)
│   ├── screens/        - UI screens (StartScreen, TrainingScreen, ResultScreen, OptionMenu)
│   ├── training/       - Training logic (Rect, TimerClass, AudioClass)
│   └── window/         - Custom UI components (CustomWindow, CustomButton, etc.)
├── resources/
│   └── audio/          - Audio files for stimuli
└── CLAUDE.md           - This file
```

---

## Core Architecture

### 1. Training Timing System (CRITICAL)

The training uses a **two-phase timer system** based on `indexCreateCounter`:

**File:** `training/Rect.java` + `training/TimerClass.java`

```java
// TimerClass.java
public static int repeatTime = 950; // Timer interval
private static int delay = 2000;    // Initial delay before first trial

timer.scheduleAtFixedRate(new TimerTask() {
    @Override
    public void run() {
        if (paused == false && interrupt == false) {
            counter++;
            if (counter >= speed) {
                runIt.run(); // Executes every repeatTime (950ms)
                counter = 0;
            }
        }
    }
}, delay, repeatTime);
```

**Trial Cycle (Rect.java lines 284-404):**

```
Timer Tick 1 (t=0):
  - indexCreateCounter becomes 1
  - Show stimulus (rect.get(X).setVisible(true))
  - Play audio
  - allowToRegisterClicks = true

Timer Tick 2 (t=repeatTime, e.g. 950ms):
  - indexCreateCounter becomes 2
  - Hide stimulus (rect.get(X).setVisible(false))
  - indexCreateCounter reset to 0

Timer Tick 3 (t=2×repeatTime, e.g. 1900ms):
  - indexCreateCounter becomes 1
  - Next trial starts
```

**Timing Summary:**
- **Stimulus visible:** `repeatTime` (950ms)
- **Pause (stimulus hidden):** `repeatTime` (950ms)
- **Total per trial:** `2 × repeatTime` (1900ms)
- **Clicks:** ALWAYS allowed during training (no explicit `allowToRegisterClicks = false` after stimulus hides)

**IMPORTANT:** The `speed` variable (default: 2) controls how many timer ticks must pass before executing the trial logic. With `speed=2` and `repeatTime=950`, the actual cycle is 1900ms.

---

### 2. Input System

**Keyboard Inputs (TrainingScreen.java lines 358-481):**
- **ESC** - Pause/Resume training
- **SPACE** - Continue to next screen
- **F1** - Toggle dropdown menu
- **F2** - Change square color (if color mode disabled)
- **PAGE_UP/DOWN** - Adjust repeatTime (+/- 15ms)
- **PLUS** - Toggle visibility of all rectangles (debug)
- **PRINTSCREEN** - Reset repeatTime to 900ms

**Match Confirmation:**
- **L** - Confirm audio match (default)
- **S** - Confirm position match (default)
- **D** - Confirm color match (default)
- **Left Mouse** - Confirm first mode
- **Middle Mouse** - Confirm second mode
- **Right Mouse** - Confirm third mode

**Match Detection Logic (Rect.java lines 291-304):**
```java
if (Lists.indexes[0][nback+1] != -1) {
    clickedRightMode = Results.registerClicks(matchesMode, clickedToConfirmMatch, clickedRightMode);
    rightValueMode = Results.increaseRightValue(clickedRightMode, rightValueMode);
    progressBars = DurationTraining.colorMatches(clickedRightMode, progressIndexModes, progressBars);
    clicksList = DurationTraining.storeDurationInput(clickedRightMode, clicksList);

    for (int n = 0; n < Results.includedModes; n++) {
        clickedToConfirmMatch[n] = false; // Reset for next trial
    }
}
```

---

### 3. Training Modes

**Available Modes (enums/Modes.java):**
- `AUDIO` - Audio only
- `POSITION` - Visual position only
- `COLOR` - Visual color only
- `AUDIO_POSITION` - Audio + Position (most common)
- `AUDIO_COLOR` - Audio + Color
- `COLOR_POSITION` - Color + Position
- `AUDIO_COLOR_POSITION` - All three modes

**Mode Configuration:**
- Set in `StartScreen` via checkboxes
- Stored in static boolean variables:
  - `StartScreen.includeAudio`
  - `StartScreen.includePosition`
  - `StartScreen.includeColor`

**Multi-Modal Training:**
Each mode has separate accuracy tracking:
- `percentageList[Rect.indexPlayAudio]` - Audio accuracy
- `percentageList[Rect.indexShowPosition]` - Position accuracy
- `percentageList[Rect.indexShowColor]` - Color accuracy

---

### 4. Visual Layout System

**Rectangle Grid (Rect.java lines 176-210):**

9 positions in a 3×3 grid (positions 0-8):
```
0 (top-left)    1 (top-center)    2 (top-right)
3 (mid-left)    4 (center)        5 (mid-right)
6 (bot-left)    7 (bot-center)    8 (bot-right)
```

**Position 4 (center):** Used for:
- Color-only mode (colored square in center)
- Text mode (text appears in center)

**Color System (lists/Lists.java):**
```java
public static String[] colors = {
    "red", "blue", "green", "yellow",
    "orange", "purple", "pink", "cyan", "lime"
};
```

**Square Styling:**
- `inside` pane: Black background, rectSize × rectSize
- `outside` pane: Dark gray border (#1e1e1e), slightly larger

---

### 5. Results & Data Storage

**File Storage (files/ResultsFiles.java):**
- Results stored as text files in user directory
- Separate files for:
  - Regular training: `storingPathRegular`
  - Duration training: `storingPathDuration`
  - Mode-specific percentages: `tempAudioDurationPercentage`, etc.

**Data Format:**
```
date:
2024-12-26

n-back:
3

percentage:
0.85

sessionID:
12345678

day:
2024-12-26
```

**Results Calculation (logics/Results.java):**
- Accuracy calculated per mode
- Overall accuracy = average of all active modes
- Adaptive difficulty: N-level increases/decreases based on accuracy thresholds

---

### 6. Language System

**Supported Languages (language/LanguageClass.java):**
- Over 30 languages supported
- Audio files per language: `/audio/{lang_code}{number}.mp3`
- Example: `/audio/de3.mp3` (German, number 3)

**Language Selection:**
- Stored in `StartScreen.indexLang`
- Language codes map to audio file directories
- Fallback to English (index 3) for unsupported languages (Rect.java lines 361-393)

**Word Lookup:**
```java
String text = LanguageClass.word(StartScreen.indexLang, 22); // Get word ID 22 in current language
```

---

### 7. Pause System

**Pause Behavior (TrainingScreen.java lines 420-469):**

**On ESC Press (while training):**
1. Set `TimerClass.paused = true`
2. Show pause overlay (`lblPause`, `lblESC`)
3. Show EXIT button
4. Hide stimuli
5. Show current N-level info
6. Show cursor (normally hidden during training)

**On ESC Press (while paused):**
1. Set `TimerClass.paused = false`
2. Hide pause overlay
3. Hide EXIT button
4. Resume training from current trial
5. Hide cursor

**Pause Tracking:**
- No explicit pause duration tracking in Java version
- Timer simply stops when `paused == true`
- Resumes from exact same state when `paused == false`

---

### 8. Adaptive Difficulty System

**N-Level Adjustment (logics/Results.java):**
- Accuracy thresholds determine level changes
- Implemented via performance monitoring
- Level increases when accuracy consistently high
- Level decreases when accuracy too low

**Implementation:**
- Check accuracy after each trial
- Compare against thresholds
- Adjust `Rect.nback` value
- Update UI to show new level

---

### 9. Custom UI Components

**CustomWindow (window/CustomWindow.java):**
- Fullscreen stage management
- Cursor hiding: `hideCursor(true)`
- Resizable control
- Mouse drag control

**CustomButton (window/CustomButton.java):**
- Styled buttons with frames
- Layout helpers
- Click handlers

**CustomClock (window/CustomClock.java):**
- Session timer
- Pause/resume functionality
- Display in MM:SS format

---

### 10. Common Development Tasks

#### Adding a New Language

1. **Add language to LanguageClass.java** - Add new index and word mappings
2. **Create audio files** - Record/generate audio for numbers 0-9 in `/audio/{lang_code}{number}.mp3`
3. **Test TTS fallback** - Ensure fallback to English works (Rect.java lines 361-393)

#### Modifying Timing

**To change trial speed:**
```java
// TimerClass.java line 22
public static int repeatTime = 950; // Change this value

// Or use keyboard shortcuts during training:
// PAGE_UP: Increase repeatTime by 15ms
// PAGE_DOWN: Decrease repeatTime by 15ms
```

**IMPORTANT:** Remember that total trial duration = `2 × repeatTime` due to the two-phase counter system.

#### Adding a New Training Mode

1. **Add enum** - `enums/Modes.java`
2. **Update UI** - `screens/StartScreen.java` (add checkbox)
3. **Update stimulus display** - `training/Rect.java` (lines 345-357)
4. **Update results display** - `screens/TrainingScreen.java` (lines 327-356)

#### Debugging Timing Issues

**Enable visual debugging:**
- Press **PLUS** key during training to show all rectangles
- Use `testPane` (TrainingScreen.java lines 158-164) to display debug values
- Uncomment lines 249-251 in Rect.java to show trial counters

**Console logging:**
- `indexCreateCounter` value shows current phase (1 or 2)
- `Results.presentations` shows current trial number
- `TimerClass.passedTrials` shows completed trials

---

## File System Structure

### Critical Files (Most Frequently Modified)

1. **training/Rect.java** (426 lines)
   - Main training loop
   - Timer logic
   - Stimulus display
   - Audio playback

2. **training/TimerClass.java** (62 lines)
   - Timer configuration
   - Timing constants
   - Pause control

3. **screens/TrainingScreen.java** (752 lines)
   - Training UI
   - Keyboard handlers
   - Pause screen
   - Results display

4. **logics/Results.java**
   - Accuracy calculation
   - Match detection
   - Performance tracking

5. **screens/StartScreen.java**
   - Main menu
   - Mode selection
   - Session configuration

### Configuration Files

- **User settings:** Stored via `files/FilesCls.java`
- **Audio files:** `/resources/audio/`
- **Results data:** User directory (platform-dependent)

---

## Timing Comparison: Java vs JavaScript

| Aspect | Java Version | JavaScript Version |
|--------|--------------|-------------------|
| Stimulus Duration | `repeatTime` (950ms) | `repeatTime` (950ms) ✅ |
| Pause Duration | `repeatTime` (950ms) | `repeatTime` (950ms) ✅ |
| Total per Trial | `2 × repeatTime` (1900ms) | `2 × repeatTime` (1900ms) ✅ |
| Click Window | Always open | Always open ✅ |
| Initial Delay | 2000ms | Configurable |
| Timer Mechanism | `scheduleAtFixedRate` | Nested `setTimeout` |

**JavaScript Implementation:** See `TrainingManager.js` lines 470-527

---

## Known Issues & Quirks

1. **Language Fallback** - Many languages fall back to English audio (Rect.java lines 361-393)
2. **Speed Variable** - `TimerClass.speed = 2` requires 2 ticks before executing logic
3. **IndexCreateCounter** - Must cycle through 1 → 2 → 0 for proper trial flow
4. **Clicks Always Allowed** - No explicit `allowToRegisterClicks = false` during pause between stimuli
5. **Fullscreen Only** - Training always runs in fullscreen mode

---

## Testing Checklist

### Basic Training Flow
- [ ] Training starts with correct N-level
- [ ] Stimuli appear for `repeatTime` duration
- [ ] Pause between trials equals `repeatTime`
- [ ] Total trial cycle = `2 × repeatTime` (1900ms default)
- [ ] Audio plays correctly
- [ ] Clicks register during entire training

### Multi-Modal
- [ ] Audio + Position mode works
- [ ] All three modes work together
- [ ] Accuracy tracked separately per mode
- [ ] Correct stimuli displayed for each mode

### Pause/Resume
- [ ] ESC pauses training
- [ ] ESC resumes from exact state
- [ ] EXIT button ends session
- [ ] Cursor shows/hides correctly

### Results
- [ ] Accuracy calculated correctly
- [ ] Results saved to files
- [ ] Results screen displays all modes
- [ ] Session ID unique

---

## Build & Run

**Eclipse:**
1. Import project as existing Java project
2. Ensure JavaFX libraries are configured
3. Run `StartScreen.java` as Java Application

**Command Line:**
```bash
# Compile
javac -cp .:javafx-sdk/lib/* src/**/*.java

# Run
java -cp .:javafx-sdk/lib/* --module-path javafx-sdk/lib --add-modules javafx.controls,javafx.media screens.StartScreen
```

---

## Recent Changes (2025-12-26)

### JavaScript Timing Synchronization
- Updated JavaScript version to match Java timing exactly
- Total per trial now `2 × repeatTime` (1900ms with default 950ms)
- Clicks remain enabled throughout training (like Java)
- See `TrainingManager.js` for implementation

---

**Last Updated:** 2025-12-26
**Java Version:** 8+
**JavaFX Version:** 11+
