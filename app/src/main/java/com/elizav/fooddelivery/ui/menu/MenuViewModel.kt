package com.elizav.fooddelivery.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elizav.fooddelivery.domain.interactors.MealsInteractor
import com.elizav.fooddelivery.domain.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val mealsInteractor: MealsInteractor
) : ViewModel() {
    private var _meals: MutableLiveData<Result<List<Meal>>> = MutableLiveData()
    val meals: LiveData<Result<List<Meal>>> = _meals

    init {
        getMeals()
    }

    fun getMeals() = viewModelScope.launch {
        mealsInteractor.getMealsForQuery(query = QUERY_PIZZA).fold(
            onSuccess = { meals ->
                _meals.value = Result.success(meals)
            },
            onFailure = { _meals.value = Result.failure(it) }
        )
    }

    companion object {
        const val QUERY_PIZZA = "pizza"
    }
}