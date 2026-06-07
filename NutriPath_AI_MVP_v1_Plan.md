# NutriPath AI — MVP v1 Plan

## 1. MVP v1 Target

NutriPath AI MVP v1 tập trung vào bài toán cốt lõi:

> User thiết lập mục tiêu dinh dưỡng cá nhân, app tính ra kế hoạch calories/macros hằng ngày, user log bữa ăn, và app theo dõi lượng đã tiêu thụ/còn lại trong ngày.

MVP v1 chưa xử lý adaptive tracking dài hạn. Phần weekly check-in, phân tích tiến độ theo tuần, và tự điều chỉnh calories sẽ để cho v2.

---

## 2. Scope MVP v1

### 2.1. MVP v1 bao gồm

- Onboarding để user nhập thông tin cơ thể và mục tiêu.
- Tính BMR.
- Tính TDEE.
- Tính goal calories.
- Tính macro target: protein, fat, carb.
- Tính goal estimation: ước tính thời gian đạt target weight.
- Update profile / goal và recalculate active nutrition plan.
- Add meal manually bằng cách nhập calories/macros.
- Quick add meal từ bộ món có sẵn.
- User nhập định lượng cho quick add, ví dụ món có dữ liệu trên 100g, user nhập 300g.
- Remove meal entry.
- Observe daily nutrition summary: consumed và remaining calories/macros.

### 2.2. MVP v1 không bao gồm

- Weekly check-in.
- Adaptive calorie adjustment theo tiến độ thực tế.
- Weight progress analysis.
- Food recognition bằng AI.
- Barcode scan.
- Ingredient-based recipe calculation.
- Large external food database.
- Exercise calorie integration.
- Google Fit / Apple Health integration.
- Micronutrient tracking.

---

## 3. Core User Flow

```text
User mở app lần đầu
→ Onboarding
→ Nhập height, weight, age, gender, PAL
→ Chọn goal type, goal speed, target weight
→ App tính nutrition plan
→ User vào Today Dashboard
→ User add meal manually hoặc quick add
→ App cập nhật consumed / remaining calories và macros
```

---

## 4. Nutrition Formulas

### 4.1. BMR — Mifflin-St Jeor

```text
BMR = 10W + 6.25H - 5A + S
```

Trong đó:

```text
W = weight in kg
H = height in cm
A = age
S = +5 nếu male, -161 nếu female
```

Ví dụ Kotlin:

```kotlin
fun calculateBmr(
    weightKg: Double,
    heightCm: Double,
    age: Int,
    gender: Gender
): Double {
    val genderFactor = if (gender == Gender.MALE) 5.0 else -161.0
    return 10.0 * weightKg + 6.25 * heightCm - 5.0 * age + genderFactor
}
```

---

### 4.2. TDEE

```text
TDEE = BMR × ActivityFactor
```

Activity factor / PAL:

```text
Sedentary: 1.2
Lightly active: 1.375
Moderately active: 1.55
Very active: 1.725
```

Ví dụ Kotlin:

```kotlin
enum class ActivityLevel(val factor: Double) {
    SEDENTARY(1.2),
    LIGHT(1.375),
    MODERATE(1.55),
    VERY_ACTIVE(1.725)
}

fun calculateTdee(bmr: Double, activityLevel: ActivityLevel): Double {
    return bmr * activityLevel.factor
}
```

---

### 4.3. Target Calories

#### Maintain weight

```text
targetCalories = TDEE
```

#### Lose weight

```text
targetCalories = TDEE - deficit
```

MVP v1 goal speed:

```text
Slow loss: -250 kcal/day
Normal loss: -500 kcal/day
```

#### Gain weight

```text
targetCalories = TDEE + surplus
```

MVP v1 goal speed:

```text
Slow gain: +250 kcal/day
Normal gain: +400 kcal/day
```

Ví dụ Kotlin:

```kotlin
enum class GoalSpeed(val calorieAdjustment: Double) {
    SLOW_LOSS(-250.0),
    NORMAL_LOSS(-500.0),
    MAINTAIN(0.0),
    SLOW_GAIN(250.0),
    NORMAL_GAIN(400.0)
}

fun calculateTargetCalories(tdee: Double, goalSpeed: GoalSpeed): Double {
    return tdee + goalSpeed.calorieAdjustment
}
```

---

### 4.4. Protein Target

MVP v1 nên tính protein theo cân nặng.

```text
Maintain: proteinG = weightKg × 1.4
Lose weight: proteinG = weightKg × 1.6
Gain weight: proteinG = weightKg × 1.6
```

Ví dụ Kotlin:

```kotlin
fun calculateProtein(weightKg: Double, goalType: GoalType): Double {
    val ratio = when (goalType) {
        GoalType.MAINTAIN -> 1.4
        GoalType.LOSE_WEIGHT -> 1.6
        GoalType.GAIN_WEIGHT -> 1.6
    }
    return weightKg * ratio
}
```

---

### 4.5. Fat Target

Fat nên tính theo target calories, không phải TDEE.

```text
fatCalories = targetCalories × 0.25
fatG = fatCalories / 9
```

Ví dụ Kotlin:

```kotlin
fun calculateFat(targetCalories: Double): Double {
    return (targetCalories * 0.25) / 9.0
}
```

---

### 4.6. Carb Target

Carb lấy phần calories còn lại sau protein và fat.

```text
carbCalories = targetCalories - proteinG × 4 - fatG × 9
carbG = carbCalories / 4
```

Ví dụ Kotlin:

```kotlin
fun calculateCarb(
    targetCalories: Double,
    proteinG: Double,
    fatG: Double
): Double {
    val proteinCalories = proteinG * 4.0
    val fatCalories = fatG * 9.0
    val remainingCalories = targetCalories - proteinCalories - fatCalories

    require(remainingCalories >= 0.0) {
        "Invalid macro plan: remaining calories for carbs is negative."
    }

    return remainingCalories / 4.0
}
```

---

### 4.7. Goal Estimation

Goal estimation giúp user biết ước tính bao lâu đạt target weight.

Công thức tổng quát:

```text
calorieGapPerDay = abs(targetCalories - TDEE)
estimatedKgPerWeek = calorieGapPerDay × 7 / 7700
weightDiff = abs(currentWeight - targetWeight)
estimatedWeeks = weightDiff / estimatedKgPerWeek
```

Ví dụ Kotlin:

```kotlin
fun estimateGoalWeeks(
    currentWeightKg: Double,
    targetWeightKg: Double,
    tdee: Double,
    targetCalories: Double
): Double? {
    val weightDiff = kotlin.math.abs(targetWeightKg - currentWeightKg)
    val calorieGapPerDay = kotlin.math.abs(targetCalories - tdee)

    if (weightDiff <= 0.0) return 0.0
    if (calorieGapPerDay <= 0.0) return null

    val estimatedKgPerWeek = (calorieGapPerDay * 7.0) / 7700.0
    if (estimatedKgPerWeek <= 0.0) return null

    return weightDiff / estimatedKgPerWeek
}
```

Ghi chú business:

```text
Goal estimation chỉ là ước tính ban đầu, không phải cam kết chính xác.
Weekly check-in ở v2 sẽ giúp estimate chính xác hơn dựa trên dữ liệu thực tế.
```

---

### 4.8. Quick Add Scaling Formula

Food dataset lưu nutrition theo base amount, ví dụ 100g.

```text
scaledCalories = baseCalories × inputAmount / baseAmount
scaledProtein = baseProtein × inputAmount / baseAmount
scaledFat = baseFat × inputAmount / baseAmount
scaledCarb = baseCarb × inputAmount / baseAmount
```

Ví dụ:

```text
Phở bò trong dataset:
Calories: 180 kcal / 100g
Protein: 9g / 100g
Fat: 5g / 100g
Carb: 24g / 100g

User nhập amount = 300g

Calories = 180 × 300 / 100 = 540 kcal
Protein = 9 × 300 / 100 = 27g
Fat = 5 × 300 / 100 = 15g
Carb = 24 × 300 / 100 = 72g
```

Ví dụ Kotlin:

```kotlin
fun scaleNutrition(
    baseCalories: Double,
    baseProteinG: Double,
    baseFatG: Double,
    baseCarbG: Double,
    baseAmountG: Double,
    inputAmountG: Double
): ScaledNutrition {
    val ratio = inputAmountG / baseAmountG
    return ScaledNutrition(
        calories = baseCalories * ratio,
        proteinG = baseProteinG * ratio,
        fatG = baseFatG * ratio,
        carbG = baseCarbG * ratio
    )
}
```

---

### 4.9. Daily Nutrition Summary Formula

Consumed values:

```text
consumedCalories = sum(mealEntry.calories)
consumedProtein = sum(mealEntry.proteinG)
consumedFat = sum(mealEntry.fatG)
consumedCarb = sum(mealEntry.carbG)
```

Remaining values:

```text
remainingCalories = targetCalories - consumedCalories
remainingProtein = targetProtein - consumedProtein
remainingFat = targetFat - consumedFat
remainingCarb = targetCarb - consumedCarb
```

Daily summary là derived data:

```text
DailyNutritionSummary = ActiveNutritionPlan + MealEntries by selected date
```

---

## 5. MVP v1 Use Cases

---

# UC1: Calculate Nutrition Plan From Onboarding

## Mục tiêu

User lần đầu vào app, nhập thông tin cơ thể và mục tiêu. Hệ thống tính ra nutrition plan ban đầu.

## Actor

```text
User
```

## Input

User nhập:

```text
Height
Weight
Age
Gender
PAL / Activity level
Goal type: lose / maintain / gain
Goal speed
Target weight
```

## Main Flow

```text
1. User mở app lần đầu.
2. App hiển thị onboarding flow.
3. User nhập body profile: height, weight, age, gender, PAL.
4. User chọn goal type.
5. User chọn goal speed.
6. User nhập target weight nếu goal là lose/gain.
7. User xác nhận.
8. System validate input.
9. System tính BMR.
10. System tính TDEE.
11. System tính target calories.
12. System tính protein, fat, carb target.
13. System tính goal estimation.
14. System tạo active NutritionPlan.
15. System chuyển user vào Today Dashboard.
```

## Output

```text
BMR
TDEE
Target calories
Target protein
Target fat
Target carb
Estimated weight change per week
Estimated goal time
Active nutrition plan
```

## Business Rules

```text
Nutrition plan được tính dựa trên thông tin user nhập tại thời điểm onboarding.
Kết quả này trở thành active plan hiện tại.
Goal estimation chỉ là ước tính ban đầu.
```

---

# UC2: Update Profile And Recalculate Active Nutrition Plan

## Mục tiêu

User cập nhật lại thông tin cơ thể hoặc goal. System dùng thông tin mới làm baseline mới và tính lại active nutrition plan.

## Actor

```text
User
```

## Input

User có thể update:

```text
Age
Height
Weight
PAL / Activity level
Goal type
Goal speed
Target weight
```

## Main Flow

```text
1. User vào Profile / Edit Goal screen.
2. User chỉnh sửa một hoặc nhiều thông tin.
3. User xác nhận update.
4. System validate input.
5. System cập nhật UserProfile / Goal.
6. System xem dữ liệu mới là baseline mới.
7. System tính lại BMR.
8. System tính lại TDEE.
9. System tính lại target calories.
10. System tính lại macro target.
11. System tính lại goal estimation.
12. System cập nhật hoặc tạo lại active NutritionPlan.
13. System giữ nguyên các meal entry đã log.
14. Today Dashboard cập nhật lại consumed / remaining theo plan mới.
```

## Output

```text
Updated UserProfile
Updated Active NutritionPlan
Updated goal estimation
Updated daily remaining values
```

## Business Rules

```text
Update profile không xoá meal logs.
Update profile không reset toàn bộ app.
Update profile tạo baseline mới cho nutrition plan từ thời điểm hiện tại.
Goal estimation được tính lại dựa trên current weight mới và target weight hiện tại.
Meal entries đã nhập trong ngày vẫn được giữ nguyên.
```

Ví dụ:

```text
Trước update:
Target calories = 2100
Consumed today = 800
Remaining = 1300

Sau update:
Target calories mới = 1900
Consumed today vẫn = 800
Remaining mới = 1100
```

---

# UC3: Add Meal Manually

## Mục tiêu

User log một meal entry bằng cách tự nhập thông tin dinh dưỡng.

## Actor

```text
User
```

## Input

User nhập:

```text
Meal name
Meal type
Calories
Protein
Fat
Carb
Date
```

Meal type:

```text
Breakfast
Lunch
Dinner
Snack
```

## Main Flow

```text
1. User mở Today Dashboard.
2. User chọn Add Meal.
3. User chọn manual input.
4. User chọn meal type.
5. User nhập tên món.
6. User nhập calories, protein, fat, carb.
7. User xác nhận.
8. System validate input.
9. System tạo MealEntry mới cho ngày hiện tại.
10. System lưu MealEntry.
11. System cập nhật daily consumed.
12. System cập nhật daily remaining.
13. User quay lại Today Dashboard.
```

## Output

```text
New MealEntry
Updated daily consumed calories/macros
Updated daily remaining calories/macros
```

## Business Rules

```text
Mỗi lần user nhập tay một món được tính là một MealEntry.
MealEntry là dữ liệu user đã ăn trong một ngày cụ thể.
Manual input yêu cầu user tự biết calories/macros của món ăn.
```

---

# UC4: Quick Add Meal From Food Dataset

## Mục tiêu

User thêm nhanh món ăn từ bộ món có sẵn, sau đó nhập định lượng để hệ thống tự scale calories/macros.

## Actor

```text
User
```

## Input

User chọn:

```text
Food item từ dataset
Serving amount / quantity
Meal type
Date
```

Food dataset cần lưu rõ base amount, ví dụ:

```text
Phở bò:
Base amount: 100g
Calories: 180 kcal / 100g
Protein: 9g / 100g
Fat: 5g / 100g
Carb: 24g / 100g
```

## Main Flow

```text
1. User mở Add Meal screen.
2. User chọn tab Quick Add.
3. User search tên món.
4. System hiển thị danh sách món phù hợp.
5. User chọn một món.
6. User nhập định lượng, ví dụ 300g.
7. User chọn meal type.
8. System scale calories/macros dựa trên định lượng.
9. User xác nhận.
10. System tạo MealEntry mới từ món đã chọn.
11. System lưu MealEntry.
12. System cập nhật daily consumed.
13. System cập nhật daily remaining.
```

## Output

```text
New MealEntry generated from Food item
Scaled calories/macros
Updated daily consumed
Updated daily remaining
```

## Business Rules

```text
Food trong dataset chỉ là dữ liệu tham chiếu.
Khi user quick add, system tạo ra một MealEntry mới.
MealEntry lưu lại calories/macros đã được scale tại thời điểm thêm.
Không nên chỉ lưu foodId + amount vì nếu Food dataset thay đổi thì log cũ có thể bị lệch.
```

---

# UC5: Remove Meal Entry

## Mục tiêu

User xoá một meal entry đã thêm trong ngày.

## Actor

```text
User
```

## Input

```text
MealEntry id
```

## Main Flow

```text
1. User mở Today Dashboard hoặc Meal List.
2. User chọn một MealEntry.
3. User chọn Remove/Delete.
4. System hỏi xác nhận nếu cần.
5. User xác nhận xoá.
6. System xoá MealEntry.
7. System cập nhật lại daily consumed.
8. System cập nhật lại daily remaining.
```

## Output

```text
MealEntry removed
Updated daily consumed
Updated daily remaining
```

## Business Rules

```text
Mỗi lần manual add hoặc quick add đều tạo ra một MealEntry.
Remove meal chỉ xoá MealEntry đó.
Remove meal không ảnh hưởng đến Food dataset.
Remove meal không ảnh hưởng đến NutritionPlan.
```

---

# UC6: Observe Daily Nutrition Summary

## Mục tiêu

System luôn tính và cập nhật trạng thái daily calories/macros dựa trên active plan và các meal entry trong ngày.

## Actor

```text
System
```

## Input

```text
Active NutritionPlan
MealEntries by selected date
```

## Main Flow

```text
1. User mở Today Dashboard.
2. System lấy active NutritionPlan.
3. System observe danh sách MealEntry của ngày hiện tại.
4. System tính tổng consumed calories.
5. System tính tổng consumed protein.
6. System tính tổng consumed fat.
7. System tính tổng consumed carb.
8. System tính remaining calories/macros.
9. Khi MealEntry thay đổi, system tự tính lại summary.
10. UI update lại trạng thái mới.
```

## Output

```text
DailyNutritionSummary
```

DailyNutritionSummary gồm:

```text
Target calories
Consumed calories
Remaining calories

Target protein
Consumed protein
Remaining protein

Target fat
Consumed fat
Remaining fat

Target carb
Consumed carb
Remaining carb

Meal entries of selected date
```

## Business Rules

```text
DailyNutritionSummary không nhất thiết phải lưu database.
DailyNutritionSummary là derived data được tính từ Active NutritionPlan + MealEntries.
Today Dashboard phải observe MealEntries để tự cập nhật khi user add/remove meal.
```

---

## 6. Domain Models

### 6.1. UserProfile

```kotlin
data class UserProfile(
    val id: String,
    val gender: Gender,
    val age: Int,
    val heightCm: Double,
    val weightKg: Double,
    val activityLevel: ActivityLevel
)
```

### 6.2. NutritionGoal

```kotlin
data class NutritionGoal(
    val id: String,
    val goalType: GoalType,
    val goalSpeed: GoalSpeed,
    val targetWeightKg: Double?
)
```

### 6.3. NutritionPlan

```kotlin
data class NutritionPlan(
    val id: String,
    val currentWeightKg: Double,
    val targetWeightKg: Double?,
    val bmr: Double,
    val tdee: Double,
    val targetCalories: Double,
    val targetProteinG: Double,
    val targetFatG: Double,
    val targetCarbG: Double,
    val estimatedWeightChangePerWeekKg: Double?,
    val estimatedGoalWeeks: Double?,
    val createdAt: LocalDateTime,
    val isActive: Boolean
)
```

### 6.4. Food

Food là dữ liệu tham chiếu cho quick add.

```kotlin
data class Food(
    val id: String,
    val name: String,
    val baseAmountG: Double,
    val caloriesPerBase: Double,
    val proteinPerBaseG: Double,
    val fatPerBaseG: Double,
    val carbPerBaseG: Double
)
```

Ví dụ:

```text
name = "Phở bò"
baseAmountG = 100
caloriesPerBase = 180
proteinPerBaseG = 9
fatPerBaseG = 5
carbPerBaseG = 24
```

### 6.5. MealEntry

MealEntry là dữ liệu user đã ăn thật trong ngày.

```kotlin
data class MealEntry(
    val id: String,
    val date: LocalDate,
    val mealType: MealType,
    val name: String,
    val calories: Double,
    val proteinG: Double,
    val fatG: Double,
    val carbG: Double,
    val source: MealEntrySource,
    val foodId: String? = null,
    val amountG: Double? = null
)
```

```kotlin
enum class MealEntrySource {
    MANUAL,
    QUICK_ADD
}
```

Ghi chú:

```text
Nếu source = MANUAL, foodId và amountG có thể null.
Nếu source = QUICK_ADD, foodId và amountG nên có giá trị.
MealEntry vẫn phải lưu snapshot calories/macros đã scale.
```

### 6.6. DailyNutritionSummary

```kotlin
data class DailyNutritionSummary(
    val date: LocalDate,
    val targetCalories: Double,
    val consumedCalories: Double,
    val remainingCalories: Double,
    val targetProteinG: Double,
    val consumedProteinG: Double,
    val remainingProteinG: Double,
    val targetFatG: Double,
    val consumedFatG: Double,
    val remainingFatG: Double,
    val targetCarbG: Double,
    val consumedCarbG: Double,
    val remainingCarbG: Double,
    val mealEntries: List<MealEntry>
)
```

---

## 7. Persistent vs Derived Data

### 7.1. Persistent Data

Nên lưu database:

```text
UserProfile
NutritionGoal
NutritionPlan
Food
MealEntry
```

### 7.2. Derived Data

Không cần lưu database:

```text
DailyNutritionSummary
```

Vì DailyNutritionSummary được tính từ:

```text
Active NutritionPlan + MealEntries by selected date
```

---

## 8. Main Data Flow

### 8.1. Onboarding Flow

```text
OnboardingScreen
→ OnboardingViewModel
→ CalculateNutritionPlanUseCase
→ UserProfileRepository.saveProfile()
→ NutritionGoalRepository.saveGoal()
→ NutritionPlanRepository.saveActivePlan()
→ Navigate to TodayScreen
```

### 8.2. Update Profile Flow

```text
ProfileScreen
→ ProfileViewModel
→ UpdateProfileAndRecalculatePlanUseCase
→ UserProfileRepository.updateProfile()
→ NutritionGoalRepository.updateGoal()
→ CalculateNutritionPlanUseCase
→ NutritionPlanRepository.replaceActivePlan()
→ TodayScreen observes updated plan
```

### 8.3. Manual Add Meal Flow

```text
AddMealScreen
→ AddMealViewModel
→ AddManualMealEntryUseCase
→ MealEntryRepository.addMealEntry()
→ TodayScreen observes MealEntries
→ DailyNutritionSummary recalculated
```

### 8.4. Quick Add Flow

```text
AddMealScreen
→ User searches Food
→ FoodRepository.searchFoods(query)
→ User selects Food and amount
→ QuickAddMealEntryUseCase
→ Scale nutrition by amount
→ Create MealEntry snapshot
→ MealEntryRepository.addMealEntry()
→ TodayScreen observes MealEntries
→ DailyNutritionSummary recalculated
```

### 8.5. Remove Meal Flow

```text
TodayScreen / MealList
→ User deletes MealEntry
→ RemoveMealEntryUseCase
→ MealEntryRepository.removeMealEntry(id)
→ TodayScreen observes MealEntries
→ DailyNutritionSummary recalculated
```

### 8.6. Daily Summary Flow

```text
TodayScreen
→ TodayViewModel
→ ObserveDailyNutritionSummaryUseCase
→ observeActiveNutritionPlan()
  + observeMealEntriesByDate(date)
→ combine plan + meal entries
→ calculate consumed
→ calculate remaining
→ emit DailyNutritionSummary
→ update UI state
```

---

## 9. Repository Interfaces

### 9.1. UserProfileRepository

```kotlin
interface UserProfileRepository {
    suspend fun saveProfile(profile: UserProfile)
    suspend fun updateProfile(profile: UserProfile)
    fun observeProfile(): Flow<UserProfile?>
}
```

### 9.2. NutritionGoalRepository

```kotlin
interface NutritionGoalRepository {
    suspend fun saveGoal(goal: NutritionGoal)
    suspend fun updateGoal(goal: NutritionGoal)
    fun observeGoal(): Flow<NutritionGoal?>
}
```

### 9.3. NutritionPlanRepository

```kotlin
interface NutritionPlanRepository {
    suspend fun saveActivePlan(plan: NutritionPlan)
    suspend fun replaceActivePlan(plan: NutritionPlan)
    fun observeActivePlan(): Flow<NutritionPlan?>
}
```

### 9.4. FoodRepository

```kotlin
interface FoodRepository {
    fun searchFoods(query: String): Flow<List<Food>>
    suspend fun getFoodById(id: String): Food?
}
```

### 9.5. MealEntryRepository

```kotlin
interface MealEntryRepository {
    suspend fun addMealEntry(entry: MealEntry)
    suspend fun removeMealEntry(id: String)
    fun observeMealEntriesByDate(date: LocalDate): Flow<List<MealEntry>>
}
```

---

## 10. Suggested Use Cases In Code

```text
CalculateNutritionPlanUseCase
UpdateProfileAndRecalculatePlanUseCase
AddManualMealEntryUseCase
QuickAddMealEntryUseCase
RemoveMealEntryUseCase
ObserveDailyNutritionSummaryUseCase
SearchFoodsUseCase
```

Trong đó use case quan trọng nhất cho dashboard là:

```text
ObserveDailyNutritionSummaryUseCase
```

Use case này combine:

```text
ActiveNutritionPlan Flow
+
MealEntriesByDate Flow
```

Sau đó tính:

```text
Consumed calories/macros
Remaining calories/macros
```

---

## 11. Final MVP v1 Statement

```text
NutriPath AI MVP v1 allows users to create a personalized nutrition plan from onboarding data, update their profile to recalculate the active plan, add meals manually or through quick food search with quantity scaling, remove meal entries, and observe daily consumed/remaining calories and macros in real time.
```

Tiếng Việt:

```text
NutriPath AI MVP v1 cho phép người dùng tạo kế hoạch dinh dưỡng cá nhân từ dữ liệu onboarding, cập nhật profile để tính lại plan đang active, thêm bữa ăn bằng cách nhập tay hoặc quick add từ bộ món có sẵn kèm định lượng, xoá meal entry, và theo dõi calories/macros đã tiêu thụ cũng như còn lại trong ngày theo thời gian thực.
```

---

## 12. Recommended Next Step

Sau tài liệu use case này, bước tiếp theo nên là:

```text
Architecture Design
→ Package structure
→ Screen/ViewModel mapping
→ UseCase mapping
→ Repository boundary
→ Database design
```

Không nên nhảy thẳng vào database nếu chưa chốt architecture boundary, vì cần biết rõ model nào là domain model, model nào là entity, model nào chỉ là derived UI/domain state.
