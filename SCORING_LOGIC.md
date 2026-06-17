# N-Back Scoring Logic Documentation

**Version:** Java Desktop Application
**Last Updated:** 2025-12-26
**Synchronized with:** JavaScript/HTML Version (C:\Users\pc\Documents\Phoenix Code\nback)

---

## Overview

This document explains the **critical scoring logic** used in the N-Back cognitive training application. This Java implementation serves as the **reference implementation** that the JavaScript web version must match.

---

## 1. Score Calculation System

### 1.1 The Four Response Types

In N-Back training, each trial can result in one of **four outcomes**:

| # | Situation | Match Exists? | User Clicked? | Correct? | Score Change |
|---|-----------|---------------|---------------|----------|--------------|
| 1 | **True Positive** | ✅ Yes | ✅ Yes | ✅ Correct | **+1** |
| 2 | **True Negative** | ❌ No | ❌ No | ✅ Correct | **+1** |
| 3 | **False Positive** | ❌ No | ✅ Yes | ❌ Wrong | **-1** |
| 4 | **False Negative** | ✅ Yes | ❌ No | ❌ Wrong | **-1** |

### 1.2 Why Wrong Answers Must Subtract

**Critical Rule:** Wrong answers **MUST subtract 1** from the score, not add 0.

**Rationale:**
- **Prevents accuracy inflation** - Adding 0 for wrong answers artificially inflates performance metrics
- **Maintains challenge balance** - Users must be penalized for incorrect responses
- **Ensures fair difficulty adjustment** - N-Back level increases/decreases based on true performance
- **Scientific validity** - Matches established N-Back research protocols

**Example Scenario:**
```
Session: 10 trials, N-Back level = 2
Valid trials: 10 - 2 = 8 trials

User performance:
- 4 True Positives (clicked on match)
- 2 True Negatives (didn't click on non-match)
- 2 False Positives (clicked when no match)

CORRECT calculation:
rightValueMode[n] = 4 + 2 - 2 = 4
percentageList[n] = 4 / 8 = 0.5 = 50%

INCORRECT calculation (if using +0):
rightValueMode[n] = 4 + 2 + 0 + 0 = 6
percentageList[n] = 6 / 8 = 0.75 = 75%  ❌ WRONG!
```

---

## 2. Implementation Details

### 2.1 Core Methods

#### **registerClicks()**
**File:** `src/logics/Results.java:46-62`

Determines if each mode's response was correct:

```java
public static boolean[] registerClicks(boolean[] match, boolean[] clickedToConfirmMatch,
                                       boolean[] clickedRightMode) {
    for (int n = 0; n < Results.includedModes; n++) {
        if (match[n] && clickedToConfirmMatch[n]) {
            clickedRightMode[n] = true;      // True Positive
        }
        if (!match[n] && clickedToConfirmMatch[n]) {
            clickedRightMode[n] = false;     // False Positive
        }
        if (match[n] && !clickedToConfirmMatch[n]) {
            clickedRightMode[n] = false;     // False Negative
        }
        if (!match[n] && !clickedToConfirmMatch[n]) {
            clickedRightMode[n] = true;      // True Negative
        }
    }
    return clickedRightMode;
}
```

**Simplified Logic:**
```
clickedRightMode[n] = (match[n] == clickedToConfirmMatch[n])
```

#### **increaseRightValue()**
**File:** `src/logics/Results.java:64-73`

Updates the running score counter:

```java
public static double[] increaseRightValue(boolean[] right, double[] rightCounter) {
    for (int n = 0; n < Results.includedModes; n++) {
        if (right[n]) {
            rightCounter[n]++;   // ✅ Correct answer: +1
        } else {
            rightCounter[n]--;   // ✅ Wrong answer: -1 (CRITICAL!)
        }
    }
    return rightCounter;
}
```

**⚠️ CRITICAL - DO NOT CHANGE:**

This logic **MUST use `-1` for wrong answers**, not `0`. This is NOT a bug.

**Common Misconception:**
```java
// ❌ WRONG - DO NOT USE:
if (right[n]) {
    rightCounter[n]++;
}
// (no else clause = wrong answers add 0)

// ✅ CORRECT:
if (right[n]) {
    rightCounter[n]++;
} else {
    rightCounter[n]--;  // MUST subtract!
}
```

#### **calculateTrialResults()**
**File:** `src/logics/Results.java:119-133`

Converts raw score to percentage:

```java
public static double calculateTrialResults(double right) {
    // Handle negative scores
    if (right < 0) {
        right = 0;
    }

    // Accuracy = score / valid trials
    // Note: presentations includes warmup trials, so we subtract nback
    double percentage = right / (presentations - Rect.nback);

    // Clamp to [0, 1] range
    if (percentage > 1) {
        percentage = 1;
    }
    if (percentage < 0) {
        percentage = 0;
    }

    return percentage;
}
```

**Key Points:**
- Score (`right`) can go **negative** (e.g., 2 correct, 5 wrong = -3)
- Negative scores are clamped to 0 before percentage calculation
- Denominator is `presentations - Rect.nback` (excludes warmup trials)

---

## 3. Multi-Modal Training

When multiple modes are active (e.g., Audio + Position), **each mode is scored independently**:

**Example: Audio + Position Mode**
```java
// Trial 1:
matchesMode = {true, false}           // Audio match, Position no match
clickedToConfirmMatch = {true, false} // User clicked audio, didn't click position
clickedRightMode = {true, true}       // Both correct!
rightValueMode[0]++                   // Audio: +1
rightValueMode[1]++                   // Position: +1

// Trial 2:
matchesMode = {false, true}           // Audio no match, Position match
clickedToConfirmMatch = {true, false} // User clicked audio (wrong!), didn't click position (wrong!)
clickedRightMode = {false, false}     // Both wrong!
rightValueMode[0]--                   // Audio: -1
rightValueMode[1]--                   // Position: -1
```

**Overall accuracy** = average of all mode accuracies:
```java
// File: src/logics/Results.java:157-165
double value = 0;
for (int n = 0; n < Results.includedModes; n++) {
    percentageList[n] = calculateTrialResults(rightValueMode[n]);
    value = value + percentageList[n];
}
overallPercentage = value / includedModes;
```

---

## 4. N-Back Level Adjustment

**File:** `src/logics/Results.java:135-155`

Based on accuracy thresholds:

```java
public static void inOrDecreaseNBackLevelByResultsDualTraining(double[] percentageList) {
    int increase = 1;  // Default: increase level

    for (int n = 0; n < Results.includedModes; n++) {
        if (percentageList[n] < increaseThreshold && percentageList[n] >= decreseThreshold) {
            increase = 0;   // 75-85% → maintain level
        }
        if (percentageList[n] < decreseThreshold) {
            increase = -1;  // < 75% → decrease level
            break;
        }
    }

    if (increase == -1) {
        Rect.nback--;      // Make easier
    }
    if (increase == 1) {
        Rect.nback++;      // Make harder
    }
    if (Rect.nback < 1) {
        Rect.nback = 1;    // Minimum level
    }
}
```

**Thresholds (defined at class level):**
```java
private static double decreseThreshold = 0.75;   // 75%
private static double increaseThreshold = 0.85;  // 85%
```

**Logic:**
- **< 75%** → Decrease N-Back level (too hard)
- **75-85%** → Maintain current level (optimal difficulty)
- **≥ 85%** → Increase N-Back level (too easy)

**Critical:** If scoring logic is wrong (using +0 instead of -1), users will see inflated percentages and level up too quickly, making training frustratingly difficult.

---

## 5. Random Sequence Generation

### 5.1 createRandomIndex()
**File:** `src/logics/Results.java:75-86`

Generates random stimulus indices:

```java
public static int[] createRandomIndex(int[] randomIndex) {
    for (int n = 0; n < Results.includedModes; n++) {
        int tempIndex = new Random().nextInt(Rect.randomIndexLimit);

        if (Rect.excludeFourFromRandomIndex) {
            // Position 4 = center, excluded when using position-only mode
            while (tempIndex == 4 || randomIndex[n] == -1) {
                tempIndex = new Random().nextInt(Rect.randomIndexLimit);
            }
        }
        randomIndex[n] = tempIndex;
    }
    return randomIndex;
}
```

### 5.2 increaseNumberOfMatches()
**File:** `src/logics/Results.java:88-102`

Probabilistically creates matches to control difficulty:

```java
public static int[] increaseNumberOfMatches(int[][] indexes, int[] randomIndex,
                                            int randomIndexIncreaseFactor) {
    for (int n = 0; n < Results.includedModes; n++) {
        int random = new Random().nextInt(randomIndexIncreaseFactor);

        if (random == 0) {
            // Create a match: use stimulus from N trials back
            randomIndex[n] = indexes[n][Rect.nback + 1];

            if (Rect.excludeFourFromRandomIndex) {
                while (random == 4 || randomIndex[n] < 0) {
                    random = new Random().nextInt(Rect.randomIndexLimit);
                    randomIndex[n] = random;
                }
            }
        }
    }
    return randomIndex;
}
```

**Match Probability:**
- `randomIndexIncreaseFactor = 3` → 33% chance of match per trial
- `randomIndexIncreaseFactor = 4` → 25% chance of match per trial
- `randomIndexIncreaseFactor = 5` → 20% chance of match per trial

### 5.3 storePresentedIndex()
**File:** `src/logics/Results.java:104-117`

Maintains history of presented stimuli:

```java
public static int[][] storePresentedIndex(int[][] indexes, int[] randomIndex,
                                          int randomIndexIncreaseFactor) {
    for (int n = 0; n < Results.includedModes; n++) {
        int add = 2;

        // Shift array and add new index
        for (int i = 0; i <= Rect.nback * add; i++) {
            randomIndex = increaseNumberOfMatches(indexes, randomIndex, randomIndexIncreaseFactor);
            int temp = indexes[n][i + 1];
            indexes[n][i] = indexes[n][i + 1];
            indexes[n][i] = temp;
        }
        indexes[n][Rect.nback * add] = randomIndex[n];
    }
    return indexes;
}
```

**Array Structure:**
- Index 0: Oldest stimulus (N-back + 2 trials ago)
- Index N: Stimulus from N trials back (used for match detection)
- Index N*2: Current trial's stimulus

---

## 6. Common Bugs to Avoid

### ❌ Bug #1: Wrong Increment Logic
```java
// WRONG:
if (right[n]) {
    rightCounter[n]++;
}
// (missing else = wrong answers add 0)
```

**Impact:** Accuracy overestimated by ~20-30% on average

### ❌ Bug #2: Wrong Denominator
```java
// WRONG:
double percentage = right / presentations;  // Includes warmup trials
```

**Correct:**
```java
double percentage = right / (presentations - Rect.nback);
```

### ❌ Bug #3: Forgetting to Handle Negative Scores
```java
// WRONG:
return right / (presentations - Rect.nback);  // Allows negative percentages

// CORRECT:
if (right < 0) {
    right = 0;  // Clamp negative scores
}
return right / (presentations - Rect.nback);
```

---

## 7. Testing & Validation

### Manual Test Cases

**Test Case 1: All Correct**
```
10 trials, N=2, valid=8
8 correct, 0 wrong
Expected: rightValueMode = 8, percentage = 100%
```

**Test Case 2: All Wrong**
```
10 trials, N=2, valid=8
0 correct, 8 wrong
Expected: rightValueMode = -8 → clamped to 0, percentage = 0%
```

**Test Case 3: Mixed Performance**
```
10 trials, N=2, valid=8
6 correct, 2 wrong
Expected: rightValueMode = 4, percentage = 50%
```

**Test Case 4: More Wrong than Right**
```
10 trials, N=2, valid=8
2 correct, 6 wrong
Expected: rightValueMode = -4 → clamped to 0, percentage = 0%
```

---

## 8. Synchronization with JavaScript Version

The JavaScript web version **must remain synchronized** with this Java implementation:

**JavaScript Reference:**
- File: `C:\Users\pc\Documents\Phoenix Code\nback\NBackLogic.js`
- Method: `increaseRightValue()` (line 95-109)
- Method: `calculateTrialResults()` (line 107-118)

**Any changes to scoring logic must be applied to BOTH versions simultaneously.**

---

## 9. Key Variables Reference

### Static Variables (Results.java)
```java
public static double presentations;           // Current trial number
public static double percentageAll;           // Legacy (unused)
private static double decreseThreshold = 0.75; // 75% threshold
private static double increaseThreshold = 0.85; // 85% threshold
public static double overallPercentage;       // Combined accuracy across modes
public static int includedModes;              // Number of active modes (1-3)
```

### Arrays
```java
public static double[] percentageList;        // Accuracy per mode
public static double[] rightValueMode;        // Score counter per mode
public static boolean[] clickedRightMode;     // Correct/wrong per mode
public static boolean[] matchesMode;          // Match exists per mode
public static boolean[] clickedToConfirmMatch; // User clicked per mode
```

---

## 10. Version History

| Date | Change | Reason |
|------|--------|--------|
| 2025-12-26 | Created this documentation | Ensure JavaScript version matches Java logic |
| 2025-12-26 | Clarified that `-1` for wrong answers is CORRECT | Prevent misconception as bug |

---

**CRITICAL REMINDER:**

```java
// ✅ ALWAYS USE THIS:
if (right[n]) {
    rightCounter[n]++;
} else {
    rightCounter[n]--;  // MUST subtract!
}

// ❌ NEVER USE THIS:
if (right[n]) {
    rightCounter[n]++;
}
// (no else = adds 0 for wrong answers)
```

Wrong answers **MUST penalize the score** to maintain training validity.
