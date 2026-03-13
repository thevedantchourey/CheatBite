package com.app.cheatbite.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cheatbite.UserSharedPreference
import com.app.cheatbite.app.Events
import com.app.cheatbite.app.data.mapper.toWeekDietDto
import com.app.cheatbite.app.domain.model.MealEntry
import com.app.cheatbite.app.domain.model.UserProfile
import com.app.cheatbite.app.domain.model.WeekDiet
import com.app.cheatbite.app.domain.usecase.ObserveCurrentUserUseCase
import com.app.cheatbite.app.domain.usecase.SignInWithGoogleUseCase
import com.app.cheatbite.app.domain.usecase.UserDataUseCase
import com.app.cheatbite.app.states.UserState
import com.app.cheatbite.core.domain.util.onError
import com.app.cheatbite.core.domain.util.onSuccess
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AccountViewModel(
//    private val appThemeController: ThemeController,
    private val userDataUseCase: UserDataUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val preferenceManager: UserSharedPreference,
    observeCurrentUserUseCase: ObserveCurrentUserUseCase
) : ViewModel() {

    private val _events = MutableSharedFlow<Events>()
    val events = _events.asSharedFlow()
//    val isCurrentlyDark: StateFlow<Boolean> = appThemeController.isDarkThemeActive

    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    val user: StateFlow<UserProfile> = observeCurrentUserUseCase.invoke()


    private val _username = MutableStateFlow(user.value.username)
    val username: StateFlow<String> = _username.asStateFlow()

    private val _day = MutableStateFlow("")
    val day: StateFlow<String> = _day.asStateFlow()

    private val _mealType = MutableStateFlow(user.value.mealType)
    val mealType: StateFlow<String?> = _mealType.asStateFlow()

    private val _avatar = MutableStateFlow("base_profile")
    val avatar: StateFlow<String?> = _avatar.asStateFlow()
    private val _subscription = MutableStateFlow("pro")
    val subscription: StateFlow<String?> = _subscription.asStateFlow()
    private val _mealTime = MutableStateFlow("")
    val mealTime: StateFlow<String> = _mealTime.asStateFlow()
    private val _diet = MutableStateFlow("")
    val diet: StateFlow<String> = _diet.asStateFlow()

    fun updateDay(day: String) {
        _day.update { day }
    }

    fun updateUsername(username: String) {
        _username.update { username }
    }

    fun updateMealType(mealType: String) {
        _mealType.update { mealType }
        saveAllPreferences()
    }

    fun updateSubscription(subscription: String) {
        _subscription.update { subscription }
    }

    fun updateMealTime(mealTime: String) {
        _mealTime.update { mealTime }
    }

    fun updateAvatar(avatar: String) {
        _avatar.update { avatar }
    }


    fun updateDiet(diet: String) {
        _diet.update { diet }
    }


    private val _monday = MutableStateFlow(arrayListOf<MealEntry>())
    val monday: StateFlow<ArrayList<MealEntry>> = _monday.asStateFlow()

    fun addMondayMeals(mealEntry: MealEntry){
        _monday.update { ArrayList(it).apply { add(mealEntry) } }
    }


    fun removeMondayMeals(mealEntry: MealEntry){
        _monday.update { ArrayList(it).apply { remove(mealEntry) } }
    }

    private val _tuesday = MutableStateFlow(arrayListOf<MealEntry>())
    val tuesday: StateFlow<ArrayList<MealEntry>> = _tuesday.asStateFlow()

    fun addTuesdayMeals(mealEntry: MealEntry){
        _tuesday.update { ArrayList(it).apply { add(mealEntry) } }
    }


    fun removeTuesdayMeals(mealEntry: MealEntry){
        _tuesday.update { ArrayList(it).apply { remove(mealEntry) } }
    }


    private val _wednesday = MutableStateFlow(arrayListOf<MealEntry>())
    val wednesday: StateFlow<ArrayList<MealEntry>> = _wednesday.asStateFlow()

    fun addWednesdayMeals(mealEntry: MealEntry){
        _wednesday.update { ArrayList(it).apply { add(mealEntry) } }
    }


    fun removeWednesdayMeals(mealEntry: MealEntry){
        _wednesday.update { ArrayList(it).apply { remove(mealEntry) } }
    }


    private val _thursday = MutableStateFlow(arrayListOf<MealEntry>())
    val thursday: StateFlow<ArrayList<MealEntry>> = _thursday.asStateFlow()

    fun addThursdayMeals(mealEntry: MealEntry){
        _thursday.update { ArrayList(it).apply { add(mealEntry) } }
    }


    fun removeThursdayMeals(mealEntry: MealEntry){
        _thursday.update { ArrayList(it).apply { remove(mealEntry) } }
    }



    private val _friday = MutableStateFlow(arrayListOf<MealEntry>())
    val friday: StateFlow<ArrayList<MealEntry>> = _friday.asStateFlow()

    fun addFridayMeals(mealEntry: MealEntry){
        _friday.update { ArrayList(it).apply { add(mealEntry) } }
    }

    fun removeFridayMeals(mealEntry: MealEntry){
        _friday.update { ArrayList(it).apply { remove(mealEntry) } }
    }

    private val _saturday = MutableStateFlow(arrayListOf<MealEntry>())
    val saturday: StateFlow<ArrayList<MealEntry>> = _saturday.asStateFlow()

    fun addSaturdayMeals(mealEntry: MealEntry){
        _saturday.update { ArrayList(it).apply { add(mealEntry) } }
    }

    fun removeSaturdayMeals(mealEntry: MealEntry){
        _saturday.update { ArrayList(it).apply { remove(mealEntry) } }
    }

    private val _sunday = MutableStateFlow(arrayListOf<MealEntry>())
    val sunday: StateFlow<ArrayList<MealEntry>> = _sunday.asStateFlow()

    fun addSundayMeals(mealEntry: MealEntry){
        _sunday.update { ArrayList(it).apply { add(mealEntry) } }
    }


    fun removeSundayMeals(mealEntry: MealEntry){
        _sunday.update { ArrayList(it).apply { remove(mealEntry) } }
    }

    fun saveAllPreferences(){
        _userState.update { UserState.Loading }
        val currentUserId = firebaseAuth.currentUser?.uid ?: return
        viewModelScope.launch {
            val updates = mutableMapOf<String, Any>()
            val weekDiet = WeekDiet(
                    monday = monday.value,
                    tuesday = tuesday.value,
                    wednesday = wednesday.value,
                    thursday = thursday.value,
                    friday = friday.value,
                    saturday = saturday.value,
                    sunday = sunday.value
                )
            updates["username"] = _username.value
            updates["mealType"] = _mealType.value
            updates["avatar"] = _avatar.value
            updates["subscription"] = _subscription.value
            updates["weeklyDiet"] = weekDiet.toWeekDietDto()

            val userPrefResult = userDataUseCase.updateUser(uID = currentUserId, updates = updates)
            userPrefResult.onSuccess { _, _ ->
                _userState.update { UserState.Success }
                getUserData()
                _events.emit(Events.Success("You are all set up! \uD83E\uDEF0"))
            }.onError { error, message ->
                _userState.update { UserState.Error }
                _events.emit(Events.Error(error, message ?: "Failed to update the information for the user."))
            }
        }
    }


    fun updateDayDiet() {
        _userState.update { UserState.Loading }
        val currentUserId = firebaseAuth.currentUser?.uid ?: return

        val mealsForDay = when (day.value.lowercase()) {
            "monday" -> monday.value
            "tuesday" -> tuesday.value
            "wednesday" -> wednesday.value
            "thursday" -> thursday.value
            "friday" -> friday.value
            "saturday" -> saturday.value
            "sunday" -> sunday.value
            else -> emptyList()
        }

        viewModelScope.launch {
            val fieldPath = "weeklyDiet.${day.value.lowercase()}"
            val validMeals = mealsForDay.filter { it.food.isNotBlank() && it.time.isNotBlank()}

            val dayDiet = userDataUseCase.updateUser(uID = currentUserId, field = fieldPath, value = validMeals)

            dayDiet.onSuccess { _, _ ->
                getUserData()
                _userState.update { UserState.Success }
                _events.emit(Events.Success("Diet plan updated for ${day.value.lowercase()}! \uD83D\uDE01"))
            }.onError { error, message ->
                _userState.update { UserState.Error }
                _events.emit(Events.Error(error, message))
            }
        }
    }

    fun getUserData(){
        _userState.update { UserState.Loading }
        val currentUserId = firebaseAuth.currentUser?.uid ?: return
        viewModelScope.launch {
            val userData = userDataUseCase.getUserById(currentUserId)
            userData.onSuccess { user, _ ->
                _userState.update { UserState.Success }
                if(user != null){
                    _monday.update { ArrayList(user.weeklyDiet.monday)}
                    _tuesday.update { ArrayList(user.weeklyDiet.tuesday)}
                    _wednesday.update { ArrayList(user.weeklyDiet.wednesday)}
                    _thursday.update { ArrayList(user.weeklyDiet.thursday)}
                    _friday.update { ArrayList(user.weeklyDiet.friday)}
                    _saturday.update { ArrayList(user.weeklyDiet.saturday)}
                    _sunday.update { ArrayList(user.weeklyDiet.sunday)}
                }

            }.onError { error, message ->
                _userState.update { UserState.Error }
                _events.emit(Events.Error(error, message ?: "Failed to update the information for the user."))
            }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            signInWithGoogleUseCase.signOut().onSuccess { _, _ ->
                preferenceManager.saveUserStatus(loggedIn = false)
                _events.emit(Events.LoggedOut("Logged Out!"))
            }
        }
    }

}
