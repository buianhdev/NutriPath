# NutriPath AI - Architecture Analysis MVP v1

## 1. Mục tiêu của bản MVP v1

MVP v1 của NutriPath AI tập trung vào việc kiểm chứng core business chính của một ứng dụng dinh dưỡng cá nhân:

1. User nhập thông tin cơ thể và mục tiêu dinh dưỡng.
2. App tính toán kế hoạch dinh dưỡng gồm BMR, TDEE, goal calories, macro target và goal estimation.
3. User log bữa ăn mỗi ngày bằng nhập tay hoặc quick add từ danh sách món có sẵn.
4. App hiển thị lượng calories/macros đã ăn và còn lại trong ngày.
5. User có thể cập nhật profile/goal và app tính lại nutrition plan mới.

MVP này chưa tập trung vào AI recommendation phức tạp, barcode scan, food recognition, chart nâng cao hoặc tracking dài hạn theo tuần/tháng. Những phần đó có thể để v2.

---

# 2. Domain Layer

## 2.1 Entity Analyze

### UserProfile

Đại diện cho thông tin cơ thể và mức độ hoạt động của user.

**Fields đề xuất:**

```kotlin
UserProfile
- id
- weightKg
- heightCm
- age
- gender
- pal // Physical Activity Level
- createdAt
- updatedAt
```

**Mục đích:**

- Là input bắt buộc để tính BMR và TDEE.
- Được dùng trong onboarding và profile update.
- Với MVP local-first, app có thể chỉ cần lưu một current user profile.

---

### NutritionGoal

Đại diện cho mục tiêu dinh dưỡng tại một thời điểm cụ thể.

**Fields đề xuất:**

```kotlin
NutritionGoal
- id
- goalType // lose, gain, maintain
- goalSpeed // low, normal, fast
- targetWeightKg
- startWeightKg
- createdAt
- isActive
```

**Mục đích:**

- Lưu mục tiêu hiện tại của user.
- Dùng để tính goal calories và estimated goal weeks.
- Trong MVP, nên coi `NutritionGoal` là một **goal version/snapshot**.

Điều này nghĩa là mỗi lần user update profile hoặc goal, app tạo một `NutritionGoal` mới và set goal cũ thành inactive.

Ví dụ:

```text
Goal #1: startWeight = 62kg, targetWeight = 58kg, speed = normal
Goal #2: startWeight = 61kg, targetWeight = 58kg, speed = normal
```

Dù target weight không đổi, goal mới vẫn được tạo vì trạng thái tính toán đã thay đổi.

---

### NutritionPlan

Đại diện cho kết quả tính toán dinh dưỡng từ `UserProfile` và `NutritionGoal`.

**Fields đề xuất:**

```kotlin
NutritionPlan
- id
- nutritionGoalId
- bmr
- tdee
- goalCalories
- proteinG
- fatG
- carbG
- goalEstimation
- estimatedWeightChangePerWeek
- currentWeightKg
- targetWeightKg
- estimatedGoalWeeks
- profileSnapshot
- goalSnapshot
- createdAt
- isActive
```

**Mục đích:**

- Lưu kết quả tính toán để dashboard load nhanh.
- Có thể lấy active nutrition plan hiện tại mà không cần tính lại mỗi lần vào app.
- Giữ lịch sử thay đổi plan nếu user update profile/goal.
- Là dữ liệu quan trọng của business nên nên lưu DB trong MVP.

**Lưu ý quan trọng:**

`NutritionPlan` nên lưu `profileSnapshot` và `goalSnapshot`, hoặc ít nhất lưu lại các field quan trọng như `currentWeightKg`, `targetWeightKg`, `goalSpeed`, `pal`, `bmr`, `tdee`, `goalCalories`.

Lý do: nếu user update profile sau này, plan cũ vẫn phải phản ánh đúng trạng thái tại thời điểm nó được tạo.

---

### MacroBreakdown

Đại diện cho calories và macro nutrients.

**Fields đề xuất:**

```kotlin
MacroBreakdown
- calories
- proteinG
- carbG
- fatG
```

**Mục đích:**

- Dùng cho `MealEntry`.
- Dùng cho target macros trong `NutritionPlan`.
- Dùng cho consumed/remaining macros trong `DailyLogSummary`.

**Quyết định architecture:**

`MacroBreakdown` không nên là bảng riêng trong DB ở MVP. Nên coi nó là **Value Object** và dùng dạng embedded object.

Ví dụ trong Room có thể dùng `@Embedded`.

---

### MealEntry

Đại diện cho một lần user log món ăn.

**Fields đề xuất:**

```kotlin
MealEntry
- id
- source // quick add, manual
- date
- foodId // nullable, chỉ có khi quick add
- foodName
- amountG
- macroBreakdown
- createdAt
- updatedAt
```

**Mục đích:**

- Lưu lại từng bữa ăn hoặc từng lần thêm món.
- Là nguồn dữ liệu chính để tính daily consumed calories/macros.
- Mỗi lần quick add hoặc manual add được coi là một `MealEntry`.

**Meal source:**

```text
manual: user tự nhập calories, protein, carb, fat
quick add: user chọn Food có sẵn, nhập amountG, app tự scale macro
```

---

### Food

Đại diện cho món ăn mẫu trong database để phục vụ quick add.

**Fields đề xuất:**

```kotlin
Food
- id
- name
- ingredients
- baseAmountG
- caloriesPerBase
- proteinPerBase
- carbPerBase
- fatPerBase
```

**Mục đích:**

- Là bộ dữ liệu món ăn có sẵn.
- User search món và nhập định lượng.
- App tính macro dựa trên `amountG` user nhập.

Ví dụ:

```text
Food: Phở bò
baseAmountG = 100g
caloriesPerBase = 120 kcal
proteinPerBase = 8g
carbPerBase = 15g
fatPerBase = 4g

User nhập 300g:
calories = 120 * 300 / 100 = 360 kcal
protein = 8 * 300 / 100 = 24g
carb = 15 * 300 / 100 = 45g
fat = 4 * 300 / 100 = 12g
```

---

### DailyLogSummary

Đại diện cho tổng kết ăn uống trong một ngày.

**Fields đề xuất:**

```kotlin
DailyLogSummary
- date
- mealEntries
- targetCalories
- consumedCalories
- remainingCalories
- targetMacros
- consumedMacros
- remainingMacros
```

**Mục đích:**

- Dùng để render Dashboard và Daily Log.
- Tổng hợp target từ active `NutritionPlan`.
- Tổng hợp consumed từ danh sách `MealEntry` theo ngày.

**Quyết định architecture:**

`DailyLogSummary` không nên lưu thành bảng riêng trong MVP.

Lý do:

- Có thể tính lại từ `MealEntry` theo ngày.
- Tránh duplicate data.
- Tránh lỗi dữ liệu lệch giữa `MealEntry` và `DailyLogSummary`.
- MVP chưa cần tối ưu chart/thống kê dài hạn.

Có thể coi `DailyLogSummary` là **computed model/read model**.

---

# 3. Use Case Layer

## 3.1 SaveOnboardingUseCase

```kotlin
SaveOnboardingUseCase(
    userProfile: UserProfile,
    nutritionGoal: NutritionGoal
): NutritionPlan
```

**Nhiệm vụ:**

1. Lưu `UserProfile`.
2. Tạo và lưu `NutritionGoal` mới.
3. Tính `NutritionPlan` từ profile + goal.
4. Lưu `NutritionPlan` mới.
5. Set goal và plan mới là active.
6. Return `NutritionPlan` để UI hiển thị estimation/result.

**Flow:**

```text
Input profile + goal
        ↓
Save UserProfile
        ↓
Save NutritionGoal
        ↓
Calculate NutritionPlan
        ↓
Save NutritionPlan
        ↓
Return NutritionPlan
```

---

## 3.2 GetUserProfileUseCase

```kotlin
GetUserProfileUseCase(): UserProfile
```

**Nhiệm vụ:**

- Lấy current user profile.
- Dùng ở màn Profile hoặc khi cần prefill thông tin user.

---

## 3.3 UpdateProfileAndRecalculateNutritionPlanUseCase

```kotlin
UpdateProfileAndRecalculateNutritionPlanUseCase(
    userProfile: UserProfile,
    nutritionGoal: NutritionGoal
): NutritionPlan
```

**Nhiệm vụ:**

1. Update `UserProfile` hiện tại.
2. Deactivate old active `NutritionGoal` nếu cần.
3. Deactivate old active `NutritionPlan`.
4. Tạo `NutritionGoal` version mới.
5. Tính `NutritionPlan` mới.
6. Lưu plan mới và set active.
7. Return plan mới.

**Ý nghĩa business:**

Mỗi lần user thay đổi cân nặng, PAL, target weight hoặc goal speed, app cần tính lại BMR/TDEE/goal calories/macros.

---

## 3.4 GetActiveNutritionPlanUseCase

```kotlin
GetActiveNutritionPlanUseCase(): NutritionPlan
```

**Nhiệm vụ:**

- Lấy nutrition plan đang active.
- Dùng cho Dashboard, Daily Log và Profile.

---

## 3.5 AddMealEntryUseCase

```kotlin
AddMealEntryUseCase(
    source: MealSource,
    date: LocalDate,
    macroBreakdown: MacroBreakdown,
    foodId: String? = null,
    foodName: String? = null,
    amountG: Double? = null
)
```

**Nhiệm vụ:**

- Thêm một meal entry vào ngày được chọn.
- Có thể là manual add hoặc quick add.

**Manual add:**

```text
User tự nhập calories/protein/carb/fat
```

**Quick add:**

```text
User chọn Food → nhập amountG → app scale macro → save MealEntry
```

---

## 3.6 RemoveMealEntryUseCase

```kotlin
RemoveMealEntryUseCase(
    mealEntryId: String
)
```

**Nhiệm vụ:**

- Xóa một meal entry khỏi daily log.
- Sau khi xóa, Dashboard/DailyLog sẽ tự tính lại summary từ danh sách meal còn lại.

---

## 3.7 GetDailyLogSummaryUseCase

```kotlin
GetDailyLogSummaryUseCase(
    date: LocalDate
): DailyLogSummary
```

**Nhiệm vụ:**

1. Lấy active `NutritionPlan`.
2. Lấy danh sách `MealEntry` theo ngày.
3. Cộng consumed calories/macros.
4. Tính remaining calories/macros.
5. Return `DailyLogSummary`.

**Pseudo flow:**

```text
Get active nutrition plan
        ↓
Get meal entries by date
        ↓
Sum consumed calories/macros
        ↓
Calculate remaining calories/macros
        ↓
Return DailyLogSummary
```

---

# 4. UI Layer

## 4.1 Tổng quan flow

```text
Onboarding
    ↓
Main Screen
    ├── Dashboard Tab
    ├── Daily Log Tab
    └── Profile Tab
```

---

## 4.2 Onboarding Screen

**Mục đích:**

- Thu thập thông tin ban đầu của user.
- Tạo goal và nutrition plan đầu tiên.

**Flow:**

```text
Nhập profile information
        ↓
Nhập goal information
        ↓
Calculate plan
        ↓
Show goal estimation
        ↓
User confirm
        ↓
Save onboarding data
        ↓
Navigate to Main Screen
```

**Input:**

- Weight
- Height
- Age
- Gender
- PAL
- Goal type
- Goal speed
- Target weight

**Output hiển thị:**

- BMR
- TDEE
- Goal calories
- Protein target
- Fat target
- Carb target
- Estimated goal weeks
- Estimated weight change per week

---

## 4.3 Dashboard Screen

**Mục đích:**

Hiển thị tổng quan tình trạng ăn uống trong ngày hiện tại.

**Data:**

- Today `DailyLogSummary`
- Active `NutritionPlan`

**UI nên có:**

```text
- Consumed calories
- Remaining calories
- Target calories
- Protein consumed / target
- Carb consumed / target
- Fat consumed / target
- Quick button: Add meal
```

---

## 4.4 Daily Log Screen

**Mục đích:**

Quản lý danh sách meal entry theo ngày.

**UI nên có:**

```text
- Date selector
- List meal entries
- Add manual meal button
- Quick add food button
- Remove meal button
- Daily consumed summary
```

**Flow add manual:**

```text
Click Add Manual
        ↓
Input calories/protein/carb/fat
        ↓
Save MealEntry
        ↓
Refresh DailyLogSummary
```

**Flow quick add:**

```text
Click Quick Add
        ↓
Search food
        ↓
Select food
        ↓
Input amountG
        ↓
Scale macro
        ↓
Save MealEntry
        ↓
Refresh DailyLogSummary
```

---

## 4.5 Profile Screen

**Mục đích:**

Hiển thị và cập nhật thông tin user, goal và active nutrition plan.

**UI nên có:**

```text
- Current profile information
- Current goal information
- Active nutrition plan summary
- Update profile/goal button
```

**Flow update:**

```text
User update profile/goal
        ↓
Recalculate nutrition plan
        ↓
Deactivate old goal/plan
        ↓
Save new goal/plan
        ↓
Show updated result
```

---

# 5. Database Analyze

## 5.1 Câu hỏi chính

Những thông tin nào nên lưu vào database trong MVP?

---

## 5.2 Bảng nên lưu

### UserProfile

**Có lưu.**

Lý do:

- Là dữ liệu bắt buộc của user.
- Dùng để prefill profile.
- Dùng để tính lại plan khi user cập nhật thông tin.

---

### NutritionGoal

**Có lưu.**

Lý do:

- Là dữ liệu bắt buộc để biết user đang muốn giảm/tăng/giữ cân.
- Nên lưu theo version để có lịch sử thay đổi goal.
- Mỗi goal active sẽ đi với một nutrition plan active.

---

### NutritionPlan

**Nên lưu.**

Ban đầu có thể nghĩ `NutritionPlan` không cần lưu vì có thể tính lại từ profile + goal. Tuy nhiên trong MVP này nên lưu vì:

- Đây là kết quả business quan trọng.
- Dashboard và Daily Log cần lấy target calories/macros thường xuyên.
- Tránh phải tính lại mỗi lần vào app.
- Hỗ trợ lịch sử plan sau này.
- Dễ xác định active nutrition plan.

**Quyết định:**

```text
Lưu NutritionPlan trong DB.
Mỗi lần update profile/goal thì tạo NutritionPlan mới.
Plan cũ set isActive = false.
Plan mới set isActive = true.
```

---

### Food

**Có lưu.**

Lý do:

- Bắt buộc cho tính năng quick add.
- Là bộ dữ liệu món ăn mẫu.
- Có thể seed sẵn trong local database.

---

### MealEntry

**Có lưu.**

Lý do:

- Là dữ liệu log ăn uống chính.
- Dùng để tính consumed calories/macros theo ngày.
- Dùng để hiển thị lịch sử ăn uống.

---

## 5.3 Bảng không nên lưu trong MVP

### DailyLogSummary

**Không lưu.**

Lý do:

- Có thể query `MealEntry` theo date rồi tính lại.
- Tránh duplicate data.
- Tránh inconsistency khi user add/remove meal.
- MVP chưa cần tối ưu thống kê dài hạn.

`DailyLogSummary` chỉ nên là computed model.

---

### MacroBreakdown

**Không tạo bảng riêng.**

Lý do:

- Chỉ là value object gồm calories, protein, carb, fat.
- Có thể embedded trực tiếp vào `MealEntry` hoặc `NutritionPlan`.
- Tạo bảng riêng sẽ làm database phức tạp không cần thiết.

---

# 6. Database Relationship

## 6.1 Relationship chốt cho MVP

```text
UserProfile 1 - n NutritionGoal
NutritionGoal 1 - 1 NutritionPlan
Food 1 - n MealEntry
MealEntry embeds MacroBreakdown
DailyLogSummary không lưu bảng
```

---

## 6.2 Giải thích relationship

### UserProfile 1 - n NutritionGoal

Một user có thể tạo nhiều goal version theo thời gian.

Ví dụ:

```text
UserProfile
    ├── Goal #1: 62kg → 58kg, normal speed
    ├── Goal #2: 61kg → 58kg, normal speed
    └── Goal #3: 60kg → 57kg, low speed
```

Mỗi lần user update profile hoặc goal, app có thể tạo một `NutritionGoal` mới.

---

### NutritionGoal 1 - 1 NutritionPlan

Trong MVP này, `NutritionGoal` được hiểu là một **goal snapshot/version**.

Mỗi goal version sinh ra đúng một nutrition plan.

```text
NutritionGoal #1 → NutritionPlan #1
NutritionGoal #2 → NutritionPlan #2
NutritionGoal #3 → NutritionPlan #3
```

Cách này dễ code, dễ giải thích và phù hợp với MVP.

---

### Food 1 - n MealEntry

Một food có thể được add nhiều lần trong nhiều ngày khác nhau.

Ví dụ:

```text
Food: Phở bò
    ├── MealEntry ngày 08/06, 300g
    ├── MealEntry ngày 09/06, 250g
    └── MealEntry ngày 10/06, 400g
```

Nếu meal entry là manual add thì `foodId` có thể null.

---

### MealEntry embeds MacroBreakdown

Mỗi meal entry chứa trực tiếp calories/protein/carb/fat.

Ví dụ:

```text
MealEntry
- foodName: Phở bò
- amountG: 300g
- calories: 360
- proteinG: 24
- carbG: 45
- fatG: 12
```

Không cần tạo bảng `MacroBreakdown` riêng.

---

# 7. Data Layer

## 7.1 Repository đề xuất

```kotlin
ProfileRepository
NutritionPlanRepository
FoodRepository
MealEntryRepository
```

---

## 7.2 ProfileRepository

Quản lý `UserProfile` và `NutritionGoal`.

**Methods gợi ý:**

```kotlin
interface ProfileRepository {
    suspend fun getUserProfile(): UserProfile?
    suspend fun saveUserProfile(profile: UserProfile)
    suspend fun getActiveNutritionGoal(): NutritionGoal?
    suspend fun saveNutritionGoal(goal: NutritionGoal)
    suspend fun deactivateCurrentGoal()
}
```

---

## 7.3 NutritionPlanRepository

Quản lý nutrition plan.

**Methods gợi ý:**

```kotlin
interface NutritionPlanRepository {
    suspend fun getActiveNutritionPlan(): NutritionPlan?
    suspend fun saveNutritionPlan(plan: NutritionPlan)
    suspend fun deactivateCurrentPlan()
    suspend fun getNutritionPlanHistory(): List<NutritionPlan>
}
```

---

## 7.4 FoodRepository

Quản lý danh sách món ăn mẫu.

**Methods gợi ý:**

```kotlin
interface FoodRepository {
    suspend fun searchFoods(query: String): List<Food>
    suspend fun getFoodById(id: String): Food?
    suspend fun getAllFoods(): List<Food>
}
```

---

## 7.5 MealEntryRepository

Quản lý meal log.

**Methods gợi ý:**

```kotlin
interface MealEntryRepository {
    suspend fun addMealEntry(entry: MealEntry)
    suspend fun removeMealEntry(id: String)
    suspend fun getMealEntriesByDate(date: LocalDate): List<MealEntry>
    fun observeMealEntriesByDate(date: LocalDate): Flow<List<MealEntry>>
}
```

Nên có `Flow` để Dashboard/Daily Log tự update khi user add/remove meal.

---

# 8. Enum

## 8.1 PAL

Physical Activity Level.

```kotlin
enum class PAL {
    SEDENTARY,
    LIGHTLY_ACTIVE,
    MODERATELY_ACTIVE,
    VERY_ACTIVE,
    EXTRA_ACTIVE
}
```

Có thể map sang hệ số:

```text
sedentary: 1.2
lightly active: 1.375
moderately active: 1.55
very active: 1.725
extra active: 1.9
```

---

## 8.2 GoalType

```kotlin
enum class GoalType {
    LOSE,
    GAIN,
    MAINTAIN
}
```

Ý nghĩa:

```text
LOSE: giảm cân
GAIN: tăng cân
MAINTAIN: giữ cân
```

---

## 8.3 GoalSpeed

```kotlin
enum class GoalSpeed {
    LOW,
    NORMAL,
    FAST
}
```

Có thể map sang estimated weight change per week.

Ví dụ:

```text
LOW: 0.25 kg/week
NORMAL: 0.5 kg/week
FAST: 0.75 kg/week
```

Với bản MVP, nên giới hạn tốc độ giảm/tăng cân ở mức an toàn và dễ giải thích.

---

## 8.4 MealSource

Nên đặt là `MealSource`, không nên đặt là `FoodSource`, vì source này mô tả nguồn tạo ra `MealEntry`.

```kotlin
enum class MealSource {
    QUICK_ADD,
    MANUAL
}
```

Ý nghĩa:

```text
QUICK_ADD: user chọn từ Food database
MANUAL: user tự nhập calories/macros
```

---

# 9. Architecture Decision Summary

## 9.1 Những quyết định đã chốt

```text
1. NutritionGoal được coi là goal snapshot/version.
2. Mỗi NutritionGoal sinh ra đúng một NutritionPlan.
3. Mỗi lần user update profile/goal thì tạo NutritionGoal + NutritionPlan mới.
4. Chỉ có một active NutritionGoal và một active NutritionPlan tại một thời điểm.
5. NutritionPlan nên được lưu DB.
6. DailyLogSummary không lưu DB, chỉ tính từ MealEntry + active NutritionPlan.
7. MacroBreakdown là value object, không tạo bảng riêng.
8. MealEntry là nguồn dữ liệu chính cho daily tracking.
9. Food phục vụ quick add và có thể seed sẵn local DB.
```

---

## 9.2 Database tables chốt cho MVP

```text
user_profile
nutrition_goal
nutrition_plan
food
meal_entry
```

Không tạo bảng:

```text
daily_log_summary
macro_breakdown
```

---

## 9.3 MVP flow chốt

```text
Onboarding
    ↓
Save UserProfile
    ↓
Create active NutritionGoal
    ↓
Calculate active NutritionPlan
    ↓
User vào Dashboard
    ↓
User add/remove MealEntry mỗi ngày
    ↓
Dashboard/DailyLog tính DailyLogSummary từ MealEntry + active NutritionPlan
```

---

# 10. Kết luận

Architecture hiện tại đã đủ ổn cho MVP v1.

Thiết kế này cân bằng giữa:

- Đủ đơn giản để làm trong thời gian ngắn.
- Không over-engineering.
- Vẫn giữ được lịch sử goal/plan.
- Dễ mở rộng sang v2.
- Phù hợp với Clean Architecture trong Android.

Phần nên ưu tiên implement trước:

```text
1. Domain model + calculation logic
2. Room entity + DAO
3. Repository
4. Use case
5. Onboarding flow
6. Dashboard daily summary
7. Manual add meal
8. Quick add food
9. Profile update + recalculate plan
```
