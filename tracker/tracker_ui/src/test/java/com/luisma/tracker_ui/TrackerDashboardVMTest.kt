package com.luisma.tracker_ui

import app.cash.turbine.test
import com.luisma.core.models.UserProfile
import com.luisma.core.models.food.*
import com.luisma.core.services.TimeHelperService
import com.luisma.core.services.database_service.IFoodDataBaseService
import com.luisma.core.services.preferences_service.IPreferencesService
import com.luisma.core_test.TestCoroutineDispatcherRule
import com.luisma.core_ui.services.GoBackNavigationCommand
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import com.luisma.tracker_domain.use_cases.MealCalculatorUseCase
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboardEvents
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboardModel
import com.luisma.tracker_ui.pages.tracker_dashboard.TrackerDashboardVM
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate


@ExperimentalCoroutinesApi
class TrackerDashboardVMTest {

    private var mealCalculatorUseCaseMock: MealCalculatorUseCase = mockk()
    private var foodDataBaseServiceMock: IFoodDataBaseService = mockk()
    private var timeHelpersServiceMock: TimeHelperService = mockk()
    private var navigationServiceMock: NavigationService = mockk()
    private var preferenceServiceMock: IPreferencesService = mockk()
    private var startDateMock: LocalDate = LocalDate.now()
    private val mainDispatcherRule = TestCoroutineDispatcherRule()

    private fun getVM(
        mealCalculatorUseCase: MealCalculatorUseCase? = null,
        foodDataBaseService: IFoodDataBaseService? = null,
        timeHelpersService: TimeHelperService? = null,
        navigationService: NavigationService? = null,
        preferencesService: IPreferencesService? = null,
        initialState: TrackerDashboardModel? = null,
    ): TrackerDashboardVM {
        return TrackerDashboardVM(
            mealCalculatorUseCase = mealCalculatorUseCase ?: mealCalculatorUseCaseMock,
            foodDataBaseService = foodDataBaseService ?: foodDataBaseServiceMock,
            timeHelpersService = timeHelpersService ?: timeHelpersServiceMock,
            navigationService = navigationService ?: navigationServiceMock,
            mainDispatcher = mainDispatcherRule.testDispatcher,
            userPrefService = preferencesService ?: preferenceServiceMock,
            initialState = initialState ?: TrackerDashboardModel.initial()
        )
    }


    @Test
    fun `when initialized, the state current day should be the initialized (now)`() {
        val vm = getVM()
        assertEquals(startDateMock, vm.state.currentDate)
    }

    @Test
    fun `when initialized, should get the initialized date (now) food data from the database and update the state`() =
        runTest {
            //arrange
            val mockDbService = mockk<IFoodDataBaseService>()
            val mockPrefService = mockk<IPreferencesService>()
            val mockCalculator = mockk<MealCalculatorUseCase>()
            val mealInDb = MealsByDate.initial()
            val userProfile = UserProfile(height = 10)
            val calculatorResult = mealInDb.copy(caloriesGoal = 20)
            coEvery {
                mockDbService.getFoodFrom(startDateMock)
            } returns mealInDb
            coEvery {
                mockCalculator.invoke(
                    mealsByDateData = any(),
                    userProfile = any()
                )
            } returns calculatorResult
            coEvery {
                mockPrefService.getUserProfile()
            } returns flowOf(userProfile)
            //act
            val vm = getVM(
                foodDataBaseService = mockDbService,
                preferencesService = mockPrefService,
                mealCalculatorUseCase = mockCalculator
            )
            //assert
            coVerify { mockDbService.getFoodFrom(startDateMock) }
            coVerify { mockPrefService.getUserProfile() }
            coVerify {
                mockCalculator.invoke(
                    mealsByDateData = mealInDb,
                    userProfile = userProfile
                )
            }
            assertEquals(vm.state.currentFood, calculatorResult)
        }

    @Test
    fun `events - NextPreviousDay - when called to the right should get the next day and update the state`() =
        runTest {
            //arrange
            val mockTimer = mockk<TimeHelperService>()
            val startDate = LocalDate.of(2022, 12, 22)
            val nextDate = LocalDate.of(2022, 12, 23)
            val initialState = TrackerDashboardModel.initial().copy(
                currentDate = startDate
            )
            every { mockTimer.nextDay(startDate) } returns nextDate
            //act
            val vm = getVM(
                timeHelpersService = mockTimer,
                initialState = initialState
            )
            vm.eventDispatcher(TrackerDashboardEvents.NextPreviousDay(isNext = true))
            //assert
            verify { mockTimer.nextDay(startDate) }
            assertEquals(vm.state.currentDate, nextDate)
        }

    @Test
    fun `events - NextPreviousDay - when called to the left should get the previous day and update the state`() =
        runTest {
            //arrange
            val mockTimer = mockk<TimeHelperService>()
            val startDate = LocalDate.of(2022, 12, 22)
            val previous = LocalDate.of(2022, 12, 21)
            val initialState = TrackerDashboardModel.initial().copy(
                currentDate = startDate
            )
            every { mockTimer.previousDay(startDate) } returns previous
            //act
            val vm = getVM(
                timeHelpersService = mockTimer,
                initialState = initialState
            )
            vm.eventDispatcher(TrackerDashboardEvents.NextPreviousDay(isNext = false))
            //assert
            verify { mockTimer.previousDay(startDate) }
            assertEquals(vm.state.currentDate, previous)
        }

    @Test
    fun `events - AddMeal - when called should go to the search food screen`() = runTest {
        //arrange
        val mealType = MealType.Breakfast
        //act
        val vm = getVM()
        vm.eventDispatcher(TrackerDashboardEvents.AddFood(mealType))
        //assert
        coVerify {
            navigationServiceMock.setNavigationCommand(
                NavigationCommands.TrackerSearchFoodFromTrackerDashboard(foodTypeSerialize(mealType))
            )
        }
    }

    @Test
    fun `events - when meal selected from SearchFood should update the db and the state`() =
        runTest {
            //arrange
            val returnNavigationFlow =
                MutableSharedFlow<GoBackNavigationCommand>()
            val navigationService = NavigationService(
                navigationFlow = MutableSharedFlow(),
                returnNavigationFlow = returnNavigationFlow
            )
            val userProfile = UserProfile(height = 10)
            val initialMealsByDate = MealsByDate.initial().copy(
                date = LocalDate.now()
            )
            val dinner = ProductsFood(name = "bread")
            val withDinner = initialMealsByDate.copy(
                meals = initialMealsByDate.meals.map { track ->
                    if (track.mealType == MealType.Dinner) {
                        MealTrack(
                            mealType = MealType.Dinner,
                            productsFood = listOf(dinner)
                        )
                    } else {
                        track
                    }
                }
            )
            val withDinnerCalculated = withDinner.copy(caloriesGoal = 10)
            val dinnerCommand =
                GoBackNavigationCommand.TrackerFromSearchToDashboard(
                    mealType = MealType.Dinner,
                    food = dinner
                )
            val mockFoodDB = spyk<IFoodDataBaseService>()
            val mockPref = mockk<IPreferencesService>()
            val mockCalculator = mockk<MealCalculatorUseCase>()
            coEvery {
                mockFoodDB.getFoodFrom(startDateMock)
            } returns initialMealsByDate andThen withDinner
            coEvery {
                mockCalculator.invoke(
                    userProfile = any(),
                    mealsByDateData = any()
                )
            } returns initialMealsByDate andThen withDinnerCalculated
            coEvery {
                mockPref.getUserProfile()
            } returns flowOf(userProfile)
            //act
            val vm = getVM(
                navigationService = navigationService,
                foodDataBaseService = mockFoodDB,
                mealCalculatorUseCase = mockCalculator,
                preferencesService = mockPref,
            )
            returnNavigationFlow.emit(dinnerCommand)
            //assert
            returnNavigationFlow.test {
                coVerify { mockFoodDB.setFood(withDinner) }
                coVerify { mockFoodDB.getFoodFrom(startDateMock) }
                coVerify {
                    mockCalculator.invoke(
                        mealsByDateData = withDinner,
                        userProfile = userProfile
                    )
                }
                assertEquals(vm.state.currentFood, withDinnerCalculated)
            }
        }

    @Test
    fun `events - DeleteFood - when called should remove the selected food, update the db and the state`() =
        runTest {
            //arrange
            val userProfile = UserProfile(height = 10)
            val initialMeals = MealsByDate.initial().copy(
                caloriesGoal = 10,
                meals = MealsByDate.initial().meals.map { track ->
                    if (track.mealType == MealType.Dinner) {
                        MealTrack(
                            mealType = MealType.Dinner,
                            productsFood = listOf(ProductsFood(name = "bread"))
                        )
                    } else {
                        track
                    }
                }
            )
            val deletedProduct = MealsByDate.initial().copy(caloriesGoal = 10)
            val deletedProductCalculated = MealsByDate.initial()
            val mockFoodDB = spyk<IFoodDataBaseService>()
            val mockPref = mockk<IPreferencesService>()
            val mockCalculator = mockk<MealCalculatorUseCase>()
            coEvery {
                mockFoodDB.getFoodFrom(startDateMock)
            } returns initialMeals andThen deletedProduct
            coEvery {
                mockCalculator.invoke(
                    userProfile = any(),
                    mealsByDateData = any()
                )
            } returns initialMeals andThen deletedProductCalculated
            coEvery {
                mockPref.getUserProfile()
            } returns flowOf(userProfile)
            //act
            val vm = getVM(
                foodDataBaseService = mockFoodDB,
                mealCalculatorUseCase = mockCalculator,
                preferencesService = mockPref
            )
            vm.eventDispatcher(
                TrackerDashboardEvents.DeleteFood(mealType = MealType.Dinner, index = 0)
            )
            //asset
            coVerify { mockFoodDB.setFood(deletedProduct) }
            coVerify { mockFoodDB.getFoodFrom(startDateMock) }
            coVerify {
                mockCalculator.invoke(
                    mealsByDateData = deletedProduct,
                    userProfile = userProfile
                )
            }
            assertEquals(vm.state.currentFood, deletedProductCalculated)
        }

    @Test
    fun `events - ExpandFood - when called should expand the selected food`() = runTest {
        val initialMeals = MealsByDate.initial(

        )
        //arrange
        val initialState = TrackerDashboardModel.initial().copy(
            currentFood = MealsByDate.initial()
        )
        //act
        val vm = getVM(initialState = initialState)
        vm.eventDispatcher(TrackerDashboardEvents.ExpandFood(mealType = MealType.Breakfast))
        vm.eventDispatcher(TrackerDashboardEvents.ExpandFood(mealType = MealType.Lunch))
        vm.eventDispatcher(TrackerDashboardEvents.ExpandFood(mealType = MealType.Dinner))
        vm.eventDispatcher(TrackerDashboardEvents.ExpandFood(mealType = MealType.Snacks))
        //assert
        assertEquals(vm.state.expandedMeal, MealType.values().associateWith { true })
    }
}

