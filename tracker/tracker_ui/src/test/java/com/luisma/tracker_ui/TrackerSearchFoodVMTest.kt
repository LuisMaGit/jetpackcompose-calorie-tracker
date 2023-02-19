package com.luisma.tracker_ui

import androidx.lifecycle.SavedStateHandle
import com.luisma.core.models.BaseResponse
import com.luisma.core.services.pagination_service.PaginationState
import com.luisma.core.models.food.Food
import com.luisma.core.models.food.MealType
import com.luisma.core.models.food.ProductsFood
import com.luisma.core.services.StringHelperService
import com.luisma.core.services.network_service.IFoodNetworkService
import com.luisma.core.services.pagination_service.PaginationService
import com.luisma.core_test.TestCoroutineDispatcherRule
import com.luisma.core_ui.base.BaseUIState
import com.luisma.core_ui.services.GoBackNavigationCommand
import com.luisma.core_ui.services.NavigationCommands
import com.luisma.core_ui.services.NavigationService
import com.luisma.tracker_ui.pages.tracker_search_food.TrackerSearchFoodEvents
import com.luisma.tracker_ui.pages.tracker_search_food.TrackerSearchFoodModel
import com.luisma.tracker_ui.pages.tracker_search_food.TrackerSearchFoodVM
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class TrackerSearchFoodVMTest {

    private val navigationMockService: NavigationService = mockk()
    private val foodNetworkMockService: IFoodNetworkService = mockk()
    private val paginationMockService: PaginationService = mockk()
    private val stringHelperMockService: StringHelperService = spyk()
    private val savedStateMockHandle: SavedStateHandle = spyk()
    private val mainDispatcherRule = TestCoroutineDispatcherRule()

    private fun getVM(
        navigationService: NavigationService? = null,
        foodNetworkService: IFoodNetworkService? = null,
        stringHelperService: StringHelperService? = null,
        savedStateHandle: SavedStateHandle? = null,
        initialState: TrackerSearchFoodModel? = null,
        paginationService: PaginationService? = null,
    ): TrackerSearchFoodVM {
        return TrackerSearchFoodVM(
            navigationService = navigationService ?: navigationMockService,
            foodNetworkService = foodNetworkService ?: foodNetworkMockService,
            stringHelperService = stringHelperService ?: stringHelperMockService,
            savedStateHandle = savedStateHandle ?: savedStateMockHandle,
            mainDispatcher = mainDispatcherRule.testDispatcher,
            initialState = initialState ?: TrackerSearchFoodModel.initial(),
            paginationService = paginationService ?: paginationMockService
        )
    }

    private fun getFoodNetwork(
        productsSize: Int = 10,
        paginationCount: Int = 30,
    ): Food {
        return Food(
            count = paginationCount,
            products = List(size = productsSize) { count ->
                ProductsFood(
                    name = "product $count"
                )
            }
        )
    }

    @Test
    fun `when initialized - the state should have the type of food from the previous screen`() {
        //arrange
        val mealType = MealType.Lunch
        val mockSavedState = mockk<SavedStateHandle>()
        every {
            mockSavedState.get<String>(NavigationCommands.TrackerSearchFood.key)
        } returns mealType.name
        //act
        val vm = getVM(savedStateHandle = mockSavedState)
        //assert
        assertEquals(vm.state.mealType, mealType)
    }

    @Test
    fun `when initialized - if the type of food is not correct the state should have the default one (Breakfast)`() {
        //arrange
        val mockSavedState = mockk<SavedStateHandle>()
        every {
            mockSavedState.get<String>(NavigationCommands.TrackerSearchFood.key)
        } returns "Incorrect"
        //act
        val vm = getVM(savedStateHandle = mockSavedState)
        //assert
        assertEquals(vm.state.mealType, MealType.Breakfast)
    }

    @Test
    fun `when initialized - the ui state should be Initial`() {
        //arrange
        val vm = getVM()
        //assert
        assertEquals(vm.state.uiState, BaseUIState.Initial)
    }

    @Test
    fun `events - SetSearch - should save the search in the state`() {
        //arrange
        val search = "apple"
        val vm = getVM()
        //act
        vm.eventDispatcher(TrackerSearchFoodEvents.SetSearch(search = search))
        //assert
        assertEquals(vm.state.search, search)
    }

    @Test
    fun `events - SubmitSearch - should reset the pagination, put the ui state as Loading, keep the search and call the network`() =
        runTest {
            //arrange
            val search = "apple"
            val mockNetwork = spyk<IFoodNetworkService>()
            val initialPagination = PaginationState.initial()
            val nextPage = initialPagination.nextPage
            val vm = getVM(foodNetworkService = mockNetwork)
            //act
            vm.eventDispatcher(TrackerSearchFoodEvents.SetSearch(search = search))
            vm.eventDispatcher(TrackerSearchFoodEvents.SubmitSearch)
            //assert
            coVerify {
                mockNetwork.searchFood(
                    search = vm.state.search,
                    page = nextPage,
                    pageSize = vm.state.pageSize
                )
            }
            assertEquals(vm.state.uiState, BaseUIState.Loading)
            assertEquals(vm.state.paginationState, initialPagination)
            assertEquals(vm.state.search, search)
        }

    @Test
    fun `events - SubmitSearch - when the response is error the ui state should be error`() =
        runTest {
            //arrange
            val mockNetwork = spyk<IFoodNetworkService>()
            val initialState = TrackerSearchFoodModel.initial()
            coEvery {
                mockNetwork.searchFood(
                    search = initialState.search,
                    page = 1,
                    pageSize = initialState.pageSize
                )
            } returns BaseResponse.Failure()
            val vm = getVM(foodNetworkService = mockNetwork)
            //act
            vm.eventDispatcher(TrackerSearchFoodEvents.SubmitSearch)
            //assert
            assertEquals(vm.state.uiState, BaseUIState.Error)
        }

    @Test
    fun `events - SubmitSearch - when the response is successful and the data is empty the ui state should be Empty and not run the pagination`() =
        runTest {
            //arrange
            val mockNetwork = spyk<IFoodNetworkService>()
            val initialState = TrackerSearchFoodModel.initial()
            coEvery {
                mockNetwork.searchFood(
                    search = initialState.search,
                    page = 1,
                    pageSize = initialState.pageSize
                )
            } returns BaseResponse.Success(data = Food())
            val vm = getVM(foodNetworkService = mockNetwork)
            //act
            vm.eventDispatcher(TrackerSearchFoodEvents.SubmitSearch)
            //assert
            assertEquals(vm.state.uiState, BaseUIState.Empty)
            coVerify(exactly = 0) {
                paginationMockService.runPagination(
                    state = initialState.paginationState,
                    lastPage = 0,
                    lastIndex = 0
                )
            }
        }

    @Test
    fun `events - SubmitSearch - when the response is successful should update the state and run the pagination, `() =
        runTest {
            //arrange
            val mockNetwork = spyk<IFoodNetworkService>()
            val mockPagination = spyk<PaginationService>()
            val initialState = TrackerSearchFoodModel.initial()
            val foodNetwork = getFoodNetwork(paginationCount = 10)
            coEvery {
                mockNetwork.searchFood(
                    search = initialState.search,
                    page = 1,
                    pageSize = initialState.pageSize
                )
            } returns BaseResponse.Success(data = foodNetwork)
            val vm = getVM(
                foodNetworkService = mockNetwork,
                paginationService = mockPagination
            )
            //act
            vm.eventDispatcher(TrackerSearchFoodEvents.SubmitSearch)
            //assert
            coVerify {
                mockPagination.runPagination(
                    state = initialState.paginationState,
                    lastIndex = foodNetwork.products.lastIndex,
                    lastPage = foodNetwork.count / initialState.pageSize
                )
            }
            assertEquals(vm.state.uiState, BaseUIState.Success)
            assertEquals(vm.state.products, foodNetwork.products)
            assertEquals(vm.state.paginationState.withLoading(), true)
        }

    @Test
    fun `events - PaginationTrigger - when the pagination service returns null should never call for the next page`() =
        runTest {
            //arrange
            val mockNetwork = spyk<IFoodNetworkService>()
            val mockPagination = mockk<PaginationService>()
            val initialState = TrackerSearchFoodModel.initial()
            val foodNetwork = getFoodNetwork()
            coEvery {
                mockNetwork.searchFood(
                    search = initialState.search,
                    page = 1,
                    pageSize = initialState.pageSize
                )
            } returns BaseResponse.Success(data = foodNetwork)
            coEvery {
                mockPagination.shouldRequestOtherPage(
                    state = initialState.paginationState,
                    index = 0,
                )
            } returns null
            val vm = getVM(
                foodNetworkService = mockNetwork,
                paginationService = mockPagination
            )
            //act
            vm.eventDispatcher(TrackerSearchFoodEvents.SubmitSearch)
            vm.eventDispatcher(TrackerSearchFoodEvents.PaginationTrigger(productIndex = 0))
            //assert
            coVerify(exactly = 1) {
                mockNetwork.searchFood(
                    search = vm.state.search,
                    pageSize = vm.state.pageSize,
                    page = 1
                )
            }
        }

    @Test
    fun `events - PaginationTrigger - when the pagination service returns a new state should append the next product data`() =
        runTest {
            //arrange
            val mockNetwork = spyk<IFoodNetworkService>()
            val mockPagination = spyk<PaginationService>()
            val foodNetwork = getFoodNetwork(productsSize = 1)
            val foodNetwork2 = getFoodNetwork(productsSize = 2)
            val initialStateWithFood = TrackerSearchFoodModel.initial().copy(
                products = foodNetwork.products
            )
            val triggerIndex = foodNetwork.products.count()
            val paginationStateWithLastIndex = initialStateWithFood.paginationState.copy(
                lastIndex = triggerIndex
            )
            val paginationStateAfterResponse = paginationStateWithLastIndex.copy(
                lastPage = 20,
            )
            coEvery {
                mockPagination.shouldRequestOtherPage(
                    state = initialStateWithFood.paginationState,
                    index = triggerIndex
                )
            } returns paginationStateWithLastIndex
            coEvery {
                mockNetwork.searchFood(
                    search = initialStateWithFood.search,
                    page = initialStateWithFood.paginationState.nextPage,
                    pageSize = initialStateWithFood.pageSize
                )
            } returns BaseResponse.Success(data = foodNetwork2)
            coEvery {
                mockPagination.runPagination(
                    lastPage = foodNetwork2.count / initialStateWithFood.pageSize,
                    lastIndex = foodNetwork2.products.lastIndex,
                    state = paginationStateWithLastIndex
                )
            } returns paginationStateAfterResponse
            //act
            val vm = getVM(
                foodNetworkService = mockNetwork,
                paginationService = mockPagination,
                initialState = initialStateWithFood
            )
            //assert
            vm.eventDispatcher(
                TrackerSearchFoodEvents.PaginationTrigger(triggerIndex)
            )
            assertEquals(vm.state.uiState, BaseUIState.Success)
            assertEquals(vm.state.products, foodNetwork.products + foodNetwork2.products)
            assertEquals(vm.state.paginationState, paginationStateAfterResponse)
        }

    @Test
    fun `events - OnTapProduct - when called should go back and send the product to the previous page`() =
        runTest {
            //arrange
            val mockNavigation = mockk<NavigationService>(relaxed = true)
            val foodNetwork = getFoodNetwork()
            val initialState = TrackerSearchFoodModel.initial().copy(
                products = foodNetwork.products
            )
            val productIndex = 5
            val vm = getVM(
                navigationService = mockNavigation,
                initialState = initialState
            )
            //act
            vm.eventDispatcher(TrackerSearchFoodEvents.OnSubmitProduct(productIndex = productIndex))
            //assert
            coVerify {
                mockNavigation.setNavigationCommand(NavigationCommands.GoBack)
            }
            coVerify {
                mockNavigation.setGoBackNavigationCommand(
                    command = GoBackNavigationCommand.TrackerFromSearchToDashboard(
                        mealType = vm.state.mealType,
                        food = vm.state.products[productIndex]
                    ),
                )
            }
        }

    @Test
    fun `events - SetGramsConsumed - should set the grams in the state`() =
        runTest {
            //arrange
            val foodNetwork = getFoodNetwork()
            val initialState = TrackerSearchFoodModel.initial().copy(
                products = foodNetwork.products
            )
            val productIndex = 5
            val gramsConsumed = 500
            val productsWithGrams = foodNetwork.products.mapIndexed { index, productsFood ->
                if (index == productIndex)
                    productsFood.copy(gramsConsumedByUser = gramsConsumed)
                else
                    productsFood
            }
            val vm = getVM(initialState = initialState)
            //act
            vm.eventDispatcher(
                TrackerSearchFoodEvents.SetGramsConsumed(
                    productIndex = productIndex,
                    gramsConsumed = gramsConsumed.toString()
                )
            )
            //assert
            assertEquals(vm.state.products, productsWithGrams)
        }

    @Test
    fun `events - ExpandProduct - should expand or collapse the selected product`() =
        runTest {
            //arrange
            val foodNetwork = getFoodNetwork()
            val initialState = TrackerSearchFoodModel.initial().copy(
                products = foodNetwork.products
            )
            val productIndex = 5
            val productsWithOneExpanded = foodNetwork.products.mapIndexed { index, productsFood ->
                if (index == productIndex)
                    productsFood.copy(expanded = true)
                else
                    productsFood
            }
            val vm = getVM(initialState = initialState)
            //act
            vm.eventDispatcher(
                TrackerSearchFoodEvents.ExpandProduct(
                    productIndex = productIndex,
                )
            )
            //assert
            assertEquals(vm.state.products, productsWithOneExpanded)
        }

}